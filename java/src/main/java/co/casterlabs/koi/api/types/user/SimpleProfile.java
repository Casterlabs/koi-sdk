package co.casterlabs.koi.api.types.user;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonClass(exposeAll = true)
public class SimpleProfile {
    public String UPID;
    public String id;
    public @JsonField("channel_id") String channelId;
    public UserPlatform platform;

    public int tryGetIdAsInt() {
        return Integer.parseInt(this.id);
    }

    public int tryGetChannelIdAsInt() {
        return Integer.parseInt(this.channelId);
    }

}
