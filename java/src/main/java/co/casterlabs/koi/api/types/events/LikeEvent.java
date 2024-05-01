package co.casterlabs.koi.api.types.events;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class LikeEvent extends KoiEvent {
    /**
     * @implSpec This will always be null on the platforms that do not tell us who
     *           liked the broadcast.
     */
    public final @Nullable User liker;

    @JsonField("total_likes")
    public final @NonNull Long totalLikes;

    @Override
    public KoiEventType type() {
        return KoiEventType.LIKE;
    }

}
