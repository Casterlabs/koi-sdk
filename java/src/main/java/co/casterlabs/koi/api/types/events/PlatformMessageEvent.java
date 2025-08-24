package co.casterlabs.koi.api.types.events;

import java.time.Instant;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.MessageId;
import co.casterlabs.koi.api.types.RoomId;
import co.casterlabs.koi.api.types.events.rich.Attachment;
import co.casterlabs.koi.api.types.events.rich.fragments.ChatFragment;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.koi.api.types.user.UserPlatform;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.SneakyThrows;

@SuppressWarnings("deprecation")
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

    @SneakyThrows
    public static PlatformMessageEvent of(
        @NonNull UserPlatform platform,
        @NonNull SimpleProfile streamer,
        @NonNull Instant timestamp,
        @NonNull List<ChatFragment> fragments,
        @NonNull List<Attachment> attachments,
        @Nullable String replyTarget
    ) {
        RoomId roomId = RoomId.of(streamer, platform.link);
        MessageId messageId = MessageId.random(platform.systemProfile, roomId);

        RichMessageEvent base = RichMessageEvent.builder(messageId)
            .streamer(streamer)
            .sender(platform.systemUser)
            .timestamp(timestamp)
            .fragments(fragments)
            .attachments(attachments)
            .replyTarget(replyTarget)
            .build();

        // Jank way to convert the events. idc.
        return Rson.DEFAULT.fromJson(Rson.DEFAULT.toJson(base), PlatformMessageEvent.class);
    }

}
