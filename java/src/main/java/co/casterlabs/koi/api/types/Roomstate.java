package co.casterlabs.koi.api.types;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.GenericBuilder.BuilderDefault;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class Roomstate {
    @BuilderDefault("false")
    @JsonField("is_emote_only")
    public final @NonNull Boolean isEmoteOnly = null;

    @BuilderDefault("false")
    @JsonField("is_subs_only")
    public final @NonNull Boolean isSubsOnly = null;

    @BuilderDefault("false")
    @JsonField("is_r9k")
    public final @NonNull Boolean isR9KMode = null;

    @BuilderDefault("false")
    @JsonField("is_followers_only")
    public final @NonNull Boolean isFollowersOnly = null;

    @BuilderDefault("false")
    @JsonField("is_slowmode")
    public final @NonNull Boolean isSlowMode = null;

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<Roomstate> {

        protected Builder() {
            super(Roomstate.class);
        }

        protected Builder(Roomstate existing) {
            this();
            this.inherit(existing);
        }

        public Builder emoteOnly(boolean value) {
            this.put("isEmoteOnly", value);
            return this;
        }

        public Builder subsOnly(boolean value) {
            this.put("isSubsOnly", value);
            return this;
        }

        public Builder r9kMode(boolean value) {
            this.put("isR9KMode", value);
            return this;
        }

        public Builder followersOnly(boolean value) {
            this.put("isFollowersOnly", value);
            return this;
        }

        public Builder slowMode(boolean value) {
            this.put("isSlowMode", value);
            return this;
        }

    }

}
