package com.evan.winfile.core.view.common.warn;

import com.evan.winfile.core.style.CommonColor;
import com.evan.winfile.core.style.CommonFont;
import com.evan.winfile.core.view.AbstractController;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import org.springframework.stereotype.Component;

/**
 * @author Evan
 * @date 2022-10-12
 */
@Component
public class WarnController extends AbstractController {

    private String msg;
    
    @Override
    protected void start() {

        msg = (String) this.data;

        this.body.setAlignment(Pos.CENTER);

        loadContent();
    }

    private void loadContent() {
        Label label = new Label(this.msg);
        label.setFont(CommonFont.NORMAL);
        label.setTextFill(CommonColor.AYANO);

        this.body.getChildren().add(label);
    }
}