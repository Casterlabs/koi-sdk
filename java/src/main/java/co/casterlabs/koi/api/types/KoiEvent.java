package co.casterlabs.koi.api.types;

import java.time.Instant;

import co.casterlabs.koi.api.types.user.SimpleProfile;
import co.casterlabs.rakurai.json.Rson;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonSerializationMethod;
import co.casterlabs.rakurai.json.element.JsonElement;
import co.casterlabs.rakurai.json.element.JsonString;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@EqualsAndHashCode
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public abstract class KoiEvent {
    /**
     * The streamer's account this belongs to.
     */
    public final @NonNull SimpleProfile streamer = null;

    /**
     * The time this event was processed by the backend.
     */
    public final @NonNull Instant timestamp = null;

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
