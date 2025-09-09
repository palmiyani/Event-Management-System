import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

public class Event {
    int EventId;
    String EventName;
    String EventDate;
    String EventTime;
    String EventPlace;
    String EventDiscription;
    String EventDecoration;
    String EventCompletion;
    int userid;
    int adminid;

    public Event(int eventid, String eventName, String eventDate, String eventTime, String eventPlace,
            String eventDiscription, String eventDecoration, String eventCompletion, int userid) throws Exception {
        EventId = eventid;
        EventName = eventName;
        EventDate = eventDate;
        EventTime = eventTime;
        EventPlace = eventPlace;
        EventDiscription = eventDiscription;
        EventDecoration = eventDecoration;
        EventCompletion = eventCompletion;
        this.userid = userid;
    }

    public int getEventId() {
        return EventId;
    }

    public String getEventName() {
        return EventName;
    }

    public String getEventDate() {
        return EventDate;
    }

    public String getEventTime() {
        return EventTime;
    }

    public String getEventPlace() {
        return EventPlace;
    }

    public String getEventDiscription() {
        return EventDiscription;
    }

    public String getEventDecoration() {
        return EventDecoration;
    }

    public String getEventCompletion() {
        return EventCompletion;
    }

    public void setEventCompletion(String eventCompletion) {
        EventCompletion = eventCompletion;
    }

    public void setEventName(String eventName) {
        EventName = eventName;
    }

    public void setEventDate(String eventDate) {
        EventDate = eventDate;
    }

    public void setEventTime(String eventTime) {
        EventTime = eventTime;
    }

    public void setEventPlace(String eventPlace) {
        EventPlace = eventPlace;
    }

    public void setEventDiscription(String eventDiscription) {
        EventDiscription = eventDiscription;
    }

    public void setEventDecoration(String eventDecoration) {
        EventDecoration = eventDecoration;
    }

    @Override
    public String toString() {
        return "Event [EventId=" + EventId + ", EventName=" + EventName + ", EventDate=" + EventDate + ", EventTime="
                + EventTime + ", EventPlace=" + EventPlace + ", EventDiscription=" + EventDiscription
                + ", EventDecoration=" + EventDecoration + ", EventCompletion=" + EventCompletion + ", userid=" + userid
                + ", adminid=" + adminid + "]";

    }

    public String toStringforuser() {
        return "Event [EventName=" + EventName + ", EventDate=" + EventDate + ", EventTime="
                + EventTime + ", EventPlace=" + EventPlace + ", EventDiscription=" + EventDiscription
                + ", EventDecoration=" + EventDecoration + "]";
    }

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getAdminid() {
        return adminid;
    }

    public void setAdminid(int adminid) {
        this.adminid = adminid;
    }

}

class linkedlist {
    static int count = 0;

    class Node {
        Event data;
        Node next;

        Node(Event data) {
            this.data = data;
            this.next = null;
        }

    }

    static Node first = null;

    void insertfirst(Event data) {// insert event at first node
        Node node = new Node(data);
        if (first == null) {
            first = node;
        } else {
            node.next = first;
            first = node;
        }
    }

    void insertLast(Event data) {// insert event at last node
        Node node = new Node(data);
        if (first == null) {
            first = node;

        } else {
            Node temp = first;
            while (temp.next != null) {
                temp = temp.next;
            }
            temp.next = node;
        }

    }

    static int count() {
        Node temp = first;
        while (temp != null) {
            ++count;
            temp = temp.next;
        }
        return count;
    }

    static void displayundone() {
        if (first == null) {
            System.out.println("List is empty there is no event in the list");
        } else {
            Node temp = first;
            while (temp != null) {
                if (temp.data.getEventCompletion().equalsIgnoreCase("undone")) {
                    System.out.println(temp.data.toString());
                }
                temp = temp.next;
            }
        }
    }

    static void display() {
        if (first == null) {
            System.out.println("List is empty there is no event in the list");
        } else {
            Node temp = first;
            while (temp != null) {
                System.out.println(temp.data.toString());
                temp = temp.next;
            }
        }
    }

    void removeEvent(int eventId, int userId) {
        Node temp = first;
        Node prev = null;

        while (temp != null && temp.data.getEventId() != eventId && temp.data.getUserid() != userId) {
            prev = temp;
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("Event with ID " + eventId + " not found in the list");
            return;
        }

        if (prev == null) {
            first = temp.next; // Removing the first node
        } else {
            prev.next = temp.next; // Removing a node other than the first
        }
        System.out.println("Event with ID " + eventId + " is removed from the list");
    }

    void completeEvent(int eventId, int adminid) {
        Node temp = first;

        while (temp != null && temp.data.getEventId() != eventId && temp.data.getAdminid() != adminid) {
            temp = temp.next;
        }

        if (temp == null) {
            System.out.println("Event with ID " + eventId + " not found in the list");
            return;
        }

        temp.data.setEventCompletion("done");
        System.out.println("Event with ID " + eventId + " is marked as completed");
    }

}

class DatabaseUtil {
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

class Eventoppration {
    void eventadding(Event event) throws Exception {
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();

            if (con != null) {
                // System.out.println("Connected to the database successfully");

            } else {
                System.out.println("Cannot able to connect with the database");
            }
            String sql = "insert into event (event_id,event_name,event_date,event_time,event_place,event_discription,event_decoration,event_completion,user_id) values(?,?,?,?,?,?,?,?,?)";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, event.EventId);
            pst.setString(2, event.EventName);
            pst.setString(3, event.EventDate);
            pst.setString(4, event.EventTime);
            pst.setString(5, event.EventPlace);
            pst.setString(6, event.EventDiscription);
            pst.setString(7, event.EventDecoration);
            pst.setString(8, event.EventCompletion);
            pst.setInt(9, event.userid);
            int r = pst.executeUpdate();
            if (r > 0) {
                System.out.println(event.EventName + " is added successfully to the database");

            } else {
                System.out.println("Unable to add to in the database");
            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
    }

    void removeEvent(int eventId, int userId) throws Exception {
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();

            if (con != null) {
                // System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Cannot connect to the database");
            }

            String sql = "DELETE FROM event WHERE event_id = ? AND user_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, eventId);
            pst.setInt(2, userId);
            int r = pst.executeUpdate();
            if (r > 0) {
                System.out.println("Event with ID " + eventId + " is removed successfully from the database");
            } else {
                System.out.println("Unable to remove the event from the database");
            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
    }

    void loadEvents(linkedlist list) throws Exception {// store events into the linked list
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            if (con != null) {
                // System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Cannot connect to the database");
            }

            String sql = "SELECT * FROM event";
            PreparedStatement pst = con.prepareStatement(sql);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int eventId = rs.getInt("event_id");
                String eventName = rs.getString("event_name");
                String eventDate = rs.getString("event_date");
                String eventTime = rs.getString("event_time");
                String eventPlace = rs.getString("event_place");
                String eventDiscription = rs.getString("event_discription");
                String eventDecoration = rs.getString("event_decoration");
                String eventCompletion = rs.getString("event_completion");
                int userid = rs.getInt("user_id");
                Event event = new Event(eventId, eventName, eventDate, eventTime, eventPlace, eventDiscription,
                        eventDecoration, eventCompletion, userid);
                list.insertLast(event);
            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
    }

    Event printevent(int eventID) throws Exception {// prints event
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            if (con != null) {
                // System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Cannot connect to the database");
            }

            String sql = "SELECT * FROM event where event_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, eventID);
            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                int eventId = rs.getInt("event_id");
                String eventName = rs.getString("event_name");
                String eventDate = rs.getString("event_date");
                String eventTime = rs.getString("event_time");
                String eventPlace = rs.getString("event_place");
                String eventDiscription = rs.getString("event_discription");
                String eventDecoration = rs.getString("event_decoration");
                String eventCompletion = rs.getString("event_completion");
                int userid = rs.getInt("user_id");
                Event event = new Event(eventId, eventName, eventDate, eventTime, eventPlace, eventDiscription,
                        eventDecoration, eventCompletion, userid);
                return event;
            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
        return null;
    }

    void completeEvent(int eventId, int adminid) throws Exception {// complete event in database
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            if (con != null) {
                // System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Cannot connect to the database");
            }

            String sql = "UPDATE event SET event_completion = 'done' , admin_id = ? WHERE event_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, adminid);
            pst.setInt(2, eventId);

            int r = pst.executeUpdate();
            if (r > 0) {
                System.out.println("Event with ID " + eventId + " is marked as completed in the database");
            } else {
                System.out.println("Unable to mark the event as completed in the database");
            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
    }

    int getUserIdByEventid(int eventid) throws Exception {// return user id by event id
        int userid = 0;
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            if (con != null) {
                // System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Cannot connect to the database");
            }

            String sql = "select user_id from event where event_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, eventid);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                userid = rs.getInt("user_id");

            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
        return userid;
    }

    int getAdminIdByEventId(int eventid) throws Exception {// return admin id by event id
        int adminid = 0;
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            if (con != null) {
                // System.out.println("Connected to the database successfully");
            } else {
                System.out.println("Cannot connect to the database");
            }

            String sql = "select admin_id from event where event_id = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setInt(1, eventid);

            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                adminid = rs.getInt("event_id");

            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
        return adminid;
    }

    void updateEvent(int eventId, int userId) throws Exception {
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();
            if (con != null) {
                // System.out.println("Connected to the database successfully...!");
            } else {
                System.out.println("Cannot connect to the database...!");
            }

            Scanner sc = new Scanner(System.in);
            System.out.println("Choose the field to update:");
            System.out.println("1. Event Name:");
            System.out.println("2. Event Date:");
            System.out.println("3. Event Time:");
            System.out.println("4. Event Place:");
            System.out.println("5. Event Description:");
            System.out.println("6. Event Decoration:");

            int choice = sc.nextInt();
            sc.nextLine();

            String sql = "";
            switch (choice) {
                case 1:
                    System.out.println("Enter new Event Name:");
                    String newEventName = sc.nextLine();
                    sql = "UPDATE event SET event_name = ? WHERE event_id = ? and user_id = ?";
                    try (PreparedStatement pst = con.prepareStatement(sql)) {
                        pst.setString(1, newEventName);
                        pst.setInt(2, eventId);
                        pst.setInt(3, userId);
                        int r = pst.executeUpdate();
                        if (r > 0) {
                            System.out.println("Event Name updated successfully:");
                            updateEventInList(eventId, con, Main.list);

                        } else {
                            System.out.println("Unable to update Event Name:");
                        }
                    }
                    break;
                case 2:
                    try {
                        System.out.println("Enter new Event Date (YYYY-MM-DD):");
                        String eventDate = sc.nextLine();
                        validateDate(eventDate);
                        sql = "UPDATE event SET event_date = ? WHERE event_id = ? AND user_id =?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, eventDate);
                            pst.setInt(2, eventId);
                            pst.setInt(3, userId);
                            int r = pst.executeUpdate();
                            if (r > 0) {
                                System.out.println("Event Date updated successfully...!");
                                updateEventInList(eventId, con, Main.list);
                            } else {
                                System.out.println("Unable to update Event Date...!");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
                    } catch (dateExceptionA e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                    break;
                case 3:
                    try {
                        System.out.println("Enter new Event Time (HH:MM:AM/PM):");
                        String eventTime = sc.nextLine();
                        validateTime(eventTime);
                        sql = "UPDATE event SET event_time = ? WHERE event_id = ? and user_id = ?";
                        try (PreparedStatement pst = con.prepareStatement(sql)) {
                            pst.setString(1, eventTime);
                            pst.setInt(2, eventId);
                            pst.setInt(3, userId);
                            int r = pst.executeUpdate();
                            if (r > 0) {
                                System.out.println("Event Time updated successfully...!");
                                updateEventInList(eventId, con, Main.list);
                            } else {
                                System.out.println("Unable to update Event Time...!");
                            }
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(e.getMessage());
                    } catch (IndexOutOfBoundsException e) {
                        System.out.println("Invalid time format. Please enter the time in the format HH:MM:SS.");
                    } catch (timeExceptionA e) {
                        System.out.println(e.getMessage());
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }

                    break;
                case 4:
                    System.out.println("Enter new Event Place:");
                    String eventPlace = sc.nextLine();
                    sql = "UPDATE event SET event_place = ? WHERE event_id = ? and user_id = ?";
                    try (PreparedStatement pst = con.prepareStatement(sql)) {
                        pst.setString(1, eventPlace);
                        pst.setInt(2, eventId);
                        pst.setInt(3, userId);
                        int r = pst.executeUpdate();
                        if (r > 0) {
                            System.out.println("Event Place updated successfully...!");
                            updateEventInList(eventId, con, Main.list);
                        } else {
                            System.out.println("Unable to update Event Place...!");
                        }
                    }
                    break;
                case 5:
                    System.out.println("Enter new Event Description:");
                    String eventDescription = sc.nextLine();
                    sql = "UPDATE event SET event_description = ? WHERE event_id = ? and user_id = ?";
                    try (PreparedStatement pst = con.prepareStatement(sql)) {
                        pst.setString(1, eventDescription);
                        pst.setInt(2, eventId);
                        pst.setInt(3, userId);
                        int r = pst.executeUpdate();
                        if (r > 0) {
                            System.out.println("Event Description updated successfully...!");
                            updateEventInList(eventId, con, Main.list);
                        } else {
                            System.out.println("Unable to update Event Description...!");
                        }
                    }
                    break;
                case 6:
                    System.out.println("Enter new Event Decoration:");
                    String eventDecoration = sc.nextLine();
                    sql = "UPDATE event SET event_decoration = ? WHERE event_id = ? AND user_id = ?";
                    try (PreparedStatement pst = con.prepareStatement(sql)) {
                        pst.setString(1, eventDecoration);
                        pst.setInt(2, eventId);
                        pst.setInt(3, userId);
                        int r = pst.executeUpdate();
                        if (r > 0) {
                            System.out.println("Event Decoration updated successfully");
                            updateEventInList(eventId, con, Main.list);
                        } else {
                            System.out.println("Unable to update Event Decoration...!");
                        }
                    }
                    break;

                default:
                    System.out.println("Invalid choice...!");
                    break;
            }
            String query = "select * from event_log where event_id = ?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, eventId);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int e_id = rs.getInt("event_id");
                int u_id = rs.getInt("user_id");
                String e_name = rs.getString("event_name");
                String e_date = rs.getString("event_date");
                String e_time = rs.getString("event_time");
                String e_place = rs.getString("event_place");
                String e_discription = rs.getString("event_discription");
                String e_decoration = rs.getString("event_decoration");

                System.out.println("Before update Event [EventId=" + e_id + ", userid=" + u_id + ", EventName=" + e_name
                        + ", EventDate=" + e_date + ", EventTime="
                        + e_time + ", EventPlace=" + e_place + ", EventDiscription=" + e_discription
                        + ", EventDecoration=" + e_decoration + ", userid=" + u_id
                        + "]");
            }

        } finally {
            DatabaseUtil.closeConnection(con);
        }
    }

    void updateEventInList(int eventId, Connection con, linkedlist list) throws Exception {
        Event updatedEvent = getEventFromDatabase(eventId, con);
        if (updatedEvent != null) {
            linkedlist.Node temp = linkedlist.first;
            while (temp != null) {
                if (temp.data.getEventId() == eventId) {
                    temp.data = updatedEvent;
                    System.out.println("Event with ID " + eventId + " is updated in the linked list...!");
                    return;
                }
                temp = temp.next;
            }
        } else {
            System.out.println("Event with ID " + eventId + " not found in the database...!");
        }

    }

    Event getEventFromDatabase(int eventId, Connection con) throws Exception {
        String sql = "SELECT * FROM event WHERE event_id = ?";
        try (PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setInt(1, eventId);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                return new Event(rs.getInt("event_id"), rs.getString("event_name"), rs.getString("event_date"),
                        rs.getString("event_time"), rs.getString("event_place"), rs.getString("event_discription"),
                        rs.getString("event_decoration"), rs.getString("event_completion"), rs.getInt("user_id"));
            }
        }
        return null;
    }

    void validateDate(String date) throws dateExceptionA {// check whether given date is write or wrong
        String[] parts = date.split("-");
        int year = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int day = Integer.parseInt(parts[2]);

        if (year < 2024) {
            throw new dateExceptionA("Please enter a proper year");
        }
        if (month < 1 || month > 12) {
            throw new dateExceptionA("Please enter a proper month");
        }
        if (day < 1 || day > 31) {
            throw new dateExceptionA("Please enter a proper date");
        }
    }

    void validateTime(String time) throws timeExceptionA {// check whether give time is in write formate or not
        String[] parts = time.split(":");
        int hour = Integer.parseInt(parts[0]);
        int minute = Integer.parseInt(parts[1]);
        String period = parts[2].toUpperCase();

        if (hour < 1 || hour > 12) {
            throw new timeExceptionA("Please enter the hour correctly");
        }
        if (minute < 0 || minute > 59) {
            throw new timeExceptionA("Please enter the minute correctly");
        }
        if (!period.equals("AM") && !period.equals("PM")) {
            throw new timeExceptionA("Please enter AM or PM correctly");
        }
    }

    int getEventIdByNameAndTime(String eventName, String eventTime) throws Exception {// return event id by name and
                                                                                      // time
        Connection con = null;
        try {
            con = DatabaseUtil.getConnection();

            if (con != null) {
                System.out.println("Connected to the database successfully...!");
            } else {
                System.out.println("Cannot connect to the database...!");
            }

            String sql = "select event_id from event where event_name = ? and event_time = ?";
            PreparedStatement pst = con.prepareStatement(sql);
            pst.setString(1, eventName);
            pst.setString(2, eventTime);
            ResultSet rs = pst.executeQuery();

            if (rs.next()) {
                return rs.getInt("event_id");
            } else {
                throw new Exception("Event not found with the given name and time...!");
            }
        } finally {
            DatabaseUtil.closeConnection(con);
        }
    }

}

class dateExceptionA extends Exception {
    public dateExceptionA(String message) {
        super(message);
    }
}

class timeExceptionA extends Exception {
    public timeExceptionA(String message) {
        super(message);
    }
}