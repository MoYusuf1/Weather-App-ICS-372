package edu.metrostate.utils;

import javafx.scene.image.Image;

import java.util.Objects;

public final class ImageUtils {

    private ImageUtils() {
        throw new IllegalAccessError("Don't do me like that!");
    }

    public static Image getImage(String imagePath) {
        return new Image(Objects.requireNonNull(ImageUtils.class.getResource(imagePath)).toExternalForm());
    }

}
