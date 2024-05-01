package co.casterlabs.koi.api.types.events;

import java.util.Map;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ConnectionStateEvent extends KoiEvent {
    public final @NonNull Map<String, ConnectionState> states;

    @Override
    public KoiEventType type() {
        return KoiEventType.CONNECTION_STATE;
    }

    public static enum ConnectionState {
        CONNECTED,
        DISCONNECTED,
        WAITING;
    }

}
