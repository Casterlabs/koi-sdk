package co.casterlabs.koi.api.types.user;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@NoArgsConstructor
@JsonClass(exposeAll = true)
@EqualsAndHashCode(callSuper = true)
public class User extends SimpleProfile {
    private List<UserRoles> roles;

    private List<String> badges;

    private String color;

    private String username;

    private String displayname;

    private Pronouns pronouns;

    private String bio;

    private String link;

    @JsonField("image_link")
    private String imageLink;

    @JsonField("followers_count")
    private long followersCount;

    @JsonField("subscriber_count")
    private long subCount;

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
