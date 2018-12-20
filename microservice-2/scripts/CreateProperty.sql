CREATE TABLE ms2.property
	(
		id INT NOT NULL AUTO_INCREMENT,
		profile_id INT NOT NULL,
		description VARCHAR(100) NOT NULL,
		value DECIMAL(10,2) NULL NULL,
		PRIMARY KEY (id)
	);
	
ALTER TABLE ms2.property ADD CONSTRAINT property_profile_FK FOREIGN KEY (id) REFERENCES ms2.profile(id) ;