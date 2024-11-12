package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.KoiRoomEvent;
import co.casterlabs.koi.api.types.RoomId;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@SuppressWarnings("deprecation")
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ViewerLeaveEvent extends KoiRoomEvent {
    public final @NonNull User viewer = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.VIEWER_LEAVE;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder(@NonNull RoomId roomId) {
        return new Builder(roomId);
    }

    public static class Builder extends GenericBuilder<ViewerLeaveEvent> {

        protected Builder(@NonNull RoomId roomId) {
            super(ViewerLeaveEvent.class);
            this.timestamp(Instant.now()); // Default.
            this.put("roomId", roomId.serialize());
        }

        protected Builder(ViewerLeaveEvent existing) {
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

        public Builder viewer(@NonNull User value) {
            this.put("viewer", value);
            return this;
        }

    }

}
