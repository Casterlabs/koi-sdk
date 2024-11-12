package co.casterlabs.koi.api.types;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public abstract class KoiRoomEvent extends KoiEvent {
    @JsonField("room_id")
    public final @NonNull String roomId = null;

}
