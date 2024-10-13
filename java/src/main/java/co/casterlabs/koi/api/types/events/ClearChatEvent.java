package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ClearChatEvent extends KoiEvent {
    @JsonField("clear_type")
    public final @NonNull ClearChatType clearType = null;

    /**
     * `null` if {@link #clearType} == ClearChatType.ALL.
     */
    @JsonField("user_upid")
    public final @Nullable String userUPID = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.CLEARCHAT;
    }

    public static enum ClearChatType {
        ALL,
        USER;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<ClearChatEvent> {

        protected Builder() {
            super(ClearChatEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(ClearChatEvent existing) {
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

        public Builder clearType(@NonNull ClearChatType value) {
            this.put("clearType", value);
            return this;
        }

        /**
         * Will be `null` if {@link #clearType} == ClearChatType.ALL.
         */
        public Builder userUPID(@Nullable String value) {
            this.put("userUPID", value);
            return this;
        }

        @Override
        public synchronized ClearChatEvent build() {
            if (this.get("clearType") == ClearChatType.ALL) {
                // Enforce the contract.
                this.userUPID(null);
            }

            return super.build();
        }

    }

}
