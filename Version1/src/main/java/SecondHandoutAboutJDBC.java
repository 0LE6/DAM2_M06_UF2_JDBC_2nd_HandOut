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
				
				// - EXERCISE 1 -
				// 1st -> Preparing the SELECT query w/ ?
                String sSQL = "SELECT * FROM alumnos WHERE exp = ?";

                // 2nd -> Creating the PreparedStatement
                pS = con.prepareStatement(sSQL);
                
                // We're going to select alumnos with exp from 1 to 3
				for (int i = 1; i <= 2; i++) {
                    pS.setInt(1, i); 
                    ResultSet resultSet = pS.executeQuery();
                    System.out.println("Results for exp -> " + i + ":");
                    ShowResults(resultSet);
                }
				
				// -- EXERCISE 2 --
				
				// Preparing the INSERT statement w/ query
				sSQL = "INSERT INTO doctor (doctor_codi, doctor_hospital_codi,"
						+ "doctor_nom, doctor_especialitat)"
						+ "VALUES (?, ?, ?, ?)";
				pS = con.prepareStatement(sSQL);
				
				// INSERT w/ addBatch() & executeBatch() 
				pS.setInt(1, 1);
				pS.setInt(2, 18);
				pS.setString(3, "Frankenstein");
				pS.setString(4, "Monstruos");
				pS.addBatch();
				
				pS.setInt(1, 2);
				pS.setInt(2, 18);
				pS.setString(3, "Dolittle");
				pS.setString(4, "Zoologia");
				pS.addBatch();
				
				pS.setInt(1, 3);
				pS.setInt(2, 18);
				pS.setString(3, "Patch Adams");
				pS.setString(4, "Risoterapia");
				pS.addBatch();
				
				int[] batchInsertResult = pS.executeBatch();
				System.out.println("Number of INSERTs w/ Batch -> " + batchInsertResult.length);
				
				// Preparing the UPDATE statement w/ query
				sSQL = "UPDATE doctor SET hospital_codi = ?"
						+ "WHERE doctor_codi >= ? AND doctor_codi <= ?";
				pS = con.prepareStatement(sSQL);
				
				for (int i = 1; i <= 3; i++) {
					// Changing the Hospital of our 3 new doctors
					pS.setInt(i,22);
					pS.addBatch();
				}
				int[] batchUpdateResult = pS.executeBatch();
				System.out.println("Number of UPDATE w/ Batch -> " + batchUpdateResult.length);
				
				
				
				// Finally closing
				pS.close();
			}
		}
		catch (Exception e){
			System.out.println("EXCEPTION: CONNECTION NOT ESTABLISHED!");
			e.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
		}
	}
	
	// Method for EXERCISE 1
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
