package co.casterlabs.koi.api.types.events;

import java.util.HashSet;
import java.util.Set;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public abstract class MessageMeta extends KoiEvent {
    @JsonField("is_visible")
    private @Setter boolean visible = true;

    private @Setter int upvotes = 0;

    private @Getter Set<MessageAttribute> attributes = new HashSet<>();

    public void apply(MessageMeta other) {
        other.visible = this.visible;
        other.attributes = new HashSet<>(this.attributes);

        // Only apply upvotes if we think this instance is current.
        // Sometimes it won't be, and we don't want a message to randomly lose upvotes.
        if (other.upvotes < this.upvotes) {
            other.upvotes = this.upvotes;
        }
    }

    public static enum MessageAttribute {
        HIGHLIGHTED,
        RP_ACTION,
        FIRST_TIME_CHATTER,
        ANNOUNCEMENT,
    }

}
