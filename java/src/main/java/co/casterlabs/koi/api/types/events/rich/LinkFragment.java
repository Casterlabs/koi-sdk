package co.casterlabs.koi.api.types.events.rich;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class LinkFragment extends ChatFragment {
    public String url;
//    public JsonObject og_properties;

    @Override
    public FragmentType getType() {
        return FragmentType.LINK;
    }

}
