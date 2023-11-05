	import java.sql.*;

public class LastExerciseOf2ndHandOut {
	
	public static void main(String[] args) {

			try {
				String connectionUrl = "jdbc:mysql://localhost:3306/hospital?serverTimezone=UTC";
				Connection con = DriverManager.getConnection(connectionUrl, "root", "");
				if (con != null) {
					
					
					// Finally closing
				}
			}
			catch (Exception e){
				System.out.println("EXCEPTION: CONNECTION NOT ESTABLISHED!");
				e.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
			}
		}

}

