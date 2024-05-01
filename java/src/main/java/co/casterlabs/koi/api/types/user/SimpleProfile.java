package co.casterlabs.koi.api.types.user;

import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@EqualsAndHashCode()
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class SimpleProfile {
    public final @NonNull String UPID;
    public final @NonNull String id;
    public final @NonNull @JsonField("channel_id") String channelId;
    public final @NonNull UserPlatform platform;

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

}
