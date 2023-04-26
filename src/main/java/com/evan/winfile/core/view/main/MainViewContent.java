package com.evan.winfile.core.view.main;

import com.evan.winfile.core.style.CommonBorder;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Evan
 * @date 2022-11-11
 */
@Slf4j
public class MainViewContent {

    public static final SimpleBooleanProperty MAIN = new SimpleBooleanProperty(true);

    public static void load(MainController controller, VBox rightContent) {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefHeight(rightContent.getPrefHeight());
        scrollPane.setPrefWidth(rightContent.getPrefWidth());
        scrollPane.setHmax(rightContent.getPrefWidth());
        CommonBorder.removeBorder(scrollPane);
        scrollPane.setPadding(new Insets(10));

        RightContentLoader.initContent(scrollPane);

        LabelFileLoader.initContent(scrollPane);

        scrollPane.setContent(RightContentLoader.CONTENT);
        final double speed = 0.001D;

        scrollPane.getContent().setOnScroll(scrollEvent -> {
            double deltaY = scrollEvent.getDeltaY() * speed;
            scrollPane.setVvalue(scrollPane.getVvalue() - deltaY);
        });
        MAIN.addListener((o, m1, m2) -> {
            if(m1 != m2){
                if(m2){
                    scrollPane.setContent(RightContentLoader.CONTENT);
                    scrollPane.getContent().setOnScroll(scrollEvent -> {
                        double deltaY = scrollEvent.getDeltaY() * speed;
                        scrollPane.setVvalue(scrollPane.getVvalue() - deltaY);
                    });
                }else{
                    scrollPane.setContent(LabelFileLoader.CONTENT);

                    scrollPane.getContent().setOnScroll(scrollEvent -> {
                        double deltaY = scrollEvent.getDeltaY() * speed;
                        scrollPane.setVvalue(scrollPane.getVvalue() - deltaY);
                    });
                }
            }
        });

        rightContent.getChildren().add(scrollPane);
    }
}