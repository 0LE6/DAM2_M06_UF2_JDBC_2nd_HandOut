import java.sql.*;

public class SecondHandoutAboutJDBC {

	public static void main(String[] args) {

		try {
			String connectionUrl = "jdbc:mysql://localhost:3306/hospital?serverTimezone=UTC";
			Connection con = DriverManager.getConnection(connectionUrl, "root", "");
			if (con != null) {
				
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
				
				// ---- EXERCISE 1 ----
				// 1st -> Preparing the SELECT query w/ ?
                String sSQL = "SELECT * FROM alumnos WHERE exp = ?";

                // 2nd -> Creating the PreparedStatement
                pS = con.prepareStatement(sSQL);
                
                // We're going to select alumnos with exp from 1 to 3
                ResultSet resultSet;
				for (int i = 1; i <= 2; i++) {
                    pS.setInt(1, i); 
                    resultSet = pS.executeQuery();
                    System.out.println("Results for exp -> " + i + ":");
                    ShowResults(resultSet);
                }
				
				// ---- EXERCISE 2 ----
				
				// Preparing the INSERT statement w/ parametrization
				sSQL = "INSERT INTO doctor (doctor_codi, doctor_hospital_codi,"
						+ "doctor_nom, doctor_especialitat)"
						+ "VALUES (?, ?, ?, ?)";
				pS = con.prepareStatement(sSQL);
				
				// INSERT w/ addBatch() & executeBatch() 
//				pS.setInt(1, 1);
//				pS.setInt(2, 18);
//				pS.setString(3, "Frankenstein");
//				pS.setString(4, "Monstruos");
//				pS.addBatch();
//				
//				pS.setInt(1, 2);
//				pS.setInt(2, 18);
//				pS.setString(3, "Dolittle");
//				pS.setString(4, "Zoologia");
//				pS.addBatch();
//				
//				pS.setInt(1, 3);
//				pS.setInt(2, 18);
//				pS.setString(3, "Patch Adams");
//				pS.setString(4, "Risoterapia");
//				pS.addBatch();
				
				int[] batchInsertResult = pS.executeBatch();
				System.out.println("Number of INSERTs w/ Batch -> " + batchInsertResult.length);
				
				// Preparing the UPDATE statement w/ parametrization
				sSQL = "UPDATE doctor SET doctor_hospital_codi = ? "
						+ "WHERE doctor_codi = ?";
				pS = con.prepareStatement(sSQL);
				
				for (int i = 1; i <= 3; i++) {
					// Changing the Hospital of our 3 new doctors
					pS.setInt(1, 22);
					pS.setInt(2, i);
					pS.addBatch();
				}
				int[] batchUpdateResult = pS.executeBatch();
				System.out.println("Number of UPDATE w/ Batch -> " + batchUpdateResult.length);
				
				
				// ---- EXERCISE 3 ----
				// CallableStatement w/ single value
				
				// 1st - Preparing the call to our stored PROCEDURE
				String storedProcedureCall = "{call GetDoctor(?, ?)}";
				CallableStatement cS = con.prepareCall(storedProcedureCall);
				
				int doctor_codi = 1;
				cS.setInt(1, doctor_codi);
				
				// Important, register the output parameter
				cS.registerOutParameter(2, Types.VARCHAR);
				
				// Execute the stored procedure
				cS.execute();
				
				// Print the result of the OUT parameter
				System.out.println("Doctor name for ID " + 
				doctor_codi + ": " + cS.getString(2));
				
				// ---- EXERCISE 4 ----
				// CallableStatement w/ cursor
				
				// 1st - Preparing the call to our stored PROCEDURE
				storedProcedureCall = "{call GetDoctorsByHospital(?)}";
				cS = con.prepareCall(storedProcedureCall);
				
				int doctor_hospital_codi = 22;
				cS.setInt(1, doctor_hospital_codi);
				
				// Execute the stored procedure w/ bool variable 
				boolean hasResults = cS.execute();
				
				// Processing the result:
				if (hasResults) {
					resultSet = cS.getResultSet();
					ShowResultForCallableStatementWithcursor(resultSet);
					
				}
				else System.out.println("The stored procedure did not return a ResultSet.");
				
				
				
				// Finally closing
				//resultSet.close();
				pS.close();
				cS.close();
				con.close();
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
	
	public static void ShowResultForCallableStatementWithcursor(ResultSet results) {
		try {
			while (results.next()) {
				int doctorCode = results.getInt("doctor_codi");
				int hospitalCode = results.getInt("doctor_hospital_codi");
	            String doctorName = results.getString("doctor_nom");
	            String doctorSpecialization= results.getString("doctor_especialitat");
	            System.out.println("Code: " + doctorCode + 
	            		" | Name: " + doctorName + 
	            		" | HospitalCode: " + hospitalCode + 
	            		" | DocSpecialization: " + doctorSpecialization);
			}
		}
		catch (Exception e) {
			System.out.println("EXCEPTION: ERROR IN METHOD ShowResultForCallableStatementWithcursor");
			e.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
		}
	}

}
