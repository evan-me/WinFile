package com.evan.winfile.runtime.text.impl;

import com.evan.winfile.runtime.text.AbstractTextAlias;

/**
 * @author Evan
 * @date 2022-11-21
 */
public class ChineseTextAlias implements AbstractTextAlias {

    @Override
    public String language() {
        return "cn";
    }

    @Override
    public String computer() {
        return "此电脑";
    }

    @Override
    public String fastFile() {
        return "快速访问";
    }

    @Override
    public Title TITLE() {
        return new Title() {
            @Override
            public String confirm() {
                return "确认";
            }

            @Override
            public String warn() {
                return "提示";
            }

            @Override
            public String labelManager() {
                return "标签管理";
            }

            @Override
            public String fileManager() {
                return "文件管理";
            }
        };
    }

    @Override
    public System SYSTEM() {
        return new System() {
            @Override
            public String desktop() {
                return "桌面";
            }

            @Override
            public String download() {
                return "下载";
            }

            @Override
            public String document() {
                return "文档";
            }

            @Override
            public String searchText() {
                return "输入文件名以搜索";
            }
        };
    }

    @Override
    public Drive DRIVE() {
        return new Drive() {
            @Override
            public String totalSpace() {
                return "总大小";
            }

            @Override
            public String freeSpace() {
                return "可用";
            }
        };
    }

    @Override
    public Directory DIRECTORY() {
        return new Directory() {
            @Override
            public String typeLogo() {
                return "类型";
            }

            @Override
            public String fileName() {
                return "文件名";
            }

            @Override
            public String modifyTime() {
                return "修改时间";
            }

            @Override
            public String size() {
                return "文件大小";
            }

            @Override
            public String label() {
                return "标签";
            }

            @Override
            public String currentFileCount() {
                return "当前文件数量：";
            }
        };
    }

    @Override
    public Operation OPERATION() {
        return new Operation() {
            @Override
            public String add() {
                return "添加";
            }

            @Override
            public String confirm() {
                return "确认";
            }

            @Override
            public String cancel() {
                return "取消";
            }
        };
    }

    @Override
    public Warn WARN() {
        return new Warn() {
            @Override
            public String maxPinFileLimit() {
                return "最大支持固定文件： 8个";
            }

            @Override
            public Object cantOpenFile() {
                return "无法打开该文件";
            }
        };
    }

    @Override
    public RightClick RIGHTCLICK() {
        return new RightClick() {
            @Override
            public String openWithWindows() {
                return "从 Windows 打开";
            }

            @Override
            public String pinToQuickAccess() {
                return "固定到" + fastFile();
            }

            @Override
            public String cancelPin() {
                return "取消固定";
            }
        };
    }
}