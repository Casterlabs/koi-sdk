package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.List;

import co.casterlabs.koi.api.types.stream.KoiStreamContentRating;
import co.casterlabs.koi.api.types.stream.KoiStreamLanguage;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class StreamStatusEvent extends KoiEvent {
    @JsonField("is_live")
    public boolean live;

    public String title;

    public Instant startTime;

    public List<String> tags;

    public String category;

    @JsonField("content_rating")
    public KoiStreamContentRating contentRating;

    @JsonField("thumbnail_url")
    public String thumbnailUrl;

    public KoiStreamLanguage language;

    @Override
    public KoiEventType getType() {
        return KoiEventType.STREAM_STATUS;
    }

}
