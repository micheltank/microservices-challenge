USE ms1;

DROP procedure IF EXISTS spCreateDebt;

DELIMITER $$

USE ms1$$
CREATE PROCEDURE spCreateDebt (
	IN p_user_id varchar(45),
	IN p_value DECIMAL(10,2),
	IN p_date DATE)
								)

BEGIN

INSERT INTO user
	(
    	user_id,
    	value,
    	date
	)
VALUES
(
    p_user_id,
    p_value,
    p_date
);

END IF;

END$$

DELIMITER ;