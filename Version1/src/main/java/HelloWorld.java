import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class HelloWorld {

	public static void main(String[] args) {
		
		// First part
		try {
			String connectionUrl = "jdbc:mysql://localhost:3306/hospital?serverTimezone=UTC";
			Connection conn = DriverManager.getConnection(connectionUrl, "root", "");
			if (conn != null) {
				System.out.println("CONNECTED TO HOSPITAL");
			
				// Second part
				Statement st = conn.createStatement();
				// ------ pruebas github 
				
				//for DDL and DML (except SELECT)
				String sSql= "INSERT/DELETE/UPDATE/CREATE TABLE/DROP TABLE…..";
				//int n = st.executeUpdate(sSql);
				//n contains the number of rows affected except in DML sentences that returns 0
				//for SELECT queries a ResultSet instance is returned.
				
				// >> SELECT part:
				sSql = "SELECT * FROM empl";
				ResultSet rS = st.executeQuery(sSql); // returns a ResultSet that is a JDBC cursor.
				
				// Processing the values
//				int num = 0;
//				System.out.println("num |  emplNum  |  emplName  |  salary");
//                while (rS.next()) {
//                    // Obtaining the values
//                    int emplNum = rS.getInt("empl_num");
//                    String emplName = rS.getString("empl_nom");
//                    double salary = rS.getDouble("empl_salari");
//                    num++;
//                    // Showing the values
//                    System.out.println(num + " | " + emplNum + " | " + emplName + " | " + salary);
//                }
				
				// >> DELETE part:
                sSql = "DROP TABLE alumnos";
                int dropTableOutput = st.executeUpdate(sSql);
                if (dropTableOutput == 0) System.out.println("SUCCESFUL DELETE!");
                else System.out.println("INVALID DELETE!");
                
                // >> CREATE TABLE part:
                sSql = "CREATE TABLE alumnos("
                		+ "exp INTEGER, "
                		+ "nombre VARCHAR(32),"
                		+ "nota VARCHAR(3) default 'NP',"
                		+ "PRIMARY KEY (exp)"
                		+ ");";
                
                int output = st.executeUpdate(sSql);
                if (output == 0) {
                    System.out.println("NEW TABLE CREATED");
                } else {
                    System.out.println("ERROR TO CREATE A NEW TABLE");
                }
                
                // >> INSERT part:
                sSql = "INSERT INTO alumnos (exp, nombre, nota) "
                		+ "VALUES (1, 'Oleg', 'A');";

                int createdRowsCount = st.executeUpdate(sSql);
                if (createdRowsCount == 0) {
                    System.out.println("NOT SUCCESFUL INSERT");
                } else {
                    System.out.println("SUCCESFUL INSERT WAS DONE!");
                }
                
                sSql = "INSERT INTO alumnos (exp, nombre, nota) "
                		+ "VALUES (2, 'Ferran', 'B+');";
                st.executeUpdate(sSql);
                
                // >> SELECT part (over new table):
                sSql = "SELECT * FROM alumnos WHERE exp = 1";
                ResultSet resultSet = st.executeQuery(sSql);

                if (resultSet.next()) {
                    String nombre = resultSet.getString("nombre");
                    String nota = resultSet.getString(3);
                    System.out.println("Nombre: " + nombre + " | Nota: " + nota);
                } else {
                    System.out.println("NOT FOUND SELECT");
                }
                
                // >> UPDATE part (over our new table):
                sSql = "UPDATE alumnos SET nota = 'A+' WHERE nombre = 'Oleg';";
                int updateRowsCount = st.executeUpdate(sSql);
                if (updateRowsCount == 0) {
                    System.out.println("NOT SUCCESFUL UPDATE");
                } else {
                    System.out.println("SUCCESFUL UPDATE WAS DONE!");
                }
                
                // >> DELETE part (over our new table):
//                String deleteSQL = "DELETE FROM alumnos WHERE nota = 'A+';";
//                int deleteRowsCount = st.executeUpdate(deleteSQL);
//                if (deleteRowsCount == 0) System.out.println("NOT SUCCESFUL DELETE");
//                else System.out.println(deleteRowsCount + " ROWS AFFECTED!");
                
                // Important, closing everything
                rS.close();
                st.close();
                conn.close();
			}
		}
		catch (Exception ex){
			System.out.println("EXCEPTION: CONNECTION NOT ESTABLISHED!");
			ex.printStackTrace(); // Showing the TRACEBACK of our EXCEPTION
		}
		
		// finally
	}
}
