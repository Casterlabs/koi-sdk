package co.casterlabs.koi.api.types.events.rich;

import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class MentionFragment extends ChatFragment {
    public User mentioned;

    @Override
    public FragmentType getType() {
        return FragmentType.MENTION;
    }

}
