package com.evan.winfile.module.label.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evan.winfile.common.util.SpringUtils;
import com.evan.winfile.module.label.dao.FileLabelMapper;
import com.evan.winfile.module.label.entity.FileLabel;
import com.evan.winfile.module.label.service.FileLabelService;
import com.evan.winfile.module.winfile.service.RelLabelFileService;
import com.evan.winfile.runtime.RuntimeConstant;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Evan
 * @date 2022-11-11
 */
@Service
public class FileLabelServiceImpl extends ServiceImpl<FileLabelMapper, FileLabel> implements FileLabelService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void initLabel() {
        LambdaQueryWrapper<FileLabel> lambdaQueryWrapper =  Wrappers.lambdaQuery();
        if(count(lambdaQueryWrapper)<1){
            RuntimeConstant.FILE_LABELS.forEach(this::save);
        }else{
            lambdaQueryWrapper = Wrappers.lambdaQuery();
            RuntimeConstant.FILE_LABELS.clear();
            RuntimeConstant.FILE_LABELS.addAll(list(lambdaQueryWrapper));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLabel(FileLabel label) {
        removeById(label);
        SpringUtils.getBean(RelLabelFileService.class).labelRemove(label);
    }
}