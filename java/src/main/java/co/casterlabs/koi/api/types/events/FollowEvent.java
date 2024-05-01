package co.casterlabs.koi.api.types.events;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Note that you will <i>eventually</i> receive a {@link UserUpdateEvent} with a
 * corrected {@link User#followersCount}. In the meantime, you can increment the
 * value yourself to keep a "true" count.
 */
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class FollowEvent extends KoiEvent {
    public final @NonNull User follower;

    @Override
    public KoiEventType type() {
        return KoiEventType.FOLLOW;
    }

}
