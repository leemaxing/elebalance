package org.jdiy.core;

import java.io.Serializable;

import org.jdiy.core.ex.JDiyParamException;

/**
 * Args查询参数对象,用于JDiy数据库Dao操作的查询参数带入.
 *
 * @author 子秋(ziquee)  http://www.jdiy.org
 */
public class Args implements Cloneable, Serializable {
    private final static String allCols = "*";
    private String table;
    private String cols = allCols;
    private String filter = null;
    private int pageSize = 0;
    private int absPage = 1;


    /**
     * 构造一个新的Args对象的同时指定要操作的表.
     *
     * @param table 表名
     * @see #setTable(String)
     */
    public Args(String table) {
        this.setTable(table);
    }

    /**
     * 构造一个新的Args对象的同时指定要操作的表,并且指定查询条件或分组/排序子句(group by / order by).
     *
     * @param table  表名
     * @param filter 查询条件或分组/排序子句(group by / order by)
     * @see #setTable(String)
     * @see #setFilter(String)
     */
    public Args(String table, String filter) {
        this(table);
        this.setFilter(filter);
    }

    /**
     * 构造一个新的Args对象的同时指定要操作的表名,查询条件及要获取的数据表列.
     *
     * @param table  表名
     * @param filter 指定查询条件或分组/排序子句(group by / order by)
     * @param cols 要获取的数据表列
     * @see #setTable(String)
     * @see #setFilter(String)
     * @see #setCols(String)
     */
    public Args(String table, String filter, String cols) {
        this(table, filter);
        this.setCols(cols);
    }

    /**
     * 根据给定的参数构造Args对象.
     * @param table 表名
     * @param filter 查询条件或分组/排序子句(group by / order by)
     * @param pageSize 分页查询中每页显示的记录数
     * @param absPage  分页查询中指定当前页
     * @see #setPageSize(int)
     * @see #setAbsPage(int)
     */
    public Args(String table, String filter, int pageSize, int absPage){
        this(table, filter);
        this.setPageSize(pageSize);
        this.setAbsPage(absPage);
    }

    /**
     * 根据给定的参数构造Args对象.
     * @param table 表名
     * @param filter 查询条件或分组/排序子句(group by / order by)
     * @param pageSize 分页查询中每页显示的记录数
     * @param absPage  分页查询中指定当前页
     * @param cols 数据表列名
     * @see #setTable(String)
     * @see #setFilter(String)
     * @see #setPageSize(int)
     * @see #setAbsPage(int)
     * @see #setCols(String)
     */
    public Args(String table, String filter, int pageSize, int absPage, String cols){
        this(table, filter, pageSize, absPage);
        this.setCols(cols);
    }

    /**
     * 获取已指定的查询条件或分组/排序子句(group by / order by).
     * <br />查询条件是SQL语句的"WHERE"关键字后面的SQL代码片段.
     * @see #setFilter(String)
     * @return 查询条件字符串或分组/排序子句(group by / order by).
     */
    public String getFilter() {
        return filter;
    }

     /**
     * 指定查询条件.
     * <br /><br />查询条件是SQL语句的 " WHERE " 关键字后面的SQL代码片段. <br />
     *
     * 默认值为null.如果不指定此属性或指定为空值(null或空字符串"")，将查询所有记录.
     * 同时排序方式及分组方式也可以在此指定.
      * <br/><strong>示例代码：</strong><br/>
      * argsObj.setFilter("age>0 and name&lt;&gt;''");<br />
      * argsObj.setFilter("age>0 order by name");<br/>
      * argsObj.setFilter("order by name");<br/>
      * @param filter 查询条件字符串(可带上ORDER BY, GROUP BY 子句)
     */
    public void setFilter(String filter) {

        this.filter = filter;
    }

    /**
     * 获取对象要操作的数据表名.
     * @return 数据表名.
     */
    public String getTable() {
        return table;
    }

    /**
     * 指定对象要操作的数据表名.      <br /><br />
     * 可以同时指定表的别名，在多表查询中也可以指定多个数据表，例如:<br />
     * args.setTable("news n");<br/>
     * args.setTable("newstype t, news n");
     * @param table 数据库表名称.
     */
    public void setTable(String table) {
        if(table==null || "".equals(table=table.trim()))
            throw new JDiyParamException("参数[table]不正确");
        this.table = table;
    }

    /**
     * 返回要查询的表列字符串.     <br/><br/>
     * 如果未指定，默认值为<strong>"*"</strong>, 代表查询表的全部列.
     * @return 表列字符串.
     * @see #setCols(String)
     */
    public String getCols() {
        return cols;
    }

    /**
     * 指定要查询的表列字符串.
     * <br /><br /><strong>注意：</strong> 默认值为<strong>"*"</strong>, 代表查询表的全部列数据.
     * 在多表查询语句中，可以分别指定要获取的表列名称，同时也可以使用AS语句指定列别名。
     * <br/><br/><strong>代码示例：</strong><br/>
     * args.setCols("id, name, age");<br />
     * args.setCols("table1.*, table2.name AS table2Name");
     *
     * @param cols 要查询的列名称. <br /><br />如果通过此查询参数取得的Rs对象需要进行更新或删除操作，
     * cols中必须包含主键的字段名.因为更新删除操作都是使用主键值作为条件依据的.
     */
    public void setCols(String cols) {
        this.cols = cols==null || "".equals(cols.trim()) ? allCols:cols;
    }

    /**
     * 获取已指定的每页显示记录的条数.
     * @return 每页显示记录的条数
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * 分页查询中，指定每页显示的记录条数.
     * <br /><br/>默认值为0， 代表不分页（即查询返回全部满足条件的记录.）
     * @param pageSize 指定每页显示的记录条数.
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize<0?0:pageSize;
    }

    /**
     * 获取已指定的当前页的页号.
     * @return 页号. 不分页时，默认返回1.
     */
    public int getAbsPage() {
        return absPage;
    }

    /**
     * 分页查询中，指定当前要获取的页号.
     * @param absPage 页号. 如不指定，默认值为1.
     */
    public void setAbsPage(int absPage) {
        this.absPage = absPage<1?1:absPage;
    }

    /**
     * 克隆一个新的Args对象.
     *
     * @return Args 克隆后的新Args对象
     */
    @Override
    @SuppressWarnings("ALL")
    public Args clone() {
        try {
            return (Args) super.clone();
        } catch (CloneNotSupportedException e) {
            return new Args(this.getTable());
        }
    }
}
