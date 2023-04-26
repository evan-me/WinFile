package com.evan.winfile.common.util;

import java.io.File;
import java.text.DecimalFormat;

/**
 * @author Administrator
 * @date 2022/11/12 01:23
 */
public class FileSizeUtil {
    /**
     * 转换文件大小
     */
    public static String formatSize(long fileS) {
        DecimalFormat df = new DecimalFormat("#");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (fileS == 0) {
            return wrongSize;
        }
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + " B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + " KB";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + " MB";
        } else if(fileS < 1099511627776L) {
            fileSizeString = df.format((double) fileS / 1073741824) + " GB";
        }else {
            fileSizeString = df.format((double) fileS / 1099511627776L) + " TB";
        }
        return fileSizeString;
    }

    public static  boolean isRoot(File file){
        return file.toPath().getNameCount()==0;
    }
}
