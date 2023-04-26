package com.evan.winfile.core.style;

import javafx.scene.image.Image;

/**
 * @author Evan
 * @date 2022-09-28
 */
public interface CommonIcon {

    Image LOGO_IMAGE = new Image("/icons/logo.png", 96, 96, true, true);

    interface Media{
        Image VIDEO = new Image("/icons/media/video.png", 48, 48, true, true);
        Image IMAGE = new Image("/icons/media/image.png", 48, 48, true, true);
    }

    interface File{
        Image DIRECTORY = new Image("/icons/file/folder.png", 96, 96, true, true);
        Image FILE = new Image("/icons/file/file.png", 96, 96, true, true);
        Image DRIVE = new Image("/icons/file/drive.png", 96, 96, true, true);

        Image PIN = new Image("/icons/file/pin.png", 96, 96, true, true);

        Image UNPIN = new Image("/icons/file/unpin.png", 96, 96, true, true);

    }

    interface FileType{

        Image IMAGE = new Image("/icons/filetype/image.png", 96, 96, true, true);

        Image VIDEO = new Image("/icons/filetype/video.png", 96, 96, true, true);

        Image WORD = new Image("/icons/filetype/word.png", 96, 96, true, true);

        Image EXCEL = new Image("/icons/filetype/excel.png", 96, 96, true, true);

        Image PPT = new Image("/icons/filetype/ppt.png", 96, 96, true, true);

        Image SCRIPT = new Image("/icons/filetype/script.png", 96, 96, true, true);

        Image PROGRAM = new Image("/icons/filetype/program.png", 96, 96, true, true);

        Image COMPRESS = new Image("/icons/filetype/compress.png", 96, 96, true, true);

        Image PDF = new Image("/icons/filetype/pdf.png", 96, 96, true, true);

        Image TEXT = new Image("/icons/filetype/text.png", 96, 96, true, true);

        Image APP = new Image("/icons/filetype/app.png", 96, 96, true, true);

        Image INSTALL = new Image("/icons/filetype/install.png", 96, 96, true, true);

        Image MUSIC = new Image("/icons/filetype/music.png", 96, 96, true, true);

        Image FAST_LINK = new Image("/icons/filetype/fast_link.png", 96, 96, true, true);

    }

    interface System{
        Image MY_COMPUTER = new Image("/icons/system/MyComputer.png", 96, 96, true, true);

        Image FAST_VIEW = new Image("/icons/system/fast.png", 96, 96, true, true);

        Image DOWNLOAD = new Image("/icons/system/download.png", 96, 96, true, true);

        Image DOCUMENTS = new Image("/icons/system/documents.png", 96, 96, true, true);

        Image DESKTOP = new Image("/icons/system/desktop.png", 96, 96, true, true);

    }

    interface Operation{
        Image BACK = new Image("/icons/operation/back.png", 96, 96, true, true);

        Image NO_BACK = new Image("/icons/operation/no_back.png", 96, 96, true, true);

        Image ADD = new Image("/icons/operation/add.png", 96, 96, true, true);

        Image ADD_DEFAULT = new Image("/icons/operation/add_default.png", 96, 96, true, true);

        Image MORE = new Image("/icons/operation/more.png", 96, 96, true, true);

        Image COLOR = new Image("/icons/operation/color.png", 96, 96, true, true);

        Image DELETE = new Image("/icons/operation/delete.png", 96, 96, true, true);

        Image DONE = new Image("/icons/operation/done.png", 96, 96, true, true);

        Image ADD_FILE = new Image("/icons/operation/add_file.png", 96, 96, true, true);

        Image ADD_DIRECTORY = new Image("/icons/operation/add_directory.png", 96, 96, true, true);

    }
}