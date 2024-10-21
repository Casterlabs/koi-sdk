package co.casterlabs.koi.api.types;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

/**
 * Used internally to map rooms and connections.
 */
@Deprecated
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class RoomId {
    public final @NonNull SimpleProfile streamer;

    @JsonField("true_id")
    public final @NonNull String trueId;

    @JsonField("link")
    public final @NonNull String link;

    /**
     * @implNote Not all platforms expose a name, so this field might be `null`.
     *           Additionally, certain platforms allow merging chats from two
     *           different accounts into one, in that case the chat owned by the
     *           current streamer will always have a `null` name value.
     */
    @JsonField("name")
    public final @Nullable String name;

    public static RoomId of(@NonNull SimpleProfile streamer, @NonNull String trueId, @NonNull String link, @Nullable String name) {
        return new RoomId(streamer, trueId, link, name);
    }

    public static RoomId of(@NonNull SimpleProfile streamer, @NonNull String link) {
        return of(streamer, streamer.channelId, link, null);
    }

    public String serialize() {
        byte[] json = Rson.DEFAULT
            .toJson(this)
            .toString()
            .getBytes(StandardCharsets.UTF_8);

        return Base64
            .getEncoder()
            .encodeToString(json);
    }

    @SneakyThrows
    public static RoomId deserialize(String base64) {
        String json = new String(
            Base64
                .getDecoder()
                .decode(base64),
            StandardCharsets.UTF_8
        );

        return Rson.DEFAULT.fromJson(json, RoomId.class);
    }

}
