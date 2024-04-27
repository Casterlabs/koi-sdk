package co.casterlabs.koi.api.types.events;

import java.util.List;

import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class ViewerListEvent extends KoiEvent {
    public List<User> viewers;

    @Override
    public KoiEventType getType() {
        return KoiEventType.VIEWER_LIST;
    }

}
