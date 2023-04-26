package com.evan.winfile.core.style;

import javafx.scene.paint.Color;


/**
 * @author Evan
 * @date 2022-01-13
 */
public interface CommonColor {
    interface Theme{
        Color Background = Color.valueOf("#f4f4f4");
        Color Shadow = Color.valueOf("#6c6c6cbd");
    }
    interface Hover{
        Color HOVER_BACKGROUD = Color.valueOf("#cce8ff");
        Color HOVER_BORDER = Color.valueOf("#99d1ff");
    }
    interface Font{
        Color FONT_COLOR = Color.valueOf("#808080"); //d3d3d3
        Color MAIN_FONT_COLOR = Color.valueOf("#4c4c4c"); //
    }
    Color Blue = Color.valueOf("#4ca0f7");
    Color Green = Color.valueOf("#95e1c6");
    Color Grey = Color.valueOf("#808080"); //d3d3d3
    Color LIGHT_GREY = Color.LIGHTGRAY;
    Color Yellow = Color.valueOf("#ebcb8b");
    Color AYANO = Color.valueOf("#ff6666");
    Color White = Color.WHITE;
    Color Black = Color.BLACK;
}