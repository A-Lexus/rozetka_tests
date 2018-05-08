package Utils;

import java.sql.*;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Iterator;

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
    PreparedStatement preparedStatement = null;

    ResultSetMetaData rsmd;

    public DBManager() {

        this.HOST = "localhost";
        this.USER = "root";
        this.PASS = "UFL6szTL@2012";
        this.DB_NAME = "tutorial_database";
        this.DB_PORT = "3306";

        dbUrl = "jdbc:mysql://" + HOST + ':' + DB_PORT + '/' + DB_NAME + "?useUnicode=yes&characterEncoding=UTF-8";
    }

    public void sendDataWhichConfirmedStatement(HashMap<String, Integer> namesAndPrices, String nameContains,
                                                int minPrice, int maxPrice) {
        try {
            Class.forName(DB_DRIVER);

            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(dbUrl, USER, PASS);

            //STEP 4: Execute a query
            System.out.println("Creating INSERT statement...");
            String query = "INSERT INTO powder_test(ITEM_NAME,PRICE) VALUE(?,?);";
            preparedStatement = conn.prepareStatement(query);

            Iterator<String> it = namesAndPrices.keySet().iterator();
            while (it.hasNext()) {
                String itemName = it.next();
                int itemPrice = namesAndPrices.get(itemName);
                boolean first = itemName.contains(nameContains.toLowerCase()) && itemPrice >= minPrice;
                boolean second = itemName.contains(nameContains.toLowerCase()) && maxPrice >= itemPrice;
                if (first | second) {
                    preparedStatement.setString(1, itemName);
                    preparedStatement.setInt(2, itemPrice);

                    preparedStatement.execute();
                }
            }
            conn.close();
            //STEP 5: Clean-up environment
        } catch (Exception e) {
            //Handle errors for JDBC
            e.printStackTrace();
        }
    }

}