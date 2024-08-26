package co.casterlabs.koi.api.types.events.rich;

import org.jetbrains.annotations.Nullable;
import org.unbescape.html.HtmlEscape;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode()
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class Attachment {
    public final @NonNull AttachmentType type;
    public final @NonNull AttachmentContent content;
    public final @NonNull String html;
    public final @Nullable Donation donation;

    @EqualsAndHashCode()
    @AllArgsConstructor(access = AccessLevel.PRIVATE)
    @JsonClass(exposeAll = true, unsafeInstantiation = true)
    public static class AttachmentContent {
        public final @NonNull String src;
        public final @NonNull String alt;

    }

    public static enum AttachmentType {
        IMAGE,
        INTERACTIVE;
    }

    public static Attachment ofImage(String imageSrc, String alt, @Nullable Donation donation) {
        String html = String.format(
            "<img data-rich-attachment-type=\"image\" src=\"%s\" alt=\"%s\" style=\"border-radius: 0.25rem; height: auto; max-height: 2.5em; max-width: 2.5em;\"/>",
            HtmlEscape.escapeHtml5(imageSrc),
            HtmlEscape.escapeHtml5(alt)
        );

        if (donation != null) {
            html += String.format("<span data-rich-attachment-type=\"image-donation-count\">x%d</span>", donation.count);
        }

        return new Attachment(
            AttachmentType.IMAGE,
            new AttachmentContent(imageSrc, alt),
            html,
            donation
        );
    }

    public static Attachment ofInteractive(String url, String title) {
        return new Attachment(
            AttachmentType.INTERACTIVE,
            new AttachmentContent(url, title),
            String.format(
                "<iframe data-rich-attachment-type=\"interactive\" src=\"%s\" title=\"%s\" style=\"width: 8em; height: 8em;\"></iframe>",
                HtmlEscape.escapeHtml5(url),
                HtmlEscape.escapeHtml5(title)
            ),
            null
        );
    }

}
