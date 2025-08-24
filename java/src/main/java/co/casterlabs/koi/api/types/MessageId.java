package co.casterlabs.koi.api.types;

import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.UUID;

import org.jetbrains.annotations.ApiStatus.Internal;

import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NonNull;
import lombok.SneakyThrows;

/**
 * Used internally to map replies and rooms and actions.
 */
@Internal
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class MessageId {
    public final @NonNull SimpleProfile sender;

    public final @NonNull RoomId roomId;

    /**
     * @implNote Not all platforms have an ID for a message. In that case, this will
     *           probably be a random string to ensure the data appears sane.
     */
    @JsonField("true_id")
    public final @NonNull String trueId;

    public static MessageId of(@NonNull SimpleProfile sender, @NonNull RoomId roomId, @NonNull String trueId) {
        return new MessageId(sender, roomId, trueId);
    }

    public static MessageId random(@NonNull SimpleProfile sender, @NonNull RoomId roomId) {
        return of(sender, roomId, UUID.randomUUID().toString());
    }

    @Deprecated
    public String serialize() {
        byte[] json = Rson.DEFAULT
            .toJson(this)
            .toString()
            .getBytes(StandardCharsets.UTF_8);

        return Base64
            .getEncoder()
            .encodeToString(json);
    }

    @Deprecated
    @SneakyThrows
    public static MessageId deserialize(String base64) {
        String json = new String(
            Base64
                .getDecoder()
                .decode(base64),
            StandardCharsets.UTF_8
        );

        return Rson.DEFAULT.fromJson(json, MessageId.class);
    }

}
