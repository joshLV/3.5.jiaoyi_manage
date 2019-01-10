package cn.juhaowan.cpoywriting.vo;

import java.util.List;

/**
 *
 * @author tanghong
 * @fileName Pagebean.java
 * @declaration 用户表查询条件工具类
 * @date 2018年5月18日上午11:25:19
 */
public class PageResult {
	private String total; //数据总行数
    private String success; //返回获取状态
    private List<?> rows; //返回结果
    private String totalPage;//总页数
    private String pageNumber;//当前页
    private String pageSize;//每页多少条数据
    
	public String getTotal() {
		return total;
	}
	public void setTotal(String total) {
		this.total = total;
	}
	public String getSuccess() {
		return success;
	}
	public void setSuccess(String success) {
		this.success = success;
	}
	public List<?> getRows() {
		return rows;
	}
	public void setRows(List<?> rows) {
		this.rows = rows;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(String pageNumber) {
		this.pageNumber = pageNumber;
	}
    
}
