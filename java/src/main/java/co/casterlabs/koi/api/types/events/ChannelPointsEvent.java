package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.types.ChannelPointsRewardRedemption;
import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ChannelPointsEvent extends KoiEvent {
    public final @NonNull User sender = null;
    public final @NonNull ChannelPointsRewardRedemption reward = null;
    public final @NonNull String id = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.CHANNEL_POINTS;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<ChannelPointsEvent> {

        protected Builder() {
            super(ChannelPointsEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(ChannelPointsEvent existing) {
            this();
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
