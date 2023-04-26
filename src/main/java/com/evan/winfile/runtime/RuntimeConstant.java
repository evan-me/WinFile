package com.evan.winfile.runtime;

import com.evan.winfile.common.util.DateUtils;
import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.common.util.UUIDGenerator;
import com.evan.winfile.module.label.entity.FileLabel;
import com.evan.winfile.module.label.service.FileLabelService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import lombok.extern.slf4j.Slf4j;

import java.io.File;

/**
 * @author Evan
 * @date 2022-11-11
 */
@Slf4j
public class RuntimeConstant {

    public static String RUNNING_PATH;

    public static void init() {
        synchronized (RuntimeConstant.class){
            try{
                RUNNING_PATH = new File(".").getCanonicalPath();
                log.info("running in path -> {}", RUNNING_PATH);
            }catch (Exception e){
                log.error("初始化失败", e);
            }
        }
    }

    public static final ObservableList<FileLabel> FILE_LABELS = FXCollections.observableArrayList();

    static {
        FileLabel high = new FileLabel();
        high.setId(UUIDGenerator.generate());
        high.setName("Label 1");
        high.setSort(20);
        high.setFileLabelType(FileLabel.FileLabelType.SYSTEM);
        high.setColor("#ee3f4d");
        high.setCreateTime(DateUtils.now());
        FILE_LABELS.add(high);

        FileLabel MIDDLE = new FileLabel();
        MIDDLE.setId(UUIDGenerator.generate());
        MIDDLE.setName("Label 2");
        MIDDLE.setSort(20);
        MIDDLE.setFileLabelType(FileLabel.FileLabelType.SYSTEM);
        MIDDLE.setColor("#ffd111");
        MIDDLE.setCreateTime(DateUtils.now());
        FILE_LABELS.add(MIDDLE);

        FileLabel NORMAL = new FileLabel();
        NORMAL.setId(UUIDGenerator.generate());
        NORMAL.setName("Label 3");
        NORMAL.setSort(20);
        NORMAL.setFileLabelType(FileLabel.FileLabelType.SYSTEM);
        NORMAL.setColor("#68d2fb");
        NORMAL.setCreateTime(DateUtils.now());
        FILE_LABELS.add(NORMAL);
    }

    public static void refreshLabels(){
        FILE_LABELS.setAll(SpringUtils.getBean(FileLabelService.class).list());
    }

    public static void initProgram() {
        SpringUtils.getBean(FileLabelService.class).initLabel();
        log.info("load labels, total -> {}", RuntimeConstant.FILE_LABELS.size());
    }
}