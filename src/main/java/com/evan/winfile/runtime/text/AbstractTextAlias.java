package com.evan.winfile.runtime.text;

/**
 * @author Evan
 * @date 2022-11-21
 */
public interface AbstractTextAlias {

    default String language(){ return "cn";}
    String computer();
    String fastFile();

    Title TITLE();
    interface Title{
        String confirm();
        String warn();
        String labelManager();
        String fileManager();
    }

    System SYSTEM();
    interface System{
        String desktop();
        String download();
        String document();
        String searchText();
    }

    Drive DRIVE();
    interface Drive{
        String totalSpace();
        String freeSpace();
    }

    Directory DIRECTORY();
    interface Directory{
        String typeLogo();
        String fileName();
        String modifyTime();
        String size();
        String label();
        String currentFileCount();
    }

    Operation OPERATION();
    interface Operation{
        String add();
        String confirm();

        String cancel();
    }

    Warn WARN();

    interface Warn{
        String maxPinFileLimit();

        Object cantOpenFile();
    }

    RightClick RIGHTCLICK();

    interface RightClick{
        String openWithWindows();
        String pinToQuickAccess();
        String cancelPin();
    }
}