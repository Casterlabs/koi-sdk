package co.casterlabs.koi.api.types.events;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class SubscriptionEvent extends KoiEvent {
    public final @NonNull User subscriber;

    @JsonField("gift_recipient")
    public final @NonNull User giftRecipient;

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

    @JsonField("sub_type")
    public final @NonNull SubscriptionType subType;

    @JsonField("sub_level")
    public final @NonNull SubscriptionLevel subLevel;

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
