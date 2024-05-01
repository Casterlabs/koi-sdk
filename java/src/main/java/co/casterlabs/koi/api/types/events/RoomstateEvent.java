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
public class RoomstateEvent extends KoiEvent {
    @JsonField("is_emote_only")
    public final @NonNull Boolean isEmoteOnly;

    @JsonField("is_subs_only")
    public final @NonNull Boolean isSubsOnly;

    @JsonField("is_r9k")
    public final @NonNull Boolean isR9KMode;

    @JsonField("is_followers_only")
    public final @NonNull Boolean isFollowersOnly;

    @JsonField("is_slowmode")
    public final @NonNull Boolean isSlowMode;

    @Override
    public KoiEventType type() {
        return KoiEventType.ROOMSTATE;
    }

}
