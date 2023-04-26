package com.evan.winfile.core.view;

import com.evan.winfile.core.style.CommonIcon;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Builder;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.HashSet;
import java.util.Set;

/**
 * @author Evan
 * @date 2022-11-02
 */
@Slf4j
public class ViewManager {
    private static Stage CURRENT;
    private static Stage PRIMARY;

    private static final Set<String> SINGLE_WINDOW = new HashSet<>(16);

    public static void switchView(AbstractView abstractView){
        InnerView innerView = load(abstractView);
        Stage stage = initStage(abstractView, innerView);
        primary(stage);
        showStage(CURRENT);
    }

    public static void newView(AbstractView abstractView){
        newView(abstractView, Modality.APPLICATION_MODAL);
    }

    public static void newView(AbstractView abstractView, Modality modality){
        newView(abstractView, modality, null);
    }

    public static void newView(AbstractView abstractView, Modality modality, Object data){
        newView(abstractView, modality, data, null);
    }

    public static void newView(AbstractView abstractView, Modality modality, Object data, ControllerCallback<?> callback){
        if(modality.equals(Modality.NONE) && SINGLE_WINDOW.contains(abstractView.fxml())){
            showStage(CURRENT);
            return ;
        }
        InnerView innerView = load(abstractView, data, callback);
        Stage stage = initStage(abstractView, innerView);
        stage.setOnCloseRequest(event -> {
            innerView.controller.destroy();
        });
        stage.initModality(modality);

        abstractView.setParentStage(CURRENT);
        if(!modality.equals(Modality.WINDOW_MODAL)){
            stage.setX(CURRENT.getX());
            stage.setY(CURRENT.getY());
        }else{
            stage.initModality(Modality.APPLICATION_MODAL);
        }
        if(modality.equals(Modality.APPLICATION_MODAL)){
            abstractView.parentStage().hide();
            stage.setOnCloseRequest(event -> {
                abstractView.parentStage().setX(CURRENT.getX());
                abstractView.parentStage().setY(CURRENT.getY());
                abstractView.parentStage().show();
                CURRENT = abstractView.parentStage();
            });
            CURRENT = stage;
            SINGLE_WINDOW.clear();
        }else if (modality.equals(Modality.NONE)){
            SINGLE_WINDOW.add(abstractView.fxml());
            stage.initOwner(CURRENT);
            stage.setX(CURRENT.getX() + abstractView.parentStage().getWidth() + 10);
            stage.setY(CURRENT.getY());
            stage.xProperty().addListener((o, d, d1) -> {
                CURRENT.setX(d1.doubleValue() - abstractView.parentStage().getWidth() - 10);
            });
            stage.yProperty().addListener((o, d, d1) -> {
                CURRENT.setY(d1.doubleValue());
            });
            CURRENT.xProperty().addListener((o, d, d1) -> {
                stage.setX(d1.doubleValue() + abstractView.parentStage().getWidth() + 10);
            });
            CURRENT.yProperty().addListener((o, d, d1) -> {
                stage.setY(d1.doubleValue());
            });
            stage.setOnCloseRequest(event -> {
                SINGLE_WINDOW.remove(abstractView.fxml());
                CURRENT = abstractView.parentStage();
                showStage(CURRENT);
            });
            stage.show();
        }
        showStage(stage);
    }

    private static Stage initStage(AbstractView abstractView, InnerView innerView){
        Stage stage = new Stage();
        stage.getIcons().add(CommonIcon.LOGO_IMAGE);
        stage.setTitle(abstractView.title());

        Scene scene = new Scene(innerView.root, abstractView.width(), abstractView.height());
        stage.setScene(scene);
        stage.setResizable(false);
        stage.setOnCloseRequest(event -> innerView.controller.destroy());
        return stage;
    }

    public static void showStage(Stage stage){
        if(stage!=null){
            stage.setAlwaysOnTop(true);
            stage.show();
            stage.setAlwaysOnTop(false);
        }else{
            if(CURRENT!=null){
                CURRENT.setIconified(!CURRENT.isIconified());
            }
        }
    }

    private static InnerView load(AbstractView abstractView){
        return load(abstractView, null, null);
    }

    private static InnerView load(AbstractView abstractView, Object data, ControllerCallback<?> callback){
        try{
            FXMLLoader loader = new FXMLLoader(ViewManager.class.getResource(abstractView.fxml()));
            AbstractController controller = abstractView.controller();
            controller.init(abstractView, data, callback);
            loader.setController(controller);
            Parent root = loader.load();
            return InnerView.builder().root(root).controller(controller).build();
        }catch (Exception e){
            e.printStackTrace();
            log.error("视图初始化失败");
        }
        //      TODO    弹窗警告
        throw new RuntimeException("加载错误");
    }

    public static void closeCurrent() {
        if(CURRENT!=null){
            CURRENT.close();
            CURRENT = PRIMARY;
            showStage(CURRENT);
        }
    }

    @Data
    @Builder
    static class InnerView{
        private Parent root;
        private AbstractController controller;
    }

    public static void primary(Stage stage){
        PRIMARY = stage;
        if(CURRENT!=null){
            CURRENT.close();
        }
        CURRENT = PRIMARY;
        PRIMARY.setOnCloseRequest(event -> {
            ViewManager.exit();
        });
    }

    public static void exit(){
        log.info("program exit..");
        if(CURRENT!=null){
            CURRENT.close();
        }
        if(PRIMARY!=null){
            PRIMARY.close();
        }
        System.exit(1);
    }
}