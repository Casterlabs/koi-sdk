package co.casterlabs.koi.api.types.user;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
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
    public final @NonNull List<UserRole> roles = null;

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

    @Override
    public String toString() {
        return Rson.DEFAULT.toJson(this).toString(false);
    }

    public SimpleProfile toSimpleProfile() {
        return SimpleProfile.of(this.platform, this.id, this.channelId);
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

        public Builder roles(@NonNull UserRole... values) {
            return this.roles(Arrays.asList(values));
        }

        public Builder roles(@NonNull Collection<UserRole> values) {
            for (UserRole v : values) {
                if (v == null) {
                    throw new NullPointerException("Element in array/list cannot be null.");
                }
            }

            this.put("roles", Collections.unmodifiableList(new ArrayList<>(values)));
            return this;
        }

        public Builder appendRole(@NonNull UserRole value) {
            List<UserRole> list = new LinkedList<>(this.getOrDefault("roles", Collections.emptyList())); // Make modifiable.
            list.add(value); // Append.

            this.roles(list);
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

            this.put("badges", Collections.unmodifiableList(new ArrayList<>(values)));
            return this;
        }

        public Builder appendBadge(@NonNull UserBadge value) {
            List<UserBadge> list = new LinkedList<>(this.getOrDefault("badges", Collections.emptyList())); // Make modifiable.
            list.add(value); // Append.

            this.badges(list);
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

            if (this.get("link") == null) {
                switch ((UserPlatform) this.get("platform")) {
                    case CAFFEINE:
                        this.put("link", String.format("https://caffeine.tv/%s", (String) this.get("username")));
                        break;

                    case TWITCH:
                        this.put("link", String.format("https://twitch.tv/%s", (String) this.get("username")));
                        break;

                    case TROVO:
                        this.put("link", String.format("https://trovo.live/%s", (String) this.get("username")));
                        break;

                    case GLIMESH:
                        this.put("link", String.format("https://glimesh.tv/%s", (String) this.get("username")));
                        break;

                    case BRIME:
                        this.put("link", String.format("https://brime.tv/%s", (String) this.get("username")));
                        break;

                    case YOUTUBE:
                        this.put("link", String.format("https://youtube.com/channel/%s", (String) this.get("id")));
                        break;

                    case DLIVE:
                        this.put("link", String.format("https://dlive.tv/%s", (String) this.get("username")));
                        break;

                    case TIKTOK:
                        this.put("link", String.format("https://tiktok.com/@%s", (String) this.get("username")));
                        break;

                    case THETA:
                        this.put("link", String.format("https://theta.tv/%s", (String) this.get("username")));
                        break;

                    case KICK:
                        this.put("link", String.format("https://kick.com/%s", (String) this.get("username")));
                        break;

                    case YOUNOW:
                        this.put("link", String.format("https://younow.com/%s", (String) this.get("username")));
                        break;

                    case LIVESPACE:
                        this.put("link", String.format("https://live.space/watch/%s", (String) this.get("username")));
                        break;

                    case NOICE:
                        this.put("link", String.format("https://noice.com/%s", (String) this.get("username")));
                        break;

                    case X:
                        this.put("link", String.format("https://x.com/%s", (String) this.get("username")));
                        break;

                    case RUMBLE:
                        this.put("link", String.format("https://rumble.com/c/%s", (String) this.get("username")));
                        break;

                    case LOCO:
                        this.put("link", String.format("https://loco.com/streamers/%s", (String) this.get("username")));
                        break;

                    case CASTERLABS_SYSTEM:
                    case CUSTOM_INTEGRATION:
                        this.put("link", "https://casterlabs.co");
                        break;
                }
            }

            return super.build();
        }

    }

}
