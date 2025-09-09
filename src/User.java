import java.sql.*;

public class User {

    int userid;
    String userName;
    int userAge;
    String userPhone;
    String userEmail;
    String password;

    public User(String userName, int userAge, String userPhone, String userEmail, String password) {

        this.userName = userName;
        this.userAge = userAge;
        this.userPhone = userPhone;
        this.userEmail = userEmail;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public int getUserid() {
        return userid;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getUserAge() {
        return userAge;
    }

    public void setUserAge(int userAge) {
        this.userAge = userAge;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String toString() {
        return "User [userName=" + userName + ", userAge=" + userAge + ", userPhone=" + userPhone + ", userEmail="
                + userEmail + "]";
    }

}

class DatabaseUtilofuser {
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

class UserOperation {
    public void addUser(User user) throws Exception {
        String sql = "INSERT INTO user (user_name,user_age,user_phone,user_email,user_password) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DatabaseUtilofuser.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, user.getUserName());
            pst.setInt(2, user.getUserAge());
            pst.setString(3, user.getUserPhone());
            pst.setString(4, user.getUserEmail());
            pst.setString(5, user.getPassword());
            pst.executeUpdate();
        }
    }

    public boolean checkpassword(String phonenum, String password) throws SQLException, Exception {
        String pass = "";
        String phone = "";

        String sql = "select user_password,user_phone from user where user_password=? AND user_phone=?";
        try (Connection con = DatabaseUtilofuser.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, password);
            pst.setString(2, phonenum);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                pass = rs.getString("user_password");
                phone = rs.getString("user_phone");

            }
            if (pass.equals(password) && phone.equals(phonenum)) {
                return true;
            } else {
                return false;
            }
        }

    }

    public int getUserid(String password) throws SQLException, Exception {
        int userid = 0;
        String sql = "select user_id from user where user_password=?";
        try (Connection con = DatabaseUtilofuser.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, password);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                userid = rs.getInt("user_id");
            }
        }
        return userid;
    }

}