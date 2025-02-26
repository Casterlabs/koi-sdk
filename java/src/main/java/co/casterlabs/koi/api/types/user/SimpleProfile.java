package co.casterlabs.koi.api.types.user;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.element.JsonObject;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import org.jetbrains.annotations.Nullable;

@EqualsAndHashCode
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class SimpleProfile {
    /**
     * A unique ID that will NEVER collide with another ID from another platform.
     * Use this for databasing or statistics.
     */
    public final @NonNull String UPID = null;
    public final @NonNull String id = null;
    public final @NonNull @JsonField("channel_id") String channelId = null;
    public final @NonNull UserPlatform platform = null;

    /**
     * Any additional metadata. Not guaranteed to be in a particular format. May be null. 
     * If you do add your own metadata, is is preferrable that you prefix with identifier:key 
     *
     * This field is mutable!
     */
    @Deprecated
    public final @Nullable JsonObject extraMetadata = null;
    
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

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<SimpleProfile> {

        protected Builder() {
            super(SimpleProfile.class);
            this.put("extraMetadata", new JsonObject());
        }

        protected Builder(SimpleProfile existing) {
            this();
            this.inherit(existing);
        }

        public Builder id(@NonNull String value) {
            this.put("id", value);
            return this;
        }

        public Builder channelId(@NonNull String value) {
            this.put("channelId", value);
            return this;
        }

        public Builder bothIds(@NonNull String value) {
            this.put("id", value);
            this.put("channelId", value);
            return this;
        }

        public Builder platform(@NonNull UserPlatform value) {
            this.put("platform", value);
            return this;
        }

        @Override
        public synchronized SimpleProfile build() {
            this.put("UPID", String.format("%s;%s", this.get("id"), this.get("platform")));
            return super.build();
        }

    }

}
