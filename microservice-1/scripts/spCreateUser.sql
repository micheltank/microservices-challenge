USE ms1;

DROP procedure IF EXISTS spCreateUser;

DELIMITER $$

USE ms1$$
CREATE PROCEDURE spCreateUser (
	IN p_login varchar(45),
	IN p_password varchar(50),
	IN p_cpf VARCHAR(11),
    IN p_name VARCHAR(100),
    IN p_address VARCHAR(100)
								)

BEGIN

IF ( SELECT EXISTS
		(SELECT 1 FROM user WHERE login = p_login)
	)
	THEN

    SELECT 'Login Exists !!';

ELSE

INSERT INTO user
	(
    	login,
    	password,
    	cpf,
    	name,
    	address
	)
VALUES
(
    p_login,
    p_password,
    p_cpf,
    p_name,
    p_address
);

END IF;

END$$

DELIMITER ;