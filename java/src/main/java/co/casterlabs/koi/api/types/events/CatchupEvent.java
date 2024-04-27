package co.casterlabs.koi.api.types.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonArray;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class CatchupEvent extends KoiEvent {
    public JsonArray events;

    @Override
    public KoiEventType getType() {
        return KoiEventType.CATCHUP;
    }

}
