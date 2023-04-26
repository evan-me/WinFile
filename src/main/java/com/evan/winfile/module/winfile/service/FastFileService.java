package com.evan.winfile.module.winfile.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.evan.winfile.module.winfile.entity.FastFile;

/**
 * @author Evan
 * @date 2022-11-14
 */
public interface FastFileService extends IService<FastFile> {
    void addFile(FastFile file);

    void removeFile(FastFile file);

    void updateFile(FastFile f);

    void addFileToLast(FastFile file);

    void removeFirst();
}