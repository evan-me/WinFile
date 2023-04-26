package com.evan.winfile.module.winfile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.module.winfile.dao.MarkFileMapper;
import com.evan.winfile.module.winfile.entity.MarkFile;
import com.evan.winfile.module.winfile.entity.RelLabelFile;
import com.evan.winfile.module.winfile.service.MarkFileService;
import com.evan.winfile.module.winfile.service.RelLabelFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Evan
 * @date 2022-11-18
 */
@Service
public class MarkFileServiceImpl
        extends ServiceImpl<MarkFileMapper, MarkFile>
        implements MarkFileService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addMarkFile(MarkFile markFile) {
        if(getById(markFile.getId())==null){
            save(markFile);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, List<RelLabelFile>> listByParentFile(File parent) {
        LambdaQueryWrapper<MarkFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        int deepLevel = MarkFile.getDeepLevel(parent)+1;
        lambdaQueryWrapper.eq(MarkFile::getDeepLevel, deepLevel);
        List<MarkFile> markFiles = list(lambdaQueryWrapper);
        if(markFiles.size()>0){
            LambdaQueryWrapper<RelLabelFile> relMapper = Wrappers.lambdaQuery();
            relMapper.eq(RelLabelFile::getDeepLevel, deepLevel)
                    .in(RelLabelFile::getFileId, markFiles.stream().map(MarkFile::getId).collect(Collectors.toList()));
            List<RelLabelFile> relations = SpringUtils.getBean(RelLabelFileService.class).list(relMapper);
            if(relations.size()>0){
                return relations.stream().collect(Collectors.groupingBy(RelLabelFile::getFileId, Collectors.toList()));
            }
        }
        return new HashMap<>();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MarkFile> listByFileId(List<String> fileIds) {
        LambdaQueryWrapper<MarkFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.in(MarkFile::getId, fileIds);
        return list(lambdaQueryWrapper);
    }
}