package co.casterlabs.koi.api.types.user;

import org.jetbrains.annotations.Nullable;

import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@AllArgsConstructor(staticName = "of")
@EqualsAndHashCode()
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class UserBadge {
    public final @NonNull String name;

    @JsonField("image_link")
    public final @NonNull String imageLink;

    public final @Nullable String provider; // For external providers.
}