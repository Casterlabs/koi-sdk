package co.casterlabs.koi.api.types;

import java.util.Set;

import co.casterlabs.koi.api.types.MessageMeta.BuildableMessageMeta.BuildableMessageMetaBuilder;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(builderMethodName = "__internal_builder")
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public abstract class MessageMeta extends KoiEvent {
    @JsonField("is_visible")
    public final @NonNull Boolean visible;

    public final @NonNull Integer upvotes;

    public final @NonNull Set<MessageAttribute> attributes;

    public void applyTo(MessageMeta other) {
//        other.visible = this.visible;
//        other.attributes = new HashSet<>(this.attributes);
//
//        // Only apply upvotes if we think this instance is current.
//        // Sometimes it won't be, and we don't want a message to randomly lose upvotes.
//        if (other.upvotes < this.upvotes) {
//            other.upvotes = this.upvotes;
//        }
    }

    public BuildableMessageMetaBuilder<?, ?> toBuilder() {
        return _builder()
            .visible(this.visible)
            .upvotes(this.upvotes)
            .attributes(this.attributes);
    }

    public static BuildableMessageMetaBuilder<?, ?> _builder() {
        return BuildableMessageMeta.builder();
    }

    public static enum MessageAttribute {
        HIGHLIGHTED,
        RP_ACTION,
        FIRST_TIME_CHATTER,
        ANNOUNCEMENT,
    }

    @SuperBuilder(toBuilder = true)
    public static class BuildableMessageMeta extends MessageMeta {

        @Override
        public KoiEventType type() {
            throw new UnsupportedOperationException();
        }

    }

}
