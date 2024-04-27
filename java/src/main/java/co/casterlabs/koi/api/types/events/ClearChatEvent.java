package co.casterlabs.koi.api.types.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class ClearChatEvent extends KoiEvent {
    @JsonField("user_upid")
    public String userUPID;

    @JsonField("clear_type")
    public ClearChatType clearType;

    @Override
    public KoiEventType getType() {
        return KoiEventType.CLEARCHAT;
    }

    public static enum ClearChatType {
        ALL,
        USER;
    }

}
