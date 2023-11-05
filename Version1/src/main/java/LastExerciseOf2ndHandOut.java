	import java.sql.*;

public class LastExerciseOf2ndHandOut {
	
	public static void main(String[] args) {
		
		// Here we create our new Doc.
		
		

	}
	
	public static boolean CreateAndUpdate(Doctor d, int updateHospitalCode) {
		
		String connectionUrl = "jdbc:mysql://localhost:3306/hospital?serverTimezone=UTC";
		String user = "root"; String pass = "";
		
		try (Connection con = DriverManager.getConnection(connectionUrl, user, pass);) {
			
			con.setAutoCommit(false);
			
			try {
				// ...
				// ...
				
				con.commit();
				System.out.println("Transaction committed succesfully!");
				
			}
			catch (SQLException e){
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
		return true;
	}
}

