package com.evan.winfile.module.winfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.evan.winfile.module.label.entity.FileLabel;
import com.evan.winfile.module.winfile.entity.FastFile;
import com.evan.winfile.module.winfile.entity.RelLabelFile;

import java.io.File;
import java.util.List;

/**
 * @author Evan
 * @date 2022-11-18
 */
public interface RelLabelFileService extends IService<RelLabelFile> {

    void addFileLabel(File file, FileLabel label);

    void removeRelation(File file, FileLabel label);

    List<RelLabelFile> listByLabel(FileLabel fileLabel);

    List<RelLabelFile> listByFile(FastFile f);

    void labelRemove(FileLabel label);
}