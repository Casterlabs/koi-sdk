package co.casterlabs.koi.api.types.events.rich;

import org.jetbrains.annotations.Nullable;

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
public class EmoteFragment extends ChatFragment {
    private String emoteName;
    private String imageLink;
    private @Nullable String provider;
    private @Nullable Donation donation;

    @Override
    public FragmentType getType() {
        return FragmentType.EMOTE;
    }

}
