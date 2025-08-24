package co.casterlabs.koi.api.types.user;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class SimpleProfile {
    /**
     * A unique ID that will NEVER collide with another ID from another platform.
     * Use this for databasing or statistics.
     */
    public final @NonNull String UPID;
    public final @NonNull String id;
    public final @NonNull @JsonField("channel_id") String channelId;
    public final @NonNull UserPlatform platform;

    /**
     * Any additional metadata. Not guaranteed to be in a particular format. May be
     * null. If you do add your own metadata, is is preferrable that you prefix with
     * identifier:key
     *
     * This field is mutable!
     */
    @Deprecated
    public final @Nullable JsonObject extraMetadata;

    public int tryGetIdAsInt() {
        return Integer.parseInt(this.id);
    }

    public int tryGetChannelIdAsInt() {
        return Integer.parseInt(this.channelId);
    }

    @Override
    public String toString() {
        return Rson.DEFAULT.toJson(this).toString(false);
    }

    public static SimpleProfile of(@NonNull UserPlatform platform, @NonNull String bothIds) {
        return of(platform, bothIds, bothIds);
    }

    public static SimpleProfile of(@NonNull UserPlatform platform, @NonNull String id, @NonNull String channelId) {
        String UPID = String.format("%s;%s", id, platform);
        return new SimpleProfile(UPID, id, channelId, platform, new JsonObject());
    }

}
