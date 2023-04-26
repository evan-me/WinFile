package com.evan.winfile.core.style;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * @author Evan
 * @date 2022-11-16
 */
public class CommonImageView {

    public static ImageView imageView(Image image, int size){
        return imageView(image, size, size);
    }

    public static ImageView imageView(Image image, int width, int height){
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        CommonEffect.addEffective(imageView);
        return imageView;
    }
}