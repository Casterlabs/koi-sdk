package co.casterlabs.koi.api.types;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

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
@Deprecated
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class MessageId {
    public final @NonNull SimpleProfile streamer;
    public final @NonNull SimpleProfile sender;

    /**
     * @implNote Not all platforms have an ID for a message. In that case, this will
     *           probably be a random string to ensure the data appears sane.
     */
    @JsonField("true_id")
    public final @NonNull String trueId;

    public static MessageId of(@NonNull SimpleProfile streamer, @NonNull SimpleProfile sender, @NonNull String trueId) {
        return new MessageId(streamer, sender, trueId);
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
