package com.evan.winfile.core.view.init;

import com.evan.winfile.core.view.AbstractController;
import com.evan.winfile.core.view.ViewManager;
import com.evan.winfile.core.view.main.MainView;
import com.evan.winfile.runtime.RuntimeConstant;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.StackPane;

/**
 * @author Evan
 * @date 2022-11-11
 */
public class InitController extends AbstractController {

    @Override
    protected void start() {
        this.body.setAlignment(Pos.CENTER);

        StackPane stackPane = new StackPane();
        stackPane.setPrefWidth(this.body.getPrefWidth());
        stackPane.setPrefHeight(this.body.getPrefHeight());
        stackPane.setAlignment(Pos.CENTER);

        ProgressIndicator indicator = new ProgressIndicator(-1);
        indicator.setPrefHeight(50);
        indicator.setPrefWidth(50);
        indicator.setMaxWidth(50);
        indicator.setMaxHeight(50);

        stackPane.getChildren().add(indicator);

        this.body.getChildren().add(stackPane);

        Platform.runLater(() -> {
            RuntimeConstant.initProgram();
            indicator.setProgress(1);
            ViewManager.switchView(MainView.main());
        });
    }
}