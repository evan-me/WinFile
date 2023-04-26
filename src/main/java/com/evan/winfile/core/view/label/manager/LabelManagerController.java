package com.evan.winfile.core.view.label.manager;

import com.evan.winfile.common.util.DateUtils;
import com.evan.winfile.common.util.FxColorUtil;
import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.common.util.UUIDGenerator;
import com.evan.winfile.core.style.*;
import com.evan.winfile.core.view.AbstractController;
import com.evan.winfile.module.label.entity.FileLabel;
import com.evan.winfile.module.label.service.FileLabelService;
import com.evan.winfile.runtime.text.TextAlias;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.effects.DepthLevel;
import io.github.palexdev.materialfx.enums.ButtonType;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.List;

/**
 * @author Evan
 * @date 2022-11-16
 */
public class LabelManagerController extends AbstractController {

    private List<FileLabel> fileLabels;

    private VBox contentBox = new VBox(14);

    @Override
    protected void start() {

        this.body.setAlignment(Pos.TOP_CENTER);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefWidth(this.body.getPrefWidth() - 20);
        scrollPane.setPrefHeight(this.body.getPrefHeight());

        contentBox.setPrefHeight(scrollPane.getPrefHeight());
        contentBox.setPrefWidth(scrollPane.getPrefWidth());
        contentBox.setAlignment(Pos.TOP_CENTER);
        contentBox.setSpacing(14);
        contentBox.setPadding(new Insets(20, 20, 20, 20));

        loadLabel();

        scrollPane.setContent(contentBox);

        this.body.getChildren().add(scrollPane);
    }

    private void loadLabel() {
        contentBox.getChildren().clear();
        fileLabels = SpringUtils.getBean(FileLabelService.class).list();
        for(FileLabel label:fileLabels){
            loadLabelBox(label);
        }
        loadAddBox();
    }

    private void loadAddBox() {
        HBox addBox = new HBox();
        addBox.setPrefWidth(this.body.getPrefWidth() - 40);
        addBox.setPrefHeight(30);
        addBox.setPrefWidth(this.body.getPrefWidth());

        loadAddButton(addBox);

        contentBox.getChildren().add(addBox);
    }

    private void loadAddButton(HBox addBox) {
        addBox.getChildren().clear();
        addBox.setAlignment(Pos.CENTER_RIGHT);
        //Button add = new Button("添加");
        MFXButton mfxButton = new MFXButton(TextAlias.INSTANCE.OPERATION().add());
        mfxButton.setButtonType(ButtonType.RAISED);
        mfxButton.setDepthLevel(DepthLevel.LEVEL3);
        mfxButton.setRippleColor(CommonColor.AYANO);
        mfxButton.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                loadAddTemp(addBox);
            }
        });
        addBox.getChildren().add(mfxButton);
    }

    private void loadAddTemp(HBox addBox) {
        addBox.getChildren().clear();
        addBox.setAlignment(Pos.CENTER_LEFT);

        ColorPicker colorPicker = new ColorPicker();
        colorPicker.setPrefWidth(120);
        colorPicker.setPrefHeight(addBox.getPrefHeight());
        addBox.getChildren().add(colorPicker);

        TextField labelText = new TextField();
        labelText.setFont(CommonFont.NORMAL);
        labelText.setPrefHeight(addBox.getPrefHeight());
        labelText.setPrefWidth(150);
        //labelText.setPromptText("输入标签名..");
        labelText.setCursor(Cursor.TEXT);
        labelText.setFont(CommonFont.NORMAL);
        Platform.runLater(labelText::requestFocus);
        //labelText.setTextFill(CommonColor.Font.FONT_COLOR);
        HBox.setMargin(labelText, new Insets(0, 0, 0, 10));
        addBox.getChildren().add(labelText);

        Pane emptyPane = new Pane();
        HBox.setHgrow(emptyPane, Priority.ALWAYS);
        addBox.getChildren().add(emptyPane);

        HBox operationBox = new HBox(6);
        operationBox.setAlignment(Pos.CENTER_RIGHT);
        operationBox.setPrefHeight(addBox.getPrefHeight());


        HBox deleteBox = new HBox();
        deleteBox.setPrefHeight(addBox.getPrefHeight());
        deleteBox.setPrefWidth(addBox.getPrefHeight());
        deleteBox.setAlignment(Pos.CENTER);
        ImageView deleteView = CommonImageView.imageView(CommonIcon.Operation.DELETE, 26);
        deleteView.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                loadAddButton(addBox);
            }
        });
        deleteBox.getChildren().add(deleteView);
        operationBox.getChildren().add(deleteBox);

        HBox doneBox = new HBox();
        doneBox.setPrefHeight(addBox.getPrefHeight());
        doneBox.setPrefWidth(addBox.getPrefHeight());
        doneBox.setAlignment(Pos.CENTER);
        ImageView doneView =  CommonImageView.imageView(CommonIcon.Operation.DONE, 26);
        labelText.setOnKeyPressed(event -> {
            if(event.getCode().equals(KeyCode.ENTER)){
                FileLabel label = new FileLabel();
                label.setId(UUIDGenerator.generate());
                label.setName(labelText.getText());
                String color = FxColorUtil.toRGBCode(colorPicker.getValue());
                label.setColor(color);
                label.setFileLabelType(FileLabel.FileLabelType.USER);
                label.setSort(100);
                label.setCreateTime(DateUtils.now());
                SpringUtils.getBean(FileLabelService.class).save(label);
                loadLabel();
                callback.callback(null);
            }
        });
        doneBox.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                FileLabel label = new FileLabel();
                label.setId(UUIDGenerator.generate());
                label.setName(labelText.getText());
                String color = FxColorUtil.toRGBCode(colorPicker.getValue());
                label.setColor(color);
                label.setFileLabelType(FileLabel.FileLabelType.USER);
                label.setSort(100);
                label.setCreateTime(DateUtils.now());
                SpringUtils.getBean(FileLabelService.class).save(label);
                loadLabel();
                callback.callback(null);
            }
        });
        doneBox.getChildren().add(doneView);
        operationBox.getChildren().add(doneBox);

        addBox.getChildren().add(operationBox);
    }

    private void loadLabelBox(FileLabel label) {
        HBox labelBox = new HBox(10);
        labelBox.setPrefWidth(this.body.getPrefWidth() - 40);
        labelBox.setPrefHeight(32);
        labelBox.setAlignment(Pos.CENTER_LEFT);

        Pane colorPane = new Pane();
        colorPane.setPrefWidth(120);
        colorPane.setBackground(new Background(new BackgroundFill(Color.valueOf(label.getColor()), new CornerRadii(6), null)));
        CommonEffect.addEffective(colorPane);
        labelBox.getChildren().add(colorPane);

        Label labelText = new Label(label.getName());
        labelText.setFont(CommonFont.NORMAL);
        labelText.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        HBox.setMargin(labelText, new Insets(0, 0, 0, 10));
        labelBox.getChildren().add(labelText);

        Pane emptyPane = new Pane();
        HBox.setHgrow(emptyPane, Priority.ALWAYS);
        labelBox.getChildren().add(emptyPane);


        HBox operationBox = new HBox(10);
        operationBox.setAlignment(Pos.CENTER_RIGHT);
        operationBox.setPrefHeight(labelBox.getPrefHeight());

        //ImageView colorView = CommonImageView.imageView(CommonIcon.Operation.COLOR, 26);
        //operationBox.getChildren().add(colorView);

        HBox deleteBox = new HBox();
        deleteBox.setPrefHeight(labelBox.getPrefHeight());
        deleteBox.setPrefWidth(labelBox.getPrefHeight());
        deleteBox.setAlignment(Pos.CENTER);
        ImageView deleteView = CommonImageView.imageView(CommonIcon.Operation.DELETE, 26);
        deleteBox.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                SpringUtils.getBean(FileLabelService.class).deleteLabel(label);
                loadLabel();
                callback.callback(null);
            }
        });
        deleteBox.getChildren().add(deleteView);
        operationBox.getChildren().add(deleteBox);

        labelBox.getChildren().add(operationBox);

        contentBox.getChildren().add(labelBox);
    }
}