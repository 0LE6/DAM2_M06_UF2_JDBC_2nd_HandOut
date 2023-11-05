import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class SecondHandoutAboutJDBC {

	public static void main(String[] args) {

		try {
			String connectionUrl = "jdbc:mysql://localhost:3306/hospital?serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connectionUrl, "root", "");
			if (conn != null) {
				System.out.println("SUCCESFULLY CONNECTED TO HOSPITAL");
				Statement st = conn.createStatement();
				
				
				
				
				//rS.close();
                st.close();
                conn.close();
			}
		}
		catch (Exception ex){
			System.out.println("EXCEPTION: CONNECTION NOT ESTABLISHED!");
			ex.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
		}

	}

}
