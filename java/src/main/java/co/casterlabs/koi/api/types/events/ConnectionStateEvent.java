package co.casterlabs.koi.api.types.events;

import java.util.Map;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class ConnectionStateEvent extends KoiEvent {
    public Map<String, ConnectionState> states;

    @Override
    public KoiEventType getType() {
        return KoiEventType.CONNECTION_STATE;
    }

    public static enum ConnectionState {
        CONNECTED,
        DISCONNECTED,
        WAITING;
    }

}
