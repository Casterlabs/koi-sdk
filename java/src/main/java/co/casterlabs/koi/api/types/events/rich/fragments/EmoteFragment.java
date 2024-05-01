package co.casterlabs.koi.api.types.events.rich.fragments;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.types.events.rich.Donation;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class EmoteFragment extends ChatFragment {
    public final @NonNull String emoteName;

    public final @NonNull String imageLink;

    /**
     * Some emotes are purely for augmenting another emote. For instance, adding a
     * rain effect on a Sad Pepe. This is already handled for you in the rendered
     * html.
     */
    public final @NonNull Boolean isZeroWidth;

    public final @Nullable String provider;

    public final @Nullable Donation donation;

    private EmoteFragment(String raw, String html, String emoteName, String imageLink, boolean isZeroWidth, String provider, Donation donation) {
        super(raw, html);
        this.emoteName = emoteName;
        this.imageLink = imageLink;
        this.isZeroWidth = isZeroWidth;
        this.provider = provider;
        this.donation = donation;
    }

    @Override
    public FragmentType type() {
        return FragmentType.EMOTE;
    }

    public static EmoteFragment of(@NonNull String sequence, @NonNull String emoteName, @NonNull String imageLink, boolean isZeroWidth, @Nullable String provider, @Nullable Donation donation) {
        String titleString = emoteName;

        if (provider != null) {
            titleString += " - ";
            titleString += provider;
        }

        String html = String.format(
            "<img title='%s' alt='%s' src='%s' data-rich-type='emote' style='height: 1.5em; width: auto; display: inline-block; vertical-align: middle; position: %s;' />",
            titleString,
            sequence,
            imageLink,
            isZeroWidth ? "absolute" : "relative"
        );

        if (donation != null && donation.amount > 0) {
            String amount = String.valueOf(donation.amount);

            if (donation.amount == Math.floor(donation.amount)) {
                amount = amount.substring(0, amount.indexOf('.'));
            }

            // The "Twitch convention" is to show a number beside the cheermote. So far,
            // only Twitch has this style of "Paid Emotes", so we'll stick with this and
            // cross the bridge of problems down the road.
            html += String.format(
                "<span style='font-size: .6em; opacity: .85;'>%s</span>",
                amount
            );
        }

        return new EmoteFragment(sequence, html, emoteName, imageLink, isZeroWidth, provider, donation);
    }

}
