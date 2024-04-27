package co.casterlabs.koi.api.types.events;

import java.time.Instant;

import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class ChannelPointsEvent extends KoiEvent {
    public User sender;
    public ChannelPointsReward reward;
    public RedemptionStatus status;
    public String id;
    public String message;

    @Override
    public KoiEventType getType() {
        return KoiEventType.CHANNEL_POINTS;
    }

    public static enum RedemptionStatus {
        FULFILLED,
        UNFULFILLED
    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @JsonClass(exposeAll = true)
    public static class ChannelPointsReward {

        @JsonField("background_color")
        public String backgroundColor;

        public String id;

        @JsonField("cooldown_expires_at")
        public Instant cooldownExpiresAt;

        public String title;

        public String prompt;

        public int cost;

        @JsonField("is_enabled")
        public boolean enabled;

        @JsonField("is_in_stock")
        public boolean inStock;

        @JsonField("is_paused")
        public boolean paused;

        @JsonField("is_sub_only")
        public boolean subOnly;

        @JsonField("is_user_input_required")
        public boolean userInputRequired;

        @JsonField("reward_image")
        public String rewardImage;

        @JsonField("default_reward_image")
        public String defaultRewardImage;

        @JsonField("max_per_stream")
        public ChannelPointsMaxPerStream maxPerStream;

        @JsonField("max_per_user_per_stream")
        public ChannelPointsMaxPerUserPerStream maxPerUserPerStream;

        @JsonField("global_cooldown")
        public ChannelPointsCooldown globalCooldown;

    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @JsonClass(exposeAll = true)
    public static class ChannelPointsMaxPerStream {

        @JsonField("is_enabled")
        public boolean enabled;

        @JsonField("max_per_stream")
        public int max;

    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @JsonClass(exposeAll = true)
    public static class ChannelPointsMaxPerUserPerStream {

        @JsonField("is_enabled")
        public boolean enabled;

        @JsonField("max_per_user_per_stream")
        public int max;

    }

    @Getter
    @ToString
    @EqualsAndHashCode
    @JsonClass(exposeAll = true)
    public static class ChannelPointsCooldown {

        @JsonField("is_enabled")
        public boolean enabled;

        @JsonField("global_cooldown_seconds")
        public int globalCooldownSeconds;

    }

}
