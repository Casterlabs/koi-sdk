package co.casterlabs.koi.api.types.user;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum Pronouns {
    HE("he/him"),
    SHE("she/her"),
    IT("it/its"),
    THEY("they/them"),
    ANY("(any)"),
    ASK("(ask)"),
    AVOID("(avoid, use name)"),
    OTHER("(other)");

    public static final Map<String, String> NAMES = Collections.unmodifiableMap(
        Arrays.asList(values())
            .stream()
            .collect(Collectors.toMap((p) -> p.name(), (v) -> v.str))
    );

    private String str;

    @Override
    public String toString() {
        return this.str;
    }

}
