package com.evan.winfile.core.view.main;

import com.evan.winfile.common.util.DateUtils;
import com.evan.winfile.common.util.FileSizeUtil;
import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.core.style.*;
import com.evan.winfile.module.label.entity.FileLabel;
import com.evan.winfile.module.winfile.entity.MarkFile;
import com.evan.winfile.module.winfile.entity.RelLabelFile;
import com.evan.winfile.module.winfile.service.MarkFileService;
import com.evan.winfile.module.winfile.service.RelLabelFileService;
import com.evan.winfile.runtime.FileImageContainer;
import com.evan.winfile.runtime.text.TextAlias;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author Administrator
 * @date 2022/11/18 22:05
 */
public class LabelFileLoader {

    public static final FlowPane CONTENT = new FlowPane();

    private static final VBox LABEL_FILE_CONTENT = new VBox(6);

    private static final double lineHeight = 40D;

    private static final Set<String> LABEL_ID_SET = new HashSet<>(32);
    private static final Map<String, List<RelLabelFile>> FILE_COLOR_MAP = new HashMap<>(32);
    private static final Map<String, MarkFile> LABEL_FILE_MAP = new HashMap<>(32);


    private static final ObservableList<MarkFile> SELECT_FILES = FXCollections.observableArrayList();

    public static void addFilesFromLabel(FileLabel fileLabel) {
        if(LABEL_ID_SET.contains(fileLabel.getId())){
            return ;
        }
        List<RelLabelFile> relations =
                SpringUtils.getBean(RelLabelFileService.class).listByLabel(fileLabel);
        List<String> fileIds = relations.stream().map(RelLabelFile::getFileId).collect(Collectors.toList());
        Map<String, List<RelLabelFile>> groupBy = relations.stream()
                .collect(Collectors.groupingBy(RelLabelFile::getFileId, Collectors.toList()));
        groupBy.forEach((key, set) -> {
            if(FILE_COLOR_MAP.containsKey(key)){
                List<RelLabelFile> temp = FILE_COLOR_MAP.get(key);
                set.forEach(newLabel -> {
                    if(temp.stream().noneMatch(t -> t.getId().equals(newLabel.getId()))){
                        temp.add(newLabel);
                    }
                });
                FILE_COLOR_MAP.put(key, temp);
            }else{
                FILE_COLOR_MAP.put(key, set);
            }
        });
        List<MarkFile> markFiles = SpringUtils.getBean(MarkFileService.class).listByFileId(fileIds);
        markFiles.forEach(markFile -> {
            if(SELECT_FILES.stream()
                    .noneMatch(select -> select.getId().equals(markFile.getId()))){
                SELECT_FILES.add(markFile);
            }
            LABEL_FILE_MAP.put(fileLabel.getId(), markFile);
        });
        //SELECT_FILES.addAll(markFiles);
        LABEL_ID_SET.add(fileLabel.getId());
        // load node
        loadLabelContent();
        if(MainViewContent.MAIN.get()){
            MainViewContent.MAIN.set(false);
        }
    }

    public static void removeFilesFromLabel(FileLabel fileLabel) {
        LABEL_ID_SET.remove(fileLabel.getId());

        if(LABEL_ID_SET.size()<1){
            MainViewContent.MAIN.set(true);
            //      clear all
            clearAll();
            return ;
        }
        Set<String> fileIds = new HashSet<>(64);
        FILE_COLOR_MAP.forEach((key, set) -> {
            List<RelLabelFile> temp = FILE_COLOR_MAP.get(key);
            temp.removeIf(s -> s.getFileLabelId().equals(fileLabel.getId()));
            FILE_COLOR_MAP.put(key, temp);
            if(temp.size()<1){
                fileIds.add(key);
            }
        });
        fileIds.forEach(id -> {
            SELECT_FILES.removeIf(select -> id.equals(select.getId()));
            FILE_COLOR_MAP.remove(id);
        });
        loadLabelContent();
    }

    public static void clearAll() {
        FILE_COLOR_MAP.clear();
        SELECT_FILES.clear();
        LABEL_FILE_MAP.clear();
    }

    public static void initContent(ScrollPane scrollPane) {
        CONTENT.setHgap(40);
        CONTENT.setVgap(20);
        CommonBorder.removeBorder(CONTENT);
        CONTENT.setPrefHeight(scrollPane.getPrefHeight() - 60);
        CONTENT.setPrefWidth(scrollPane.getPrefWidth() - 60);
        CONTENT.setAlignment(Pos.TOP_LEFT);

        CONTENT.getChildren().add(LABEL_FILE_CONTENT);
    }

    private static void loadHeader() {
        LABEL_FILE_CONTENT.setAlignment(Pos.TOP_LEFT);
        LABEL_FILE_CONTENT.setPrefWidth(CONTENT.getPrefWidth());
        LABEL_FILE_CONTENT.setPrefHeight(CONTENT.getPrefHeight());
        //      header
        HBox headerBox = new HBox();
        headerBox.setPrefWidth(LABEL_FILE_CONTENT.getPrefWidth());
        headerBox.setPrefHeight(lineHeight);
        headerBox.setAlignment(Pos.CENTER_LEFT);

        Label fileTypeLabel = new Label(TextAlias.INSTANCE.DIRECTORY().typeLogo());
        fileTypeLabel.setFont(CommonFont.MID);
        fileTypeLabel.setPrefWidth(50);
        fileTypeLabel.setTextFill(CommonColor.Font.FONT_COLOR);
        headerBox.getChildren().add(fileTypeLabel);
        headerBox.getChildren().add(new Separator(Orientation.VERTICAL));

        Label fileNameLabel = new Label(TextAlias.INSTANCE.DIRECTORY().fileName());
        fileNameLabel.setPrefWidth(400);
        fileNameLabel.setFont(CommonFont.MID);
        fileNameLabel.setTextFill(CommonColor.Font.FONT_COLOR);
        HBox.setMargin(fileNameLabel, new Insets(0, 0, 0, 10));
        headerBox.getChildren().add(fileNameLabel);
        headerBox.getChildren().add(new Separator(Orientation.VERTICAL));

        Label fileTimeLabel = new Label(TextAlias.INSTANCE.DIRECTORY().modifyTime());
        fileTimeLabel.setFont(CommonFont.MID);
        fileTimeLabel.setPrefWidth(160);
        fileTimeLabel.setTextFill(CommonColor.Font.FONT_COLOR);
        headerBox.getChildren().add(fileTimeLabel);
        headerBox.getChildren().add(new Separator(Orientation.VERTICAL));


        Label fileSizeLabel = new Label(TextAlias.INSTANCE.DIRECTORY().size());
        fileSizeLabel.setPrefWidth(100);
        fileSizeLabel.setFont(CommonFont.MID);
        fileSizeLabel.setTextFill(CommonColor.Font.FONT_COLOR);
        headerBox.getChildren().add(fileSizeLabel);
        headerBox.getChildren().add(new Separator(Orientation.VERTICAL));

        Label fileLabel = new Label(TextAlias.INSTANCE.DIRECTORY().label());
        fileLabel.setFont(CommonFont.MID);
        fileLabel.setTextFill(CommonColor.Font.FONT_COLOR);
        headerBox.getChildren().add(fileLabel);

        //VBox.setMargin(headerBox, new Insets(20, 0, 0, 0));
        LABEL_FILE_CONTENT.getChildren().add(headerBox);
        LABEL_FILE_CONTENT.getChildren().add(new Separator(Orientation.HORIZONTAL));
    }

    private static void loadLabelContent() {
        LABEL_FILE_CONTENT.getChildren().clear();
        loadHeader();
        List<File> fileList = SELECT_FILES
                .stream().map(f -> new File(f.getPath()))
                .sorted((f1, f2) -> Boolean.compare(f2.isDirectory(), f1.isDirectory()))
                .collect(Collectors.toList());
        for(File item:fileList){
            HBox fileBox = new HBox(10);
            fileBox.setPrefWidth(LABEL_FILE_CONTENT.getPrefWidth());
            fileBox.setPrefHeight(lineHeight);
            fileBox.setAlignment(Pos.CENTER_LEFT);
            HoverStyle.addHoverStyle(fileBox, "RightContent");
            String id = MarkFile.md5Id(item.getPath());
            ClickStyle.addFileRightClick(fileBox, item, FILE_COLOR_MAP.get(id));
            ClickStyle.addClickStyle(fileBox,
                    "RightContent",
                    event -> {
                        if(event.getButton().equals(MouseButton.PRIMARY)){
                            if(event.getClickCount()==2){
                                RightContentLoader.load(item);
                                return ;
                            }
                            RightContentLoader.CLICK_FILE.set(fileBox);
                            RightContentLoader.CLICK_FILE_DATA.set(item);
                        }
                    });

            HBox typeBox = new HBox();
            typeBox.setPrefWidth(50);
            typeBox.setAlignment(Pos.CENTER);
            ImageView fileTypeView = new ImageView();
            fileTypeView.setFitWidth(28);
            fileTypeView.setFitHeight(28);
            fileTypeView.setImage(item.isDirectory()?
                    CommonIcon.File.DIRECTORY : FileImageContainer.loadImage(item.getPath()));
            CommonEffect.addEffective(fileTypeView);
            typeBox.getChildren().add(fileTypeView);
            fileBox.getChildren().add(typeBox);

            Label fileName = new Label(item.getName());
            fileName.setPrefWidth(400);
            fileName.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);
            CommonTooltip.addTooltip(fileName, item.getName());
            fileName.setFont(CommonFont.NORMAL);
            fileName.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
            fileBox.getChildren().add(fileName);

            Label fileTime = new Label(DateUtils.toString(DateUtils.now(item.lastModified()), DateUtils.DATE));
            fileTime.setPrefWidth(160);
            fileTime.setFont(CommonFont.NORMAL);
            fileTime.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
            fileBox.getChildren().add(fileTime);

            Label fileSize = new Label(item.isDirectory()? "" : FileSizeUtil.formatSize(item.length()));
            fileSize.setTextAlignment(TextAlignment.RIGHT);
            fileSize.setFont(CommonFont.NORMAL);
            fileSize.setPrefWidth(100);
            fileSize.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
            fileBox.getChildren().add(fileSize);

            StackPane stackPane = new StackPane();
            stackPane.setPrefHeight(lineHeight);
            stackPane.setAlignment(Pos.CENTER_LEFT);
            if(FILE_COLOR_MAP.containsKey(id)){
                double marginLeft = 0;
                for(RelLabelFile relation:FILE_COLOR_MAP.get(id)){
                    Circle circle = CommonCircle.stackCircle(Color.valueOf(relation.getFileLabelColor()));
                    stackPane.getChildren().add(circle);
                    StackPane.setMargin(circle, new Insets(0, 0, 0, marginLeft));
                    marginLeft+=8;
                }
            }
            fileBox.getChildren().add(stackPane);

            LABEL_FILE_CONTENT.getChildren().add(fileBox);
        }
    }
}
