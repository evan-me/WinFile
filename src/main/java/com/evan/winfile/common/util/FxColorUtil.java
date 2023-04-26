package com.evan.winfile.common.util;

import javafx.scene.paint.Color;

/**
 * @author Evan
 * @date 2022-11-16
 */
public class FxColorUtil {
    public static String toRGBCode( Color color ) {
        return String.format( "#%02X%02X%02X",
                (int)( color.getRed() * 255 ),
                (int)( color.getGreen() * 255 ),
                (int)( color.getBlue() * 255 ) );
    }
}