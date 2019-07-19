package com.realife.services.domains;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

@Entity
public class Characterise extends BaseDomain {
	
	public static int DEFAULT_LEVEL = 0;
	public static Long DEFAULT_POINTS = new Long(0);
	
	@NotNull
	private Long userId;
	
	@NotBlank
	@Length(max = 255)
	private String name;
	
	private int level;
	
	@NotNull
	private Long points;
	
	@Length(max = 512)
	private String description;
	
	@NotNull
	private Date createdAt;
	
	private Date updatedAt;

	public static int getDEFAULT_LEVEL() {
		return DEFAULT_LEVEL;
	}

	public static void setDEFAULT_LEVEL(int dEFAULT_LEVEL) {
		DEFAULT_LEVEL = dEFAULT_LEVEL;
	}

	public static Long getDEFAULT_POINTS() {
		return DEFAULT_POINTS;
	}

	public static void setDEFAULT_POINTS(Long dEFAULT_POINTS) {
		DEFAULT_POINTS = dEFAULT_POINTS;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public Long getPoints() {
		return points;
	}

	public void setPoints(Long points) {
		this.points = points;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}
}
