package co.casterlabs.koi.api.types.events;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class LikeEvent extends KoiEvent {
    @JsonField("total_likes")
    public final @NonNull Long totalLikes;

    @Override
    public KoiEventType type() {
        return KoiEventType.LIKE;
    }

}
