import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Admin {
    String Adminname;
    int Adminage;
    String Adminphone;
    String Adminemail;
    String password;

    public void setAdminname(String adminname) {
        Adminname = adminname;
    }

    public void setAdminage(int adminage) {
        Adminage = adminage;
    }

    public void setAdminphone(String adminphone) {
        Adminphone = adminphone;
    }

    public void setAdminemail(String adminemail) {
        Adminemail = adminemail;
    }

    public String getAdminname() {
        return Adminname;
    }

    public int getAdminage() {
        return Adminage;
    }

    public String getAdminphone() {
        return Adminphone;
    }

    public String getAdminemail() {
        return Adminemail;
    }

    public Admin(String adminname, int adminage, String adminphone, String adminemail, String password) {
        Adminname = adminname;
        Adminage = adminage;
        Adminphone = adminphone;
        Adminemail = adminemail;
        this.password = password;
    }

    public String getAdminpassword() {
        return password;
    }

}

class DatabaseUtilofadmin {
    static String drivername = "com.mysql.cj.jdbc.Driver";
    static String DB_URL = "jdbc:mysql://localhost:3306/projectsemtwo";
    static String DB_USER = "root";
    static final String DB_PASS = "";

    static {
        try {
            Class.forName(drivername);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    public static void closeConnection(Connection con) {
        if (con != null) {
            try {
                con.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

class AdminOperation {
    public void addAdmin(Admin admin) throws Exception {// it adds admin
        String sql = "INSERT INTO admin (admin_name,admin_age,admin_phone,admin_email,admin_password) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseUtilofadmin.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, admin.getAdminname());
            pst.setInt(2, admin.getAdminage());
            pst.setString(3, admin.getAdminphone());
            pst.setString(4, admin.getAdminemail());
            pst.setString(5, admin.getAdminpassword());
            pst.executeUpdate();
        }
    }

    public boolean checkpassword(String phonenum, String password) throws SQLException, Exception {// it checks password
                                                                                                   // by phone number
                                                                                                   // and password
        String pass = "";
        String phone = "";
        String sql = "select admin_password,admin_phone from admin where admin_password=? AND admin_phone=?";
        try (Connection con = DatabaseUtilofadmin.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, password);
            pst.setString(2, phonenum);
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pass = rs.getString("admin_password");
                phone = rs.getString("admin_phone");
            }
            if (pass.equals(password) && phone.equals(phonenum)) {
                return true;
            } else {
                return false;
            }
        }

    }

    public int getAdminid(String password) throws SQLException, Exception {// get admin id
        int adminid = 0;
        String sql = "select admin_id from admin where admin_password=?";
        try (Connection con = DatabaseUtilofadmin.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, password);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                adminid = rs.getInt("admin_id");
            }
        }
        return adminid;
    }
}