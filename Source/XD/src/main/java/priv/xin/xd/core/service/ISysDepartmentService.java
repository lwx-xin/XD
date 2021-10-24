package priv.xin.xd.core.service;

import priv.xin.xd.common.result.Result;
import priv.xin.xd.core.entity.Department;

/**
 * @author ：lu
 * @date ：2021/7/7 10:36
 */
public interface ISysDepartmentService {

    /**
     * 条件查询
     * @param department 条件
     * @return data: departmentList
     */
    public Result queryAll(Department department);

    /**
     *
     * @param department
     * @return data: departmentExList
     */
    public Result queryTreeList(Department department);

    /**
     *
     * @param departmentId
     * @return data: departmentDetail
     */
    public Result queryDetail(String departmentId);

    public Result updateDepartmentDetail(String departmentId, Department department);

    public Result insertDepartmentDetail(Department department);

    public Result deleteByDepartmentId(String departmentId);

}
