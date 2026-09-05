package io.github.yourname.playerdataaddon.api;

public enum PlayerDataKey {
    HValue("hvalue"), // 0 ~ 1000
    LastTop("lasttop");

    private final String id;

    PlayerDataKey(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
