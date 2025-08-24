package co.casterlabs.koi.api.types.events;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.GenericBuilder.BuilderDefault;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.MessageId;
import co.casterlabs.koi.api.types.MessageMeta;
import co.casterlabs.koi.api.types.events.rich.Attachment;
import co.casterlabs.koi.api.types.events.rich.Donation;
import co.casterlabs.koi.api.types.events.rich.fragments.ChatFragment;
import co.casterlabs.koi.api.types.events.rich.fragments.ChatFragment.FragmentType;
import co.casterlabs.koi.api.types.events.rich.fragments.EmojiFragment;
import co.casterlabs.koi.api.types.events.rich.fragments.EmoteFragment;
import co.casterlabs.koi.api.types.events.rich.fragments.LinkFragment;
import co.casterlabs.koi.api.types.events.rich.fragments.MentionFragment;
import co.casterlabs.koi.api.types.events.rich.fragments.TextFragment;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.annotating.JsonSerializationMethod;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonObject;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class RichMessageEvent extends MessageMeta {
    public final @NonNull User sender = null;

    public final @NonNull @JsonExclude List<ChatFragment> fragments = null;

    @BuilderDefault("[]")
    public final @NonNull List<Donation> donations = null;

    @BuilderDefault("[]")
    public final @NonNull List<Attachment> attachments = null;

    @JsonField("meta_id")
    public final @NonNull String metaId = null;
    public final @NonNull String id = null;

    @BuilderDefault("null")
    @JsonField("reply_target")
    public final @Nullable String replyTarget = null;

    public final @NonNull String raw = null;
    public final @NonNull String html = null;

    @JsonDeserializationMethod("fragments")
    private void $deserialize_fragments(JsonElement arr) throws JsonParseException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        List<ChatFragment> fragments = new ArrayList<>(arr.getAsArray().size());
        for (JsonElement fragment : arr.getAsArray()) {
            JsonObject obj = fragment.getAsObject();
            Class<? extends ChatFragment> fragmentClass = null;

            switch (FragmentType.valueOf(obj.getString("type"))) {
                case EMOJI:
                    fragmentClass = EmojiFragment.class;
                    break;
                case EMOTE:
                    fragmentClass = EmoteFragment.class;
                    break;
                case LINK:
                    fragmentClass = LinkFragment.class;
                    break;
                case MENTION:
                    fragmentClass = MentionFragment.class;
                    break;
                case TEXT:
                    fragmentClass = TextFragment.class;
                    break;
            }

            assert fragmentClass != null : "Unrecognized ChatFragment type: " + obj.getString("type");

            fragments.add(Rson.DEFAULT.fromJson(fragment, fragmentClass));
        }

        {
            Field f = RichMessageEvent.class.getDeclaredField("fragments");
            f.setAccessible(true);
            f.set(this, fragments);
        }
    }

    @JsonSerializationMethod("fragments")
    private JsonElement $serialize_fragments() {
        return Rson.DEFAULT.toJson(this.fragments);
    }

    @Override
    public KoiEventType type() {
        return KoiEventType.RICH_MESSAGE;
    }

    @Override
    protected @Nullable String ueidPart() {
        return this.metaId;
    }

    /* -------------------- */
    /* -------------------- */
    /* -------------------- */

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder(@NonNull MessageId id) {
        return new Builder(id);
    }

    @SuppressWarnings("deprecation")
    public static class Builder extends GenericBuilder<RichMessageEvent> {

        protected Builder(@NonNull MessageId id) {
            super(RichMessageEvent.class);
            this.timestamp(Instant.now()); // Default.
            this.put("id", id.serialize());
            this.put("metaId", id.trueId);
            this.put("roomId", id.roomId.serialize());
        }

        protected Builder(RichMessageEvent existing) {
            this(MessageId.deserialize(existing.id));
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
            List<MessageAttribute> list = new LinkedList<>(this.getOrDefault("attributes", Collections.emptyList())); // Make modifiable.
            list.add(value); // Append.

            this.attributes(list);
            return this;
        }

        public Builder sender(@NonNull User value) {
            this.put("sender", value);
            return this;
        }

        public Builder fragments(@NonNull ChatFragment... values) {
            return this.fragments(Arrays.asList(values));
        }

        public Builder fragments(@NonNull Collection<ChatFragment> values) {
            for (ChatFragment v : values) {
                if (v == null) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("fragments", Collections.unmodifiableList(new ArrayList<>(values)));
            return this;
        }

        public Builder appendFragment(@NonNull ChatFragment value) {
            List<ChatFragment> list = new LinkedList<>(this.getOrDefault("fragments", Collections.emptyList())); // Make modifiable.
            list.add(value); // Append.

            this.fragments(list);
            return this;
        }

        public Builder donations(@NonNull Donation... values) {
            return this.donations(Arrays.asList(values));
        }

        public Builder donations(@NonNull Collection<Donation> values) {
            for (Donation v : values) {
                if (v == null) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("donations", Collections.unmodifiableList(new ArrayList<>(values)));
            return this;
        }

        public Builder appendDonation(@NonNull Donation value) {
            List<Donation> list = new LinkedList<>(this.getOrDefault("donations", Collections.emptyList())); // Make modifiable.
            list.add(value); // Append.

            this.donations(list);
            return this;
        }

        public Builder attachments(@NonNull Attachment... values) {
            return this.attachments(Arrays.asList(values));
        }

        public Builder attachments(@NonNull Collection<Attachment> values) {
            for (Attachment v : values) {
                if (v == null) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("attachments", Collections.unmodifiableList(new ArrayList<>(values)));
            return this;
        }

        public Builder appendAttachment(@NonNull Attachment value) {
            List<Attachment> list = new LinkedList<>(this.getOrDefault("attachments", Collections.emptyList())); // Make modifiable.
            list.add(value); // Append.

            this.attachments(list);
            return this;
        }

        public Builder replyTarget(@Nullable String value) {
            this.put("replyTarget", value);
            return this;
        }

        @SuppressWarnings("unchecked")
        @Override
        public synchronized RichMessageEvent build() {
            this.put(
                "raw",
                ((List<ChatFragment>) this.get("fragments"))
                    .stream()
                    .map((f) -> f.raw)
                    .collect(Collectors.joining())
            );

            {
                String html = ((List<ChatFragment>) this.get("fragments"))
                    .stream()
                    .map((f) -> f.html)
                    .collect(Collectors.joining());

                html += "<sup class=\"upvote-counter\"></sup>"; // Upvote counter. Manually used & populated by the client.

                if (!((List<Attachment>) this.get("attachments")).isEmpty()) {
                    html += "<br />";
                    for (Attachment attachment : ((List<Attachment>) this.get("attachments"))) {
                        html += attachment.html;
                    }
                }

                this.put("html", html);
            }

            return super.build();
        }

    }

}
