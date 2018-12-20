USE ms2;

DROP procedure IF EXISTS spCreateIncome;

DELIMITER $$

USE ms2$$
CREATE PROCEDURE spCreateIncome (
	IN p_profile_id INT,
	IN p_description varchar(100)
	IN p_value DECIMAL(10,2))

BEGIN

INSERT INTO income
	(
    	profile_id,
    	description,
    	value
	)
VALUES
(
    p_profile_id,
    p_description,
    p_value
);

END IF;

END$$

DELIMITER ;