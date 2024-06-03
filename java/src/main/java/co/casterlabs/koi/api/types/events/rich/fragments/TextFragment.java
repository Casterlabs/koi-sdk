package co.casterlabs.koi.api.types.events.rich.fragments;

import org.unbescape.html.HtmlEscape;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class TextFragment extends ChatFragment {
    public final @NonNull String text;

    private TextFragment(String text, String html) {
        super(text, html);
        this.text = text;
    }

    @Override
    public FragmentType type() {
        return FragmentType.TEXT;
    }

    public static TextFragment of(@NonNull String text) {
        return new TextFragment(
            text,
            String.format(
                "<span data-rich-type='text'>%s</span>",
                HtmlEscape.escapeHtml5(text)
            )
        );
    }

}
