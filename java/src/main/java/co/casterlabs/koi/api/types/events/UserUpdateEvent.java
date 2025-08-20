package co.casterlabs.koi.api.types.events;

import java.lang.reflect.Field;
import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
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

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class UserUpdateEvent extends KoiEvent {
    public final @NonNull User streamer = null;

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
            f.set(this, streamer.toSimpleProfile());
        }
    }

    @JsonSerializationMethod("streamer")
    private JsonElement $serialize_streamer() {
        return Rson.DEFAULT.toJson(UserUpdateEvent.this.streamer);
    }

    @Override
    public KoiEventType type() {
        return KoiEventType.USER_UPDATE;
    }

    @Override
    protected @Nullable String ueidPart() {
        return null;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<UserUpdateEvent> {

        protected Builder() {
            super(UserUpdateEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(UserUpdateEvent existing) {
            this();
            this.inherit(existing);
        }

        public Builder streamer(@NonNull User value) {
            this.put("streamer", value);
            return this;
        }

        public Builder timestamp(@NonNull Instant value) {
            this.put("timestamp", value);
            return this;
        }

    }

}
