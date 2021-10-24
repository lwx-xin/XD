package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.entity.Page;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.CodeUtil;
import priv.xin.xd.common.util.ShiroUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.service.ISysFileTypeService;
import priv.xin.xd.core.dao.FileTypeMapper;
import priv.xin.xd.core.entity.FileType;
import priv.xin.xd.core.entity.ex.FileTypeEx;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/10/3 11:58
 */
@Service
public class SysFileTypeServiceImpl implements ISysFileTypeService {

    @Resource
    private FileTypeMapper fileTypeMapper;

    @Override
    public Result queryListLimit(FileType fileType, Page page) {
        List<FileTypeEx> fileTypeList = fileTypeMapper.queryListLimit(fileType, page);
        int count = fileTypeMapper.queryListLimitCount(fileType);
        return new Result(true)
                .data("fileTypeList", fileTypeList)
                .data("count", count);
    }

    @Override
    public Result updateFileTypeDetail(String fileSuffix, FileType fileType) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        if (StrUtil.isEmpty(fileSuffix)) {
            return new Result(false).message(MessageLevel.ERROR, Message.FILE_TYPE_UNKNOWN);
        }

        String fileTypeCode = fileType.getFileType();
        CodeEnum code = CodeUtil.getCode(CodeEnum.FILE_TYPE_OTHER.getGroup(), fileTypeCode);
        if (code == null) {
            return new Result(false).message(MessageLevel.ERROR, Message.FILE_TYPE_UNKNOWN);
        }

        FileType fileTypeUpdate = new FileType();
        fileTypeUpdate.setFileSuffix(fileSuffix);
        fileTypeUpdate.setFileType(fileTypeCode);
        fileTypeUpdate.setFileTypeModifyUser(operator);

        if (fileTypeMapper.updateByPrimaryKey(fileTypeUpdate) != 1) {
            return new Result(false);
        }

        return new Result(true);
    }

}
