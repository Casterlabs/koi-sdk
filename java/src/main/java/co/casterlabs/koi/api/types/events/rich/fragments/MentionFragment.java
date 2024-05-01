package co.casterlabs.koi.api.types.events.rich.fragments;

import org.unbescape.html.HtmlEscape;

import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class MentionFragment extends ChatFragment {
    public final @NonNull User mentioned;

    private MentionFragment(String raw, String html, User mentioned) {
        super(raw, html);
        this.mentioned = mentioned;
    }

    @Override
    public FragmentType type() {
        return FragmentType.MENTION;
    }

    public static MentionFragment of(@NonNull User mentioned) {
        return new MentionFragment(
            '@' + mentioned.username,
            String.format(
                "<a href='%s' target='_blank' data-rich-type='mention'>@%s</a>",
                mentioned.link,
                HtmlEscape.escapeHtml5(mentioned.displayname)
            ),
            mentioned
        );
    }

}
