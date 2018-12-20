USE ms1;

DROP procedure IF EXISTS spGetUser;

DELIMITER $$


CREATE PROCEDURE spGetUser (
	IN _userId INT
								)

BEGIN

	SELECT * FROM user WHERE id = _userId;

END$$

DELIMITER ;