CREATE TABLE characterises(
	id 					INT(11) NOT NULL AUTO_INCREMENT,
	user_id				INT(11) NOT NULL,
	name				VARCHAR(255) NOT NULL,
	level 				SMALLINT NOT NULL,
	points				INT(11) NOT NULL,
	description			VARCHAR(512) DEFAULT NULL,
	created_at			DATETIME NOT NULL,
	updated_at			DATETIME DEFAULT NULL,
	PRIMARY KEY (id),
	INDEX characterises_idx_user_id (user_id),
	UNIQUE INDEX characterise_idx_unique_user_name (user_id,name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;