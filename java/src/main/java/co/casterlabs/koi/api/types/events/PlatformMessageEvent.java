package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.MessageMeta;
import co.casterlabs.koi.api.types.events.rich.Attachment;
import co.casterlabs.koi.api.types.events.rich.fragments.ChatFragment;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class PlatformMessageEvent extends RichMessageEvent {

    @Override
    public KoiEventType type() {
        return KoiEventType.PLATFORM_MESSAGE;
    }

    /* -------------------- */
    /* -------------------- */
    /* -------------------- */

    private PlatformMessageEvent(
        @NonNull MessageMetaBuilder<?, ?> b,
        @NonNull User sender,
        @NonNull List<ChatFragment> fragments,
        @NonNull List<Attachment> attachments,
        @NonNull String raw,
        @NonNull String html,
        @NonNull String id
    ) {
        super(b, sender, fragments, Collections.emptyList(), attachments, raw, html, id, id, null);
    }

    public static PlatformMessageEvent of(
        @NonNull SimpleProfile streamer,
        @NonNull Instant timestamp,
        @NonNull User sender,
        @NonNull List<ChatFragment> fragments,
        @NonNull List<Attachment> attachments,
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

        return new PlatformMessageEvent(
            MessageMeta
                ._builder()
                .attributes(Collections.emptySet())
                .upvotes(0)
                .streamer(streamer)
                .timestamp(timestamp),
            sender,
            fragments,
            attachments,
            raw,
            html,
            UUID.randomUUID().toString()
        );
    }

}
