package co.casterlabs.koi.api;

public enum KoiChatterType {
    /**
     * The current authenticated user.
     */
    CLIENT,

    /**
     * A puppet account. Use loginPuppet() to link.
     */
//    PUPPET,

    /**
     * The system sender. Usually @/Casterlabs or the account we set up for your
     * Client ID.
     */
    SYSTEM;

}
