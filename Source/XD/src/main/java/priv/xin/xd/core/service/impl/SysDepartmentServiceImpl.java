package priv.xin.xd.core.service.impl;

import org.springframework.stereotype.Service;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.code.CodeEnum;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.common.util.ShiroUtil;
import priv.xin.xd.common.util.StrUtil;
import priv.xin.xd.core.service.ISysDepartmentService;
import priv.xin.xd.core.dao.DepartmentMapper;
import priv.xin.xd.core.entity.Department;
import priv.xin.xd.core.entity.ex.DepartmentEx;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/7 10:36
 */
@Service
public class SysDepartmentServiceImpl implements ISysDepartmentService {

    @Resource
    private DepartmentMapper departmentMapper;

    @Override
    public Result queryAll(Department department) {
        List<Department> departmentList = departmentMapper.queryAll(department);
        return new Result(true).data("departmentList", departmentList);
    }

    @Override
    public Result queryTreeList(Department department) {
        List<DepartmentEx> departmentExList = departmentMapper.queryAll_tree();
        return new Result(true).data("departmentExList", departmentExList);
    }

    @Override
    public Result queryDetail(String departmentId) {
        if (StrUtil.isEmpty(departmentId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.DEPARTMENT_UNKNOWN);
        }
        Department department = departmentMapper.selectByPrimaryKey(departmentId);
        return new Result(true).data("departmentDetail", department);
    }

    @Override
    public Result updateDepartmentDetail(String departmentId, Department department) {
        if (StrUtil.isEmpty(departmentId)) {
            return new Result(false).message(MessageLevel.ERROR, Message.DEPARTMENT_UNKNOWN);
        }
        Department departmentUpdate = new Department();
        departmentUpdate.setDepartmentId(departmentId);
        departmentUpdate.setDepartmentName(department.getDepartmentName());
        departmentUpdate.setDepartmentModifyUser(ShiroUtil.getUserId());

        int i = departmentMapper.updateByPrimaryKey(departmentUpdate);
        if (i == 0) {
            department = departmentMapper.selectByPrimaryKey(departmentId);
            Result result = new Result(false);
            if (department == null) {
                result.message(MessageLevel.ERROR, Message.DEPARTMENT_UNKNOWN);
            }
            return result;
        }
        return new Result(true);
    }

    @Override
    public Result insertDepartmentDetail(Department department) {
        // 操作者(登录用户)
        String operator = ShiroUtil.getUserId();

        Department departmentInsert = new Department();
        departmentInsert.setDepartmentId(StrUtil.getUUID());
        departmentInsert.setDepartmentName(department.getDepartmentName());
        departmentInsert.setDepartmentDelFlag(CodeEnum.DEL_FLAG_1.getValue());
        departmentInsert.setDepartmentCreateUser(operator);
        departmentInsert.setDepartmentModifyUser(operator);

        int i = departmentMapper.insert(departmentInsert);
        if (i == 0) {
            return new Result(false);
        }
        return new Result(true);
    }

    @Override
    public Result deleteByDepartmentId(String departmentId) {
        if (StrUtil.isEmpty(departmentId)) {
            return new Result(false).message(MessageLevel.ERROR,Message.DEPARTMENT_UNKNOWN);
        }

        // check
        Result checkResult = new Result(true);
        DepartmentEx departmentUsedCount = departmentMapper.queryDepartmentUsedCount(departmentId);
        int positionCount = departmentUsedCount.getPositionCount();
        if (positionCount != 0) {
            checkResult.status(false).message(MessageLevel.ERROR, "该部门下还有" + positionCount + "个职位未删除");
        }

        if (!checkResult.getStatus()) {
            return checkResult;
        }

        int i = departmentMapper.deleteByPrimaryKey(departmentId);
        if (i == 0) {
            return new Result(false);
        }
        return new Result(true);
    }
}
