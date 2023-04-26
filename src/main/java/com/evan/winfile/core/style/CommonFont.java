package com.evan.winfile.core.style;

import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;

/**
 * @author Evan
 * @date 2022-11-15
 */
public class CommonFont {

    private static final int FONT_BASE_SIZE = 16;

    public static final Font HUGE = new Font(FONT_BASE_SIZE + 16);
    public static final Font BIG = new Font(FONT_BASE_SIZE + 8);
    public static final Font MID = new Font(FONT_BASE_SIZE + 4);
    public static final Font NORMAL = new Font(FONT_BASE_SIZE);
    public static final Font NORMAL_ITALIC = Font.font("Verdana", FontWeight.NORMAL, FontPosture.ITALIC, FONT_BASE_SIZE + 4);
    public static final Font SMALL = new Font(FONT_BASE_SIZE - 4);


}