package com.evan.winfile.core.view.main;

import com.evan.winfile.common.util.FileSizeUtil;
import com.evan.winfile.core.style.*;
import com.evan.winfile.runtime.text.TextAlias;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author Evan
 * @date 2022-11-18
 */
public class DriveLoader {
    public static void load(FlowPane content){
        content.getChildren().clear();
        File[] files = File.listRoots();
        for(File item:files) {
            HBox fileBox = new HBox(20);
            fileBox.setAlignment(Pos.CENTER_LEFT);
            fileBox.setPrefWidth(340);
            fileBox.setPrefHeight(60);
            HoverStyle.addHoverStyle(fileBox, "RightContent");
            ClickStyle.addClickStyle(fileBox,
                    "RightContent",
                    event -> {
                        if (event.getButton().equals(MouseButton.PRIMARY)) {
                            if (event.getClickCount() == 2) {
                                RightContentLoader.load(item);
                                return;
                            }
                            RightContentLoader.CLICK_FILE.set(fileBox);
                            RightContentLoader.CLICK_FILE_DATA.set(item);
                        }
                    });
            ImageView drive = new ImageView();
            drive.setFitHeight(80);
            drive.setFitWidth(80);
            drive.setSmooth(true);
            drive.setPreserveRatio(true);
            CommonEffect.addEffective(drive);
            drive.setImage(CommonIcon.File.DRIVE);

            fileBox.getChildren().add(drive);

            VBox spaceBox = new VBox(10);
            spaceBox.setPrefHeight(80);
            spaceBox.setPrefWidth(fileBox.getPrefWidth() - drive.getFitWidth());
            spaceBox.setAlignment(Pos.CENTER_LEFT);

            Label label = new Label(item.getPath().replace(File.separator, ""));
            label.setPrefWidth(40);
            label.setPrefHeight(20);
            label.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
            label.setFont(CommonFont.MID);
            spaceBox.getChildren().add(label);

            ProgressBar progressBar = new ProgressBar();
            progressBar.setPrefHeight(20);
            progressBar.setPrefWidth(spaceBox.getPrefWidth());
            progressBar.setMouseTransparent(true);
            double space = BigDecimal.valueOf((item.getTotalSpace() - item.getFreeSpace()))
                    .divide(BigDecimal.valueOf(item.getTotalSpace()), 2, RoundingMode.HALF_UP)
                    .doubleValue();
            progressBar.setProgress(space);
            spaceBox.getChildren().add(progressBar);

            Label spaceText = new Label(TextAlias.INSTANCE.DRIVE().totalSpace() + FileSizeUtil.formatSize(item.getTotalSpace())
                    + ", " + TextAlias.INSTANCE.DRIVE().freeSpace() + FileSizeUtil.formatSize(item.getUsableSpace()));
            spaceText.setPrefHeight(20);
            spaceText.setPrefWidth(spaceBox.getPrefWidth());
            spaceText.setFont(CommonFont.SMALL);
            spaceBox.getChildren().add(spaceText);

            fileBox.getChildren().add(spaceBox);

            content.getChildren().add(fileBox);
        }
    }
}