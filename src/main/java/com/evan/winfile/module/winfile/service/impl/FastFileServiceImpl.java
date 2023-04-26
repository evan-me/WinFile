package com.evan.winfile.module.winfile.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.evan.winfile.module.winfile.dao.FastFileMapper;
import com.evan.winfile.module.winfile.entity.FastFile;
import com.evan.winfile.module.winfile.service.FastFileService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Evan
 * @date 2022-11-14
 */
@Service
public class FastFileServiceImpl extends ServiceImpl<FastFileMapper, FastFile> implements FastFileService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFile(FastFile file) {
        LambdaQueryWrapper<FastFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(FastFile::getPath, file.getPath());
        if(count(lambdaQueryWrapper) < 1){
            save(file);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFile(FastFile file) {
        LambdaQueryWrapper<FastFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(FastFile::getPath, file.getPath());
        remove(lambdaQueryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateFile(FastFile file) {
        LambdaQueryWrapper<FastFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(FastFile::getPath, file.getPath());
        FastFile fastFile = getOne(lambdaQueryWrapper);
        if(fastFile!=null){
            fastFile.setPin(file.isPin());
            updateById(fastFile);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addFileToLast(FastFile file) {
        addFile(file);
        removeFirst();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void removeFirst() {
        LambdaQueryWrapper<FastFile> lambdaQueryWrapper = Wrappers.lambdaQuery();
        lambdaQueryWrapper.eq(FastFile::isPin, false);
        List<FastFile> list = list(lambdaQueryWrapper);
        if(list.size()>0){
            removeById(list.get(0));
        }
    }
}