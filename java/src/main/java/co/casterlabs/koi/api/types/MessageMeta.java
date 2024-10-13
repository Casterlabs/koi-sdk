package co.casterlabs.koi.api.types;

import java.util.Set;

import co.casterlabs.koi.api.GenericBuilder.BuilderDefault;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public abstract class MessageMeta extends KoiEvent {
    @JsonField("room_id")
    public final @NonNull String roomId = null;

    /**
     * Whether or not the message should be shown.
     * 
     * @see MessageMetaEvent. This'll provide updates.
     */
    @BuilderDefault("true")
    @JsonField("is_visible")
    public final @NonNull Boolean visible = null;

    /**
     * The amount of upvotes this message has. You should only display the count if
     * the number is greater than 0.
     * 
     * @implSpec This number will always be 0 for the platforms that do not have an
     *           upvoting feature.
     */
    @BuilderDefault("0")
    public final @NonNull Integer upvotes = null;

    @BuilderDefault("[]")
    public final @NonNull Set<MessageAttribute> attributes = null;

    public void applyTo(MessageMeta other) {
        // TODO reflection.
//        other.visible = this.visible;
//        other.attributes = new HashSet<>(this.attributes);
//
//        // Only apply upvotes if we think this instance is current.
//        // Sometimes it won't be, and we don't want a message to randomly lose upvotes.
//        if (other.upvotes < this.upvotes) {
//            other.upvotes = this.upvotes;
//        }
    }

    public static enum MessageAttribute {
        HIGHLIGHTED,

        /**
         * See Twitch's /me command.
         */
        RP_ACTION,

        FIRST_TIME_CHATTER,

        ANNOUNCEMENT,
    }

}
