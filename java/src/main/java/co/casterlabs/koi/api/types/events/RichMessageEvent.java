package co.casterlabs.koi.api.types.events;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.types.KoiEventType;
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
    public final @NonNull User sender;

    public final @NonNull @JsonExclude List<ChatFragment> fragments;
    public final @NonNull List<Donation> donations;
    public final @NonNull List<Attachment> attachments;
    public final @NonNull String raw;
    public final @NonNull String html;

    public final @NonNull String id;

    @JsonField("meta_id")
    public final @NonNull String metaId;

    @JsonField("reply_target")
    public final @Nullable String replyTarget;

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

    /* -------------------- */
    /* -------------------- */
    /* -------------------- */

    RichMessageEvent(
        @NonNull MessageMetaBuilder<?, ?> b,
        @NonNull User sender,
        @NonNull List<ChatFragment> fragments,
        @NonNull List<Donation> donations,
        @NonNull List<Attachment> attachments,
        @NonNull String raw,
        @NonNull String html,
        @NonNull String id,
        @NonNull String metaId,
        @NonNull String replyTarget
    ) {
        super(b);
        this.sender = sender;
        this.fragments = fragments;
        this.donations = donations;
        this.attachments = attachments;
        this.raw = raw;
        this.html = html;
        this.id = id;
        this.metaId = metaId;
        this.replyTarget = replyTarget;
    }

    public static RichMessageEvent of(
        @NonNull SimpleProfile streamer,
        @NonNull Instant timestamp,
        @NonNull User sender,
        @NonNull List<ChatFragment> fragments,
        @NonNull List<Donation> donations,
        @NonNull List<Attachment> attachments,
        @NonNull String id,
        @NonNull String metaId,
        @NonNull String replyTarget
    ) {
        String raw = fragments.stream()
            .map((f) -> f.raw)
            .collect(Collectors.joining());

        String html = fragments.stream()
            .map((f) -> f.html)
            .collect(Collectors.joining());

        html += "<sup class=\"upvote-counter\"></sup>"; // Upvote counter. Manually used & populated by the client.

        for (Attachment attachment : attachments) {
            html += "<br />";
            html += attachment.html;
        }

        return new RichMessageEvent(
            MessageMeta
                ._builder()
                .streamer(streamer)
                .timestamp(timestamp),
            sender,
            fragments,
            donations,
            attachments,
            raw,
            html,
            id,
            metaId,
            replyTarget
        );
    }

}
