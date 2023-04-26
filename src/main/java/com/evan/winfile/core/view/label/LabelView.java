package com.evan.winfile.core.view.label;

import com.evan.winfile.core.view.AbstractController;
import com.evan.winfile.core.view.AbstractView;
import com.evan.winfile.core.view.label.manager.LabelManagerController;
import com.evan.winfile.runtime.text.TextAlias;

/**
 * @author Evan
 * @date 2022-11-16
 */
public class LabelView {

    public static AbstractView labelManager(){
                return new AbstractView() {

                    @Override
                    protected String initTitle() {
                        return TextAlias.INSTANCE.TITLE().labelManager();
                    }

                    @Override
                    public AbstractController controller() {
                        return new LabelManagerController();
                    }

                    @Override
                    public int width() {
                        return 400;
                    }

                    @Override
                    public int height() {
                return 500;
            }
        };
    }
}