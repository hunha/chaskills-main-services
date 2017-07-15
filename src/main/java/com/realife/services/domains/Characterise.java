package com.realife.services.domains;

import java.util.Date;

import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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
}
