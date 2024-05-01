package co.casterlabs.koi.api.types.events;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ViewerLeaveEvent extends KoiEvent {
    public final @NonNull User viewer;

    @Override
    public KoiEventType type() {
        return KoiEventType.VIEWER_LEAVE;
    }

}
