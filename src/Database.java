import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class Database {

    private static String DB_user = "root";
    private static String DB_password = "";
    private static String connectionUrl;
    private static int port = 3306;
    private static String DB_name = "agencija";
    private static Connection connection;

    public static void DBConnect() throws SQLException{
        connectionUrl = "jdbc:mysql://localhost" + ":" + port + "/" + DB_name;
        connection = DriverManager.getConnection(connectionUrl, DB_user, DB_password);
        System.out.println("Connection est");

//        try {
//            ResultSet resultSet = null;
//            Statement statement = connection.createStatement();
//            String SQLQuery = "SELECT * FROM bankovni_racun";
//            resultSet = statement.executeQuery(SQLQuery);
//            System.out.println("--------------------------------------------");
//            while (resultSet.next()) {
//                String result = resultSet.getString(1) + ", " + resultSet.getString(2)
//                        + ", " + resultSet.getString(3) + ", " + resultSet.getString(4);
//                System.out.println(result);
//                System.out.println("--------------------------------------------");
//            }
//
//            statement.close();
//            connection.close();
//        } catch (SQLException e){
//            e.printStackTrace();
//        } //catch (ClassNotFoundException e) {
//        //throw new RuntimeException(e);
//        //}
    }

    public String[] login(String username,String password) throws SQLException
    {
        try {
            boolean success;
            ResultSet resultSet=null;
            Statement statement=connection.createStatement();
            String SQLQuary="SELECT * FROM klijent";
            resultSet=statement.executeQuery(SQLQuary);

            while (resultSet.next())
            {
                if(resultSet.getString(7).equals(username) && resultSet.getString(8).equals(password))
                {
                    success=true;
                    String[] result = new String[2];
                    result[0]=resultSet.getString(1);
                    result[1]=resultSet.getString(2);
                    System.out.println(resultSet.getString(1));
                    return result;
                }
            }

            SQLQuary="SELECT * FROM admin";
            resultSet=statement.executeQuery(SQLQuary);
            while (resultSet.next())
            {
                if(resultSet.getString(4).equals(username) && resultSet.getString(5).equals(password))
                {
                    success=true;
                    String[] result = new String[2];
                    result[0]=resultSet.getString(1);
                    result[1]=resultSet.getString(2);
                    System.out.println(resultSet.getString(2));
                    return result;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
