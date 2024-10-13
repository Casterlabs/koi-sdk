package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Note that you will <i>eventually</i> receive a {@link UserUpdateEvent} with a
 * corrected {@link User#followersCount}. In the meantime, you can increment the
 * value yourself to keep a "true" count.
 */
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class FollowEvent extends KoiEvent {
    public final @NonNull User follower = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.FOLLOW;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<FollowEvent> {

        protected Builder() {
            super(FollowEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(FollowEvent existing) {
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

        public Builder follower(@NonNull User value) {
            this.put("follower", value);
            return this;
        }

    }

}
