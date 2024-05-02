package co.casterlabs.koi.api.types.events.rich.fragments;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import co.casterlabs.emoji.data.Emoji;
import co.casterlabs.emoji.data.EmojiAssetImageSet;
import co.casterlabs.emoji.data.EmojiAssets;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.SneakyThrows;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class EmojiFragment extends ChatFragment {
    private final @NonNull JsonObject variation;

    private EmojiFragment(String raw, String html, Emoji.Variation variation) {
        super(raw, html);
        this.variation = (JsonObject) Rson.DEFAULT.toJson(variation);
    }

    @SneakyThrows
    public Emoji.Variation variation() {
        return Rson.DEFAULT.fromJson(this.variation, Emoji.Variation.class);
    }

    @Override
    public FragmentType type() {
        return FragmentType.EMOJI;
    }

    public static EmojiFragment of(@NonNull Emoji.Variation variation) {
        final List<String> PROVIDERS = EmojiAssets.emojiProviders
            .stream()
            .map((p) -> p.getProviderId())
            .collect(Collectors.toList());
        PROVIDERS.add("system");

        List<String> htmls = new ArrayList<>(PROVIDERS.size());
        for (String provider : PROVIDERS) {
            if (provider.equals("system")) {
                // We always display system by default.
                // Clients should use CSS to hide the system
                // provider and unhide their provider of choice.
                htmls.add(
                    String.format(
                        "<span data-emoji-provider='system'>%s</span>",
                        variation.getSequence()
                    )
                );
                continue;
            }

            EmojiAssetImageSet imageSet = variation.getAssets().getAsset(provider);

            if ((imageSet != null) && imageSet.isSupported()) {
                String imageUrl = imageSet.getSvgUrl();

                if (imageUrl == null) {
                    // Some providers may not have SVGs.
                    imageUrl = imageSet.getPngUrl();
                }

                htmls.add(
                    String.format(
                        "<img alt='%s' src='%s' data-emoji-provider='%s' loading='lazy' style='height: 1.25em; width: auto; vertical-align: middle; display: none;' />",
                        variation.getSequence(),
                        imageUrl,
                        provider
                    )
                );
                continue;
            } else {
                // This is a fallback, it should always be displayed.

                htmls.add(
                    String.format(
                        "<span data-emoji-provider='%s' data-emoji-provider-fallback='true' style='display: none;'>%s</span>",
                        provider,
                        variation.getSequence()
                    )
                );
                continue;
            }
        }

        return new EmojiFragment(
            variation.getSequence(),
            String.format(
                "<span title='%s' data-rich-type='emoji'>%s</span>",
                variation.getName(), // "vulcan salute: light skin tone"
                String.join("", htmls)
            ),
            variation
        );
    }

}
