package io.github.yourname.playerdataaddon.api;

public enum PlayerDataKey {
    DATA1("data1"),
    DATA2("data2");

    private final String id;

    PlayerDataKey(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
