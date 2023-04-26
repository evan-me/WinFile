package com.evan.winfile.core.view.main;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.core.style.*;
import com.evan.winfile.core.view.ViewManager;
import com.evan.winfile.core.view.common.CommonView;
import com.evan.winfile.module.winfile.entity.FastFile;
import com.evan.winfile.module.winfile.entity.RelLabelFile;
import com.evan.winfile.module.winfile.service.FastFileService;
import com.evan.winfile.module.winfile.service.RelLabelFileService;
import com.evan.winfile.runtime.text.TextAlias;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.OverrunStyle;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author Evan
 * @date 2022-11-11
 */
public class MainViewLeft {
    
    public static final ObservableList<FastFile> FATS_LIST = FXCollections.observableArrayList();

    private static final int HEIGHT = 28;
    private static final int CHILD_HEIGHT = HEIGHT - 8;

    public static void load(MainController controller, VBox leftContent) {

        HBox computerBox = new HBox(10);
        computerBox.setPrefWidth(280);
        computerBox.setPrefHeight(HEIGHT);
        HoverStyle.addHoverStyle(computerBox, "leftContent");
        ClickStyle.addClickStyle(computerBox,
                "leftContent",
                event -> {
                    if(event.getButton().equals(MouseButton.PRIMARY)){
                        RightContentLoader.load(null, "", false);
                    }
                });

        ImageView myComputer = new ImageView();
        myComputer.setFitHeight(HEIGHT);
        myComputer.setFitWidth(HEIGHT);
        myComputer.setImage(CommonIcon.System.MY_COMPUTER);
        CommonEffect.addEffective(myComputer);
        computerBox.getChildren().add(myComputer);

        Label myComputerLabel = new Label(TextAlias.INSTANCE.computer());
        myComputerLabel.setPrefWidth(180);
        myComputerLabel.setPrefHeight(HEIGHT);
        myComputerLabel.setFont(CommonFont.MID);
        myComputerLabel.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        computerBox.getChildren().add(myComputerLabel);

        leftContent.getChildren().add(computerBox);

        loadChildren(leftContent);

        HBox fastBox = new HBox(10);
        fastBox.setPrefWidth(280);
        fastBox.setPrefHeight(HEIGHT);
        HoverStyle.addHoverStyle(fastBox, "leftContent");
        ClickStyle.addClickStyle(fastBox,
                "leftContent",
                event -> {
                    if(event.getButton().equals(MouseButton.PRIMARY)){
                        RightContentLoader.load(new File(System.getProperty("user.home")),"" ,false);
                    }
                });

        ImageView fastView = new ImageView();
        fastView.setFitHeight(HEIGHT);
        fastView.setFitWidth(HEIGHT);
        fastView.setImage(CommonIcon.System.FAST_VIEW);
        CommonEffect.addEffective(fastBox);
        fastBox.getChildren().add(fastView);

        Label fastLabel = new Label(TextAlias.INSTANCE.fastFile());
        fastLabel.setPrefWidth(180);
        fastLabel.setPrefHeight(HEIGHT);
        fastLabel.setFont(CommonFont.MID);
        fastLabel.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        fastBox.getChildren().add(fastLabel);

        leftContent.getChildren().add(fastBox);

        loadFastContent(leftContent);

        FATS_LIST.addListener((ListChangeListener<FastFile>) listener -> {
            while (listener.next()){
                if(listener.getAddedSize()>0){
                    refreshFastView(leftContent);
                }
            }
        });

        loadFastFile();
    }

    private static void refreshFastView(VBox leftContent) {
        List<FastFile> tmpList = new ArrayList<>(FATS_LIST);
        List<Node> nodes = leftContent.getChildren()
                .stream()
                .filter(n -> "newFastBox".equals(n.getId()))
                .collect(Collectors.toList());
        leftContent.getChildren().removeAll(nodes);
        for(FastFile f:tmpList){
            HBox newFastBox = loadBox(new File(f.getPath()), f.getName(), CommonIcon.File.DIRECTORY);
            newFastBox.setId("newFastBox");
            Pane emptyPane = new Pane();
            HBox.setHgrow(emptyPane, Priority.ALWAYS);
            newFastBox.getChildren().add(emptyPane);

            ImageView imageView = new ImageView();
            imageView.setFitWidth(CHILD_HEIGHT);
            imageView.setFitHeight(CHILD_HEIGHT);
            imageView.setImage(f.isPin()? CommonIcon.File.PIN : CommonIcon.File.UNPIN);
            CommonEffect.addEffective(imageView);
            imageView.setVisible(f.isPin());
            ChangeListener<Boolean> hoverListener = (o, h1, h2) -> imageView.setVisible(h2);
            List<RelLabelFile> relations = SpringUtils.getBean(RelLabelFileService.class).listByFile(f);
            ClickStyle.addFileRightClick(newFastBox, new File(f.getPath()), relations);
            newFastBox.setOnMouseClicked(event -> {
                if(event.getClickCount()==2 && event.getButton().equals(MouseButton.PRIMARY)){
                    if(f.isPin()){
                        f.setPin(false);
                        imageView.setImage(CommonIcon.File.UNPIN);
                        imageView.setVisible(false);
                        newFastBox.hoverProperty().addListener(hoverListener);
                        addFastDirectory(new File(f.getPath()), false);
                    }else{
                        LambdaQueryWrapper<FastFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
                        lambdaQueryWrapper.eq(FastFile::isPin, true);
                        if(SpringUtils.getBean(FastFileService.class).count(lambdaQueryWrapper) > 7){
                            ViewManager.newView(CommonView.warn(), Modality.WINDOW_MODAL, TextAlias.INSTANCE.WARN().maxPinFileLimit());
                            return ;
                        }
                        f.setPin(true);
                        imageView.setImage(CommonIcon.File.PIN);
                        imageView.setVisible(true);
                        newFastBox.hoverProperty().removeListener(hoverListener);
                        addFastDirectory(new File(f.getPath()), true);
                    }
                    loadFastFile();
                    //event.consume();
                }
            });
            newFastBox.getChildren().add(imageView);

            if(!f.isPin()){
                newFastBox.hoverProperty().addListener(hoverListener);
            }

            leftContent.getChildren().add(newFastBox);
        }
    }

    private static void loadFastFile() {
        LambdaQueryWrapper<FastFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.orderByAsc(FastFile::getCreateTime);
        List<FastFile> files = SpringUtils.getBean(FastFileService.class).list(lambdaQueryWrapper);
        files.sort((f1, f2) -> Boolean.compare(f2.isPin(), f1.isPin()));
        FATS_LIST.clear();
        FATS_LIST.setAll(files);
    }

    public static void addFastDirectory(File file, boolean pin){
        if(file==null){
            return ;
        }
        if(file.isDirectory() && file.toPath().getNameCount()>0){
            if(pin){
                LambdaQueryWrapper<FastFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
                lambdaQueryWrapper.eq(FastFile::isPin, true);
                if(SpringUtils.getBean(FastFileService.class).count(lambdaQueryWrapper) > 7){
                    ViewManager.newView(CommonView.warn(), Modality.WINDOW_MODAL, TextAlias.INSTANCE.WARN().maxPinFileLimit());
                    return ;
                }
            }
            List<FastFile> newList = new ArrayList<>(FATS_LIST);
            List<FastFile> unPinFile = newList.stream().filter(f -> !f.isPin()).collect(Collectors.toList());
            for(FastFile temp:FATS_LIST){
                if(temp.getPath().equals(file.getPath())){
                    temp.setPin(pin);
                    Platform.runLater(() -> {
                        if(!temp.isPin() && unPinFile.size()>4){
                            SpringUtils.getBean(FastFileService.class).removeFirst();
                        }
                        SpringUtils.getBean(FastFileService.class).updateFile(temp);
                        loadFastFile();
                    });
                    return ;
                }
            }
            FastFile fastFile = FastFile.newFile(file);
            //List<FastFile> pinFile = newList.stream().filter(FastFile::isPin).collect(Collectors.toList());
            if(pin){
                fastFile.setPin(true);
                SpringUtils.getBean(FastFileService.class).addFile(fastFile);
            }else{
                if(unPinFile.size()>4){
                    SpringUtils.getBean(FastFileService.class).addFileToLast(fastFile);
                }else{
                    SpringUtils.getBean(FastFileService.class).addFile(fastFile);
                }
            }
            //newList.clear();
            //pinFile.addAll(unPinFile);
            //newList.addAll(pinFile);
            loadFastFile();
        }
    }


    private static void loadFastContent(VBox leftContent) {
        String userHome = System.getProperty("user.home");
        //      desktop
        File desktop = new File(userHome+File.separator+"Desktop");
        if(desktop.exists()){
            HBox downloadBox = loadBox(desktop, TextAlias.INSTANCE.SYSTEM().desktop(), CommonIcon.System.DESKTOP);
            leftContent.getChildren().add(downloadBox);
        }
        //      downloads
        File download = new File(userHome+File.separator+"Downloads");
        if(download.exists()){
            HBox downloadBox = loadBox(download, TextAlias.INSTANCE.SYSTEM().download(), CommonIcon.System.DOWNLOAD);
            leftContent.getChildren().add(downloadBox);
        }
        //      documents
        File document = new File(userHome+File.separator+"Documents");
        if(document.exists()){
            HBox documentBox = loadBox(document, TextAlias.INSTANCE.SYSTEM().document(), CommonIcon.System.DOCUMENTS);
            leftContent.getChildren().add(documentBox);
        }
        //      rubbish
        //      TODO    暂无相关API
    }

    private static void loadChildren(VBox leftContent) {
        File[] roots = File.listRoots();
        for(File root:roots){
            HBox contentBox = loadBox(root,root.getPath().replace(File.separator, ""), CommonIcon.File.DRIVE);
            leftContent.getChildren().add(contentBox);
        }
    }

    private static HBox loadBox(File root,String label, Image image) {
        HBox driveBox = new HBox(20);
        driveBox.setPrefWidth(240);
        driveBox.setPrefHeight(CHILD_HEIGHT);
        HoverStyle.addHoverStyle(driveBox, "leftContent");
        VBox.setMargin(driveBox, new Insets(0, 0, 0, 20));

        ImageView imageView = new ImageView();
        imageView.setFitHeight(CHILD_HEIGHT);
        imageView.setFitWidth(CHILD_HEIGHT);
        imageView.setImage(image);
        CommonEffect.addEffective(imageView);
        driveBox.getChildren().add(imageView);

        Label rootLabel = new Label();
        rootLabel.setFont(CommonFont.NORMAL);
        rootLabel.setTextFill(CommonColor.Font.MAIN_FONT_COLOR);
        rootLabel.setText(label);
        CommonTooltip.addTooltip(rootLabel, label);
        rootLabel.setTextOverrun(OverrunStyle.LEADING_ELLIPSIS);
        rootLabel.setPrefHeight(driveBox.getPrefHeight());
        ClickStyle.addClickStyle(driveBox,
                "leftContent",
                event -> {
                    if(event.getClickCount()==1 && event.getButton().equals(MouseButton.PRIMARY)){
                        RightContentLoader.load(root, "", false);
                    }
                });
        driveBox.getChildren().add(rootLabel);
        return driveBox;
    }


}