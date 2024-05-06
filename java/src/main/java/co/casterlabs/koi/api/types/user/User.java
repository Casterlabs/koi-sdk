package co.casterlabs.koi.api.types.user;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.jetbrains.annotations.Nullable;

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
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class User extends SimpleProfile {
    public final @NonNull @Singular(ignoreNullCollections = true) List<UserRoles> roles;

    public final @NonNull @JsonExclude @Singular(ignoreNullCollections = true) List<UserBadge> badges;

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

    public final @NonNull String color;

    public final @NonNull String username;

    public final @NonNull String displayname;

    public final @NonNull Pronouns pronouns;

    public final @NonNull String bio;

    public final @NonNull String link;

    @JsonField("image_link")
    public final @NonNull String imageLink;

    /**
     * The amount of people who follow this User. YouTube Subscribers are considered
     * "followers."
     * 
     * You may receive a {@link FollowEvent}, in which case you can increment this
     * value while you wait on a fresh {@link UserUpdateEvent}.
     * 
     * @implSpec Will be -1 if the information is not available.
     */
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
    @JsonField("subscriber_count")
    public @NonNull Long subscriberCount;

    @SuppressWarnings("deprecation")
    public SimpleProfile cloneSimpleProfile() {
        return new SimpleProfile(this.UPID, this.id, this.channelId, this.platform, this.extraMetadata);
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

    /**
     * Populates the color and pronouns based in intrinsics. Use this to give your
     * custom integration a more consistent feel.
     */
    public static void populateMissingInfo(UserBuilder<?, ?> builder) {
        if (builder.bio == null) {
            builder.bio(""); // So the below code doesn't break.
        }

        if (builder.color == null) {
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
            Matcher m = PATTERN.matcher(builder.bio.toLowerCase());
            if (m.find()) {
                String group = m.group();
                String color = group.substring(
                    group.indexOf(':') + 1,
                    group.length() - 1
                ); // [color:#ea4c4c] -> #ea4c4c

                builder.color(color);
            } else {
                int hashValue = builder.username.hashCode();
                int index = Math.abs(hashValue);

                String color = COLORS[index % COLORS.length];
                builder.color(color);
            }
        }

        if (builder.pronouns == null) {
            final Pattern PATTERN = Pattern.compile("(\\[pronouns:[a-z]+\\])");

            // Try to grab pronouns from their bio if they have it.
            Matcher m = PATTERN.matcher(builder.bio.toLowerCase());
            if (m.find()) {
                String group = m.group();
                String str = group.substring(
                    group.indexOf(':') + 1,
                    group.length() - 1
                ).toUpperCase(); // [pronouns:he] -> HE

                try {
                    builder.pronouns(Pronouns.valueOf(str));
                } catch (IllegalArgumentException ignored) {
                    // Invalid pronoun, ignore it.
                    builder.pronouns(Pronouns.UNKNOWN);
                }
            } else {
                builder.pronouns(Pronouns.UNKNOWN);
            }
        }

        if (builder.imageLink == null) {
            final String DEFAULT_PROFILE_PICTURE = "data:image/svg+xml,%3C%3Fxml%20version%3D%221.0%22%20encoding%3D%22UTF-8%22%20standalone%3D%22no%22%3F%3E%0A%3Csvg%20viewBox%3D%220%200%2024%2024%22%20fill%3D%22none%22%20stroke%3D%22whitesmoke%22%20stroke-width%3D%221.5%22%20version%3D%221.1%22%20xmlns%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%20xmlns%3Asvg%3D%22http%3A%2F%2Fwww.w3.org%2F2000%2Fsvg%22%3E%0A%20%20%20%20%3Crect%20style%3D%22fill%3A%23676767%3Bfill-opacity%3A1%3Bstroke%3Anone%3B%22%20width%3D%2224%22%20height%3D%2224%22%20x%3D%220%22%20y%3D%220%22%20%2F%3E%0A%20%20%20%20%3Cpath%20d%3D%22M20%2021v-2a4%204%200%200%200-4-4H8a4%204%200%200%200-4%204v2%22%20%2F%3E%0A%20%20%20%20%3Ccircle%20cx%3D%2212%22%20cy%3D%227%22%20r%3D%224%22%20%2F%3E%0A%3C%2Fsvg%3E%0A";
            builder.imageLink(DEFAULT_PROFILE_PICTURE);
        }
    }

}
