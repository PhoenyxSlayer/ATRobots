package com.redteam.engine.core.rendering;

import java.util.Objects;

public final class Texture {
    private final int id;

    public Texture(int id) {
        this.id = id;
    }

    public int id() {
        return id;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        Texture that = (Texture) obj;
        return this.id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Texture[" +
                "id=" + id + ']';
    }
}
