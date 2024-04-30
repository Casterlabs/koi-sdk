package co.casterlabs.koi.api.types.events;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class RoomstateEvent extends KoiEvent {
    @JsonField("is_emote_only")
    private boolean isEmoteOnly;

    @JsonField("is_subs_only")
    private boolean isSubsOnly;

    @JsonField("is_r9k")
    private boolean isR9KMode;

    @JsonField("is_followers_only")
    private boolean isFollowersOnly;

    @JsonField("is_slowmode")
    private boolean isSlowMode;

    @Override
    public KoiEventType getType() {
        return KoiEventType.ROOMSTATE;
    }

}
