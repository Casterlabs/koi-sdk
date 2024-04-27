package co.casterlabs.koi.api.types.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class LikeEvent extends KoiEvent {
    @JsonField("total_likes")
    public long totalLikes;

    @Override
    public KoiEventType getType() {
        return KoiEventType.LIKE;
    }

}
