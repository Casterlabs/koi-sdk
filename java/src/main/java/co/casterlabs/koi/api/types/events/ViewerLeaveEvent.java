package co.casterlabs.koi.api.types.events;

import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class ViewerLeaveEvent extends KoiEvent {
    public User viewer;

    @Override
    public KoiEventType getType() {
        return KoiEventType.VIEWER_LEAVE;
    }

}
