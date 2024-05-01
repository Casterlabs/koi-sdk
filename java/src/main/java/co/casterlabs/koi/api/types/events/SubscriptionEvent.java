package co.casterlabs.koi.api.types.events;

import java.util.List;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

/**
 * Note that you will <i>eventually</i> receive a {@link UserUpdateEvent} with a
 * corrected {@link User#subscriberCount}. In the meantime, you can increment
 * the value yourself to keep a "true" count.
 */
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class SubscriptionEvent extends KoiEvent {
    /**
     * The person who paid for the subscription. Either for their self or for
     * someone else.
     */
    public final @NonNull User subscriber;

    @JsonField("sub_type")
    public final @NonNull SubscriptionType subType;

    @JsonField("sub_level")
    public final @NonNull SubscriptionLevel subLevel;

    /**
     * Null unless {@link #subType} == /GIFT/. <br />
     * On some platforms, it is unknowable who will receive a subscription during a
     * large gift drop. So you may receive a SubscriptionEvent from
     * {@link #subscriber} multiple times during a gifting event. Do not try to
     * out-smart the backend, just treat each event as unique.
     */
    @JsonField("gift_recipients")
    public final @Nullable List<User> giftRecipients;

    /**
     * Note that this is unknowable on some platforms, like TikTok. In that case, it
     * will always be 1.
     */
    @JsonField("months_purchased")
    public final @NonNull Integer monthsPurchased;

    /**
     * Note that this is unknowable on some platforms, like TikTok. In that case, it
     * will always be 1.
     */
    @JsonField("months_streak")
    public final @NonNull Integer monthsStreak;

    @Override
    public KoiEventType type() {
        return KoiEventType.SUBSCRIPTION;
    }

    public static enum SubscriptionType {
        SUB,
        RESUB,

        SUBGIFT,
        RESUBGIFT,

        ANONSUBGIFT,
        ANONRESUBGIFT;

    }

    public static enum SubscriptionLevel {
        UNKNOWN,
        TWITCH_PRIME,
        TIER_1,
        TIER_2,
        TIER_3,
        TIER_4,
        TIER_5;

    }

}
