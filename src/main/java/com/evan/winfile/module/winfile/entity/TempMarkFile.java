package com.evan.winfile.module.winfile.entity;

import com.evan.winfile.module.label.entity.FileLabel;
import lombok.Data;

import java.util.List;

/**
 * @author Administrator
 * @date 2022/11/18 22:57
 */
@Data
public class TempMarkFile {
    private MarkFile markFile;
    private List<FileLabel> fileLabels;
}
