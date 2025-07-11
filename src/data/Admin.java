package data;

public class Admin {
    private int id;
    private String name;
    private String lastName;
    private String username;
    private String password;

    public static Admin activeAdmin;

    public Admin(int id, String name, String lastName, String username, String password) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public static Admin getActiveAdmin() {
        return activeAdmin;
    }

    public static void setActiveAdmin(Admin activeAdmin) {
        Admin.activeAdmin = activeAdmin;
    }
}
