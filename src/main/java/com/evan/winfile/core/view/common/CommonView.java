package com.evan.winfile.core.view.common;

import com.evan.winfile.core.view.AbstractController;
import com.evan.winfile.core.view.AbstractView;
import com.evan.winfile.core.view.common.confirm.ConfirmController;
import com.evan.winfile.core.view.common.warn.WarnController;
import com.evan.winfile.runtime.text.TextAlias;

/**
 * @author Evan
 * @date 2022-11-11
 */
public class CommonView {
    public static AbstractView confirm(){
        return new AbstractView() {
            @Override
            public String initTitle() {
                return TextAlias.INSTANCE.TITLE().confirm();
            }

            @Override
            public AbstractController controller() {
                return new ConfirmController();
            }

            @Override
            public int width() {
                return 300;
            }

            @Override
            public int height() {
                return 200;
            }
        };
    }

    public static AbstractView warn(){
        return new AbstractView() {
            @Override
            protected String initTitle() {
                return TextAlias.INSTANCE.TITLE().warn();
            }

            @Override
            public AbstractController controller() {
                return new WarnController();
            }

            @Override
            public int width() {
                return 300;
            }

            @Override
            public int height() {
                return 200;
            }
        };
    }
}