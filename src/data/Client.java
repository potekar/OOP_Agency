package data;

public class Client {
    private String name;
    private String lname;
    private String phone;
    private String jbmg;
    private String accountNumber;
    private String username;
    private String password;
    private int id;



    public Client(int id,String name, String lname, String phone, String jbmg, String accountNumber, String username, String password) {
        this.id=id;
        this.name = name;
        this.lname = lname;
        this.phone = phone;
        this.jbmg = jbmg;
        this.accountNumber = accountNumber;
        this.username = username;
        this.password = password;
    }
    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setJbmg(String jbmg) {
        this.jbmg = jbmg;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getName() {
        return name;
    }

    public String getLname() {
        return lname;
    }

    public String getPhone() {
        return phone;
    }

    public String getJbmg() {
        return jbmg;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
