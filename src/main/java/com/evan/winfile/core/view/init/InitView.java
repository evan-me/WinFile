package com.evan.winfile.core.view.init;

import com.evan.winfile.core.view.AbstractController;
import com.evan.winfile.core.view.AbstractView;

/**
 * @author Evan
 * @date 2022-11-11
 */
public class InitView {
    public static AbstractView init(){
        return new AbstractView() {
            @Override
            protected String initTitle() {
                return "";
            }

            @Override
            public AbstractController controller() {
                return new InitController();
            }

            @Override
            public int width() {
                return 240;
            }

            @Override
            public int height() {
                return 200;
            }
        };
    }
}