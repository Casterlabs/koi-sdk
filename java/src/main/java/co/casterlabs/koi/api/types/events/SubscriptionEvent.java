package co.casterlabs.koi.api.types.events;

import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class SubscriptionEvent extends KoiEvent {
    public User subscriber;

    @JsonField("gift_recipient")
    public User giftRecipient;

    /**
     * @deprecated This value has been superseded by {@link #monthsPurchased} and
     *             {@link #monthsStreak}. This will always have a value of 1 until
     *             it's removal.
     */
    @Deprecated
    public int months = 1;

    /**
     * Note that this is unknowable on some platforms, like TikTok. In that case, it
     * will always be 1.
     */
    @JsonField("months_purchased")
    public int monthsPurchased;

    /**
     * Note that this is unknowable on some platforms, like TikTok. In that case, it
     * will always be 1.
     */
    @JsonField("months_streak")
    public int monthsStreak;

    @JsonField("sub_type")
    public SubscriptionType subType;

    @JsonField("sub_level")
    public SubscriptionLevel subLevel;

    @Override
    public KoiEventType getType() {
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
