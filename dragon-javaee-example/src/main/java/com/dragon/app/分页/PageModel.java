package com.dragon.app.分页;

import java.util.List;

/**
 * 用于前台分页
 *
 * @param <T>
 */
public class PageModel<T> {
	
	private List<T> data;
	private int currentPage = 1;	// 当前页
	private int pageSize = 20;	// 每页大小
	private int totalCount = 0;	// 数据总条数
	private int totalPage = 1;	// 共几页
	
	private boolean hasNextPage = false;	// 是否还有下一页
	private boolean hasPreviousPage = false;	// 是否还有前一页
	
	public PageModel(List<T> data, int pageSize) {
		this.data = data;
		this.currentPage = 1;
		this.pageSize = pageSize;
		this.totalCount = data.size();
		if((this.totalCount % this.pageSize) == 0) {
			this.totalPage = this.totalCount / this.pageSize;
		} else {
			this.totalPage = (this.totalCount / this.pageSize) + 1;
		}
		
		if(this.currentPage >= this.totalPage) {
			this.hasNextPage = false;
		} else {
			this.hasNextPage = true;
		}
	}
	
	/**
	 * 获取第一页数据
	 * @return
	 */
	public List<T> getFirstPage() {
		if(this.hasNextPage) {
			return data.subList(0, this.pageSize);
		} else {
			return this.data;
		}
	}
	
	/**
	 * 获取下一页数据
	 * @return
	 */
	public List<T> getNextPage() {
		this.currentPage = this.currentPage + 1;
		this.disposePage();
		
		return getDataByPage(this.currentPage);
	}
	
	/**
	 * 获取上一页数据
	 * @return
	 */
	public List<T> getPreviousPage() {
		this.currentPage = this.currentPage - 1;
		this.disposePage();
		
		return getDataByPage(this.currentPage);
	}

	/**
	 * 判定和设置上一页和下一页
	 */
	private void disposePage() {
		if((this.currentPage - 1) > 0) {
			this.hasPreviousPage = true;
		} else {
			this.hasPreviousPage = false;
		}
		
		if(this.currentPage >= this.totalPage) {
			this.hasNextPage = false;
		} else {
			this.hasNextPage = true;
		}
	}
	
	/**
	 * 根据page获取第几页内容
	 * @param page
	 * @return
	 */
	public List<T> getDataByPage(int page) {
		if(page <= 0) {
			this.currentPage = 1;
		} else {
			this.currentPage = page;
		}
		this.disposePage();
		int pageStartRow = 0;
		int pageEndRow = 0;
		if(page * this.pageSize < this.totalCount) {	// 判断是否是最后一页
			pageEndRow = page * this.pageSize;
			pageStartRow = pageEndRow - this.pageSize;
		} else {
			pageEndRow = this.totalCount;
			pageStartRow = (this.totalPage - 1) * pageSize;
		}
		
		if(!this.data.isEmpty()) {
			return data.subList(pageStartRow, pageEndRow);
		}
		return null;
	}

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public boolean isHasNextPage() {
		return hasNextPage;
	}

	public void setHasNextPage(boolean hasNextPage) {
		this.hasNextPage = hasNextPage;
	}

	public boolean isHasPreviousPage() {
		return hasPreviousPage;
	}

	public void setHasPreviousPage(boolean hasPreviousPage) {
		this.hasPreviousPage = hasPreviousPage;
	}
	
}