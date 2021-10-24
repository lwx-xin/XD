package priv.xin.xd.common.entity;

import priv.xin.xd.common.util.StrUtil;

import java.io.Serializable;

/**
 * @author ：lu
 * @date ：2021/8/18 20:44
 */
public class Page implements Serializable {

    private static final long serialVersionUID = 2652185474655909310L;
    /**
     * 页码
     */
    private int pageNumber;

    /**
     * 每页显示的条数
     */
    private int pageSize;

    /**
     * 分页起始位置
     */
    private int offSet;

    /**
     * 排序的字段
     */
    private String sortField;

    /**
     * 排序的方式（asc，desc）
     */
    private String sortOrder;

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortField() {
        return sortField;
    }

    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    public String getSortOrder() {
        if(StrUtil.isEmpty(sortOrder)){
            sortOrder = "asc";
        }
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    /**
     * 查询起始位置
     *
     * @return
     */
    public Integer getOffSet() {
        return (pageNumber - 1) * pageSize;
    }

    public void setOffSet(int offSet) {
        this.offSet = offSet;
    }
}
