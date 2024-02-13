package data;

import java.sql.*;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class Database {

    private static String DB_user = "root";
    private static String DB_password = "";
    private static String connectionUrl;
    private static int port = 3306;
    private static String DB_name = "agencija";
    private static Connection connection;

    public static void DBConnect() {
        connectionUrl = "jdbc:mysql://localhost" + ":" + port + "/" + DB_name;
        try {
            connection = DriverManager.getConnection(connectionUrl, DB_user, DB_password);
            System.out.println("Connection est");
        } catch (SQLException e) {
            System.err.println("Xamp is off");
        }

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
                    Client client = new Client(resultSet.getInt(1), resultSet.getString(2),
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

    public boolean addNewAdmin(String name, String lastName, String username) {

        if (!searchUsername(username)) {
            System.out.println("bs");
            try {
                PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO admin (id,ime,prezime,korisnicko_ime,lozinka) values (?,?,?,?,?)");
                ResultSet res = connection.createStatement().executeQuery("SELECT COUNT(*) FROM admin");
                res.next();

                preparedStatement.setInt(1, res.getInt(1) + 1);
                preparedStatement.setString(2, name);
                preparedStatement.setString(3, lastName);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, "12345678");

                preparedStatement.execute();

                return true;

            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        } else {
            return false;
        }
    }

    public boolean addNewTrip(String name, String destination, String date, String price) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO aranzman (id,naziv_putovanja,destinacija,prevoz,datum_polaska,datum_dolaska,cijena_aranzmana) values (?,?,?,?,?,?,?)");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, destination);
            preparedStatement.setString(4, "Autobus");
            preparedStatement.setString(5, date);
            preparedStatement.setString(6, date);
            preparedStatement.setString(7, price);

            preparedStatement.execute();

            return true;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private int addAccommodation(String hotel, String stars, String pricePer, String roomType) {

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO smjestaj (id,naziv,broj_zvjezdica,vrsta_sobe,cjena_po_nocenju) values (?,?,?,?,?)");
            ResultSet res = connection.createStatement().executeQuery("SELECT COUNT(*) FROM smjestaj");
            res.next();

            preparedStatement.setInt(1, res.getInt(1) + 1);
            preparedStatement.setString(2, hotel);
            preparedStatement.setString(3, stars);
            preparedStatement.setString(4, roomType);
            preparedStatement.setString(5, pricePer);

            preparedStatement.execute();

            return res.getInt(1) + 1;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    private boolean addArrangement(String name, String destination, String transport, String arrivalDate, String returnDate, String price, int accommodationId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO aranzman (id,naziv_putovanja,destinacija,prevoz,datum_polaska,datum_dolaska,cijena_aranzmana,Smjestaj_id) values (?,?,?,?,?,?,?,?)");

            preparedStatement.setString(1, name);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, destination);
            preparedStatement.setString(4, transport);
            preparedStatement.setString(5, arrivalDate);
            preparedStatement.setString(6, returnDate);
            preparedStatement.setString(7, price);
            preparedStatement.setInt(8, accommodationId);

            preparedStatement.execute();

            return true;

        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        }
    }

    public boolean addNewMultiTrip(String name, String destination, String arivalDate, String returnDate, String transport, String hotel, String stars, String pricePer, String roomType, String price) {
        if (addArrangement(name, destination, transport, arivalDate, returnDate, price, addAccommodation(hotel, stars, pricePer, roomType))) {
            return true;
        } else {
            return false;
        }
    }

    public List<Arrangment> getArrangements() {
        List<Arrangment> list = new ArrayList<>();

        try {
            ResultSet resultSet = null;
            Statement statement = connection.createStatement();
            String SQLQuary = "SELECT * FROM aranzman";
            resultSet = statement.executeQuery(SQLQuary);

            while (resultSet.next()) {
                list.add(new Arrangment(resultSet.getString(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getDouble(7), resultSet.getInt(8)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public List<Client> getClients() {
        List<Client> list = new ArrayList<>();

        try {
            ResultSet resultSet = null;
            Statement statement = connection.createStatement();
            String SQLQuary = "SELECT * FROM klijent";
            resultSet = statement.executeQuery(SQLQuary);

            while (resultSet.next()) {
                list.add(new Client(resultSet.getInt(1), resultSet.getString(2),
                        resultSet.getString(3), resultSet.getString(4),
                        resultSet.getString(5), resultSet.getString(6),
                        resultSet.getString(7), resultSet.getString(8)));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    public Accommodation getAccomodation(int id) {
        PreparedStatement preparedStatement = null;
        ResultSet set;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM smjestaj WHERE id="+id);
            set = preparedStatement.executeQuery();
            set.next();
            return new Accommodation(set.getInt(1),set.getString(2),set.getInt(3),set.getString(4),set.getDouble(5));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean deleteTrip(String entry) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM rezervacija WHERE Aranzman_id=?");
            preparedStatement.setString(1, entry);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("DELETE FROM aranzman WHERE id=?");
            preparedStatement.setString(1, entry);
            preparedStatement.execute();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean deleteTripMulti(String entry, String id) {
        try {

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM rezervacija WHERE Aranzman_id=?");
            preparedStatement.setString(1, entry);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("DELETE FROM aranzman WHERE id=?");
            preparedStatement.setString(1, entry);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("DELETE FROM smjestaj WHERE id=?");
            preparedStatement.setString(1, id);
            preparedStatement.execute();


            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getAdminReservations(String name) {
        List<Client> clients = getClients();
        PreparedStatement preparedStatement = null;
        ResultSet set;
        StringBuilder builder = new StringBuilder();
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM rezervacija");
            set = preparedStatement.executeQuery();

            while (set.next()) {
                for (Client client : clients) {
                    if (client.getId() == set.getInt(1) && set.getString(2).equals(name)) {
                        builder.append(client.getName()).append(" ");
                        builder.append(client.getLname()).append(" ");
                        builder.append("   Paid:").append(set.getDouble(4)).append(" ");
                        builder.append(", Left to pay: ").append(set.getDouble(3) - set.getDouble(4));
                        builder.append("\n");
                    }
                }
            }

            return builder.toString();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Double getProfit() {
        PreparedStatement preparedStatement = null;
        ResultSet set;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM bankovni_racun WHERE broj_racuna=1234567887654321");
            set = preparedStatement.executeQuery();
            set.next();
            return set.getDouble(4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public Double getDueTo() {
        PreparedStatement preparedStatement = null;
        ResultSet set;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM rezervacija");
            set = preparedStatement.executeQuery();
            double dueTo=0;
            while (set.next())
            {
                dueTo+=set.getDouble(3)-set.getDouble(4);
            }
            return dueTo;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public double getFounds(String accountNumber)
    {
        PreparedStatement preparedStatement = null;
        ResultSet set;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM bankovni_racun WHERE broj_racuna="+accountNumber);
            set = preparedStatement.executeQuery();
            set.next();
            return set.getDouble(4);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean addReservation(Client client,String tripName)
    {
        List<Arrangment> arrangements = getArrangements();
        Arrangment arg=null;

        for(Arrangment a:arrangements)
        {
            if(a.getId().equals(tripName))
            {
                arg=a;
                break;
            }
        }

        try {
            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO rezervacija (Klijent_id,Aranzman_id,ukupna_cijena,placena_cijena) values (?,?,?,?)");

            System.out.println(client.getId());
            System.out.println(tripName);
            System.out.println(arg.getArrangmentPrice());
            System.out.println(arg.getArrangmentPrice()/2);
            preparedStatement.setInt(1,client.getId());
            preparedStatement.setString(2,tripName);
            preparedStatement.setString(3,String.valueOf(arg.getArrangmentPrice()));
            preparedStatement.setString(4,String.valueOf(arg.getArrangmentPrice()/2));
            preparedStatement.execute();

            preparedStatement=connection.prepareStatement("UPDATE bankovni_racun SET stanje="+(getFounds(client.getAccountNumber())-arg.getArrangmentPrice()/2)+" WHERE broj_racuna="+client.getAccountNumber());
            preparedStatement.execute();

            preparedStatement=connection.prepareStatement("UPDATE bankovni_racun SET stanje="+(getProfit()+arg.getArrangmentPrice()/2)+" WHERE broj_racuna=1234567887654321");
            preparedStatement.execute();

            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public List<Arrangment> getReservedArraingment(Client client)
    {
        List<Arrangment> arrangments=getArrangements();
        List<Arrangment> reserved=new ArrayList<>();

        PreparedStatement preparedStatement = null;
        ResultSet set;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM rezervacija WHERE Klijent_id=" + client.getId());
            set = preparedStatement.executeQuery();
            while (set.next())
            {
                for(Arrangment a:arrangments)
                {
                    if(set.getString(2).equals(a.getId()))
                    {
                        reserved.add(a);
                    }
                }
            }
            preparedStatement.close();
            return reserved;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
    public List<Reservation> getClientReservations(Client client)
    {
        List<Reservation> reservations=new ArrayList<>();
        PreparedStatement preparedStatement = null;
        ResultSet set;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM rezervacija WHERE Klijent_id=" + client.getId());
            set = preparedStatement.executeQuery();
            while (set.next())
            {
                reservations.add(new Reservation(set.getInt(1),set.getString(2),set.getDouble(3),set.getDouble(4)));
            }
            preparedStatement.close();
            return reservations;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Reservation getReservation(String id)
    {
        PreparedStatement preparedStatement = null;
        ResultSet set;
        try {
            preparedStatement = connection.prepareStatement("SELECT * FROM rezervacija WHERE Klijent_id=? AND Aranzman_id=?");
            preparedStatement.setInt(1,Client.getActiveUser().getId());
            preparedStatement.setString(2,id);
            set = preparedStatement.executeQuery();
            set.next();

            return new Reservation(set.getInt(1), set.getString(2), set.getDouble(3), set.getDouble(4) );

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Arrangment getArrangment(String id)
    {
        try {
            ResultSet resultSet = null;
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM aranzman WHERE id=?");
            statement.setString(1,id);
            resultSet = statement.executeQuery();

            resultSet.next();
            return new Arrangment(resultSet.getString(1), resultSet.getString(2),
                    resultSet.getString(3), resultSet.getString(4),
                    resultSet.getString(5), resultSet.getString(6),
                    resultSet.getDouble(7), resultSet.getInt(8));
            }
         catch (SQLException e) {throw new RuntimeException(e);
        }
    }

    public boolean deleteReservation(Client client,String tripId)
    {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM rezervacija WHERE Klijent_id=? AND Aranzman_id=?");
            preparedStatement.setInt(1, client.getId());
            preparedStatement.setString(2,tripId);
            preparedStatement.execute();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean cancelReservation(Client client,String id)
    {
        try {
            Reservation currentRes = getReservation(id);
            Arrangment currentArr = getArrangment(id);

            if (LocalDate.now().isBefore(LocalDate.parse(currentArr.getDepartureDate())))
            {
                if(ChronoUnit.DAYS.between(LocalDate.parse(currentArr.getDepartureDate()),LocalDate.now())>=14)
                {
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bankovni_racun SET stanje=? WHERE broj_racuna=?");
                    preparedStatement.setDouble(1,getFounds(client.getAccountNumber())+currentRes.getPaidPrice());
                    preparedStatement.setString(2, client.getAccountNumber());
                    preparedStatement.execute();

                    preparedStatement = connection.prepareStatement("UPDATE bankovni_racun SET stanje=? WHERE broj_racuna=1234567887654321");
                    preparedStatement.setDouble(1,getProfit()-currentRes.getPaidPrice());
                    preparedStatement.execute();

                    preparedStatement = connection.prepareStatement("UPDATE rezervacija SET placena_cijena=? WHERE Klijent_id=? AND Aranzman_id=?");
                    preparedStatement.setDouble(1,-2);
                    preparedStatement.setInt(2, client.getId());
                    preparedStatement.setString(3,id);
                    preparedStatement.execute();
                }

                else
                {
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bankovni_racun SET stanje=? WHERE broj_racuna=?");
                    preparedStatement.setDouble(1,getFounds(client.getAccountNumber())+(currentRes.getTotalPrice()/2));
                    preparedStatement.setString(2, client.getAccountNumber());
                    preparedStatement.execute();

                    preparedStatement = connection.prepareStatement("UPDATE bankovni_racun SET stanje=? WHERE broj_racuna=1234567887654321");
                    preparedStatement.setDouble(1,getProfit()-(currentRes.getTotalPrice()/2));
                    preparedStatement.execute();

                    preparedStatement = connection.prepareStatement("UPDATE rezervacija SET placena_cijena=? WHERE Klijent_id=? AND Aranzman_id=?");
                    preparedStatement.setDouble(1,-2);
                    preparedStatement.setInt(2, client.getId());
                    preparedStatement.setString(3,id);
                    preparedStatement.execute();
                }
            }

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void pastReservation()
    {
        Client client=Client.getActiveUser();
        for(Reservation r: getClientReservations(client))
        {
            String id=r.getArrangementId();
            try {
                Reservation currentRes = getReservation(id);
                Arrangment currentArr = getArrangment(id);

                System.out.println(-1*(ChronoUnit.DAYS.between(LocalDate.parse(currentArr.getDepartureDate()),LocalDate.now())));
                System.out.println(LocalDate.now().isBefore(LocalDate.parse(currentArr.getDepartureDate())));
                if (LocalDate.now().isBefore(LocalDate.parse(currentArr.getDepartureDate())))
                {
                    if(-1*(ChronoUnit.DAYS.between(LocalDate.parse(currentArr.getDepartureDate()),LocalDate.now()))<14)
                    {
                        if (currentRes.getTotalPrice()!=currentRes.getPaidPrice())
                        {
                            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE rezervacija SET placena_cijena=? WHERE Klijent_id=? AND Aranzman_id=?");
                            preparedStatement.setDouble(1,-2);
                            preparedStatement.setInt(2, client.getId());
                            preparedStatement.setString(3,id);
                            preparedStatement.execute();

                            preparedStatement = connection.prepareStatement("UPDATE bankovni_racun SET stanje=? WHERE broj_racuna=?");
                            preparedStatement.setDouble(1,getFounds(client.getAccountNumber())+(currentRes.getTotalPrice()/2));
                            preparedStatement.setString(2, client.getAccountNumber());
                            preparedStatement.execute();

                            preparedStatement = connection.prepareStatement("UPDATE bankovni_racun SET stanje=? WHERE broj_racuna=1234567887654321");
                            preparedStatement.setDouble(1,getProfit()-(currentRes.getTotalPrice()/2));
                            preparedStatement.execute();
                        }
                    }
                }
                else
                {
                    PreparedStatement preparedStatement = connection.prepareStatement("UPDATE rezervacija SET placena_cijena=? WHERE Klijent_id=? AND Aranzman_id=?");
                    preparedStatement.setDouble(1,-1);
                    preparedStatement.setInt(2, client.getId());
                    preparedStatement.setString(3,id);
                    preparedStatement.execute();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public boolean processPayment(Reservation reservation,Client client,double money)
    {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE bankovni_racun SET stanje="+(getFounds(client.getAccountNumber())-money)+" WHERE broj_racuna="+client.getAccountNumber());
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("UPDATE bankovni_racun SET stanje="+(getProfit()+money)+" WHERE broj_racuna=1234567887654321");
            preparedStatement.execute();

            Statement statement = connection.createStatement();
            String SQLupdate = "UPDATE rezervacija SET placena_cijena  = '"+(reservation.getPaidPrice()+money)+"' WHERE Aranzman_id = '"+reservation.getArrangementId()+"' AND Klijent_id = "+reservation.getClientId()+"";

            statement.executeUpdate(SQLupdate);

            statement.close();
            preparedStatement.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public int getNumberOfAdmins()
    {
        try {
            int num=0;
            PreparedStatement statement= connection.prepareStatement("SELECT * FROM admin WHERE lozinka!="+"12345678");
            ResultSet set=statement.executeQuery();

            while (set.next())
            {
                num++;
            }

            return num;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Reservation> getThreeDayLeft()
    {
        List<Reservation> reservations=getClientReservations(Client.getActiveUser());
        List<Reservation> toBePayed=new ArrayList<>();

        for (Reservation r:reservations)
        {
            if(LocalDate.now().isBefore(LocalDate.parse(getArrangment(r.getArrangementId()).getDepartureDate())))
            {
                if(-1*ChronoUnit.DAYS.between(LocalDate.parse(getArrangment(r.getArrangementId()).getDepartureDate()),LocalDate.now())<=17)
                {
                    if(r.getTotalPrice()>r.getPaidPrice() && r.getPaidPrice()>=0)
                    {
                        toBePayed.add(r);
                    }
                }
            }
        }

        return toBePayed;
    }
}
