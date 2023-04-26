package com.evan.winfile.runtime.text;

import com.evan.winfile.runtime.text.impl.ChineseTextAlias;
import com.evan.winfile.runtime.text.impl.EnglishTextAlias;
import org.springframework.util.StringUtils;

/**
 * @author Evan
 * @date 2022-11-21
 */
public class TextAlias {

    public static AbstractTextAlias INSTANCE = new ChineseTextAlias();

    public static void setAliasText(String language){
        if(!StringUtils.hasText(language)){
            return ;
        }
        if(language.equals("en")){
            INSTANCE = new EnglishTextAlias();
        }
    }

}