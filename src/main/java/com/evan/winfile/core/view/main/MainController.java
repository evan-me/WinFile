package com.evan.winfile.core.view.main;

import com.evan.winfile.common.util.FileSizeUtil;
import com.evan.winfile.core.style.*;
import com.evan.winfile.core.view.AbstractController;
import com.evan.winfile.runtime.FileImageContainer;
import com.evan.winfile.runtime.text.TextAlias;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Objects;

/**
 * @author Evan
 * @date 2022-11-11
 */
@Slf4j
public class MainController extends AbstractController {

    @Override
    protected void start() {

        registerBodyEvent();

        loadToolBar();
        this.body.getChildren().add(new Separator(Orientation.HORIZONTAL));

        HBox contentBox = new HBox();
        contentBox.setPrefHeight(this.body.getPrefHeight() - 80);
        contentBox.setPrefWidth(this.body.getPrefWidth() - 40);

        loadLeft(contentBox);

        Separator separator = new Separator(Orientation.VERTICAL);
        separator.setPrefHeight(contentBox.getPrefHeight());
        contentBox.getChildren().add(separator);

        loadFile(contentBox);

        this.body.getChildren().add(contentBox);

        loadBottomBar();
    }

    private void registerBodyEvent() {
        this.body.setOnMouseClicked(event -> {
            if(event.getEventType().equals(MouseEvent.MOUSE_CLICKED)){
                if(!event.getButton().equals(MouseButton.SECONDARY)){
                    ClickStyle.hideRightMenu();
                }
            }
        });
    }

    private void loadBottomBar() {
        ToolBar bottomBar = new ToolBar();
        bottomBar.setPrefWidth(this.body.getPrefWidth());
        bottomBar.setPrefHeight(20);
        bottomBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);

        HBox bottomBox = new HBox(6);
        bottomBox.setPrefHeight(bottomBar.getPrefHeight());
        bottomBox.setAlignment(Pos.BOTTOM_RIGHT);

        RightContentLoader.CLICK_FILE_DATA.addListener((o, f1, item) -> {
            bottomBox.getChildren().clear();
            if(item!=null && item.toPath().getNameCount()>0){
                HBox typeBox = new HBox();
                typeBox.setPrefWidth(50);
                typeBox.setAlignment(Pos.CENTER);

                ImageView fileTypeView = new ImageView();
                fileTypeView.setFitWidth(20);
                fileTypeView.setFitHeight(20);
                fileTypeView.setImage(item.isDirectory()?
                        CommonIcon.File.DIRECTORY : FileImageContainer.loadImage(item.getPath()));
                CommonEffect.addEffective(fileTypeView);
                typeBox.getChildren().add(fileTypeView);
                bottomBox.getChildren().add(typeBox);

                if(!item.isDirectory()){
                    Label fileSize = new Label(FileSizeUtil.formatSize(item.length()));
                    fileSize.setTextAlignment(TextAlignment.RIGHT);
                    fileSize.setFont(CommonFont.NORMAL);
                    fileSize.setPrefWidth(100);
                    fileSize.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
                    bottomBox.getChildren().add(fileSize);
                }

                Label fileName = new Label(item.getName());
                fileName.setFont(CommonFont.NORMAL);
                fileName.setTextFill(item.isDirectory()? CommonColor.Grey : CommonColor.Blue);
                bottomBox.getChildren().add(fileName);
            }else{
                File f = RightContentLoader.CURRENT_DIRECTORY.get();
                if(f!= null && f.listFiles()!=null){
                    Label countLabel = new Label(String.valueOf(Objects.requireNonNull(f.listFiles()).length));
                    countLabel.setFont(CommonFont.MID);
                    countLabel.setTextFill( CommonColor.Blue);
                    bottomBox.getChildren().add(countLabel);


                    Label prefixLabel = new Label(TextAlias.INSTANCE.DIRECTORY().currentFileCount());
                    prefixLabel.setFont(CommonFont.NORMAL);
                    prefixLabel.setTextFill(CommonColor.Grey);
                    bottomBox.getChildren().add(prefixLabel);
                }
            }
        });

        HBox.setMargin(bottomBox, new Insets(0, 20, 0, 0));
        bottomBar.getItems().add(bottomBox);

        this.body.getChildren().add(bottomBar);
    }

    private void loadToolBar() {
        MainViewToolBar.load(this, this.body);
    }

    private void loadLeft(HBox contentBox) {
        VBox leftContent = new VBox(6);
        leftContent.setPrefWidth(300);
        leftContent.setPrefHeight(contentBox.getPrefHeight() - 60);
        leftContent.setAlignment(Pos.TOP_LEFT);
        leftContent.setPadding(new Insets(40, 10, 0, 10));

        MainViewLeft.load(this, leftContent);
        contentBox.getChildren().add(leftContent);
    }

    private void loadFile(HBox contentBox) {
        VBox rightContent = new VBox();
        rightContent.setPrefHeight(contentBox.getPrefHeight());
        rightContent.setPrefWidth(contentBox.getPrefWidth() - 260);

        MainViewContent.load(this, rightContent);

        contentBox.getChildren().add(rightContent);
    }
}