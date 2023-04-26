package com.evan.winfile.core.style;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * @author Evan
 * @date 2022-09-29
 */
public class CommonBorder {

    public static final Border emptyBorder = border(Color.valueOf("#f4f4f4"), 0d);

    public static Border red(){
        return border(Color.RED, 1d);
    }

    public static Border border(Color color, double width){
        return border(color, 0, width);
    }

    public static Border border(Color color, double radii, double width){
        return new Border(new BorderStroke(color, BorderStrokeStyle.SOLID, new CornerRadii(radii), new BorderWidths(width)));
    }

    public static void removeBorder(Region node){
        CommonBorder.fillBorder(node,
                CommonBorder.border(CommonColor.Theme.Background, 0, 1),
                CommonColor.Theme.Background);
    }

    public static void fillBorder(Region node, Border border){
        fillBorder(node, border, null);
    }

    public static void fillBorder(Region node, Border border, Color color){
        node.setBorder(border);
        node.setBackground(new Background(new BackgroundFill(color, border.getStrokes().get(0).getRadii(), null)));
    }

    public static void hover(Region node){
        CommonBorder.fillBorder(node,
                CommonBorder.border(CommonColor.Hover.HOVER_BACKGROUD, 4, 1),
                CommonColor.Hover.HOVER_BACKGROUD);
    }

    public static void unHover(Region node){
        removeBorder(node);
    }

    public static void click(Region node){
        CommonBorder.fillBorder(node,
                CommonBorder.border(CommonColor.Hover.HOVER_BORDER, 4, 1),
                CommonColor.Hover.HOVER_BACKGROUD);
    }

    public static void unClick(Region node){
        removeBorder(node);
    }
}