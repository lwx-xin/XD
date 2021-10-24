package priv.xin.xd.core.entity.ex;

import priv.xin.xd.core.entity.Department;
import priv.xin.xd.core.entity.Position;
import priv.xin.xd.core.entity.User;

import java.io.Serializable;
import java.util.List;

/**
 * @author ：lu
 * @date ：2021/7/2 17:53
 */
public class PositionEx extends Position implements Serializable {
    private static final long serialVersionUID = 723876491699955937L;

    /**
     * 该职位所在的部门
     */
    private Department department;

    /**
     * 拥有该职位的用户
     */
    private List<User> userList;

    /**
     * 使用该职位的用户
     */
    private int userCount;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }
}
