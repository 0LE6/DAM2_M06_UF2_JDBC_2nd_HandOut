# DAM2_M06_UF2_JDBC_2nd_HandOut
Pues a ver si puedo commitear desde Eclipse

DELIMITER //
CREATE PROCEDURE CreateDoctor(
    IN doctorCode INT,
    IN doctorName VARCHAR(255),
    IN doctorEsp VARCHAR(255),
    IN hospitalCode INT
)
BEGIN
    INSERT INTO doctor (doctor_codi, doctor_nom, doctor_hospital_codi, doctor_especialitat)
    VALUES (doctorCode, doctorName, hospitalCode, doctorEsp);
END;
//
DELIMITER ;
