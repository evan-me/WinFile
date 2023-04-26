package com.evan.winfile.module.label.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.evan.winfile.module.label.entity.FileLabel;

/**
 * @author Evan
 * @date 2022-11-11
 */
public interface FileLabelService extends IService<FileLabel> {
    void initLabel();

    void deleteLabel(FileLabel label);
}