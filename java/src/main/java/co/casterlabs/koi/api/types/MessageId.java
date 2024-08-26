package co.casterlabs.koi.api.types;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

/**
 * Used internally to map replies and rooms and actions.
 */
@Getter
@Deprecated
@JsonClass(exposeAll = true, unsafeInstantiation = true)
@SuperBuilder(toBuilder = true)
public class MessageId {
    @JsonField("room_id")
    public final String roomId;

    @JsonField("true_id")
    public final String trueId;

    public final SimpleProfile sender;
    public final SimpleProfile streamer;

    public String serialize() {
        byte[] json = Rson.DEFAULT
            .toJson(this)
            .toString()
            .getBytes(StandardCharsets.UTF_8);

        return Base64
            .getEncoder()
            .encodeToString(json);
    }

    public static MessageId deserialize(String base64) throws JsonValidationException, JsonParseException {
        String json = new String(
            Base64
                .getDecoder()
                .decode(base64),
            StandardCharsets.UTF_8
        );

        return Rson.DEFAULT.fromJson(json, MessageId.class);
    }

}
