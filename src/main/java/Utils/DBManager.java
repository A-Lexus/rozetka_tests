package Utils;

import java.sql.*;
import java.sql.DriverManager;
import java.util.HashMap;

public class DBManager {


    //  Database credentials
    private String HOST;
    private String USER;
    private String PASS;
    private String DB_NAME;
    private String DB_PORT;

    // JDBC driver name and database URL
    private final String DB_DRIVER = "com.mysql.jdbc.Driver";
    private String dbUrl;
    private static ResultSet rs;


    Connection conn = null;
    Statement stmt = null;

    ResultSetMetaData rsmd;

    public DBManager() {

        this.HOST = "localhost";
        this.USER = "root";
        this.PASS = "UFL6szTL@2012";
        this.DB_NAME = "test_db";
        this.DB_PORT = "3306";

        dbUrl = "jdbc:mysql://" + HOST + ':' + DB_PORT + '/' + DB_NAME;
    }

    static String s;

    public void saveItemAndPriceToDB(String itemName, Integer itemPrice) {

        try {
            //STEP 2: Register JDBC driver
            Class.forName(DB_DRIVER);

            //STEP 3: Open a connection
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(dbUrl, USER, PASS);

//            HashMap<String, Integer> hs = new HashMap<String, Integer>(powderPrice.clone());
            //STEP 4: Execute a query
            stmt = conn.createStatement();
            System.out.println("Creating statement...");
            String item = "\"" + itemName + "\"";
            int price = itemPrice;
            String query = "INSERT INTO rozetka_powder_test (ITEM_NAME,PRICE) " +
                    "VALUE(\"" + item + "\"," + price + ");";

            rs = stmt.executeQuery(query);

            //STEP 5: Clean-up environment
        } catch (Exception e) {
            //Handle errors for JDBC
            e.printStackTrace();
        } finally {
            //finally block used to close resources
       /*     try {
                stmt.close();
                conn.close();
                System.out.println("DB connection is closed!");
            } catch (SQLException se) {
                se.printStackTrace();
            }//end finally try*/
        }//end try
    }

}