package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.types.ChannelPointsRewardRedemption;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.KoiRoomEvent;
import co.casterlabs.koi.api.types.RoomId;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ChannelPointsEvent extends KoiRoomEvent {
    public final @NonNull User sender = null;
    public final @NonNull ChannelPointsRewardRedemption reward = null;
    public final @NonNull String id = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.CHANNEL_POINTS;
    }

    @Override
    protected @Nullable String ueidPart() {
        return this.id;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder(@NonNull RoomId roomId) {
        return new Builder(roomId);
    }

    @SuppressWarnings("deprecation")
    public static class Builder extends GenericBuilder<ChannelPointsEvent> {

        protected Builder(@NonNull RoomId roomId) {
            super(ChannelPointsEvent.class);
            this.timestamp(Instant.now()); // Default.
            this.put("roomId", roomId.serialize());
        }

        protected Builder(ChannelPointsEvent existing) {
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

        public Builder sender(@NonNull User value) {
            this.put("sender", value);
            return this;
        }

        public Builder reward(@NonNull ChannelPointsRewardRedemption value) {
            this.put("reward", value);
            return this;
        }

        public Builder id(@NonNull String value) {
            this.put("id", value);
            return this;
        }

    }

}
