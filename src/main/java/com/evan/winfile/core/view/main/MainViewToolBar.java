package com.evan.winfile.core.view.main;

import com.evan.winfile.core.style.*;
import com.evan.winfile.core.view.ViewManager;
import com.evan.winfile.core.view.label.LabelView;
import com.evan.winfile.module.label.entity.FileLabel;
import com.evan.winfile.runtime.RuntimeConstant;
import com.evan.winfile.runtime.text.TextAlias;
import javafx.animation.PauseTransition;
import javafx.collections.ListChangeListener;
import javafx.geometry.Insets;
import javafx.geometry.NodeOrientation;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.regex.Pattern;

/**
 * @author Evan
 * @date 2022-11-11
 */
@Slf4j
public class MainViewToolBar {

    private static final int BUTTON_HEIGHT = 32;

    private static final HBox LABEL_BOX = new HBox(4);

    public static void load(MainController controller, VBox body){
        ToolBar toolBar = new ToolBar();
        toolBar.setPrefWidth(body.getPrefWidth());
        toolBar.setPrefHeight(60);
        toolBar.setNodeOrientation(NodeOrientation.LEFT_TO_RIGHT);

        HBox toolBox = new HBox(4);
        toolBox.setPrefHeight(toolBar.getPrefHeight());
        toolBox.setPrefWidth(toolBar.getPrefWidth());
        toolBox.setAlignment(Pos.CENTER);
        toolBox.setPadding(new Insets(0, 20, 0, 20));

        loadLabel(toolBox);

        Pane emptyPane = new Pane();
        HBox.setHgrow(emptyPane, Priority.ALWAYS);
        toolBox.getChildren().add(emptyPane);

        loadNavigation(toolBox);

        loadBack(toolBox);

        loadSearch(toolBox);

        body.getChildren().add(toolBox);
    }

    private static void loadLabel(HBox toolBox) {
        LABEL_BOX.setPrefHeight(toolBox.getPrefHeight());
        LABEL_BOX.setAlignment(Pos.CENTER_LEFT);
        RuntimeConstant.FILE_LABELS.addListener((ListChangeListener<FileLabel>) listener -> {
            while (listener.next()){
                if(listener.getAddedSize()>0 || listener.getRemovedSize() >0){
                    loadLabelBox();
                }
            }
        });

        loadLabelBox();

        toolBox.getChildren().add(LABEL_BOX);
    }

    public static void clearLabelSelect(){
        loadLabelBox();
    }

    private static void loadLabelBox() {
        LABEL_BOX.getChildren().clear();
        RuntimeConstant.FILE_LABELS.forEach(fileLabel -> {
            Circle circle = CommonCircle.circle(Color.valueOf(fileLabel.getColor()));
            if(StringUtils.hasText(fileLabel.getName())){
                if(StringUtils.hasText(fileLabel.getName())){
                    CommonTooltip.addHoverTooltip(circle, fileLabel.getName());
                }
            }
            circle.setOnMouseClicked(event -> {
                if(event.getButton().equals(MouseButton.PRIMARY)){
                    CommonCircle.active(circle);
                    if((Boolean) circle.getUserData()){
                        LabelFileLoader.addFilesFromLabel(fileLabel);
                    }else{
                        LabelFileLoader.removeFilesFromLabel(fileLabel);
                    }
                    //List<MarkFile> files = SpringUtils.getBean(RelLabelFileService.class).listByLabel(fileLabel);
                    //  TODO
                }
            });
            LABEL_BOX.getChildren().add(circle);
        });

        HBox addBox = new HBox();
        addBox.setPrefHeight(30);
        addBox.setPrefWidth(30);
        addBox.setAlignment(Pos.CENTER);
        addBox.setOnMouseClicked(event -> {
            ViewManager.newView(LabelView.labelManager(),
                    Modality.WINDOW_MODAL,
                    null,
                    o -> {
                        RuntimeConstant.refreshLabels();
                    });
        });

        ImageView addView = new ImageView();
        addView.setFitWidth(24);
        addView.setFitHeight(24);
        addView.setImage(CommonIcon.Operation.ADD_DEFAULT);
        addBox.hoverProperty().addListener((o, h1, h2) -> {
            if(h2){
                addView.setImage(CommonIcon.Operation.ADD);
            }else{
                addView.setImage(CommonIcon.Operation.ADD_DEFAULT);
            }
        });
        CommonEffect.addEffective(addView);
        addBox.getChildren().add(addView);

        LABEL_BOX.getChildren().add(addBox);
    }

    private static void loadSearch(HBox toolBox) {
        TextField searchText = new TextField();
        searchText.setPrefHeight(32);
        searchText.setPrefWidth(200);
        searchText.setPromptText(TextAlias.INSTANCE.SYSTEM().searchText());
        searchText.setCursor(Cursor.TEXT);
        PauseTransition pause = new PauseTransition(Duration.millis(600));
        searchText.textProperty().addListener((o, oldValue, newValue) -> {
            pause.setOnFinished(event ->
                    RightContentLoader.load(RightContentLoader.CURRENT_DIRECTORY.get(),
                            searchText.getText()));
            pause.playFromStart();
        });

        toolBox.getChildren().add(searchText);
    }

    private static void loadNavigation(HBox toolBox) {

        HBox naviBox = new HBox();
        naviBox.setPrefHeight(toolBox.getPrefHeight());
        naviBox.setPrefWidth(1000);
        naviBox.setAlignment(Pos.CENTER_LEFT);
        Button defaultButton = CommonButton.navigation(TextAlias.INSTANCE.computer());
        defaultButton.setFont(CommonFont.NORMAL);
        defaultButton.setPrefHeight(BUTTON_HEIGHT);
        defaultButton.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                RightContentLoader.load(null);
            }
        });
        naviBox.getChildren().add(defaultButton);
        addMark(naviBox);

        RightContentLoader.CURRENT_DIRECTORY.addListener((o, f1, f2) -> {
            naviBox.getChildren().clear();
            if(f2==null){
                naviBox.getChildren().add(defaultButton);
                addMark(naviBox);
            }else{
                naviBox.getChildren().add(defaultButton);
                addMark(naviBox);
                String[] routes = f2.getPath().split(Pattern.quote(File.separator));
                String path = "";
                for (int i=0;i<routes.length;i++) {
                    path = path + routes[i] + File.separator;
                    Button routeButton = i==(routes.length-1)? CommonButton.navigationActive(routes[i]) : CommonButton.navigation(routes[i]);
                    routeButton.setPrefHeight(BUTTON_HEIGHT);
                    routeButton.setFont(CommonFont.NORMAL);
                    String finalPath = path;
                    routeButton.setOnMouseClicked(event -> {
                        if (event.getButton().equals(MouseButton.PRIMARY)) {
                            RightContentLoader.load(new File(finalPath));
                        }
                    });
                    routeButton.setUserData(path);
                    naviBox.getChildren().add(routeButton);
                    if(i!=routes.length-1){
                        addMark(naviBox);
                    }
                }
            }
        });
        toolBox.getChildren().add(naviBox);
    }

    private static void addMark(HBox naviBox) {
        //Button markButton = CommonButton.navigation(">");
        Label markButton = new Label(">");
        //markButton.setTextFill(CommonColor.White);
        markButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGRAY, new CornerRadii(0), null)));
        markButton.setBorder(CommonBorder.border(Color.LIGHTGRAY, 0, 2));
        //markButton.setPrefWidth(6);
        markButton.setPrefHeight(BUTTON_HEIGHT + 6);
        naviBox.getChildren().add(markButton);
    }

    private static void loadBack(HBox toolBox) {
        ImageView backView = new ImageView();
        backView.setFitHeight(24);
        backView.setFitWidth(24);
        CommonEffect.addEffective(backView);
        backView.setImage(CommonIcon.Operation.NO_BACK);

        RightContentLoader.CURRENT_DIRECTORY.addListener((o, f1, f2) -> {
            if(f2!=null){
                backView.setImage(CommonIcon.Operation.BACK);
            }else{
                backView.setImage(CommonIcon.Operation.NO_BACK);
            }
        });
        backView.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(RightContentLoader.CURRENT_DIRECTORY.get()!=null){
                    RightContentLoader.load(RightContentLoader.CURRENT_DIRECTORY.get().getParentFile(),"", false);
                }
            }
        });
        toolBox.getChildren().add(backView);
    }
}