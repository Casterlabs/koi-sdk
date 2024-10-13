package co.casterlabs.koi.api.types.user;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.koi.api.GenericBuilder;
import co.casterlabs.koi.api.GenericBuilder.BuilderDefault;
import co.casterlabs.koi.api.types.events.FollowEvent;
import co.casterlabs.koi.api.types.events.SubscriptionEvent;
import co.casterlabs.koi.api.types.events.UserUpdateEvent;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonDeserializationMethod;
import co.casterlabs.rakurai.json.annotating.JsonExclude;
import co.casterlabs.rakurai.json.annotating.JsonField;
import co.casterlabs.rakurai.json.annotating.JsonSerializationMethod;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.serialization.JsonParseException;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class User {

    /**
     * A unique ID that will NEVER collide with another ID from another platform.
     * Use this for databasing or statistics.
     */
    public final @NonNull String UPID = null;

    public final @NonNull String id = null;

    public final @NonNull @JsonField("channel_id") String channelId = null;

    public final @NonNull UserPlatform platform = null;

    @BuilderDefault("\"#ff0000\"")
    public final @NonNull String color = null;

    public final @NonNull String username = null;

    public final @NonNull String displayname = null;

    /**
     * `null` if unknown.
     */
    @BuilderDefault("null")
    public final @Nullable Pronouns pronouns = null;

    @BuilderDefault("\"\"")
    public final @NonNull String bio = null;

    public final @NonNull String link = null;

    @JsonField("image_link")
    @BuilderDefault("\"data:image/svg+xml,%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%20standalone%3D%22no%22%3F%3E%0A%3Csvg%20viewBox%3D%220%200%2024%2024%22%20fill%3D%22none%22%20stroke%3D%22whitesmoke%22%20stroke-width%3D%221.5%22%20version%3D%221.1%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20xmlns%3Asvg%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%3E%0A%20%20%20%20%3Crect%20style%3D%22fill%3A%23676767%3Bfill-opacity%3A1%3Bstroke%3Anone%3B%22%20width%3D%2224%22%20height%3D%2224%22%20x%3D%220%22%20y%3D%220%22%20%2F%3E%0A%20%20%20%20%3Cpath%20d%3D%22M20%2021v-2a4%204%200%200%200-4-4H8a4%204%200%200%200-4%204v2%22%20%2F%3E%0A%20%20%20%20%3Ccircle%20cx%3D%2212%22%20cy%3D%227%22%20r%3D%224%22%20%2F%3E%0A%3C%2Fsvg%3E%0A\"")
    public final @NonNull String imageLink = null;

    /**
     * The amount of people who follow this User. YouTube Subscribers are considered
     * "followers."
     * 
     * You may receive a {@link FollowEvent}, in which case you can increment this
     * value while you wait on a fresh {@link UserUpdateEvent}.
     * 
     * @implSpec Will be -1 if the information is not available.
     */
    @BuilderDefault("-1")
    @JsonField("followers_count")
    public @NonNull Long followersCount;

    /**
     * The amount of people who pay a monthly subscription to this User. YouTube
     * Channel Members are considered "subscribers."
     * 
     * You may receive a {@link SubscriptionEvent}, in which case you can increment
     * this value while you wait on a fresh {@link UserUpdateEvent}.
     * 
     * @implSpec Will be -1 if the information is not available.
     */
    @BuilderDefault("-1")
    @JsonField("subscriber_count")
    public @NonNull Long subscriberCount;

    @BuilderDefault("[]")
    public final @NonNull List<UserRoles> roles = null;

    @BuilderDefault("[]")
    public final @NonNull @JsonExclude List<UserBadge> badges = null;

    // Compat for the current backend. Will be removed once everything's switched
    // over to this SDK.
    @JsonDeserializationMethod("badges")
    private void $deserialize_badges(JsonElement arr) throws JsonParseException, NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
        List<UserBadge> badges = new ArrayList<>(arr.getAsArray().size());
        for (JsonElement badge : arr.getAsArray()) {
            if (badge.isJsonString()) {
                badges.add(UserBadge.of("", badge.getAsString(), null));
            } else {
                badges.add(Rson.DEFAULT.fromJson(badge, UserBadge.class));
            }
        }

        {
            Field f = User.class.getDeclaredField("badges");
            f.setAccessible(true);
            f.set(this, badges);
        }
    }

    // Compat for the current backend. Will be removed once everything's switched
    // over to this SDK.
    @JsonSerializationMethod("badges")
    private JsonElement $serialize_badges() {
        return Rson.DEFAULT.toJson(
            this.badges
                .stream()
                .map((b) -> b.imageLink)
                .collect(Collectors.toList())
        );
    }

    public static enum UserRoles {
        BROADCASTER,
        SUBSCRIBER,
        FOLLOWER,
        MODERATOR,
        STAFF,
        VIP,
        OG;
    }

    @AllArgsConstructor(staticName = "of")
    @EqualsAndHashCode()
    @JsonClass(exposeAll = true, unsafeInstantiation = true)
    public static class UserBadge {
        public final @NonNull String name;

        @JsonField("image_link")
        public final @NonNull String imageLink;

        public final @Nullable String provider; // For external providers.
    }

    public SimpleProfile toSimpleProfile() {
        return SimpleProfile.builder()
            .id(this.id)
            .channelId(this.channelId)
            .platform(this.platform)
            .build();
    }

    public Builder toBuilder() {
        return new Builder(this);
    }

    public static Builder builder(@NonNull SimpleProfile profile) {
        return new Builder(profile);
    }

    public static class Builder extends GenericBuilder<User> {

        protected Builder(SimpleProfile profile) {
            super(User.class);
            this.inheritUnsafe(profile);
        }

        protected Builder(User existing) {
            super(User.class);
            this.inherit(existing);
        }

        public Builder color(@NonNull String value) {
            this.put("color", value);
            return this;
        }

        public Builder username(@NonNull String value) {
            this.put("username", value);
            return this;
        }

        public Builder displayname(@NonNull String value) {
            this.put("displayname", value);
            return this;
        }

        public Builder pronouns(@Nullable Pronouns value) {
            this.put("pronouns", value);
            return this;
        }

        public Builder bio(@NonNull String value) {
            this.put("bio", value);
            return this;
        }

        public Builder link(@NonNull String value) {
            this.put("link", value);
            return this;
        }

        public Builder imageLink(@NonNull String value) {
            this.put("imageLink", value);
            return this;
        }

        public Builder followersCount(long value) {
            this.put("followersCount", value);
            return this;
        }

        public Builder subscriberCount(long value) {
            this.put("subscriberCount", value);
            return this;
        }

        public Builder roles(@NonNull UserRoles... values) {
            return this.roles(Arrays.asList(values));
        }

        public Builder roles(@NonNull Collection<UserRoles> values) {
            for (UserRoles v : values) {
                if (v == null) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("roles", Collections.unmodifiableSet(new HashSet<>(values)));
            return this;
        }

        public Builder appendRole(@NonNull UserRoles value) {
            Set<UserRoles> set = new HashSet<>(this.get("roles"));  // Make modifiable. Never null at this location.
            set.add(value); // Append.

            this.roles(set);
            return this;
        }

        public Builder badges(@NonNull UserBadge... values) {
            return this.badges(Arrays.asList(values));
        }

        public Builder badges(@NonNull Collection<UserBadge> values) {
            for (UserBadge v : values) {
                if (v == null) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("badges", Collections.unmodifiableSet(new HashSet<>(values)));
            return this;
        }

        public Builder appendBadge(@NonNull UserBadge value) {
            Set<UserBadge> set = new HashSet<>(this.get("badges"));  // Make modifiable. Never null at this location.
            set.add(value); // Append.

            this.badges(set);
            return this;
        }

        /**
         * This also populates the color and pronouns based on intrinsics.
         */
        @Override
        public synchronized User build() {
            if (this.get("color") == null) {
                final Pattern PATTERN = Pattern.compile("(\\[color:#[a-f0-9]{6}\\])");
                final String[] COLORS = new String[] {
                        "#FF0000",
                        "#FF8000",
                        "#FFFF00",
                        "#80FF00",
                        "#00FF00",
                        "#00FF80",
                        "#00FFFF",
                        "#0080FF",
                        "#0000FF",
                        "#7F00FF",
                        "#FF00FF",
                        "#FF007F"
                };

                // Try to grab a hex code from their bio if they have it.
                Matcher m = PATTERN.matcher(((String) this.get("bio")).toLowerCase());
                if (m.find()) {
                    String group = m.group();
                    String color = group.substring(
                        group.indexOf(':') + 1,
                        group.length() - 1
                    ); // [color:#ea4c4c] -> #ea4c4c

                    this.color(color);
                } else {
                    int hashValue = this.get("username").hashCode();
                    int index = Math.abs(hashValue);

                    String color = COLORS[index % COLORS.length];
                    this.color(color);
                }
            }

            if (this.get("pronouns") == null) {
                final Pattern PATTERN = Pattern.compile("(\\[pronouns:[a-z]+\\])");

                // Try to grab pronouns from their bio if they have it.
                Matcher m = PATTERN.matcher(((String) this.get("bio")).toLowerCase());
                if (m.find()) {
                    String group = m.group();
                    String str = group.substring(
                        group.indexOf(':') + 1,
                        group.length() - 1
                    ).toUpperCase(); // [pronouns:he] -> HE

                    try {
                        this.pronouns(Pronouns.valueOf(str));
                    } catch (IllegalArgumentException ignored) {
                        // Invalid pronoun, ignore it.
                        this.pronouns(null);
                    }
                } else {
                    this.pronouns(null);
                }
            }

            return super.build();
        }

    }

}
