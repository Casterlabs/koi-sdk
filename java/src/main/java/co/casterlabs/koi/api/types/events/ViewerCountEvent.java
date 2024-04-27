package co.casterlabs.koi.api.types.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class ViewerCountEvent extends KoiEvent {
    public int count;

    @Override
    public KoiEventType getType() {
        return KoiEventType.VIEWER_COUNT;
    }

}
