package co.casterlabs.koi.api.types.events.rich;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class EmojiFragment extends ChatFragment {
//    private Emoji.Variation variation;

    /**
     * @deprecated Use this only if necessary. In the future it will be a real
     *             object.
     */
    @Deprecated
    public JsonObject variation;

    @Override
    public FragmentType getType() {
        return FragmentType.EMOJI;
    }

}
