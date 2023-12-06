package com.nikitapopov.librarymvcboot.models.enums;

public enum Authority {

    ROLE_USER("ROLE_USER"), ROLE_ADMIN("ROLE_ADMIN");

    private final String role;

    Authority(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return this.role;
    }
}
