USE ms1;

DROP procedure IF EXISTS spAuthenticateUser;

DELIMITER $$

USE ms1$$
CREATE PROCEDURE spAthenticateUser (
	IN p_login varchar(50)
								)

BEGIN

	SELECT * FROM user WHERE login = p_login;

END$$

DELIMITER ;