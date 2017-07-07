package com.realife.services.domains;

import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class User extends BaseDomain {
	
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String passwordDigest;
	
	private String rememberDigest;
	
	private Date createdAt;
	
	private Date updatedAt;
}
