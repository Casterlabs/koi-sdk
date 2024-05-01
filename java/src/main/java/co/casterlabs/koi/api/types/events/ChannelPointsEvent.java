package co.casterlabs.koi.api.types.events;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class ChannelPointsEvent extends KoiEvent {
    public final @NonNull User sender;
    public final @NonNull ChannelPointsReward reward;
    public final @NonNull RedemptionStatus status;
    public final @NonNull String id;
    public final @NonNull String message;

    @Override
    public KoiEventType type() {
        return KoiEventType.CHANNEL_POINTS;
    }

    public static enum RedemptionStatus {
        FULFILLED,
        UNFULFILLED
    }

    @EqualsAndHashCode()
    @AllArgsConstructor(staticName = "of")
    @JsonClass(exposeAll = true, unsafeInstantiation = true)
    public static class ChannelPointsReward {
        public final @NonNull String id;

        public final @NonNull String title;

        public final @NonNull Integer cost;

        @JsonField("background_color")
        public final @NonNull String backgroundColor;

        @JsonField("reward_image")
        public final @NonNull String rewardImage;

        @JsonField("default_reward_image")
        public final @NonNull String defaultRewardImage;

        /**
         * Null if disabled. See {@link #userInput}
         */
        public final @Nullable String prompt;

        /**
         * Null if disabled. See {@link #prompt}
         */
        @JsonField("user_input")
        public final @Nullable String userInput;

    }

}
