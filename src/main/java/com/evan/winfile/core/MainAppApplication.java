package com.evan.winfile.core;

import com.evan.winfile.WinfileApplication;
import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.core.view.ViewManager;
import com.evan.winfile.core.view.init.InitView;
import com.evan.winfile.runtime.RuntimeConstant;
import com.melloware.jintellitype.JIntellitype;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author Evan
 * @date 2022-11-11
 */
@Slf4j
public class MainAppApplication extends Application {

    protected static void run(Class<WinfileApplication> clazz, String[] args){
        log.info("start ...");
        RuntimeConstant.init();
        //TextAlias.setAliasText("en");
        launch(clazz, args);
    }

    @Override
    public void start(Stage primaryStage){
        ViewManager.switchView(InitView.init());
    }

    @Override
    public void init(){
        Platform.setImplicitExit(false);
        SpringUtils.setContext(new SpringApplicationBuilder(WinfileApplication.class).run());
        try{
            if(JIntellitype.isJIntellitypeSupported()){
                JIntellitype.getInstance().registerHotKey(1, JIntellitype.MOD_ALT, 'Q');
                JIntellitype.getInstance().addHotKeyListener(i -> {
                    if(i==1){
                        Platform.runLater(() -> ViewManager.showStage(null));
                    }
                });
            }
        }catch (Exception e){
            log.error("全局热键启用失败", e);
        }
    }

    @Override
    public void stop(){
        ViewManager.exit();
    }
}