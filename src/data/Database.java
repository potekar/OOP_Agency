package data;

import java.sql.*;

public class Database {

    private static String DB_user = "root";
    private static String DB_password = "";
    private static String connectionUrl;
    private static int port = 3306;
    private static String DB_name = "agencija";
    private static Connection connection;

    public static void DBConnect() throws SQLException {
        connectionUrl = "jdbc:mysql://localhost" + ":" + port + "/" + DB_name;
        connection = DriverManager.getConnection(connectionUrl, DB_user, DB_password);
        System.out.println("Connection est");
    }

    public String[] login(String username, String password) throws SQLException {
        try {
            ResultSet resultSet = null;
            Statement statement = connection.createStatement();
            String SQLQuary = "SELECT * FROM klijent";
            resultSet = statement.executeQuery(SQLQuary);

            while (resultSet.next()) {
                if (resultSet.getString(7).equals(username) && resultSet.getString(8).equals(password)) {
                    String[] result = new String[4];
                    result[0] = "user";
                    result[1] = username;
                    result[2] = resultSet.getString(2);
                    result[3] = resultSet.getString(3);
                    return result;
                }
            }

            SQLQuary = "SELECT * FROM admin";
            resultSet = statement.executeQuery(SQLQuary);
            while (resultSet.next()) {
                if (resultSet.getString(4).equals(username) && resultSet.getString(5).equals(password)) {
                    String[] result = new String[4];
                    result[0] = "admin";
                    result[1] = username;
                    result[2] = resultSet.getString(2);
                    result[3] = resultSet.getString(3);
                    return result;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void register(Client client) throws SQLException {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO klijent (id,ime,prezime,broj_telefona,jmbg,broj_racuna,korisnicko_ime,lozinka) values (?,?,?,?,?,?,?,?)");
            ResultSet res = connection.createStatement().executeQuery("SELECT COUNT(*) FROM klijent");
            res.next();

            preparedStatement.setInt(1, res.getInt(1) + 1);
            preparedStatement.setString(2, client.getName());
            preparedStatement.setString(3, client.getLname());
            preparedStatement.setString(4, client.getPhone());
            preparedStatement.setString(5, client.getJbmg());
            preparedStatement.setString(6, client.getAccountNumber());
            preparedStatement.setString(7, client.getUsername());
            preparedStatement.setString(8, client.getPassword());
            preparedStatement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean searchUsername(String username) {
        try {
            ResultSet resultSet = null;
            Statement statement = connection.createStatement();
            String SQLQuary = "SELECT * FROM klijent";
            resultSet = statement.executeQuery(SQLQuary);

            while (resultSet.next()) {
                if (resultSet.getString(7).equals(username)) {
                    return true;
                }
            }

            SQLQuary = "SELECT * FROM admin";
            resultSet = statement.executeQuery(SQLQuary);
            while (resultSet.next()) {
                if (resultSet.getString(4).equals(username)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean searchBank(String jmbg, String acc) {
        try {
            ResultSet set = null;
            Statement statement = connection.createStatement();
            set = statement.executeQuery("SELECT * FROM bankovni_racun");
            while (set.next()) {
                if (set.getString(2).equals(acc) && set.getString(3).equals(jmbg)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeAdminPassword(String username, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE admin SET lozinka=? WHERE korisnicko_ime=?");
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);

            preparedStatement.execute();
            System.out.println("eto");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean changeClientPassword(String username, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE klijent SET lozinka=? WHERE korisnicko_ime=?");
            preparedStatement.setString(1, password);
            preparedStatement.setString(2, username);
            System.out.println(username);
            System.out.println(password);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public Client getActiveClient(String username) {
        try {
            ResultSet resultSet = null;
            Statement statement = connection.createStatement();
            String SQLQuary = "SELECT * FROM klijent";
            resultSet = statement.executeQuery(SQLQuary);

            while (resultSet.next()) {
                if (resultSet.getString(7).equals(username)) {
                    Client client = new Client(0, resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4), resultSet.getString(5)
                            , resultSet.getString(6), resultSet.getString(7), resultSet.getString(8));

                    System.out.println(client);
                    return client;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }

    public Admin getActiveAdmin(String username) {
        try {
            ResultSet resultSet = null;
            Statement statement = connection.createStatement();
            String SQLQuary = "SELECT * FROM admin";
            resultSet = statement.executeQuery(SQLQuary);

            while (resultSet.next()) {
                if (resultSet.getString(4).equals(username)) {
                    Admin admin = new Admin(0, resultSet.getString(2),
                            resultSet.getString(3), resultSet.getString(4), resultSet.getString(5));

                    System.out.println(admin);
                    return admin;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return null;
    }
}
