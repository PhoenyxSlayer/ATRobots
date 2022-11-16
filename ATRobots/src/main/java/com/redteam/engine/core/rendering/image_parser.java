// https://discourse.glfw.org/t/set-window-icon-in-lwjgl-3-1/863/3

package com.redteam.engine.core.rendering;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.system.MemoryStack;
import static org.lwjgl.stb.STBImage.stbi_load;

public class image_parser {
    public ByteBuffer get_image() {
        return image;
    }

    public int get_width() {
        return width;
    }

    public int get_height() {
        return height;
    }

    private final ByteBuffer image;
    private final int width;
    private final int height;

    image_parser(int width, int height, ByteBuffer image) {
        this.image = image;
        this.height = height;
        this.width = width;
    }
    public static image_parser load_image(String path) {
        ByteBuffer image;
        int width, height;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer comp = stack.mallocInt(1);
            IntBuffer w = stack.mallocInt(1);
            IntBuffer h = stack.mallocInt(1);

            image = stbi_load(path, w, h, comp, 4);
            width = w.get();
            height = h.get();
        }
        return new image_parser(width, height, image);
    }
}