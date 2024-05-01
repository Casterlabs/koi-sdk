package co.casterlabs.koi.api.types.events;

import co.casterlabs.koi.api.types.KoiEventType;
import co.casterlabs.koi.api.types.MessageMeta;
import co.casterlabs.rakurai.json.annotating.JsonClass;
import co.casterlabs.rakurai.json.annotating.JsonField;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.experimental.SuperBuilder;

@SuperBuilder()
@EqualsAndHashCode(callSuper = true)
@JsonClass(exposeAll = true, unsafeInstantiation = true)
public class MessageMetaEvent extends MessageMeta {
    @JsonField("meta_id")
    public final @NonNull String metaId;

    @Override
    public KoiEventType type() {
        return KoiEventType.META;
    }

}
