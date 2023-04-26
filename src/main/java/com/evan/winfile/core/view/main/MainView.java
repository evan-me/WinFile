package com.evan.winfile.core.view.main;

import com.evan.winfile.core.view.AbstractController;
import com.evan.winfile.core.view.AbstractView;
import com.evan.winfile.runtime.text.TextAlias;

/**
 * @author Evan
 * @date 2022-11-11
 */
public class MainView {
    public static AbstractView main(){
        return new AbstractView() {
            @Override
            protected String initTitle() {
                return TextAlias.INSTANCE.TITLE().fileManager();
            }

            @Override
            public AbstractController controller() {
                return new MainController();
            }

            @Override
            public int width() {
                return 1600;
            }

            @Override
            public int height() {
                return 900;
            }
        };
    }
}