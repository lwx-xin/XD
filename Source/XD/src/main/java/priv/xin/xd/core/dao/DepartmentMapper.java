package priv.xin.xd.core.dao;

import priv.xin.xd.core.entity.Department;
import priv.xin.xd.core.entity.ex.DepartmentEx;

import java.util.List;

public interface DepartmentMapper {
    int deleteByPrimaryKey(String departmentId);

    int insert(Department record);

    Department selectByPrimaryKey(String departmentId);

    int updateByPrimaryKey(Department record);

    /**
     * 通过实体作为筛选条件查询
     *
     * @param department 实例对象
     * @return 对象列表
     */
    List<Department> queryAll(Department department);

    List<DepartmentEx> queryAll_tree();

    /**
     * 查询部门被使用的次数
     * @param departmentId
     * @return
     */
    DepartmentEx queryDepartmentUsedCount(String departmentId);
}