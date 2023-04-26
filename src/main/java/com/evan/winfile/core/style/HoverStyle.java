package com.evan.winfile.core.style;

import javafx.scene.layout.Region;

/**
 * @author Administrator
 * @date 2022/11/13 18:35
 */
public class HoverStyle {

    private static final String UNIQUE = "UNIQUE";

    public static void addHoverStyle(Region region){
        addHoverStyle(region, UNIQUE);
    }

    public static void addHoverStyle(Region region, String mark){
        CommonBorder.unHover(region);
        region.hoverProperty().addListener((o, h1, h2) -> {
            if(h2){
                CommonBorder.hover(region);
            }else{
                if(ClickStyle.MARK_MAP.get(mark)==null ||
                    !region.equals(ClickStyle.MARK_MAP.get(mark))){
                    CommonBorder.unHover(region);
                }
            }
        });
    }
}
