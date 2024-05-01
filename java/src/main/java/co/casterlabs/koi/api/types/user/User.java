package co.casterlabs.koi.api.types.user;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.Singular;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class User extends SimpleProfile {
    public final @NonNull @Singular(ignoreNullCollections = true) List<UserRoles> roles;

    public final @NonNull @Singular(ignoreNullCollections = true) List<String> badges;

    public final @NonNull String color;

    public final @NonNull String username;

    public final @NonNull String displayname;

    public final @NonNull Pronouns pronouns;

    public final @NonNull String bio;

    public final @NonNull String link;

    @JsonField("image_link")
    public final @NonNull String imageLink;

    /**
     * Will be -1 if the information is not available.
     */
    @JsonField("followers_count")
    public final @NonNull Long followersCount;

    /**
     * Will be -1 if the information is not available.
     */
    @JsonField("subscriber_count")
    public final @NonNull Long subCount;

    public SimpleProfile cloneSimpleProfile() {
        return new SimpleProfile(this.UPID, this.id, this.channelId, this.platform);
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

}
