package com.evan.winfile.core.style;

import io.github.palexdev.materialfx.controls.MFXButton;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

/**
 * @author Evan
 * @date 2022-10-12
 */
public class CommonButton {

    public static MFXButton success(String text){
        MFXButton button = button(text, CommonColor.LIGHT_GREY, Color.valueOf("#6bcf45"));
        button.setTextFill(CommonColor.White);
        button.setDefaultButton(true);
        return button;
    }

    public static MFXButton normal(String text) {
        MFXButton button = button(text, CommonColor.Hover.HOVER_BORDER, CommonColor.Hover.HOVER_BACKGROUD);
        button.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        return button;
    }

    public static MFXButton navigation(String text) {
        MFXButton button = button(text, Color.LIGHTGRAY, Color.LIGHTGRAY);
        button.setBorder(CommonBorder.border(Color.LIGHTGRAY, 0, 2));
        //button.setFont(CommonFont.NORMAL);
        button.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        button.hoverProperty().addListener((o, h1, h2) -> {
            if(h2){
                button.setBackground(new Background(new BackgroundFill(CommonColor.Hover.HOVER_BORDER, new CornerRadii(0), null)));
                button.setTextFill(CommonColor.White);
            }else{
                button.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(0), null)));
                button.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
            }
        });
        return button;
    }

    public static MFXButton navigationActive(String text) {
        MFXButton button = button(text, CommonColor.Hover.HOVER_BORDER, CommonColor.Hover.HOVER_BORDER);
        button.setBorder(CommonBorder.border(CommonColor.Hover.HOVER_BORDER, 0, 2));
        button.setTextFill(CommonColor.White);
        return button;
    }


    public static MFXButton blue(String text) {
        MFXButton button = button(text, CommonColor.White, Color.STEELBLUE);
        button.setTextFill(Color.WHITE);
        return button;
    }

    public static MFXButton delete(String text) {
        MFXButton button = button(text, CommonColor.LIGHT_GREY, Color.ORANGERED);
        button.setTextFill(Color.WHITE);
        return button;
    }

    public static MFXButton button(String text){
        return button(text, null);
    }

    public static MFXButton button(String text, Color border){
        return button(text, null, null);
    }

    public static MFXButton button(String text, Color border, Color background){
        MFXButton button = new MFXButton(text);
        button.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        button.setMnemonicParsing(false);
        button.setDefaultButton(false);
        button.setBorder(CommonBorder.border(border==null? CommonColor.Grey : border, 4,1));
        button.setBackground(new Background(new BackgroundFill(background==null? CommonColor.White : background, new CornerRadii(4), null)));
        return button;
    }
}