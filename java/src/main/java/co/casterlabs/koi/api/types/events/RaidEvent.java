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

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class RaidEvent extends KoiEvent {
    public final @NonNull User host = null;

    /**
     * May be 0 on platforms that do not expose this data.
     */
    public final @NonNull Integer viewers = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.RAID;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<RaidEvent> {

        protected Builder() {
            super(RaidEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(RaidEvent existing) {
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

        public Builder host(@NonNull User value) {
            this.put("host", value);
            return this;
        }

        public Builder viewers(int value) {
            this.put("viewers", value);
            return this;
        }

    }

}
