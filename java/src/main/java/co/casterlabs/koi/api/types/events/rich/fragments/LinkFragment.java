package co.casterlabs.koi.api.types.events.rich.fragments;

import co.casterlabs.emoji.generator.WebUtil;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class LinkFragment extends ChatFragment {
    public final @NonNull String url;
//     public final @Nullable JsonObject og_properties;

    private LinkFragment(@NonNull String url, @NonNull String html) {
        super(url, html);
        this.url = url;
    }

    @Override
    public FragmentType type() {
        return FragmentType.LINK;
    }

    /**
     * The url provided MUST have a protocol (e.g https://) and valid host (e.g
     * localhost, example.com, 127.0.0.1, or [::1]). Username, port, and path are
     * all optional.
     */
    public static LinkFragment of(@NonNull String url) {
        String urlStringFriendly = urlToFriendly(url);

        return new LinkFragment(
            url,
            String.format(
                "<a href='%s' target='_blank' data-rich-type='link'>%s</a>",
                WebUtil.escapeHtml(url),
                WebUtil.escapeHtml(urlStringFriendly)
            )
        );
    }

    private static String urlToFriendly(String url) {
        /* https://username@example.com/path?query#hash -> username@example.com/path?query#hash */
        url = url.substring(url.indexOf("://") + "://".length());

        /* username@example.com/path?query#hash -> username@example.com/path?query */
        if (url.indexOf('#') != -1) {
            url = url.substring(0, url.indexOf('#'));
        }

        /* username@example.com/path?query -> username@example.com/path */
        if (url.indexOf('?') != -1) {
            url = url.substring(0, url.indexOf('?'));
        }

        /* username@example.com/path -> username@example.com */
        if (url.indexOf('/') != -1) {
            url = url.substring(0, url.indexOf('/'));
        }

        /* username@example.com -> example.com */
        if (url.indexOf('@') != -1) {
            url = url.substring(url.indexOf('@' + 1));
        }

        return url;
    }

}
