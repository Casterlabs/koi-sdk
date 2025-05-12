package co.casterlabs.koi.api.types;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.GenericBuilder.BuilderDefault;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.annotating.JsonSerializationMethod;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ChannelPointsRewardRedemption {
    public final @NonNull String id = null;

    public final @NonNull String title = null;

    public final @NonNull Integer cost = null;

    @BuilderDefault("null")
    @JsonField("background_color")
    public final @Nullable String backgroundColor = null;

    @BuilderDefault("null")
    @JsonField("reward_image")
    public final @Nullable String rewardImage = null;

    /**
     * Null if disabled. See {@link #userInput}
     */
    @BuilderDefault("null")
    public final @Nullable String prompt = null;

    /**
     * Null if disabled. See {@link #prompt}
     */
    @BuilderDefault("null")
    @JsonField("user_input")
    public final @Nullable String userInput = null;

    /**
     * Compat.
     */
    @JsonSerializationMethod("default_reward_image")
    private JsonElement $serialize_default_reward_image() {
        return new JsonString(this.rewardImage);
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<ChannelPointsRewardRedemption> {

        protected Builder() {
            super(ChannelPointsRewardRedemption.class);
        }

        protected Builder(ChannelPointsRewardRedemption existing) {
            this();
            this.inherit(existing);
        }

        public Builder id(@NonNull String value) {
            this.put("id", value);
            return this;
        }

        public Builder title(@NonNull String value) {
            this.put("title", value);
            return this;
        }

        public Builder cost(int value) {
            this.put("cost", value);
            return this;
        }

        public Builder backgroundColor(@Nullable String value) {
            this.put("backgroundColor", value);
            return this;
        }

        public Builder rewardImage(@NonNull String value) {
            this.put("rewardImage", value);
            return this;
        }

        public Builder prompt(@Nullable String value) {
            this.put("prompt", value);
            return this;
        }

        public Builder userInput(@Nullable String value) {
            this.put("userInput", value);
            return this;
        }

    }

}
