package com.evan.winfile.core.style;

import javafx.scene.Node;
import javafx.scene.control.Control;
import javafx.scene.control.Tooltip;

import java.awt.*;

/**
 * @author Evan
 * @date 2022-11-21
 */
public class CommonTooltip {

    public static void addHoverTooltip(Node node, String tips){
        Tooltip tooltip = new Tooltip(tips);
        tooltip.setFont(CommonFont.SMALL);
        node.hoverProperty().addListener((o, h1, h2) -> {
            if(h1 != h2 && h2){
                Point point = MouseInfo.getPointerInfo().getLocation();
                tooltip.show(node, point.getX() + 6, point.getY() + 6);
            }else{
                tooltip.hide();
            }
        });
    }

    public static void addTooltip(Control control, String tips){
        Tooltip tooltip = new Tooltip(tips);
        tooltip.setFont(CommonFont.SMALL);
        control.setTooltip(tooltip);
    }

}