package co.casterlabs.koi.api;

import java.io.Closeable;
import java.io.IOException;
import java.net.URI;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.listener.KoiEventUtil;
import co.casterlabs.koi.api.listener.KoiLifeCycleHandler;
import co.casterlabs.koi.api.types.events.KoiEvent;
import co.casterlabs.koi.api.types.events.KoiEventType;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.TypeResolver;
import co.casterlabs.rakurai.json.TypeToken;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.Getter;
import lombok.NonNull;
import lombok.SneakyThrows;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;

public class KoiConnection implements Closeable {
    public static final String KOI_URL = "wss://api.casterlabs.co/v2/koi";
    public static final Rson RSON = new Rson.Builder()
        .registerTypeResolver(new TypeResolver<Instant>() {
            @Override
            public @Nullable Instant resolve(@NonNull JsonElement value, @NonNull Class<?> type) {
                return Instant.parse(value.getAsString());
            }

            @Override
            public @Nullable JsonElement writeOut(@NonNull Instant value, @NonNull Class<?> type) {
                return new JsonString(value.toString());
            }
        }, Instant.class)
        .build();

    private static int idx = 0;

    private KoiLifeCycleHandler listener;
    private @Getter FastLogger logger;

    private KoiSocket socket;
    private URI uri;

    public KoiConnection(@NonNull String url, @NonNull KoiLifeCycleHandler listener, @NonNull String clientId) {
        this(url, new FastLogger("Koi Connection #" + ++idx), listener, clientId);
    }

    @SneakyThrows
    public KoiConnection(@NonNull String url, @NonNull FastLogger logger, @NonNull KoiLifeCycleHandler listener, @NonNull String clientId) {
        this.logger = logger;
        this.listener = listener;
        this.uri = new URI(url + "?client_id=" + clientId);
    }

    @Override
    public void close() {
        if (this.isConnected()) {
            this.socket.close();
        }
    }

    public boolean isConnected() {
        return this.socket != null;
    }

//    public KoiConnection hookStreamStatus(String username, UserPlatform platform) throws InterruptedException, IOException {
//        if (this.isConnected()) {
//            throw new IllegalStateException("Already connected.");
//        }
//
//        JsonObject request = new JsonObject()
//            .put("type", "USER_STREAM_STATUS")
//            .put("username", username)
//            .put("platform", platform.name())
//            .put("nonce", "_login");
//
//        try {
//            this.socket = new KoiSocket(this.uri, request);
//            this.socket.doConnect();
//        } catch (InterruptedException | IOException e) {
//            socket = null;
//            throw e;
//        }
//
//        return this;
//    }

    public KoiConnection login(String token) throws InterruptedException, IOException {
        if (this.isConnected()) {
            throw new IllegalStateException("Already connected.");
        }

        JsonObject request = new JsonObject()
            .put("type", "LOGIN")
            .put("token", token)
            .put("nonce", "_login");

        try {
            this.socket = new KoiSocket(this.uri, request);
            this.socket.doConnect();
        } catch (InterruptedException | IOException e) {
            socket = null;
            throw e;
        }

        return this;
    }

//    public KoiConnection loginPuppet(String token) {
//        if (!this.isConnected()) {
//            throw new IllegalStateException("You need to be connected.");
//        }
//
//        JsonObject request = new JsonObject()
//            .put("type", "PUPPET_LOGIN")
//            .put("token", token)
//            .put("nonce", "_puppetlogin");
//
//        this.socket.send(request.toString());
//
//        return this;
//    }

    private class KoiSocket extends WebSocketClient {
        private final JsonObject loginRequest;

        public KoiSocket(URI uri, JsonObject loginRequest) {
            super(uri);
            this.loginRequest = loginRequest;

            this.setConnectionLostTimeout(60 /* Seconds */);
            this.addHeader("User-Agent", "Casterlabs Koi-SDK");
            this.setTcpNoDelay(true);
        }

        public void doConnect() throws InterruptedException, IOException {
            logger.info("Connecting to Koi...");
            boolean success = super.connectBlocking();
            if (!success) throw new IOException("Failed to connect.");
        }

        @Override
        public void onOpen(ServerHandshake handshakedata) {
            logger.info("Connected to Koi, waiting for welcome.");
        }

        @Override
        public void send(String text) {
            logger.debug("\u2191 " + text);

            super.send(text);
        }

        private void keepAlive(JsonElement nonce) {
            JsonObject request = new JsonObject();

            request.put("type", "KEEP_ALIVE");
            request.put("nonce", nonce);

            this.send(request.toString());
        }

        @Override
        public void onMessage(String raw) {
            logger.debug("\u2193 " + raw);

            try {
                JsonObject packet = KoiConnection.RSON.fromJson(raw, JsonObject.class);

                switch (packet.getString("type")) {
                    case "WELCOME": {
                        logger.info("Got welcome: %s", packet);
                        this.send(this.loginRequest.toString());
                        listener.onOpen();
                        return;
                    }

                    case "KEEP_ALIVE": {
                        this.keepAlive(packet.get("nonce"));
                        return;
                    }

                    case "INTEGRATION_FEATURES": {
                        listener.onSupportedFeatures(
                            Rson.DEFAULT.fromJson(
                                packet.get("features"),
                                new TypeToken<List<KoiIntegrationFeatures>>() {
                                }
                            )
                        );
                        return;
                    }

                    case "SERVER": {
                        listener.onServerMessage(packet.get("server").getAsString());
                        return;
                    }

                    case "ERROR": {
                        listener.onError(packet.get("error").getAsString());
                        return;
                    }

                    case "EVENT": {
                        JsonObject eventJson = packet.getObject("event");
                        KoiEvent event = KoiEventType.get(eventJson);

                        if (event == null) {
                            logger.warn("Unsupported event type: %s", eventJson.getString("event_type"));
                        }

                        KoiEventUtil.reflectInvoke(listener, event);
                        return;
                    }

                    case "CLIENT_SCOPES":
                        listener.onClientScopes(
                            Rson.DEFAULT.fromJson(
                                packet.get("scopes"),
                                new TypeToken<List<String>>() {
                                }
                            )
                        );
                        return;

                    case "RECONNECT_SOON": {
                        logger.info("Received the signal to reconnect, waiting a random amount of time and disconnecting.");
                        new Thread(() -> {
                            try {
                                Thread.sleep(ThreadLocalRandom.current().nextLong(1000, 15000));
                            } catch (InterruptedException e) {}
                            this.close();
                        }).start();
                        return;
                    }

                    default:
                        logger.debug("Unknown message type: %s\n%s", packet.getString("type"), packet);
                        return;
                }
            } catch (Exception e) {
                logger.severe("An error occurred whilst handling %s:\n%s", raw, e);
                listener.onException(e);
            }
        }

        @Override
        public void onClose(int code, String reason, boolean remote) {
            socket = null;

            if (remote) {
                logger.info("Lost connection to Koi.");
            } else {
                logger.info("Disconnected from Koi. (%s)", reason);
            }

            this.fireCloseEvent(remote);
        }

        @Override
        public void onError(Exception e) {
            if (e instanceof IOException) {
                logger.info("Connection to Koi failed.");
                this.fireCloseEvent(true);
            } else {
                logger.exception(e);
            }
        }

        private void fireCloseEvent(boolean remote) {
            if (listener != null) {
                // So the user can immediately reconnect without
                // errors from the underlying library.
                new Thread(() -> listener.onClose(remote)).start();
            }
        }

    }

    public void sendChat(@NonNull String message, @NonNull KoiChatterType chatter, @Nullable String replyTarget, boolean isUserGesture) {
        this.socket.send(
            new JsonObject()
                .put("type", "CHAT")
                .put("message", message)
                .put("chatter", chatter.name())
                .put("reply_target", replyTarget)
                .put("is_user_gesture", isUserGesture)
                .toString()
        );
    }

    public void upvoteChat(@NonNull String messageId) {
        this.socket.send(
            new JsonObject()
                .put("type", "UPVOTE")
                .put("message_id", messageId)
                .toString()
        );
    }

    public void deleteChat(@NonNull String messageId, boolean isUserGesture) {
        this.socket.send(
            new JsonObject()
                .put("type", "DELETE")
                .put("message_id", messageId)
                .put("is_user_gesture", isUserGesture)
                .toString()
        );
    }

}
