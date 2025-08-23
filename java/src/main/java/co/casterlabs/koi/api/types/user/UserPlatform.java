package co.casterlabs.koi.api.types.user;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.stream.Collectors;

public enum UserPlatform {
    // @formatter:off
    CAFFEINE( "Caffeine",  "#0000ff", "https://caffeine.tv"), // June 7, 2020. The platform shutdown on June 26, 2024.
    TWITCH(   "Twitch",    "#7d2bf9", "https://twitch.tv"),   // September 11, 2020
    TROVO(    "Trovo",     "#088942", "https://trovo.live"),  // January 23, 2021
    GLIMESH(  "Glimesh",   "#0e1726", "https://glimesh.tv"),  // March 15, 2021. The platform shutdown on July 1st, 2023.
    BRIME(    "Brime",     "#fc3537", "https://brime.tv"),    // March 21, 2021. The platform shutdown on ~December 5, 2022.
    YOUTUBE(  "YouTube",   "#ff0000", "https://youtube.com"), // May 18, 2022
    DLIVE(    "DLive",     "#ffd300", "https://dlive.tv"),    // October 11, 2022
    TIKTOK(   "TikTok",    "#fe2c55", "https://tiktok.com"),  // October 17, 2022
    THETA(    "Theta",     "#161a24", "https://theta.tv"),    // November 3, 2022
    KICK(     "Kick",      "#53fc18", "https://kick.com"),    // March 10, 2023
    YOUNOW(   "YouNow",    "#46d684", "https://younow.com"),  // Work began on June 16, 2023. It has since stalled due to lack of interest.
    LIVESPACE("LiveSpace", "#fe0070", "https://live.space"),  // Work began on February 29, 2024. The platform shutdown on May 1, 2024.
    NOICE(    "Noice",     "#b914ff", "https://noice.com"),   // The platform shutdown on June 9, 2025, before work could begin.

    // Work in progress :^)
    X(     "𝕏",      "#000000", "https://x.com"),
    RUMBLE("Rumble", "#8cc736", "https://rumble.com"),
    LOCO(  "Loco",   "#f55800", "https://loco.com"),
    // @formatter:on

    // Other.
    CASTERLABS_SYSTEM("System", "#ea4c4c", ""),
    CUSTOM_INTEGRATION("Custom Integration 🔧", "#ea4c4c", ""), // For custom events created by you.
    ;

    public static final Map<String, String> NAMES = Collections.unmodifiableMap(
        Arrays.asList(values())
            .stream()
            .collect(Collectors.toMap((p) -> p.name(), (v) -> v.friendlyName))
    );

    public final String friendlyName;
    public final String color;
    public final String link;

    public final SimpleProfile systemProfile;
    public final User systemUser;

    private UserPlatform(String friendlyName, String color, String link) {
        this.friendlyName = friendlyName;
        this.color = color;
        this.link = link;

        this.systemProfile = SimpleProfile.builder()
            .bothIds(this.name())
            .platform(this)
            .build();

        this.systemUser = User.builder(this.systemProfile)
            .username(this.name())
            .displayname(this.friendlyName)
            .link(link)
            .color(color)
            .build();
    }

    @Override
    public String toString() {
        return this.friendlyName;
    }

}
