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
				pS.setInt(1, 1); // 1 = 1st parameters, value ...
				pS.setInt(2, 5); // 2 = 2nd parameter, value ...
				
				// 3rd -> Execution
				int n = pS.executeUpdate(); // 
				if (n != 0) System.out.println("GOOD UPDATE");
				else System.out.println("BAD UPDATE");
				
				// --- EXERCISE 1 ---
				 // 1st -> Preparing the SELECT query with placeholders
                String sSQL = "SELECT * FROM alumnos WHERE exp >= ?";

                // 2nd -> Creating the PreparedStatement
                pS = con.prepareStatement(sSQL);
				for (int i = 1; i <= 3; i++) {
                    pS.setInt(1, i); // Set the parameter value
                    ResultSet resultSet = pS.executeQuery();
                    System.out.println("Results for exp > " + i + ":");
                    ShowResults(resultSet);
                }
				
			}
		}
		catch (Exception e){
			System.out.println("EXCEPTION: CONNECTION NOT ESTABLISHED!");
			e.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
		}

	}
	
	public static void ShowResults(ResultSet results) {
		try {
			while (results.next()) {
				int exp = results.getInt("exp");
				String nombre = results.getString("nombre");
	            String nota = results.getString("nota");
	            System.out.println("Exp: " + exp + " | Nombre: " + nombre + " | Nota: " + nota);
			}
		}
		catch (Exception e) {
			System.out.println("EXCEPTION: ERROR IN METHOD ShowResults");
			e.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
		}
		
	}

}
