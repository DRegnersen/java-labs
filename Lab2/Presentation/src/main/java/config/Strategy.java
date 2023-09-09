package config;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Strategy {
    VALIDATE("validate"),
    UPDATE("update"),
    CREATE("create"),
    CREATE_DROP("create-drop"),
    NONE("none");

    @NonNull
    @Getter
    private final String strategy;

    @Override
    public String toString() {
        return this.strategy;
    }
}
