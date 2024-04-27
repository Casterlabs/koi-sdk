package co.casterlabs.koi.api.types.events.rich;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class TextFragment extends ChatFragment {
    public String text;

    @Override
    public FragmentType getType() {
        return FragmentType.TEXT;
    }

}
