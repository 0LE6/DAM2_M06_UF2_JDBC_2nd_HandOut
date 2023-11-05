import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

public class SecondHandoutAboutJDBC {

	public static void main(String[] args) {

		try {
			String connectionUrl = "jdbc:mysql://localhost:3306/hospital?serverTimezone=UTC";
			Connection con = DriverManager.getConnection(connectionUrl, "root", "");
			if (con != null) {
				
				// Little reminder about Statement
//				System.out.println("SUCCESFULLY CONNECTED TO HOSPITAL");
//				Statement st = conn.createStatement();
//				
//				// Pues a ver si chuta lo de commitear a github desde aqui
//				String sSql= "INSERT/DELETE/UPDATE/CREATE TABLE/DROP TABLE…..";
//				sSql = "SELECT * FROM empl";
//				ResultSet rS = st.executeQuery(sSql); 
//				
//				rS.close();
//				st.close();
//				conn.close();
				
				// PREPARED STATEMENT from here:
				
				// 1st -> Preparing the query w/ ? as parameters
				PreparedStatement pS = con.prepareStatement(
						"UPDATE alumnos SET nota = 'NP' "
						+ "WHERE exp > ? AND exp < ?");
				
				// 2nd -> Parametrization
				pS.setInt(1, 1); // 1 = 1st parameters, value 1200
				pS.setInt(2, 5); // 2 = 2nd parameter, value 1300
				
				// 3rd -> Execution
				int n = pS.executeUpdate(); // 
				if (n != 0) System.out.println("GOOD UPDATE");
				else System.out.println("BAD UPDATE");
				
			}
		}
		catch (Exception ex){
			System.out.println("EXCEPTION: CONNECTION NOT ESTABLISHED!");
			ex.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
		}

	}

}
