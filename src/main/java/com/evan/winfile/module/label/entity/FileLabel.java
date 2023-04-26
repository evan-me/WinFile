package com.evan.winfile.module.label.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Evan
 * @date 2022-11-11
 */
@Data
@Entity(name = "tb_label")
@TableName("tb_label")
public class FileLabel {
    @Id
    @TableId(type = IdType.INPUT)
    private String id;

    private String name;
    private int sort = 100;
    private String color;
    @Enumerated(EnumType.STRING)
    private FileLabelType fileLabelType;
    private Date createTime;

    public enum FileLabelType{
        SYSTEM,
        USER
    }
}