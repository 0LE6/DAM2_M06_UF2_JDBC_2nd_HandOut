	import java.sql.*;

public class LastExerciseOf2ndHandOut {
	
	public static void main(String[] args) {
		
		// Here we create our new Doc.
		Doctor drOleg = new Doctor(69, "Oleg", 18, "Programmer");
		

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

	            // Llama al procedimiento almacenado para actualizar el código del hospital
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
		
		// TODO
		return everythingIsOK;
	}
}

