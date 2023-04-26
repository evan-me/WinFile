package com.evan.winfile.module.winfile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evan.winfile.common.util.DateUtils;
import com.evan.winfile.common.util.MD5Util;
import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.common.util.UUIDGenerator;
import com.evan.winfile.module.label.entity.FileLabel;
import com.evan.winfile.module.winfile.dao.RelLabelFileMapper;
import com.evan.winfile.module.winfile.entity.FastFile;
import com.evan.winfile.module.winfile.entity.MarkFile;
import com.evan.winfile.module.winfile.entity.RelLabelFile;
import com.evan.winfile.module.winfile.service.MarkFileService;
import com.evan.winfile.module.winfile.service.RelLabelFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.List;

/**
 * @author Evan
 * @date 2022-11-18
 */
@Service
public class RelLabelFileServiceImpl
        extends ServiceImpl<RelLabelFileMapper, RelLabelFile>
        implements RelLabelFileService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFileLabel(File file, FileLabel label) {
        MarkFile markFile = MarkFile.markFile(file);
        SpringUtils.getBean(MarkFileService.class).addMarkFile(markFile);
        LambdaQueryWrapper<RelLabelFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(RelLabelFile::getFileLabelId, label.getId())
                .eq(RelLabelFile::getFileId, markFile.getId());
        if(count(lambdaQueryWrapper)<1){
            RelLabelFile relLabelFile = new RelLabelFile();
            relLabelFile.setId(UUIDGenerator.generate());
            relLabelFile.setCreateTime(DateUtils.now());
            relLabelFile.setFileLabelId(label.getId());
            relLabelFile.setFileLabelColor(label.getColor());
            relLabelFile.setFileId(markFile.getId());
            relLabelFile.setDeepLevel(markFile.getDeepLevel());
            save(relLabelFile);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeRelation(File file, FileLabel label) {
        MarkFile markFile = MarkFile.markFile(file);
        LambdaQueryWrapper<RelLabelFile> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(RelLabelFile::getFileLabelId, label.getId())
                .eq(RelLabelFile::getFileId, markFile.getId());
        list(lambdaQueryWrapper).forEach(this::removeById);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelLabelFile> listByLabel(FileLabel fileLabel) {
        LambdaQueryWrapper<RelLabelFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(RelLabelFile::getFileLabelId, fileLabel.getId());
        return list(lambdaQueryWrapper);
    }

    @Override
    @Transactional(readOnly = true)
    public List<RelLabelFile> listByFile(FastFile file) {
        LambdaQueryWrapper<RelLabelFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(RelLabelFile::getFileId, MD5Util.md5(file.getPath()));
        return list(lambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void labelRemove(FileLabel label) {
        LambdaQueryWrapper<RelLabelFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(RelLabelFile::getFileLabelId, label.getId());
        list(lambdaQueryWrapper).forEach(relation -> {
            SpringUtils.getBean(MarkFileService.class).removeById(relation.getFileId());
            removeById(relation);
        });
    }
}