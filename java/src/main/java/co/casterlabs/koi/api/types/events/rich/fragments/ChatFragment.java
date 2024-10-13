package co.casterlabs.koi.api.types.events.rich.fragments;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonSerializationMethod;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public abstract class ChatFragment {
    public final @NonNull String raw;
    public final @NonNull String html;

    public abstract FragmentType type();

    @JsonSerializationMethod("type")
    private JsonElement $serialize_type() {
        return new JsonString(this.type());
    }

    public static enum FragmentType {
        TEXT,
        EMOTE,
        EMOJI,
        MENTION,
        LINK
    }

}
