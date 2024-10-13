package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.MessageMeta;
import co.casterlabs.koi.api.types.RoomId;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@SuppressWarnings("deprecation")
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class MessageMetaEvent extends MessageMeta {
    @JsonField("meta_id")
    public final @NonNull String metaId = null;

    @Override
    public KoiEventType type() {
        return KoiEventType.META;
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<MessageMetaEvent> {

        protected Builder() {
            super(MessageMetaEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(MessageMetaEvent existing) {
            this();
            this.inherit(existing);
        }

        public Builder streamer(@NonNull SimpleProfile value) {
            this.put("streamer", value);
            return this;
        }

        public Builder timestamp(@NonNull Instant value) {
            this.put("timestamp", value);
            return this;
        }

        public Builder metaId(@NonNull String value) {
            this.put("metaId", value);
            return this;
        }

        public Builder roomId(@NonNull RoomId value) {
            this.put("roomId", value.serialize());
            return this;
        }

        public Builder visible(boolean value) {
            this.put("visible", value);
            return this;
        }

        public Builder upvotes(int value) {
            this.put("upvotes", value);
            return this;
        }

        public Builder attributes(@NonNull MessageAttribute... values) {
            return this.attributes(Arrays.asList(values));
        }

        public Builder attributes(@NonNull Collection<MessageAttribute> values) {
            this.put("attributes", Collections.unmodifiableSet(new HashSet<>(values)));
            return this;
        }

        public Builder appendAttribute(@NonNull MessageAttribute value) {
            List<MessageAttribute> list = new LinkedList<>(this.get("attributes"));  // Make modifiable. Never null at this location.
            list.add(value); // Append.

            this.attributes(list);
            return this;
        }

    }

}
