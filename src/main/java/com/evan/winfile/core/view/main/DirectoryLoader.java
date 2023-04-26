package com.evan.winfile.core.view.main;

import com.evan.winfile.common.util.DateUtils;
import com.evan.winfile.common.util.FileSizeUtil;
import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.core.style.*;
import com.evan.winfile.module.winfile.entity.MarkFile;
import com.evan.winfile.module.winfile.entity.RelLabelFile;
import com.evan.winfile.module.winfile.service.MarkFileService;
import com.evan.winfile.runtime.FileImageContainer;
import com.evan.winfile.runtime.text.TextAlias;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.control.Separator;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.TextAlignment;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Evan
 * @date 2022-11-18
 */
public class DirectoryLoader {
    public static void load(FlowPane content, File parent, String searchText){
        content.getChildren().clear();
        VBox fileListBox = new VBox(6);
        fileListBox.setAlignment(Pos.TOP_LEFT);
        fileListBox.setPrefWidth(content.getPrefWidth());
        fileListBox.setPrefHeight(content.getPrefHeight());

        double lineHeight = 40D;
        //      header
        HBox headerBox = new HBox();
        headerBox.setPrefWidth(fileListBox.getPrefWidth());
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
        fileListBox.getChildren().add(headerBox);
        fileListBox.getChildren().add(new Separator(Orientation.HORIZONTAL));

        File[] files = parent.listFiles();
        if(files==null){
            return ;
        }
        List<File> fileList = Arrays.asList(files);
        if(StringUtils.hasText(searchText)){
            fileList = fileList.stream().filter(f -> f.getName().toLowerCase().contains(searchText.toLowerCase())).collect(Collectors.toList());
        }
        fileList.sort((f1, f2) -> Boolean.compare(f2.isDirectory(), f1.isDirectory()));
        Map<String, List<RelLabelFile>> fileRelation = SpringUtils.getBean(MarkFileService.class).listByParentFile(parent);
        for(File item:fileList){
            HBox fileBox = new HBox(10);
            fileBox.setId("fileBox");
            fileBox.setPrefWidth(fileListBox.getPrefWidth());
            fileBox.setPrefHeight(lineHeight);
            fileBox.setAlignment(Pos.CENTER_LEFT);
            HoverStyle.addHoverStyle(fileBox, "RightContent");
            String id = MarkFile.md5Id(item.getPath());
            ClickStyle.addFileRightClick(fileBox, item, fileRelation.get(id));
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
            if(fileRelation.containsKey(id)){
                double marginLeft = 0;
                for(RelLabelFile relation:fileRelation.get(id)){
                    Circle circle = CommonCircle.stackCircle(Color.valueOf(relation.getFileLabelColor()));
                    stackPane.getChildren().add(circle);
                    StackPane.setMargin(circle, new Insets(0, 0, 0, marginLeft));
                    marginLeft+=8;
                }
            }
            fileBox.getChildren().add(stackPane);

            fileListBox.getChildren().add(fileBox);
        }

        content.getChildren().add(fileListBox);
    }
}