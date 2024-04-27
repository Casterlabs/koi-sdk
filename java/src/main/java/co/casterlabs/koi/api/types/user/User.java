package co.casterlabs.koi.api.types.user;

import java.util.List;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@NoArgsConstructor
@JsonClass(exposeAll = true)
public class User extends SimpleProfile {
    public List<UserRoles> roles;

    public List<String> badges;

    public String color;

    public String username;

    public String displayname;

    public Pronouns pronouns = Pronouns.UNKNOWN;

    public String bio;

    public String link;

    @JsonField("image_link")
    public String imageLink;

    @JsonField("followers_count")
    public long followersCount = -1;

    @JsonField("subscriber_count")
    public long subCount = -1;

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
