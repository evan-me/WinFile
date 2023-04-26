package com.evan.winfile.core.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author Evan
 * @date 2022-11-02
 */
public abstract class AbstractController implements Initializable {

    @FXML
    protected VBox body;

    protected ControllerCallback callback;

    protected AbstractView currentView;

    private URL location;
    private ResourceBundle resources;

    protected Object data;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.location = location;
        this.resources = resources;
        initBody();
        start();
    }

    private void initBody() {
        if(currentView!=null){
            this.body.setPrefHeight(currentView.height());
            this.body.setPrefWidth(currentView.width());
        }
    }

    final void init(AbstractView currentView, Object data, ControllerCallback<?> callback){
        this.currentView = currentView;
        this.callback = callback;
        this.data = data;
    }

    protected abstract void start();

    void destroy(){

    }
}