# DAM2_M06_UF2_JDBC_2nd_HandOut
Pues a ver si puedo commitear desde Eclipse


-- LAST EX --

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

DELIMITER //
CREATE PROCEDURE UpdateDoctorHospital (
    IN doctorCode INT,
    IN hospitalCode INT
)
BEGIN
    UPDATE doctor
    SET doctor_hospital_codi = hospitalCode
    WHERE doctor_codi = doctorCode;
END;
//
DELIMITER ;
