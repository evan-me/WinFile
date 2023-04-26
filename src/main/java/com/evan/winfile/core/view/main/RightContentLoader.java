package com.evan.winfile.core.view.main;

import com.evan.winfile.core.style.ClickStyle;
import com.evan.winfile.core.style.CommonBorder;
import com.evan.winfile.core.view.ViewManager;
import com.evan.winfile.core.view.common.CommonView;
import com.evan.winfile.runtime.text.TextAlias;
import javafx.beans.property.SimpleObjectProperty;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import lombok.extern.slf4j.Slf4j;

import java.awt.*;
import java.io.File;

/**
 * @author Administrator
 * @date 2022/11/12 14:59
 */
@Slf4j
public class RightContentLoader {

    public static final FlowPane CONTENT = new FlowPane();

    public static final SimpleObjectProperty<HBox> CLICK_FILE = new SimpleObjectProperty<>();
    public static final SimpleObjectProperty<File> CLICK_FILE_DATA = new SimpleObjectProperty<>();

    public static final SimpleObjectProperty<File> CURRENT_DIRECTORY = new SimpleObjectProperty<>();

    public static void initContent(ScrollPane scrollPane) {
        CONTENT.setHgap(40);
        CONTENT.setVgap(20);
        CommonBorder.removeBorder(CONTENT);
        CONTENT.setPrefHeight(scrollPane.getPrefHeight() - 60);
        CONTENT.setPrefWidth(scrollPane.getPrefWidth() - 60);
        CONTENT.setAlignment(Pos.TOP_LEFT);

        load(null);
    }

    public static void load(File file){
        load(file, "");
    }

    public static void load(File file, String searchText){
        load(file,searchText,  true);
    }

    public static void load(File file, String searchText, boolean record){
        //  清空Label相关选项
        MainViewContent.MAIN.set(true);
        MainViewToolBar.clearLabelSelect();
        LabelFileLoader.clearAll();

        loadContent(file, searchText);
        CONTENT.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY)){
                if(event.getTarget() instanceof FlowPane){
                    CLICK_FILE.set(null);
                    CLICK_FILE_DATA.set(null);
                    ClickStyle.unClickStyle("RightContent");
                }
            }
        });
        if(record){
            MainViewLeft.addFastDirectory(file, false);
        }
    }

    private static void loadContent(File file, String searchText) {
        if(file!=null && !file.isDirectory()){
            try{
                Desktop.getDesktop().open(file);
            }catch (Exception e){
                ViewManager.newView(CommonView.warn(), Modality.WINDOW_MODAL, TextAlias.INSTANCE.WARN().cantOpenFile()+" ["+e.getMessage()+"]");
            }
            return ;
        }
        if(file==null){
            CURRENT_DIRECTORY.set(null);
            loadDrive();
        }else{
            CURRENT_DIRECTORY.set(file);
            loadFiles(file, searchText);
        }
        CLICK_FILE.set(null);
        CLICK_FILE_DATA.set(null);
    }

    private static void loadFiles(File parent, String searchText) {
        DirectoryLoader.load(CONTENT, parent, searchText);
    }

    private static void loadDrive() {
        DriveLoader.load(CONTENT);
    }
}
