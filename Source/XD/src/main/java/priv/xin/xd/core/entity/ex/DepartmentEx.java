package priv.xin.xd.core.entity.ex;

import priv.xin.xd.core.entity.Department;

import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/9 13:28
 */
public class DepartmentEx extends Department {

    /**
     * 该部门下的职位
     */
    private List<PositionEx> positionExList;

    private int positionCount;

    public List<PositionEx> getPositionExList() {
        return positionExList;
    }

    public void setPositionExList(List<PositionEx> positionExList) {
        this.positionExList = positionExList;
    }

    public int getPositionCount() {
        return positionCount;
    }

    public void setPositionCount(int positionCount) {
        this.positionCount = positionCount;
    }
}
