package com.evan.winfile.module.winfile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.evan.winfile.common.util.DateUtils;
import com.evan.winfile.common.util.UUIDGenerator;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;
import java.util.Date;

/**
 * @author Evan
 * @date 2022-11-14
 */
@Data
@Entity(name = "tb_fast_file")
@TableName("tb_fast_file")
public class FastFile {
    @Id
    @TableId(type = IdType.INPUT)
    private String id;

    private String name;

    private String path;

    private int sort = 100;

    private Date createTime = DateUtils.now();

    private boolean pin = true;

    public static FastFile newFile(File file){
        return newFile(file, false);
    }

    public static FastFile newFile(File file, boolean pin){
        if(file==null){
            return null;
        }
        FastFile fastFile = new FastFile();
        fastFile.setId(UUIDGenerator.generate());
        fastFile.setCreateTime(DateUtils.now());
        fastFile.setPin(pin);
        fastFile.setSort(100);
        fastFile.setName(file.getName());
        fastFile.setPath(file.getPath());
        return fastFile;
    }
}