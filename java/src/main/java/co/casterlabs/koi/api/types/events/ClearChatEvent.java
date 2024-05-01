package co.casterlabs.koi.api.types.events;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ClearChatEvent extends KoiEvent {
    @JsonField("user_upid")
    public final @NonNull String userUPID;

    @JsonField("clear_type")
    public final @NonNull ClearChatType clearType;

    @Override
    public KoiEventType type() {
        return KoiEventType.CLEARCHAT;
    }

    public static enum ClearChatType {
        ALL,
        USER;
    }

}
