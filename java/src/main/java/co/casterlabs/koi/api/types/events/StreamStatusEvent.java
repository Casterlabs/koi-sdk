package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.List;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.stream.KoiStreamContentRating;
import co.casterlabs.koi.api.types.stream.KoiStreamLanguage;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class StreamStatusEvent extends KoiEvent {
    @JsonField("is_live")
    public final @NonNull Boolean live;

    public final @NonNull String title;

    @JsonField("start_time")
    public final @NonNull Instant startTime;

    public final @NonNull List<String> tags;

    public final @NonNull String category;

    @JsonField("content_rating")
    public final @NonNull KoiStreamContentRating contentRating;

    @JsonField("thumbnail_url")
    public final @NonNull String thumbnailUrl;

    public final @NonNull KoiStreamLanguage language;

    @Override
    public KoiEventType type() {
        return KoiEventType.STREAM_STATUS;
    }

}
