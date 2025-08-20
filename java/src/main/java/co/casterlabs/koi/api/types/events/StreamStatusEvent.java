package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.GenericBuilder.BuilderDefault;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.KoiRoomEvent;
import co.casterlabs.koi.api.types.RoomId;
import co.casterlabs.koi.api.types.stream.KoiStreamContentRating;
import co.casterlabs.koi.api.types.stream.KoiStreamLanguage;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@SuppressWarnings("deprecation")
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class StreamStatusEvent extends KoiRoomEvent {
    @BuilderDefault("false")
    @JsonField("is_live")
    public final @NonNull Boolean live = null;

    /**
     * @implNote <i>Might</i> be null if the streamer is offline.
     * @implNote Not all platforms support this field, so it may be null even when
     *           the streamer is online/live.
     */
    @BuilderDefault("null")
    public final @Nullable String title = null;

    /**
     * @implNote <i>Might</i> be null if the streamer is offline.
     * @implNote Not all platforms support this field, so it may be null even when
     *           the streamer is online/live.
     */
    @BuilderDefault("null")
    @JsonField("start_time")
    public final @Nullable Instant startTime = null;

    /**
     * @implNote <i>Might</i> be null if the streamer is offline.
     * @implNote Not all platforms support this field, so it may be null even when
     *           the streamer is online/live.
     */
    @BuilderDefault("null")
    public final @Nullable List<String> tags = null;

    /**
     * @implNote <i>Might</i> be null if the streamer is offline.
     * @implNote Not all platforms support this field, so it may be null even when
     *           the streamer is online/live.
     */
    @BuilderDefault("null")
    public final @Nullable String category = null;

    /**
     * @implNote <i>Might</i> be null if the streamer is offline.
     * @implNote Not all platforms support this field, so it may be null even when
     *           the streamer is online/live.
     */
    @BuilderDefault("null")
    @JsonField("content_rating")
    public final @Nullable KoiStreamContentRating contentRating = null;

    /**
     * @implNote <i>Might</i> be null if the streamer is offline.
     * @implNote Not all platforms support this field, so it may be null even when
     *           the streamer is online/live.
     */
    @BuilderDefault("null")
    @JsonField("thumbnail_url")
    public final @Nullable String thumbnailUrl = null;

    /**
     * @implNote <i>Might</i> be null if the streamer is offline.
     * @implNote Not all platforms support this field, so it may be null even when
     *           the streamer is online/live.
     */
    @BuilderDefault("null")
    public final @Nullable KoiStreamLanguage language = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.STREAM_STATUS;
    }

    @Override
    protected @Nullable String ueidPart() {
        return null;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder(@NonNull RoomId roomId) {
        return new Builder(roomId);
    }

    public static class Builder extends GenericBuilder<StreamStatusEvent> {

        protected Builder(@NonNull RoomId roomId) {
            super(StreamStatusEvent.class);
            this.timestamp(Instant.now()); // Default.
            this.put("roomId", roomId.serialize());
        }

        protected Builder(StreamStatusEvent existing) {
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

        public Builder live(boolean value) {
            this.put("live", value);
            return this;
        }

        public Builder title(@NonNull String value) {
            this.put("title", value);
            return this;
        }

        public Builder startTime(@NonNull Instant value) {
            this.put("startTime", value);
            return this;
        }

        public Builder tags(@NonNull String... values) {
            return this.tags(Arrays.asList(values));
        }

        public Builder tags(@NonNull Collection<String> values) {
            for (String v : values) {
                if (v == null) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("tags", Collections.unmodifiableSet(new HashSet<>(values)));
            return this;
        }

        public Builder appendTag(@NonNull String value) {
            List<String> list = new LinkedList<>(this.getOrDefault("tags", Collections.emptyList())); // Make modifiable.

            list.add(value); // Append.

            this.tags(list);
            return this;
        }

        public Builder category(@NonNull String value) {
            this.put("category", value);
            return this;
        }

        public Builder contentRating(@NonNull KoiStreamContentRating value) {
            this.put("contentRating", value);
            return this;
        }

        public Builder thumbnailUrl(@NonNull String value) {
            this.put("thumbnailUrl", value);
            return this;
        }

        public Builder language(@NonNull KoiStreamLanguage value) {
            this.put("language", value);
            return this;
        }

    }

}
