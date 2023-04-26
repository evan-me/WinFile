package com.evan.winfile.module.winfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.evan.winfile.module.winfile.entity.MarkFile;
import com.evan.winfile.module.winfile.entity.RelLabelFile;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * @author Evan
 * @date 2022-11-18
 */
public interface MarkFileService extends IService<MarkFile> {
    void addMarkFile(MarkFile markFile);

    Map<String, List<RelLabelFile>> listByParentFile(File parent);

    List<MarkFile> listByFileId(List<String> fileIds);
}