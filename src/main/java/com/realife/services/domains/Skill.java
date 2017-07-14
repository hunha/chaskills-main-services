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
public class Skill extends BaseDomain {
	
	@NotNull
	private Long userId;
	
	@NotNull
	private Long CharacteriseId;
	
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
