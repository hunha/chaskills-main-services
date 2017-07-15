CREATE TABLE users(
	id 					INT(11) NOT NULL AUTO_INCREMENT,
	first_name			VARCHAR(50) NOT NULL,
	last_name			VARCHAR(50),
	email 				VARCHAR(255) NOT NULL,
	password_digest		VARCHAR(255) NOT NULL,
	remember_digest		VARCHAR(255) DEFAULT NULL,
	created_at			DATETIME NOT NULL,
	updated_at			DATETIME DEFAULT NULL,
	PRIMARY KEY (id),
	UNIQUE INDEX users_idx_unique_email (email)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

