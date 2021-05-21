package org.jdiy.core.res;

import java.util.List;

/**
 * 此类存储分页信息.
 * JDiy的分页程序使用了此类.
 *
 * @param <T> 数据库体类. 在JDiy系统中，为数据对象{@link org.jdiy.core.Rs}
 */
public abstract class AbstractLs<T> {
    protected int rowCount;
    protected int pageSize;
    protected int absPage;
    protected T[] items;
    protected int pageCount;

    protected void init(int rowCount, int pageSize, int absPage) {
        this.rowCount = rowCount;
        this.pageSize = pageSize < 0 ? 0 : pageSize;
        this.pageCount = this.pageSize == 0 ? 1 : (rowCount + pageSize - 1) / pageSize;
        if (absPage < 1) this.absPage = 1;
        else this.absPage = absPage;
    }

    /**
     * 带有指定属性参数的构造方法.
     *
     * @param rowCount 数据库记录总数
     * @param pageSize 每页显示的记录数
     * @param absPage  当前所在的页码
     * @param items    当前页的记录集（数组形式）
     */
    public AbstractLs(int rowCount, int pageSize, int absPage, T[] items) {
        init(rowCount, pageSize, absPage);
        this.items = items;
    }

    /**
     * 带有指定属性参数的构造方法.
     *
     * @param rowCount 数据库记录总数
     * @param pageSize 每页显示的记录数
     * @param absPage  当前所在的页码
     * @param items    当前页的记录集（List形式）
     */
    public AbstractLs(int rowCount, int pageSize, int absPage, List<T> items) {
        init(rowCount, pageSize, absPage);
        items.toArray(this.items);
    }

    /**
     * 获取数据库记录总数.
     *
     * @return 数据库记录总数
     */
    public int getRowCount() {
        return rowCount;
    }

    /**
     * 获取每页显示的记录数.
     *
     * @return 每页显示的记录数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 获取当前页码.
     *
     * @return 当前页码. 其值是一个1~{@link #getPageSize()}之间的一个整数数字
     */
    public int getAbsPage() {
        return absPage;
    }

    /**
     * 获取总页数.
     * 总页数根据构造方法传入的参数自动算出.
     *
     * @return 总页数.
     */
    public int getPageCount() {
        return pageCount;
    }

    /**
     * 获取当前页的记录集（对象数组）.
     *
     * @return 当前页的记录集（对象数组）.
     */
    public T[] getItems() {
        return items;
    }

}
