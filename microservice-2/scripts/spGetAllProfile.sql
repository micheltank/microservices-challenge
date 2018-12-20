USE ms1;

DROP procedure IF EXISTS spGetAllProfiles;

DELIMITER $$


CREATE PROCEDURE spGetAllProfiles ()

BEGIN

	SELECT * FROM profile;

END$$

DELIMITER ;