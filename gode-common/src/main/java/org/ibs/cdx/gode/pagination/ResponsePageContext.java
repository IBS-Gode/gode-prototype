package org.ibs.cdx.gode.pagination;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponsePageContext extends PageContext{

	private boolean next;
	private boolean previous;
	private long totalCount;
	private long totalPages;
	
	public ResponsePageContext(int pageSize) {
		super(pageSize);
	}
	
	public ResponsePageContext(PageContext ctx) {
		this(ctx.getPageSize());
		this.setPageNumber(ctx.getPageNumber());
		this.setSortOrder(ctx.getSortOrder());
	}

	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}


	public long getTotalCount() {
		return totalCount;
	}


	@JsonProperty(value="hasNext")        
	public boolean hasNext() {
		return next;
	}


	public void setNext(boolean next) {
		this.next = next;
	}


	@JsonProperty(value="hasPrevious")        
	public boolean hasPrevious() {
		return previous;
	}

	public void setPrevious(boolean previous) {
		this.previous = previous;
	}


	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}
}
