package co.casterlabs.koi.api.types;

import co.casterlabs.koi.api.types.events.CatchupEvent;
import co.casterlabs.koi.api.types.events.ChannelPointsEvent;
import co.casterlabs.koi.api.types.events.ClearChatEvent;
import co.casterlabs.koi.api.types.events.ConnectionStateEvent;
import co.casterlabs.koi.api.types.events.FollowEvent;
import co.casterlabs.koi.api.types.events.LikeEvent;
import co.casterlabs.koi.api.types.events.MessageMetaEvent;
import co.casterlabs.koi.api.types.events.PlatformMessageEvent;
import co.casterlabs.koi.api.types.events.RaidEvent;
import co.casterlabs.koi.api.types.events.RichMessageEvent;
import co.casterlabs.koi.api.types.events.RoomstateEvent;
import co.casterlabs.koi.api.types.events.StreamStatusEvent;
import co.casterlabs.koi.api.types.events.SubscriptionEvent;
import co.casterlabs.koi.api.types.events.UserUpdateEvent;
import co.casterlabs.koi.api.types.events.ViewerCountEvent;
import co.casterlabs.koi.api.types.events.ViewerJoinEvent;
import co.casterlabs.koi.api.types.events.ViewerLeaveEvent;
import co.casterlabs.koi.api.types.events.ViewerListEvent;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.AllArgsConstructor;
import xyz.e3ndr.fastloggingframework.logging.FastLogger;
import xyz.e3ndr.fastloggingframework.logging.LogLevel;

@AllArgsConstructor
public enum KoiEventType {
    // @formatter:off
    FOLLOW               (FollowEvent.class),
    SUBSCRIPTION         (SubscriptionEvent.class),
    USER_UPDATE          (UserUpdateEvent.class),
    STREAM_STATUS        (StreamStatusEvent.class),
    META                 (MessageMetaEvent.class),
    VIEWER_JOIN          (ViewerJoinEvent.class),
    VIEWER_LEAVE         (ViewerLeaveEvent.class),
    VIEWER_LIST          (ViewerListEvent.class),
    VIEWER_COUNT         (ViewerCountEvent.class),
    RAID                 (RaidEvent.class),
    CHANNEL_POINTS       (ChannelPointsEvent.class),
    CATCHUP              (CatchupEvent.class),
    CLEARCHAT            (ClearChatEvent.class),
    ROOMSTATE            (RoomstateEvent.class),
    PLATFORM_MESSAGE     (PlatformMessageEvent.class), 
    RICH_MESSAGE         (RichMessageEvent.class),
    LIKE                 (LikeEvent.class),
    CONNECTION_STATE     (ConnectionStateEvent.class),
    
    @Deprecated
    DONATION             (null),
    @Deprecated
    CHAT                 (null),
    ;
    // @formatter:on

    private Class<? extends KoiEvent> eventClass;

    public static KoiEvent get(JsonObject eventJson) {
        String eventType = eventJson.getString("event_type");

        FastLogger.logStatic(LogLevel.TRACE, "Deserializing: %s", eventJson);

        try {
            // 1) Lookup the event type
            // 2) Use RSON to deserialize to object using the eventClass.
            // 3) Profit!
            KoiEventType type = KoiEventType.valueOf(eventType);
            if (type.eventClass == null) return null;

            KoiEvent event = Rson.DEFAULT.fromJson(eventJson, type.eventClass);
            return event;
        } catch (IllegalArgumentException e) {
            // 1.1) Lookup failed, so we don't actually have that event.
            // 1.2) Return nothing.
            return null;
        } catch (Exception e) {
            // 2.1) *Something* failed, so we probably don't have that event structured
            // correctly.
            // 2.2) Return nothing.
            FastLogger.logStatic(LogLevel.SEVERE, "An error occured while converting an event of type %s", eventType);
            FastLogger.logException(e);
            FastLogger.logStatic(LogLevel.DEBUG, eventJson);
            return null;
        }
    }

}
