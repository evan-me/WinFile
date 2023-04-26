package com.evan.winfile.core.view;

import javafx.stage.Stage;
import org.springframework.util.StringUtils;

/**
 * @author Evan
 * @date 2022-11-02
 */
public abstract class AbstractView {

    private Stage parent = null;

    public final Stage parentStage(){
        return this.parent;
    }

    public final void setParentStage(Stage stage){
        this.parent = stage;
    }

    public final String title(){
        return "WinFile"+ ((StringUtils.hasText(initTitle()))? "-" + initTitle() : "");
    }

    public final String fxml(){
        return "/views"+initFxml();
    }

    protected abstract String initTitle();
    protected String initFxml(){
        return "/BaseView.fxml";
    }
    public abstract AbstractController controller();

    public int width(){ return 1600; }
    public int height(){ return 900; }
}