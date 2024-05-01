package co.casterlabs.koi.api.types.events;

import java.util.List;
import java.util.stream.Collectors;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonArray;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class CatchupEvent extends KoiEvent {
    /**
     * We avoid deserializing if you don't need it. See: {@link #eventsAsObjects()}
     */
    public final @NonNull JsonArray events;

    public List<KoiEvent> eventsAsObjects() {
        return this.events
            .toList()
            .stream()
            .map((e) -> KoiEventType.get(e.getAsObject()))
            .collect(Collectors.toList());
    }

    @Override
    public KoiEventType type() {
        return KoiEventType.CATCHUP;
    }

}
