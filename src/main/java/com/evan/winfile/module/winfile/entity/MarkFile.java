package com.evan.winfile.module.winfile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.evan.winfile.common.util.DateUtils;
import com.evan.winfile.common.util.MD5Util;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.File;
import java.util.Date;
import java.util.regex.Pattern;

/**
 * @author Evan
 * @date 2022-11-17
 */
@Data
@Entity(name = "tb_mark_file")
@TableName("tb_mark_file")
public class MarkFile {
    @Id
    @TableId(type = IdType.INPUT)
    private String id;

    private int deepLevel;

    private String path;
    private String name;

    private Date createTime;

    public static MarkFile markFile(File file){
        MarkFile markFile = new MarkFile();
        markFile.setId(md5Id(file.getPath()));
        markFile.setDeepLevel(getDeepLevel(file));
        markFile.setPath(file.getPath());
        markFile.setName(file.getName());
        markFile.setCreateTime(DateUtils.now());
        return markFile;
    }

    public static String md5Id(String path){
        return MD5Util.md5(path);
    }

    public static int getDeepLevel(File file) {
        String[] split = file.getPath().split(Pattern.quote(File.separator));
        return split.length;
    }

}