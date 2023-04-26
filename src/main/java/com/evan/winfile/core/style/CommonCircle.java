package com.evan.winfile.core.style;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @author Administrator
 * @date 2022/11/15 22:22
 */
public class CommonCircle {
    //public static Circle circle(Color color){
    //    return circle(color, false);
    //}

    public static Circle circle(Color color){
        Circle circle = new Circle();
        circle.setUserData(false);
        CommonEffect.addEffective(circle);
        circle.setRadius(6);
        circle.setStroke(color);
        circle.setStrokeWidth(4);
        circle.setFill(color);
        return circle;
    }

    public static Circle stackCircle(Color color){
        Circle circle = new Circle();
        circle.setUserData(false);
        CommonEffect.addEffective(circle);
        circle.setRadius(8);
        circle.setStroke(CommonColor.Theme.Background);
        circle.setStrokeWidth(2);
        circle.setFill(color);
        return circle;
    }

    public static void active(Circle circle){
        if((Boolean) circle.getUserData()){
            circle.setUserData(false);
            circle.setRadius(circle.getRadius() - 2);
            circle.setStrokeWidth(circle.getStrokeWidth() - 2);
            circle.setFill(circle.getStroke());
            CommonEffect.removeEffective(circle);
        }else{
            circle.setUserData(true);
            circle.setRadius(circle.getRadius() + 2);
            circle.setStrokeWidth(circle.getStrokeWidth() + 2);
            circle.setFill(CommonColor.Theme.Background);
            CommonEffect.addEffective(circle, true);
        }
    }
}
