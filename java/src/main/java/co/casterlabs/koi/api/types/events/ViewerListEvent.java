package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

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
public class ViewerListEvent extends KoiEvent {
    public final @NonNull List<User> viewers = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.VIEWER_LIST;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<ViewerListEvent> {

        protected Builder() {
            super(ViewerListEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(ViewerListEvent existing) {
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

        public Builder viewers(@NonNull User... values) {
            return this.viewers(Arrays.asList(values));
        }

        public Builder viewers(@NonNull Collection<User> values) {
            for (User v : values) {
                if (v == null) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("viewers", Collections.unmodifiableSet(new HashSet<>(values)));
            return this;
        }

        public Builder appendViewer(@NonNull User value) {
            List<User> list = new LinkedList<>(this.get("viewers"));  // Make modifiable. Never null at this location.
            list.add(value); // Append.

            this.viewers(list);
            return this;
        }

    }

}
