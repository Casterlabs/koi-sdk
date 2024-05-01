package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.List;

import org.jetbrains.annotations.Nullable;

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

    public final @Nullable String title;

    @JsonField("start_time")
    public final @Nullable Instant startTime;

    public final @Nullable List<String> tags;

    public final @Nullable String category;

    @JsonField("content_rating")
    public final @Nullable KoiStreamContentRating contentRating;

    @JsonField("thumbnail_url")
    public final @Nullable String thumbnailUrl;

    public final @Nullable KoiStreamLanguage language;

    @Override
    public KoiEventType type() {
        return KoiEventType.STREAM_STATUS;
    }

}
