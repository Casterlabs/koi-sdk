package co.casterlabs.koi.api.types;

import java.time.Instant;

import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonSerializationMethod;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder(toBuilder = true)
@EqualsAndHashCode()
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public abstract class KoiEvent {
    public final @NonNull SimpleProfile streamer;
    public final @NonNull Instant timestamp;

    public abstract KoiEventType type();

    @JsonSerializationMethod("event_type")
    private JsonElement $serialize_type() {
        return new JsonString(this.type());
    }

    @Override
    public String toString() {
        return Rson.DEFAULT.toJson(this).toString(false);
    }

    static {
        // Duplicated from KoiConnection... just in case...
        KoiEvent.class.getClassLoader().setPackageAssertionStatus("co.casterlabs.koi.api", true);
    }

}
