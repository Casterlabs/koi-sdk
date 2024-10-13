package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonArray;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class CatchupEvent extends KoiEvent {
    /**
     * We avoid deserializing if you don't need it. See: {@link #eventsAsObjects()}
     */
    public final @NonNull JsonArray events = null;

    public List<KoiEvent> eventsAsObjects() {
        return this.events
            .toList()
            .stream()
            .map((e) -> KoiEventType.get(e.getAsObject()))
            .collect(Collectors.toList());
    }

    @Override
    public KoiEventType type() {
        return KoiEventType.CATCHUP;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<CatchupEvent> {

        protected Builder() {
            super(CatchupEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(CatchupEvent existing) {
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

        public Builder events(@NonNull JsonArray values) {
            for (JsonElement v : values) {
                if (v == null || v.isJsonNull()) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("events", values);
            return this;
        }

    }

}
