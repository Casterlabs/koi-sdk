package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.GenericBuilder.BuilderDefault;
import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ConnectionStateEvent extends KoiEvent {
    @BuilderDefault("{}")
    public final @NonNull Map<String, ConnectionState> states = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.CONNECTION_STATE;
    }

    public static enum ConnectionState {
        CONNECTED,
        DISCONNECTED,
        WAITING;
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

    public static class Builder extends GenericBuilder<ConnectionStateEvent> {

        protected Builder() {
            super(ConnectionStateEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(ConnectionStateEvent existing) {
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

        public Builder states(@NonNull Map<String, ConnectionState> values) {
            for (Entry<String, ConnectionState> e : values.entrySet()) {
                if (e.getKey() == null || e.getValue() == null) {
                    throw new NullPointerException("Element in map cannot be null.");
                }
            }

            this.put("states", Collections.unmodifiableMap(values));
            return this;
        }

        public Builder appendState(@NonNull String id, ConnectionState value) {
            Map<String, ConnectionState> map = new HashMap<>(this.getOrDefault("states", Collections.emptyMap())); // Make modifiable.
            map.put(id, value); // Append.

            this.states(map); // This call makes it unmodifiable again.
            return this;
        }

    }

}
