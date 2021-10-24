package priv.xin.xd.core.controller;

import org.springframework.web.bind.annotation.*;
import priv.xin.xd.check.MessageLevel;
import priv.xin.xd.check.system.Message;
import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.service.ISysDepartmentService;
import priv.xin.xd.core.entity.Department;

import javax.annotation.Resource;

/**
 * @author ：lu
 * @date ：2021/7/7 10:33
 */
@RestController
@RequestMapping("sys/department")
public class DepartmentController {

    @Resource
    private ISysDepartmentService sysDepartmentService;

    /**
     * @param department 查询条件+
     * @return
     */
    @RequestMapping(value = "/tree", method = RequestMethod.GET)
    public Result treeList(Department department) {
        Result result = sysDepartmentService.queryTreeList(department);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.DEPARTMENT_QUERY_ERROR);
        }
        Object departmentTree = result.getData("departmentExList");
        return result.clearData()
                .data("departmentTree", departmentTree)
                .message(MessageLevel.INFO, Message.DEPARTMENT_QUERY_SUCCESS);
    }

    /**
     * @param department 查询条件+
     * @return
     */
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public Result list(Department department) {
        Result result = sysDepartmentService.queryAll(department);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.DEPARTMENT_QUERY_ERROR);
        }
        Object departmentList = result.getData("departmentList");
        return result.clearData()
                .data("departmentList", departmentList)
                .message(MessageLevel.INFO, Message.DEPARTMENT_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.GET)
    public Result detail(@PathVariable("departmentId") String departmentId) {
        Result result = sysDepartmentService.queryDetail(departmentId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.DEPARTMENT_QUERY_ERROR);
        }
        Object departmentDetail = result.getData("departmentDetail");
        return result.clearData()
                .data("departmentDetail", departmentDetail)
                .message(MessageLevel.INFO, Message.DEPARTMENT_QUERY_SUCCESS);
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.PUT)
    public Result update(@PathVariable("departmentId") String departmentId, @RequestBody Department department) {
        Result result = sysDepartmentService.updateDepartmentDetail(departmentId, department);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.DEPARTMENT_EDIT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.DEPARTMENT_EDIT_SUCCESS);
    }

    @RequestMapping(value = "", method = RequestMethod.POST)
    public Result insert(@RequestBody Department department) {
        Result result = sysDepartmentService.insertDepartmentDetail(department);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.DEPARTMENT_INSERT_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.DEPARTMENT_INSERT_SUCCESS);
    }

    @RequestMapping(value = "/{departmentId}", method = RequestMethod.DELETE)
    public Result delete(@PathVariable("departmentId") String departmentId) {
        Result result = sysDepartmentService.deleteByDepartmentId(departmentId);
        if (!result.getStatus()) {
            return result.message(MessageLevel.INFO, Message.DEPARTMENT_DELETE_ERROR);
        }
        return result.message(MessageLevel.INFO, Message.DEPARTMENT_DELETE_SUCCESS);
    }

}
