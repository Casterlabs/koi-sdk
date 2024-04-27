package co.casterlabs.koi.api.types.events.rich;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class EmoteFragment extends ChatFragment {
    public String emoteName;
    public String imageLink;
    public @Nullable String provider;
    public @Nullable Donation donation;

    @Override
    public FragmentType getType() {
        return FragmentType.EMOTE;
    }

}
