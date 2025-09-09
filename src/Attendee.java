import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Attendee {
    int eventid;
    String AttendeeName;
    int AttendeeAge;
    String attendeeEmail;
    String AttendeePhoneNO;
    int adminid;

    public Attendee(int eventid, String attendeeName, int attendeeAge, String attendeeEmail, String attendeePhoneNO,
            int adminid) {
        this.eventid = eventid;
        AttendeeName = attendeeName;
        AttendeeAge = attendeeAge;
        this.attendeeEmail = attendeeEmail;
        AttendeePhoneNO = attendeePhoneNO;
        this.adminid = adminid;
    }

    public int getEventid() {
        return eventid;
    }

    public String getAttendeeEmail() {
        return attendeeEmail;
    }

    public String getAttendeeName() {
        return AttendeeName;
    }

    public int getAttendeeAge() {
        return AttendeeAge;
    }

    public String getAttendeePhoneNO() {
        return AttendeePhoneNO;
    }

    @Override
    public String toString() {
        return "Attendee [eventid=" + eventid + ", AttendeeName=" + AttendeeName + ", AttendeeAge=" + AttendeeAge
                + ", attendeeEmail=" + attendeeEmail + ", AttendeePhoneNO=" + AttendeePhoneNO + ", adminid=" + adminid
                + "]";
    }

}

class DatabaseUtilofattendee {
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

class AttandeeOperation {
    public void addAttendee(Attendee attendee) throws Exception {
        String sql = "INSERT INTO attendee (event_id, attendee_name, attendee_age, attendee_email, attendee_phone,admin_id) VALUES (?, ?, ?, ?, ?,?)";
        try (Connection con = DatabaseUtilofattendee.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, attendee.getEventid());
            pst.setString(2, attendee.getAttendeeName());
            pst.setInt(3, attendee.getAttendeeAge());
            pst.setString(4, attendee.getAttendeeEmail());
            pst.setString(5, attendee.getAttendeePhoneNO());
            pst.setInt(6, attendee.adminid);
            pst.executeUpdate();
        }
    }

    public void deleteAttendee(int attendeeid, int adminid) throws Exception {

        String sql = "DELETE FROM attendee WHERE attendee_id = ? and admin_id = ?";
        try {
            Connection con = DatabaseUtilofattendee.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, attendeeid);
            pst.setInt(2, adminid);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Attendee with ID " + attendeeid + " deleted successfully.");
            } else {
                System.out.println("No attendee found with ID " + attendeeid);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateAttendee(int attendeeid, int adminid) throws Exception {

        Scanner sc = new Scanner(System.in);
        String sql = "UPDATE attendee SET attendee_name = ?, attendee_age = ?, attendee_email = ?, attendee_phone = ? WHERE attendee_id = ? and admin_id=?";
        try {
            Connection con = DatabaseUtilofattendee.getConnection();
            PreparedStatement pst = con.prepareStatement(sql);
            System.out.println("Enter attendeename's new name");
            String newname = sc.nextLine();

            System.out.println("Enter attendee's new age");
            int newage = sc.nextInt();
            sc.nextLine();

            String newEmailAddress = "";

            boolean correctemail = false;
            while (!correctemail) {
                try {

                    System.out.println("Enter attendee's new emailaddress");
                    newEmailAddress = sc.nextLine();
                    if (!(newEmailAddress.contains("@") && newEmailAddress.contains("gmail.com")
                            && newEmailAddress.endsWith("gmail.com"))) {
                        throw new EmailException("Please enter appropriate email id");
                    }
                } catch (EmailException e) {
                    System.out.println(e);
                    continue;
                }
                correctemail = true;
            }

            String newattendeePhone = "";
            boolean correctphone = false;
            while (!correctphone) {
                try {
                    System.out.println("Enter attendee phone:");
                    newattendeePhone = sc.nextLine();
                    if (!(newattendeePhone.length() == 10
                            && (newattendeePhone.charAt(0) == '9' || newattendeePhone.charAt(0) == '8'
                                    || newattendeePhone.charAt(0) == '7' || newattendeePhone.charAt(0) == '6'))) {
                        throw new PhonenumException("Please enter appropriate mobile number");
                    }
                } catch (PhonenumException e) {
                    System.out.println(e.getMessage());
                    continue;
                }
                correctphone = true;
            }

            pst.setString(1, newname);
            pst.setInt(2, newage);
            pst.setString(3, newEmailAddress);
            pst.setString(4, newattendeePhone);
            pst.setInt(5, attendeeid);
            pst.setInt(6, adminid);
            int rowsAffected = pst.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Attendee with ID " + attendeeid + " updated successfully.");
            } else {
                System.out.println("No attendee found with ID " + attendeeid);
            }

            String query = "select * from attendee_log where attendee_id =?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, attendeeid);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int e_id = rs.getInt("event_id");
                int a_id = rs.getInt("attendee_id");
                String a_name = rs.getString("attendee_name");
                int a_age = rs.getInt("attendee_age");
                String a_email = rs.getString("attendee_email");
                String a_phone = rs.getString("attendee_phone");
                int ADMINID = rs.getInt("admin_id");
                System.out.println("before update attendee:" + "Attendee [eventid=" + e_id + ", AttendeeName=" + a_name
                        + ", Attendeeid=" + a_id + ", AttendeeAge=" + a_age + ", attendeeEmail=" + a_email
                        + ", admin_id=" + ADMINID + ", AttendeePhoneNO=" + a_phone + "]");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }

    public void displayAttendees(int eventId) throws Exception {
        String sql = "SELECT * FROM attendee WHERE event_id = ?";
        try (Connection con = DatabaseUtilofattendee.getConnection();
                PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, eventId);
            try (ResultSet rs = pst.executeQuery()) {
                while (rs.next()) {
                    int attendeeid = rs.getInt("attendee_id");
                    int id = rs.getInt("event_id");
                    String name = rs.getString("attendee_name");
                    int age = rs.getInt("attendee_age");
                    String email = rs.getString("attendee_email");
                    String phone = rs.getString("attendee_phone");
                    System.out.println("Attendee [attendeeid = " + attendeeid + ",eventid=" + id + ", AttendeeName="
                            + name + ", AttendeeAge=" + age
                            + ", attendeeEmail=" + email + ", AttendeePhoneNO=" + phone + "]");
                }
            }
        }
    }

    Attendee printAttendee(int attendeeID) throws Exception {
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            if (con != null) {
                // System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Cannot connect to the database");
            }

            String sql = "SELECT * FROM attendee where attendee_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, attendeeID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int eventId = rs.getInt("event_id");
                String attendeeId = rs.getString("attendee_id");
                String attendeeName = rs.getString("attendee_name");
                int attendeeAge = rs.getInt("attendee_age");
                String attendeeEmail = rs.getString("attendee_email");
                String attendeePhone = rs.getString("attendee_phone");
                int adminid = rs.getInt("admin_id");
                Attendee attendee = new Attendee(eventId, attendeeName, attendeeAge, attendeeEmail, attendeePhone,
                        adminid);
                return attendee;
            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
        return null;
    }

    int getAdminIdByAttendeeId(int attendeeid) throws Exception {// it returns adminid by attendee id
        int adminid = 0;
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            if (con != null) {
                // System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Cannot connect to the database");
            }

            String sql = "select admin_id from attendee where attendee_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, attendeeid);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                adminid = rs.getInt("admin_id");

            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
        return adminid;
    }
}