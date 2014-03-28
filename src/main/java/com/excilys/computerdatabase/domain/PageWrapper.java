package com.excilys.computerdatabase.domain;

import java.util.List;

public class PageWrapper {

	private String search;
	private String searchBy;
	private String orderBy;
	private String way;
	private int offset;
	private int numberOfPages;
	private int computersPerPage = 12;
	private int currentPage;
	private int numberOfComputers;
	private List<ComputerDTO> computerDTOList;

	public PageWrapper() {
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public String getWay() {
		return way;
	}

	public void setWay(String way) {
		this.way = way;
	}

	public int getOffset() {
		return offset;
	}

	public void setOffset(int offset) {
		this.offset = offset;
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}

	public int getComputersPerPage() {
		return computersPerPage;
	}

	public void setComputersPerPage(int computersPerPage) {
		this.computersPerPage = computersPerPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getNumberOfComputers() {
		return numberOfComputers;
	}

	public void setNumberOfComputers(int numberOfComputers) {
		this.numberOfComputers = numberOfComputers;
	}

	public List<ComputerDTO> getComputerDTOList() {
		return computerDTOList;
	}

	public void setComputerDTOList(List<ComputerDTO> computerDTOList) {
		this.computerDTOList = computerDTOList;
	}

	@Override
	public String toString() {
		return "PageWrapper search=" + search + ", searchBy=" + searchBy
				+ ", orderBy=" + orderBy + ", way=" + way + ", offset="
				+ offset + ", numberOfPages=" + numberOfPages
				+ ", computersPerPage=" + computersPerPage + ", currentPage="
				+ currentPage + ", numberOfComputers=" + numberOfComputers
				+ ", computerDTOList=" + computerDTOList + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((computerDTOList == null) ? 0 : computerDTOList.hashCode());
		result = prime * result + computersPerPage;
		result = prime * result + currentPage;
		result = prime * result + numberOfComputers;
		result = prime * result + numberOfPages;
		result = prime * result + offset;
		result = prime * result + ((orderBy == null) ? 0 : orderBy.hashCode());
		result = prime * result + ((search == null) ? 0 : search.hashCode());
		result = prime * result
				+ ((searchBy == null) ? 0 : searchBy.hashCode());
		result = prime * result + ((way == null) ? 0 : way.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageWrapper other = (PageWrapper) obj;
		if (computerDTOList == null) {
			if (other.computerDTOList != null)
				return false;
		} else if (!computerDTOList.equals(other.computerDTOList))
			return false;
		if (computersPerPage != other.computersPerPage)
			return false;
		if (currentPage != other.currentPage)
			return false;
		if (numberOfComputers != other.numberOfComputers)
			return false;
		if (numberOfPages != other.numberOfPages)
			return false;
		if (offset != other.offset)
			return false;
		if (orderBy == null) {
			if (other.orderBy != null)
				return false;
		} else if (!orderBy.equals(other.orderBy))
			return false;
		if (search == null) {
			if (other.search != null)
				return false;
		} else if (!search.equals(other.search))
			return false;
		if (searchBy == null) {
			if (other.searchBy != null)
				return false;
		} else if (!searchBy.equals(other.searchBy))
			return false;
		if (way == null) {
			if (other.way != null)
				return false;
		} else if (!way.equals(other.way))
			return false;
		return true;
	}

	public static class Builder {

		PageWrapper pageWrapper;

		private Builder() {
			pageWrapper = new PageWrapper();
		}

		public Builder search(String search) {
			if (search != null)
				this.pageWrapper.search = search;
			return this;
		}

		public Builder searchBy(String searchBy) {
			this.pageWrapper.searchBy = searchBy;
			return this;
		}

		public Builder orderBy(String orderBy) {
			this.pageWrapper.orderBy = orderBy;
			return this;
		}

		public Builder way(String way) {
			this.pageWrapper.way = way;
			return this;
		}

		public Builder offset(int offset) {
			this.pageWrapper.offset = offset;
			return this;
		}

		public Builder numberOfPages(int numberOfPages) {
			this.pageWrapper.numberOfPages = numberOfPages;
			return this;
		}

		public Builder computersPerPage(int computersPerPage) {
			this.pageWrapper.computersPerPage = computersPerPage;
			return this;
		}

		public Builder currentPage(int currentPage) {
			this.pageWrapper.currentPage = currentPage;
			return this;
		}

		public Builder numberOfComputers(int numberOfComputers) {
			this.pageWrapper.numberOfComputers = numberOfComputers;
			return this;
		}

		public Builder computerDTOList(List<ComputerDTO> computerDTOList) {
			this.pageWrapper.setComputerDTOList(computerDTOList);
			return this;
		}

		public PageWrapper build() {
			return this.pageWrapper;
		}
	}

	public static Builder builder() {
		return new Builder();
	}
}
