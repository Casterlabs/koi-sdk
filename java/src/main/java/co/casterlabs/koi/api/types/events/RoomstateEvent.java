package co.casterlabs.koi.api.types.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class RoomstateEvent extends KoiEvent {
    @JsonField("is_emote_only")
    public boolean isEmoteOnly = false;

    @JsonField("is_subs_only")
    public boolean isSubsOnly = false;

    @JsonField("is_r9k")
    public boolean isR9KMode = false;

    @JsonField("is_followers_only")
    public boolean isFollowersOnly = false;

    @JsonField("is_slowmode")
    public boolean isSlowMode = false;

    @Override
    public KoiEventType getType() {
        return KoiEventType.ROOMSTATE;
    }

}
