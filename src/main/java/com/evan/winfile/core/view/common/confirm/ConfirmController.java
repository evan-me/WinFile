package com.evan.winfile.core.view.common.confirm;

import com.evan.winfile.core.style.CommonButton;
import com.evan.winfile.core.style.CommonFont;
import com.evan.winfile.core.view.AbstractController;
import com.evan.winfile.runtime.text.TextAlias;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * @author Evan
 * @date 2022-10-12
 */
public class ConfirmController extends AbstractController {


    @Override
    protected void start() {
        this.body.setAlignment(Pos.CENTER);
        this.body.setSpacing(30);

        loadContent();

        this.body.setOnKeyPressed(key -> {
            if(key.getCode().equals(KeyCode.ENTER)){
                ((Stage) body.getScene().getWindow()).close();
                callback.callback(Boolean.TRUE);
            }
        });
    }



    private void loadContent() {

        Label label = new Label(String.valueOf(this.data));
        label.setFont(CommonFont.NORMAL);
        label.setTextFill(Color.BLACK);
        this.body.getChildren().add(label);

        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPrefWidth(this.body.getPrefWidth());
        buttonBox.setPrefHeight(50);

        Button confirm = CommonButton.success(TextAlias.INSTANCE.OPERATION().confirm());
        confirm.setPrefWidth(60);
        confirm.setPrefHeight(30);
        confirm.setOnMouseClicked(event -> {
            ((Stage) confirm.getScene().getWindow()).close();
            callback.callback(true);
        });
        buttonBox.getChildren().add(confirm);
        Button cancel = CommonButton.normal(TextAlias.INSTANCE.OPERATION().cancel());
        cancel.setPrefWidth(60);
        cancel.setPrefHeight(30);
        cancel.setOnMouseClicked(event -> {
            ((Stage) cancel.getScene().getWindow()).close();
            callback.callback(false);
        });
        HBox.setMargin(cancel, new Insets(0, 0, 0, 30));
        buttonBox.getChildren().add(cancel);
        this.body.getChildren().add(buttonBox);
    }
}