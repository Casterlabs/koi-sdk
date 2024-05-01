package co.casterlabs.koi.api.types.events;

import java.lang.reflect.Field;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonSerializationMethod;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class UserUpdateEvent extends KoiEvent {
    public final @NonNull User streamer;

    // Intercept the deserialization.
    @JsonDeserializationMethod("streamer")
    private void $deserialize_streamer(JsonElement e) throws JsonParseException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        User streamer = Rson.DEFAULT.fromJson(e, User.class);

        {
            Field f = UserUpdateEvent.class.getDeclaredField("streamer");
            f.setAccessible(true);
            f.set(this, streamer);
        }
        {
            Field f = KoiEvent.class.getDeclaredField("streamer");
            f.setAccessible(true);
            f.set(this, streamer);
        }
    }

    @JsonSerializationMethod("streamer")
    private JsonElement $serialize_streamer() {
        return Rson.DEFAULT.toJson(this.streamer);
    }

    @Override
    public KoiEventType type() {
        return KoiEventType.USER_UPDATE;
    }

}
