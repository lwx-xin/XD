package priv.xin.xd.core.entity.ex;

import priv.xin.xd.core.entity.File;

/**
 * @author ：lu
 * @date ：2021/9/27 18:53
 */
public class FileEx extends File {

    private String fileType;

    private String fileTypeName;

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getFileTypeName() {
        return fileTypeName;
    }

    public void setFileTypeName(String fileTypeName) {
        this.fileTypeName = fileTypeName;
    }
}
