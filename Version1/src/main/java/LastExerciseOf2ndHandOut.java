import java.sql.*;

public class LastExerciseOf2ndHandOut {
	
	public static void main(String[] args) {
		
		// Here we create our new Doc.
		Doctor drOleg = new Doctor(69, "Oleg", 18, "Programmer");
		int newHospitalCode = 22;
		
		if (CreateAndUpdate(drOleg, newHospitalCode))
			System.out.println("SUCCESFULLY CREATED & UPDATED");
		else System.out.println("FAIL TO CREATED & UPDATED");	
		
		// Second doc must generate an exception with non existing hospitalCode
		Doctor drFail = new Doctor();
		drFail.setDoctorCode(70);
		drFail.setName("Fail");
		drFail.setHospitalCode(18);
		drFail.setSpecialization("Get Exceptions");
		
		// With not existing doctor_codi_hospital
		if (CreateAndUpdate(drFail, 666))
			System.out.println("SUCCESFULLY CREATED & UPDATED");
		else System.out.println("FAIL TO CREATED & UPDATED");	
		

	}
	
	public static boolean CreateAndUpdate(Doctor d, int updateHospitalCode) {
		
		String connectionUrl = "jdbc:mysql://localhost:3306/hospital?serverTimezone=UTC";
		String user = "root"; String pass = "";
		CallableStatement cS = null;
		boolean everythingIsOK = true;
		
		try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);) {
			
			con.setAutoCommit(false);
			
			try {
				
				// 1st - We will call our created PROCEDURE in phpMyadmin
				// to create our new doctor using parametrization (Is correctly written?)
				cS = con.prepareCall("{call CreateDoctor(?, ?, ?, ?)}");
				cS.setInt(1, d.getDoctorCode());
				cS.setString(2, d.getName());
				cS.setString(3, d.getSpecialization());
				cS.setInt(4, d.getHospitalCode());
				cS.execute();

	            // 2nd - Calling our PROCEDURE to Update the doctor
				// according to the hospital_code get from the parameter
				cS = con.prepareCall("{call UpdateDoctorHospital(?, ?)}");
				cS.setInt(1, d.getDoctorCode());
				cS.setInt(2, updateHospitalCode);
				cS.execute();
				
				con.commit();
				
				System.out.println("Transaction committed succesfully!");
				
			}
			catch (SQLException e){
				everythingIsOK = false;
				con.rollback();
				System.out.println("Transaction rolled back due to an Exception");
				e.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
			}
			finally {
				con.setAutoCommit(true);
			}
		}
		catch (SQLException e) {
			e.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
		}

		return everythingIsOK;
	}
}

