package com.evan.winfile.runtime;

import com.evan.winfile.common.util.FileUtils;
import com.evan.winfile.core.style.CommonIcon;
import javafx.scene.image.Image;
import org.springframework.util.StringUtils;

import java.util.*;


/**
 * @author Evan
 * @date 2022-11-14
 */
public class FileImageContainer {
    private static final Map<String, Image> ICON_MAP = new HashMap<>(128);

    private static final Set<String> IMAGE_SET;

    private static final Set<String> SCRIPT_SET;
    private static final Set<String> WORD_SET;
    private static final Set<String> EXCEL_SET;
    private static final Set<String> POWER_POINT_SET;
    private static final Set<String> VIDEO_SET;
    private static final Set<String> COMPRESS_SET;
    private static final Set<String> PROGRAM_SET;
    private static final Set<String> PDF_SET;
    private static final Set<String> TEXT_SET;
    private static final Set<String> INSTALL_SET;
    private static final Set<String> APP_SET;
    private static final Set<String> MUSIC_SET;
    private static final Set<String> FAST_LINK;

    static {
        IMAGE_SET = new HashSet<>(16);
        IMAGE_SET.addAll(Arrays.asList("bmp", "jpg", "tiff", "tif", "gif", "jpeg", "svg", "png"));

        SCRIPT_SET = new HashSet<>(16);
        SCRIPT_SET.addAll(Arrays.asList("dll", "bat", "ini", "inf"));

        WORD_SET = new HashSet<>(16);
        WORD_SET.addAll(Arrays.asList("doc", "docx"));

        EXCEL_SET = new HashSet<>(16);
        EXCEL_SET.addAll(Arrays.asList("xls", "xlsx"));

        POWER_POINT_SET = new HashSet<>(16);
        POWER_POINT_SET.addAll(Arrays.asList("ppt", "pptx"));

        VIDEO_SET = new HashSet<>(16);
        VIDEO_SET.addAll(Arrays.asList("avi", "mov", "rmvb", "rm", "flv", "mp4", "3gp", "mkv"));

        COMPRESS_SET = new HashSet<>(16);
        COMPRESS_SET.addAll(Arrays.asList("zip", "rar", "7z", "bz", "bz2", "tar"));

        PROGRAM_SET = new HashSet<>(16);
        PROGRAM_SET.addAll(Arrays.asList("exe"));

        PDF_SET = new HashSet<>(16);
        PDF_SET.addAll(Arrays.asList("pdf"));

        TEXT_SET = new HashSet<>(16);
        TEXT_SET.addAll(Arrays.asList("txt", "json", "log", "md", "kml", "xml"));

        APP_SET = new HashSet<>(16);
        APP_SET.addAll(Arrays.asList("app", "apk"));

        INSTALL_SET = new HashSet<>(16);
        INSTALL_SET.addAll(Arrays.asList("msi"));

        MUSIC_SET = new HashSet<>(16);
        MUSIC_SET.addAll(Arrays.asList("mp3", "aac", "wav", "wma", "flac", "m4a", "ape"));

        FAST_LINK = new HashSet<>(16);
        FAST_LINK.addAll(Arrays.asList("url", "lnk", "ink"));

        load();
    }

    private static void load() {
        IMAGE_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.IMAGE);
        });

        VIDEO_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.VIDEO);
        });

        WORD_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.WORD);
        });

        EXCEL_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.EXCEL);
        });

        POWER_POINT_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.PPT);
        });

        PROGRAM_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.PROGRAM);
        });

        COMPRESS_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.COMPRESS);
        });

        PDF_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.PDF);
        });

        SCRIPT_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.SCRIPT);
        });

        TEXT_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.TEXT);
        });

        INSTALL_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.INSTALL);
        });

        APP_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.APP);
        });

        MUSIC_SET.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.MUSIC);
        });

        FAST_LINK.forEach(s -> {
            ICON_MAP.put(s, CommonIcon.FileType.FAST_LINK);
        });
    }

    public static Image loadImage(String path){
        Image image = CommonIcon.File.FILE;
        if(StringUtils.hasText(path)){
            String type = FileUtils.getUrlFileType(path);
            if(ICON_MAP.containsKey(type)){
                return ICON_MAP.get(type);
            }
        }
        return image;
    }
}