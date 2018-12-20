USE ms1;

DROP procedure IF EXISTS spGetAllUsers;

DELIMITER $$


CREATE PROCEDURE spGetAllUsers ()

BEGIN

	SELECT * FROM user;

END$$

DELIMITER ;