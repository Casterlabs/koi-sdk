package co.casterlabs.koi.api.types.events.rich;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
@AllArgsConstructor(staticName = "of")
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class Donation {
    public final @NonNull Donation.DonationType type;
    public final @NonNull String name;

    public final @NonNull String currency;
    public final @NonNull Double amount;

    /**
     * This is the amount of the item sent. For example, Caffeine props can be sent
     * multiple times in a single message. For the true value of a donation,
     * multiply this times amount.
     */
    public final @NonNull Integer count;

    public final @NonNull String image;

    public static enum DonationType {
        CASTERLABS_TEST,
        OTHER, // Never exclude other!

        CAFFEINE_PROP,

        TWITCH_BITS,

        TROVO_SPELL,

        YOUTUBE_SUPER_CHAT,
        YOUTUBE_SUPER_STICKER,

        DLIVE_SUPER_CHAT,
        DLIVE_LEMON,

        TIKTOK_COINS,

        KICK_KICKS,

        ;
    }
}
