package co.casterlabs.koi.api.types.events.rich;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode()
public class Attachment {
    public Attachment.AttachmentType type;
    public JsonElement content;
    public String html;
    public @Nullable Donation donation;

    public static enum AttachmentType {
        IMAGE,
        INTERACTIVE;
    }
}
