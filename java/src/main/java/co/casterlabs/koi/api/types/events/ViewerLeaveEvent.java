package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

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
public class ViewerLeaveEvent extends KoiEvent {
    public final @NonNull User viewer = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.VIEWER_LEAVE;
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

    public static class Builder extends GenericBuilder<ViewerLeaveEvent> {

        protected Builder() {
            super(ViewerLeaveEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(ViewerLeaveEvent existing) {
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

        public Builder viewer(@NonNull User value) {
            this.put("viewer", value);
            return this;
        }

    }

}
