CREATE TABLE ms1.user
	(
		id INT NOT NULL AUTO_INCREMENT,
		login VARCHAR(45) NOT NULL,
		password VARCHAR(45) NOT NULL,
		cpf VARCHAR(11) NOT NULL,
		name VARCHAR(100) NOT NULL,
        address VARCHAR(100) NOT NULL,
		PRIMARY KEY (id)
	);