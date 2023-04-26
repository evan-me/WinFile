package com.evan.winfile.runtime.text.impl;

import com.evan.winfile.runtime.text.AbstractTextAlias;

/**
 * @author Evan
 * @date 2022-11-21
 */
public class EnglishTextAlias implements AbstractTextAlias {

    @Override
    public String language() {
        return "en";
    }

    @Override
    public String computer() {
        return "My Computer";
    }

    @Override
    public String fastFile() {
        return "Quick Access";
    }

    @Override
    public Title TITLE() {
        return new Title() {
            @Override
            public String confirm() {
                return "confirm";
            }

            @Override
            public String warn() {
                return "warn";
            }

            @Override
            public String labelManager() {
                return "label manager";
            }

            @Override
            public String fileManager() {
                return "file manager";
            }
        };
    }

    @Override
    public System SYSTEM() {
        return new System() {
            @Override
            public String desktop() {
                return "Desktop";
            }

            @Override
            public String download() {
                return "Downloads";
            }

            @Override
            public String document() {
                return "Documents";
            }

            @Override
            public String searchText() {
                return "Type something here to search...";
            }
        };
    }

    @Override
    public Drive DRIVE() {
        return new Drive() {
            @Override
            public String totalSpace() {
                return "Total space: ";
            }

            @Override
            public String freeSpace() {
                return "Free space: ";
            }
        };
    }

    @Override
    public Directory DIRECTORY() {
        return new Directory() {
            @Override
            public String typeLogo() {
                return "Type";
            }

            @Override
            public String fileName() {
                return "File Name";
            }

            @Override
            public String modifyTime() {
                return "Modify Time";
            }

            @Override
            public String size() {
                return "Size";
            }

            @Override
            public String label() {
                return "Label";
            }

            @Override
            public String currentFileCount() {
                return "Current files count: ";
            }
        };
    }

    @Override
    public Operation OPERATION() {
        return new Operation() {
            @Override
            public String add() {
                return "New";
            }

            @Override
            public String confirm() {
                return "Confirm";
            }

            @Override
            public String cancel() {
                return "Cancel";
            }
        };
    }

    @Override
    public Warn WARN() {
        return new Warn() {
            @Override
            public String maxPinFileLimit() {
                return "Max pin file limit: 8";
            }

            @Override
            public Object cantOpenFile() {
                return "Can't open this file: ";
            }
        };
    }

    @Override
    public RightClick RIGHTCLICK() {
        return new RightClick() {
            @Override
            public String openWithWindows() {
                return "Open file in windows";
            }

            @Override
            public String pinToQuickAccess() {
                return "Pin to " + fastFile();
            }

            @Override
            public String cancelPin() {
                return "Remove from pin";
            }
        };
    }
}