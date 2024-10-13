package co.casterlabs.koi.api.types.events;

import java.lang.reflect.Field;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.GenericBuilder.BuilderDefault;
import co.casterlabs.koi.api.types.KoiEvent;
import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.koi.api.types.user.User;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.annotating.JsonSerializationMethod;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import co.casterlabs.rakurai.json.validation.JsonValidationException;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

/**
 * Note that you will <i>eventually</i> receive a {@link UserUpdateEvent} with a
 * corrected {@link User#subscriberCount}. In the meantime, you can increment
 * the value yourself to keep a "true" count.
 */
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class SubscriptionEvent extends KoiEvent {
    /**
     * The person who paid for the subscription. Either for their self or for
     * someone else.
     */
    public final @NonNull User subscriber = null;

    @BuilderDefault("\"SUB\"")
    @JsonField("sub_type")
    public final @NonNull SubscriptionType subType = null;

    @BuilderDefault("\"UNKNOWN\"")
    @JsonField("sub_level")
    public final @NonNull SubscriptionLevel subLevel = null;

    /**
     * Null unless {@link #subType} == /GIFT/. <br />
     * On some platforms, it is unknowable who will receive a subscription during a
     * large gift drop. So you may receive a SubscriptionEvent from
     * {@link #subscriber} multiple times during a gifting event. Do not try to
     * out-smart the backend, just treat each event as unique.
     */
    @BuilderDefault("[]")
    @JsonField("gift_recipients")
    public final @Nullable List<User> giftRecipients = null;

    /**
     * Note that this is unknowable on some platforms, like TikTok. In that case, it
     * will always be 1.
     */
    @BuilderDefault("1")
    @JsonField("months_purchased")
    public final @NonNull Integer monthsPurchased = null;

    /**
     * Note that this is unknowable on some platforms, like TikTok. In that case, it
     * will always be 1.
     */
    @BuilderDefault("1")
    @JsonField("months_streak")
    public final @NonNull Integer monthsStreak = null;

    // Compat for the current backend. Will be removed once everything's switched
    // over to this SDK.
    @JsonDeserializationMethod("gift_recipient")
    private void $deserialize_gift_recipient(JsonElement e) throws JsonValidationException, JsonParseException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        if (!this.giftRecipients.isEmpty()) return;

        List<User> recipients = Arrays.asList(Rson.DEFAULT.fromJson(e, User.class));

        {
            Field f = SubscriptionEvent.class.getDeclaredField("giftRecipients");
            f.setAccessible(true);
            f.set(this, recipients);
        }
    }

    @JsonSerializationMethod("gift_recipient")
    private JsonElement $serialize_gift_recipient() {
        return Rson.DEFAULT.toJson(this.giftRecipients.isEmpty() ? null : this.giftRecipients.get(0));
    }

    @Override
    public KoiEventType type() {
        return KoiEventType.SUBSCRIPTION;
    }

    public static enum SubscriptionType {
        SUB,
        RESUB,

        SUBGIFT,
        RESUBGIFT,

        ANONSUBGIFT,
        ANONRESUBGIFT;

    }

    public static enum SubscriptionLevel {
        UNKNOWN,
        TWITCH_PRIME,
        TIER_1,
        TIER_2,
        TIER_3,
        TIER_4,
        TIER_5;

    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends GenericBuilder<SubscriptionEvent> {

        protected Builder() {
            super(SubscriptionEvent.class);
            this.timestamp(Instant.now()); // Default.
        }

        protected Builder(SubscriptionEvent existing) {
            this();
            this.inherit(existing);
        }

        public Builder streamer(@NonNull SimpleProfile value) {
            this.put("streamer", value);
            return this;
        }

        public Builder timestamp(@NonNull Instant value) {
            this.put("timestamp", value);
            return this;
        }

        public Builder subscriber(@NonNull User value) {
            this.put("subscriber", value);
            return this;
        }

        public Builder subType(@NonNull SubscriptionType value) {
            this.put("subType", value);
            return this;
        }

        public Builder subLevel(@NonNull SubscriptionLevel value) {
            this.put("subLevel", value);
            return this;
        }

        public Builder giftRecipients(@NonNull User... values) {
            return this.giftRecipients(Arrays.asList(values));
        }

        public Builder giftRecipients(@NonNull Collection<User> values) {
            for (User v : values) {
                if (v == null) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("giftRecipients", Collections.unmodifiableList(new ArrayList<>(values)));
            return this;
        }

        public Builder appendGiftRecipient(@NonNull User value) {
            List<User> list = new LinkedList<>(this.get("giftRecipients"));  // Make modifiable. Never null at this location.
            list.add(value); // Append.

            this.giftRecipients(list);
            return this;
        }

        public Builder monthsPurchased(@NonNull Integer value) {
            this.put("monthsPurchased", value);
            return this;
        }

        public Builder monthsStreak(@NonNull Integer value) {
            this.put("monthsStreak", value);
            return this;
        }

    }

}
