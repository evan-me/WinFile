package com.evan.winfile.core.style;


import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.core.view.ViewManager;
import com.evan.winfile.core.view.common.CommonView;
import com.evan.winfile.core.view.main.MainViewLeft;
import com.evan.winfile.core.view.main.RightContentLoader;
import com.evan.winfile.module.label.entity.FileLabel;
import com.evan.winfile.module.winfile.entity.FastFile;
import com.evan.winfile.module.winfile.entity.RelLabelFile;
import com.evan.winfile.module.winfile.service.RelLabelFileService;
import com.evan.winfile.runtime.RuntimeConstant;
import com.evan.winfile.runtime.text.TextAlias;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Modality;
import javafx.stage.Popup;
import org.springframework.util.StringUtils;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Administrator
 * @date 2022/11/13 18:42
 */
public class ClickStyle {

    public static Map<String, Region> MARK_MAP = new HashMap<>(16);

    private static final int LINE_HEIGH = 40;

    private static Popup CURREN_OPEN_MENU = null;

    public static void unClickStyle(String mark){
        if(MARK_MAP.containsKey(mark)){
            CommonBorder.unClick(MARK_MAP.get(mark));
            MARK_MAP.remove(mark);
        }
    }

    public static void addClickStyle(Region region, String mark){
        addClickStyle(region, mark, null);
    }

    public static void addClickStyle(Region region, String mark, EventCallback callback){
        region.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getClickCount()==1){
                CommonBorder.click(region);
                if(MARK_MAP.get(mark)!=null && !region.equals(MARK_MAP.get(mark))){
                    CommonBorder.unClick(MARK_MAP.get(mark));
                }
                MARK_MAP.put(mark, region);
            }
            if(callback!=null){
                callback.call(event);
            }
        });
    }
    public static void addFileRightClick(Region region, File file, List<RelLabelFile> relations){
        //      test context menu
        if(file==null || file.toPath().getNameCount()<1){
            return ;
        }
        Popup popup = new Popup();
        VBox clickBox = new VBox(0);
        //clickBox.setPrefWidth(100);
        clickBox.setMaxWidth(400);
        clickBox.setAlignment(Pos.TOP_LEFT);
        clickBox.setPadding(new Insets(4, 10,4,10));
        CommonEffect.addEffective(clickBox, true);
        clickBox.setBackground(new Background(new BackgroundFill(CommonColor.Theme.Background, new CornerRadii(6), null)));
        clickBox.setBorder(CommonBorder.border(CommonColor.Hover.HOVER_BACKGROUD, 6, 1));
        addClickItem(clickBox, file, relations);
        popup.getContent().add(clickBox);
        region.addEventFilter(MouseEvent.MOUSE_CLICKED, event -> {
            if(event.getClickCount()==1){
                hideRightMenu();
                if(event.getButton().equals(MouseButton.SECONDARY)){
                    Point point = MouseInfo.getPointerInfo().getLocation();
                    popup.show(region, point.getX(), point.getY());
                    CURREN_OPEN_MENU = popup;
                }
            }
        });
    }

    public static void hideRightMenu() {
        if(CURREN_OPEN_MENU!=null){
            CURREN_OPEN_MENU.hide();
            CURREN_OPEN_MENU = null;
        }
    }

    private static void addClickItem(VBox clickBox, File file, List<RelLabelFile> relations) {
        //      file info
        HBox infoBox = new HBox(10);
        infoBox.setPadding(new Insets(0, 0, 0, 10));
        infoBox.setPrefHeight(LINE_HEIGH);
        infoBox.setAlignment(Pos.CENTER_LEFT);
        //infoBox.setBorder(CommonBorder.border(CommonColor.Font.MAIN_FONT_COLOR, 12, 2));

        Label filaName = new Label(file.getName());
        filaName.setFont(CommonFont.NORMAL_ITALIC);
        filaName.setTextFill(CommonColor.Font.FONT_COLOR);
        infoBox.getChildren().add(filaName);
        clickBox.getChildren().add(infoBox);

        openMenu(clickBox, file);
        clickBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
        pinToFastFile(clickBox, file);
        clickBox.getChildren().add(new Separator(Orientation.HORIZONTAL));
        markMenu(clickBox, file, relations);
    }

    private static void pinToFastFile(VBox clickBox, File file) {
        HBox createBox = new HBox(10);
        HoverStyle.addHoverStyle(createBox, "POPUP_RIGHT");
        createBox.setPrefHeight(LINE_HEIGH);
        createBox.setPadding(new Insets(0, 0, 0, 10));
        createBox.setAlignment(Pos.CENTER_LEFT);

        SimpleBooleanProperty isPin = new SimpleBooleanProperty(false);
        isPin.set(MainViewLeft.FATS_LIST.stream().filter(FastFile::isPin).anyMatch(f -> f.getPath().equals(file.getPath())));
        ImageView addView = CommonImageView.imageView(CommonIcon.File.PIN, 24);
        createBox.getChildren().add(addView);

        Label addLabel = new Label(TextAlias.INSTANCE.RIGHTCLICK().pinToQuickAccess());
        addLabel.setFont(CommonFont.NORMAL);
        addLabel.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        createBox.getChildren().add(addLabel);

        clickBox.getChildren().add(createBox);
        isPin.addListener((o, b1, b2) -> {
            if(b2){
                addLabel.setText("取消固定");
                addView.setImage(CommonIcon.File.UNPIN);
            }else{
                addLabel.setText(TextAlias.INSTANCE.RIGHTCLICK().pinToQuickAccess());
                addView.setImage(CommonIcon.File.PIN);
            }
        });

        createBox.setOnMouseClicked(event -> {
            MainViewLeft.addFastDirectory(file, !isPin.get());
            isPin.set(!isPin.get());
            CURREN_OPEN_MENU.hide();
        });
    }

    private static void markMenu(VBox clickBox, File file, List<RelLabelFile> relations) {
        splitMarkMenu(clickBox, file, RuntimeConstant.FILE_LABELS, relations);

        HBox closeBox = new HBox(10);
        HoverStyle.addHoverStyle(closeBox, "POPUP_RIGHT");
        closeBox.setPadding(new Insets(0, 0, 0, 10));
        closeBox.setPrefHeight(LINE_HEIGH);
        closeBox.setAlignment(Pos.CENTER_LEFT);
        Label close = new Label(TextAlias.INSTANCE.OPERATION().cancel());
        close.setFont(CommonFont.NORMAL);
        close.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        closeBox.setOnMouseClicked(event -> {
            CURREN_OPEN_MENU.hide();
        });

        closeBox.getChildren().add(close);

        clickBox.getChildren().add(closeBox);
    }

    private static void splitMarkMenu(VBox clickBox, File file, List<FileLabel> labels, List<RelLabelFile> relations) {
        HBox markBox = new HBox(4);
        markBox.setPadding(new Insets(0, 0, 0, 10));
        markBox.setPrefHeight(LINE_HEIGH);
        //markBox.setPrefWidth(RuntimeConstant.FILE_LABELS.size() * 30);
        markBox.setAlignment(Pos.CENTER_LEFT);

        labels.forEach(label -> {
            HBox singleBox = new HBox();
            singleBox.setAlignment(Pos.CENTER);
            HoverStyle.addHoverStyle(singleBox, "POPUP_MARK");

            Circle circle = CommonCircle.circle(Color.valueOf(label.getColor()));
            if(relations!=null){
                relations.stream().filter(r -> r.getFileLabelId().equals(label.getId())).findAny().ifPresent(
                        r -> CommonCircle.active(circle)
                );
            }
            if(StringUtils.hasText(label.getName())){
                CommonTooltip.addHoverTooltip(circle, label.getName());
            }
            singleBox.getChildren().add(circle);
            singleBox.setOnMouseClicked(event -> {
                if(event.getClickCount()==1 && event.getButton().equals(MouseButton.PRIMARY)){
                    CommonCircle.active(circle);
                    if((Boolean) circle.getUserData()){
                        SpringUtils.getBean(RelLabelFileService.class).addFileLabel(file, label);
                    }else{
                        SpringUtils.getBean(RelLabelFileService.class).removeRelation(file, label);
                    }
                    RightContentLoader.load(RightContentLoader.CURRENT_DIRECTORY.get());
                }
            });

            markBox.getChildren().add(singleBox);
        });
        clickBox.getChildren().add(markBox);
    }

    private static void openMenu(VBox clickBox, File file) {

        HBox createBox = new HBox(10);
        HoverStyle.addHoverStyle(createBox, "POPUP_RIGHT");
        createBox.setPadding(new Insets(0, 0, 0, 10));
        createBox.setPrefHeight(LINE_HEIGH);
        createBox.setAlignment(Pos.CENTER_LEFT);

        Label addLabel = new Label(TextAlias.INSTANCE.RIGHTCLICK().openWithWindows());
        addLabel.setFont(CommonFont.NORMAL);
        addLabel.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        createBox.getChildren().add(addLabel);

        clickBox.getChildren().add(createBox);
        createBox.setOnMouseClicked(event -> {
            try{
                Desktop.getDesktop().open(file);
            }catch (Exception e){
                ViewManager.newView(CommonView.warn(), Modality.APPLICATION_MODAL, e.getMessage());
            }
            if(CURREN_OPEN_MENU!=null){
                CURREN_OPEN_MENU.hide();
            }
        });
    }

    public static void hideAll() {
        new ArrayList<>(MARK_MAP.keySet()).forEach(ClickStyle::unClickStyle);
        MARK_MAP.clear();
        hideRightMenu();
    }
}
