package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.KoiRoomEvent;
import co.casterlabs.koi.api.types.RoomId;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ClearChatEvent extends KoiRoomEvent {
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

    @Override
    protected @Nullable String ueidPart() {
        return String.join(";", this.clearType.name(), this.userUPID);
    }

    public static enum ClearChatType {
        ALL,
        USER;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder(@NonNull RoomId roomId) {
        return new Builder(roomId);
    }

    @SuppressWarnings("deprecation")
    public static class Builder extends GenericBuilder<ClearChatEvent> {

        protected Builder(@NonNull RoomId roomId) {
            super(ClearChatEvent.class);
            this.timestamp(Instant.now()); // Default.
            this.put("roomId", roomId.serialize());
        }

        protected Builder(ClearChatEvent existing) {
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
