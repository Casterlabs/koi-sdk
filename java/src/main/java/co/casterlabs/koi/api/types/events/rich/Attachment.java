package co.casterlabs.koi.api.types.events.rich;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.element.JsonElement;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode()
public class Attachment {
    private Attachment.AttachmentType type;
    private JsonElement content;
    private String html;
    private @Nullable Donation donation;

    public static enum AttachmentType {
        IMAGE,
        INTERACTIVE;
    }
}
