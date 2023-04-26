package com.evan.winfile.module.winfile.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * @author Evan
 * @date 2022-11-18
 */
@Data
@Entity(name = "rel_label_file")
@TableName("rel_label_file")
public class RelLabelFile {
    @Id
    @TableId(type = IdType.INPUT)
    private String id;
    private String fileId;
    private int deepLevel;
    private String fileLabelId;
    private String fileLabelColor;
    private Date createTime;
}