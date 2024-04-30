package co.casterlabs.koi.api.types.events.rich;

import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class MentionFragment extends ChatFragment {
    private User mentioned;

    @Override
    public FragmentType getType() {
        return FragmentType.MENTION;
    }

}
