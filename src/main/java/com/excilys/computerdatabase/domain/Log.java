package com.excilys.computerdatabase.domain;

import java.util.Date;

public class Log {

	private Long id;
	private Date date;
	private String type;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	private String description;

	public Log() {
		super();
	}

	@Override
	public String toString() {
		return "Log id=" + id + ", date=" + date + ", type=" + type
				+ ", decription=" + description + "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result
				+ ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Log other = (Log) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public static class Builder {

		Log log;

		private Builder() {
			log = new Log();
		}

		public Builder id(Long id) {
			if (id != null)
				this.log.id = id;
			return this;
		}

		public Builder type(String type) {
			this.log.type = type;
			return this;
		}

		public Builder date(Date date) {
			this.log.date = date;
			return this;
		}

		public Builder description(String description) {
			this.log.description = description;
			return this;
		}

		public Log build() {
			return this.log;
		}

	}

	public static Builder builder() {
		return new Builder();
	}

}
