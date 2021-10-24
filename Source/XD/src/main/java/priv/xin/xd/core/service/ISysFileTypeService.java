package priv.xin.xd.core.service;

import org.springframework.web.bind.annotation.RequestBody;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.FileType;

/**
 * @author ：lu
 * @date ：2021/10/3 11:58
 */
public interface ISysFileTypeService {


    /**
     * 带条件的分页查询
     *
     * @param fileType 查询条件
     * @param page
     * @return data: fileTypeList,count
     */
    public Result queryListLimit(FileType fileType, Page page);

    public Result updateFileTypeDetail(String fileSuffix, @RequestBody FileType fileType);

}
