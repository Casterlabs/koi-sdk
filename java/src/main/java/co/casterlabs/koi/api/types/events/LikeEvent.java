package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class LikeEvent extends KoiEvent {
    /**
     * @implSpec This may be be null on platforms that do not tell us who liked.
     */
    public final @Nullable User liker = null;

    @JsonField("total_likes")
    public final @NonNull Long totalLikes = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.LIKE;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<LikeEvent> {

        protected Builder() {
            super(LikeEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(LikeEvent existing) {
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

        public Builder liker(@Nullable User value) {
            this.put("liker", value);
            return this;
        }

        public Builder totalLikes(long value) {
            this.put("totalLikes", value);
            return this;
        }

    }

}
