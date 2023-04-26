package com.evan.winfile.core.style;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;

/**
 * @author Evan
 * @date 2022-11-15
 */
public class CommonEffect {


    private static final DropShadow RIGHT_BOTOM = new DropShadow(6, 1, 1, CommonColor.Theme.Shadow);

    private static final DropShadow RIGHT_BOTOM_DEEPPER = new DropShadow(6, 1, 1, CommonColor.Theme.Shadow);

    public static void addEffective(Node node){
        //node.setEffect(RIGHT_BOTOM);
    }

    public static void addEffective(Node node, boolean deeper){
        if(deeper){
            node.setEffect(RIGHT_BOTOM_DEEPPER);
        }else{
            node.setEffect(RIGHT_BOTOM);
        }
    }

    public static void removeEffective(Node node) {
        node.setEffect(null);
    }
}