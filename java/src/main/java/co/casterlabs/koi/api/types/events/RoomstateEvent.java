package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.GenericBuilder.BuilderDefault;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.KoiRoomEvent;
import co.casterlabs.koi.api.types.RoomId;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@SuppressWarnings("deprecation")
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class RoomstateEvent extends KoiRoomEvent {
    @BuilderDefault("false")
    @JsonField("is_emote_only")
    public final @NonNull Boolean isEmoteOnly = null;

    @BuilderDefault("false")
    @JsonField("is_subs_only")
    public final @NonNull Boolean isSubsOnly = null;

    @BuilderDefault("false")
    @JsonField("is_r9k")
    public final @NonNull Boolean isR9KMode = null;

    @BuilderDefault("false")
    @JsonField("is_followers_only")
    public final @NonNull Boolean isFollowersOnly = null;

    @BuilderDefault("false")
    @JsonField("is_slowmode")
    public final @NonNull Boolean isSlowMode = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.ROOMSTATE;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder(@NonNull RoomId roomId) {
        return new Builder(roomId);
    }

    public static class Builder extends GenericBuilder<RoomstateEvent> {

        protected Builder(@NonNull RoomId roomId) {
            super(RoomstateEvent.class);
            this.timestamp(Instant.now()); // Default.
            this.put("roomId", roomId.serialize());
        }

        protected Builder(RoomstateEvent existing) {
            this(RoomId.deserialize(existing.roomId));
            this.inherit(existing);
        }

        public Builder streamer(@NonNull SimpleProfile value) {
            this.put("streamer", value);
            return this;
        }

        public Builder timestamp(@NonNull Instant value) {
            this.put("timestamp", value);
            return this;
        }

        public Builder emoteOnly(boolean value) {
            this.put("isEmoteOnly", value);
            return this;
        }

        public Builder subsOnly(boolean value) {
            this.put("isSubsOnly", value);
            return this;
        }

        public Builder r9kMode(boolean value) {
            this.put("isR9KMode", value);
            return this;
        }

        public Builder followersOnly(boolean value) {
            this.put("isFollowersOnly", value);
            return this;
        }

        public Builder slowMode(boolean value) {
            this.put("isSlowMode", value);
            return this;
        }

    }

}
