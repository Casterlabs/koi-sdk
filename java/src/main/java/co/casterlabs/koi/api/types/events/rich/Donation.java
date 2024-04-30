package co.casterlabs.koi.api.types.events.rich;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode()
public class Donation {
    private Donation.DonationType type;
    private String name;

    private String currency;
    private double amount;

    /**
     * This is the amount of the item sent. For example, Caffeine props can be sent
     * multiple times in a single message. For the true value of a donation,
     * multiply this times amount.
     */
    private int count;

    private String image;

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

        ;
    }
}
