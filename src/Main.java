import java.util.*;

public class Main {

    static Event event[] = new Event[100];
    static Eventoppration eop = new Eventoppration();
    static linkedlist list = new linkedlist();
    static Attendee attendee[] = new Attendee[10000];
    static AttandeeOperation aop = new AttandeeOperation();
    static User user[] = new User[10000];
    static UserOperation uop = new UserOperation();
    static Admin admin[] = new Admin[10000];
    static AdminOperation adop = new AdminOperation();
    static String password;

    public static void main(String[] args) throws Exception {
        eop.loadEvents(list);
        Scanner sc = new Scanner(System.in);

        int i = 0;// index for user's object
        int indexforadmin = 0;// index for admin's object
        boolean userop = false;
        boolean adminop = false;

        boolean close = false;
        while (!close) {// this loop is for (user's or admin's) sign up and login
            int choose = 0;
            while (true) {
                try {
                    System.out.println("Enter 1 for user and 2 for admin:");
                    choose = sc.nextInt();
                    if (choose != 1 && choose != 2) {
                        System.out.println("Please enter choices only 1 or 2...!");
                        continue;
                    }
                    sc.nextLine(); // Consume the newline character
                    break;
                } catch (InputMismatchException e) {
                    System.out.println("Please enter a valid number (1 or 2).");
                    sc.nextLine(); // Clear the invalid input
                }
            }

            if (choose == 1) {// signup and login for user
                System.out.println("Sign up(choose 1) or login(choose 2):");
                int choose1 = 0;
                while (true) {
                    try {
                        choose1 = sc.nextInt();
                        if (choose1 != 1 && choose1 != 2) {
                            System.out.println("Please enter 1 for sign up or 2 for login:");
                            continue;
                        }
                        sc.nextLine(); // Consume the newline character
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid number (1 or 2).");
                        sc.nextLine(); // Clear the invalid input
                    }
                }

                if (choose1 == 1) {// for signup
                    createuser(user[i]);// Make a new account for user
                    i++;
                    userop = true;
                    close = true;
                    break;
                } else if (choose1 == 2) {// for login
                    String phonenum = "";
                    boolean correctphone = false;
                    while (!correctphone) {
                        try {
                            System.out.println("Enter phonenum:");
                            phonenum = sc.nextLine();
                            if (!(phonenum.length() == 10 && (phonenum.charAt(0) == '9' || phonenum.charAt(0) == '8'
                                    || phonenum.charAt(0) == '7' || phonenum.charAt(0) == '6'))) {
                                throw new PhonenumException("Please enter appropriate mobile number");
                            }
                        } catch (PhonenumException e) {
                            System.out.println(e);
                            continue;
                        }
                        correctphone = true;
                    }
                    System.out.println("For login, enter password:");
                    password = sc.nextLine();

                    userop = uop.checkpassword(phonenum, password);
                    if (userop) {
                        System.out.println("Login successful...!");
                        close = true;
                        break;
                    } else {
                        System.out.println("No such account...!");
                        continue;
                    }
                }
            } else if (choose == 2) {// signup and login for admin
                System.out.println("Sign up(choose 1) or login(choose 2):");
                int choose2 = 0;
                while (true) {
                    try {
                        choose2 = sc.nextInt();
                        if (choose2 != 1 && choose2 != 2) {
                            System.out.println("Please enter 1 for sign up or 2 for login:");
                            continue;
                        }
                        sc.nextLine(); // Consume the newline character
                        break;
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter a valid number (1 or 2).");
                        sc.nextLine(); // Clear the invalid input
                    }
                }

                if (choose2 == 1) {// signup for admin
                    createAdmin(admin[indexforadmin]);// make aa new account for admin
                    adminop = true;
                    close = true;
                    break;
                } else if (choose2 == 2) {// login for admin
                    String phonenum = "";
                    boolean correctphone = false;
                    while (!correctphone) {
                        try {
                            System.out.println("Enter phonenum:");
                            phonenum = sc.nextLine();
                            if (!(phonenum.length() == 10 && (phonenum.charAt(0) == '9' || phonenum.charAt(0) == '8'
                                    || phonenum.charAt(0) == '7' || phonenum.charAt(0) == '6'))) {
                                throw new PhonenumException("Please enter appropriate mobile number...!");
                            }
                        } catch (PhonenumException e) {
                            System.out.println(e.getMessage());
                            continue;
                        }
                        correctphone = true;
                    }
                    System.out.println("For login, enter password:");
                    password = sc.nextLine();
                    adminop = adop.checkpassword(phonenum, password);// checks password by phonenum and password
                    if (adminop) {
                        System.out.println("Login successful...!");
                        close = true;
                        break;
                    } else {
                        System.out.println("No such account...!");
                        continue;
                    }
                }
            }
        }

        // index for event's array of object
        int index = 1;
        // index for attendee's array of object
        int idx = 1;
        int choice = 1;

        if (userop) {
            while (choice < 4) {
                try {
                    System.out
                            .println(
                                    "Enter choice: \n1.Add Event \n2.Remove Event \n3.Update anything in Event \n4.exit");
                    choice = sc.nextInt();

                    switch (choice) {
                        case 1:
                            sc.nextLine();
                            System.out.println("Enter password:");
                            String pass = sc.nextLine();
                            if (password.equals(pass)) {
                                int userid = uop.getUserid(pass);// returns userid by password
                                addEvent(list, event[index], userid);
                                // System.out.println(event[index]);
                                index++;
                            } else {
                                System.out.println("please enter correct password:");
                            }

                            break;

                        case 2:

                            System.out.println("Enter event id that you want to remove:");
                            int id = sc.nextInt();// for event id
                            System.out.println("Enter userid:");
                            int usid = sc.nextInt();// for user id
                            int us_id = eop.getUserIdByEventid(id);// returns userid by event id
                            if (us_id == usid) {
                                eop.removeEvent(id, usid);
                                list.removeEvent(id, usid);
                                // System.out.println(event[index]);
                            } else {
                                System.out.println("please enter appropriate data...!");
                            }

                            break;

                        case 3:
                            System.out.println("Enter the eventid which you want to update:");
                            int eventID = sc.nextInt();
                            System.out.println("Enter UserId to update event:");
                            int userID = sc.nextInt();
                            int userId = eop.getUserIdByEventid(eventID);// it returns userid by event id
                            if (userID == userId) {
                                eop.updateEvent(eventID, userID);
                                System.out.println("after update attedee " + eop.printevent(eventID));
                            } else {
                                System.out.println("please enter appropriate data...!");
                            }

                            break;

                        case 4:
                            System.out.println("Exiting...!");
                            break;
                        default:
                            break;
                    }
                } catch (InputMismatchException e) {
                    System.out.println("Please enter integer value.");
                    sc.nextLine();
                    continue;
                }

            }
        }

        boolean correctpass = false;
        if (adminop) {
            while (!correctpass) {
                try {
                    // sc.nextLine();
                    System.out.println("Enter password:");
                    String password = sc.nextLine();
                    int adminid = adop.getAdminid(password);
                    if (adminid != 0) {
                        correctpass = true;
                        while (choice < 8) {
                            System.out.println(
                                    "Enter choice :\n1.View undone Event List \n2.Add Attandee \n3.Compleate Event \n4.display list \n5.display attendee \n6.delete attendee \n7.updateattendee \n8.exit");

                            try {
                                choice = sc.nextInt();
                            } catch (InputMismatchException e) {
                                System.out.println("Invalid input. Please enter a number between 1 and 8.");
                                sc.nextLine(); // Consume the invalid input
                                continue; // Continue the loop to prompt the user again
                            }

                            switch (choice) {
                                case 1:
                                    linkedlist.displayundone();// display uncompleted events
                                    break;

                                case 2:

                                    addAttendee(attendee[idx], adminid);// adds attendee
                                    idx++;
                                    break;

                                case 3:
                                    System.out.println("Enter event id that you want to mark as completed:");
                                    int completeId = sc.nextInt();

                                    eop.completeEvent(completeId, adminid);// complete event in database
                                    list.completeEvent(completeId, adminid);// complete event in list
                                    break;

                                case 4:
                                    linkedlist.display();// print event from linkedlist
                                    break;

                                case 5:
                                    System.out.println("Enter event id to display attendees:");
                                    int eventIdForAttendees = sc.nextInt();
                                    aop.displayAttendees(eventIdForAttendees);// print attendee
                                    break;

                                case 6:
                                    System.out.println("Enter the attendee id to delete:");
                                    int attendeeid = sc.nextInt();
                                    int AdminId = aop.getAdminIdByAttendeeId(attendeeid);// return admin id by attendee
                                                                                         // id
                                    if (AdminId == adminid) {
                                        aop.deleteAttendee(attendeeid, adminid);// delete attendee
                                    } else {
                                        System.out.println("please enter appropriate data...!");
                                    }

                                    break;

                                case 7:
                                    System.out.println("Enter attendee id to update:");
                                    int attendeeId = sc.nextInt();
                                    int ADMINID = aop.getAdminIdByAttendeeId(attendeeId);// return admin id by attendee
                                                                                         // id
                                    if (ADMINID == adminid) {
                                        aop.updateAttendee(attendeeId, adminid);
                                        System.out.println("before update " + aop.printAttendee(attendeeId));

                                    } else {
                                        System.out.println("please enter appropriate data...!");
                                    }

                                    break;

                                case 8:
                                    System.out.println("Exiting...");
                                    break;

                                default:
                                    break;
                            }
                        }
                    } else {
                        System.out.println("please enter correct password");
                    }
                } catch (InputMismatchException e) {
                    System.out.println(e.getMessage());
                    sc.nextLine();
                    continue;
                }
            }
        }

    }

    public static void addEvent(linkedlist list, Event event, int userid) throws Exception {// add event in linked list
                                                                                            // and database

        Scanner sc = new Scanner(System.in);
        System.out.println("Enter evnet id:");
        int EventId = sc.nextInt();
        sc.nextLine();
        System.out.println("Enter event name:");
        String EventName = sc.nextLine();
        String EventDate = "";
        boolean correctdate = false;
        while (!correctdate) {
            try {
                System.out.println("Enter event date in (YYYY-MM-DD) form:");
                EventDate = sc.next();

                String date[] = EventDate.split("-");
                int year = Integer.parseInt(date[0]);
                int month = Integer.parseInt(date[1]);
                int day = Integer.parseInt(date[2]);

                if (year < 2024) {
                    throw new dateException("Please enter proper year");
                }
                if (month < 1 || month > 12) {
                    throw new dateException("please enter proper month");
                }
                if (day < 1 || day > 31) {
                    throw new dateException("Please enter proper date");
                }
            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Please enter the date in the proper format (YYYY-MM-DD).");
                continue;
            } catch (dateException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            correctdate = true;
        }
        String EventTime = "";
        boolean correcttime = false;
        while (!correcttime) {
            try {
                System.out.println("Enter event time in (HH:MM:PM) or (HH:MM:AM) formate:");
                EventTime = sc.next();
                String Time[] = EventTime.split(":");
                int Hour = Integer.parseInt(Time[0]);
                int minute = Integer.parseInt(Time[1]);
                String posttime = Time[2];

                if (Hour < 1 || Hour > 12) {
                    throw new timeException("Please enter Hour correctly");
                }

                if (minute < 0 || minute > 60) {
                    throw new timeException("Please enter minute correctly");
                }
                if (!(posttime.equalsIgnoreCase("PM")) && !(posttime.equalsIgnoreCase("AM"))) {
                    throw new timeException("Please enter am or pm correctly");
                }

            } catch (NumberFormatException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Invalid time format. Please enter the time in the format HH:MM:SS.");
                continue;
            } catch (timeException e) {
                System.out.println(e.getMessage());
                continue;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                continue;
            }
            correcttime = true;
        }

        sc.nextLine();
        System.out.println("Enter event place:");
        String EventPlace = sc.nextLine();
        // sc.nextLine();
        System.out.println("Enter event discription:");
        String EventDiscription = sc.nextLine();
        System.out.println("Enter event decoration:");
        String EventDecoration = sc.nextLine();
        String EventCompletion = "undone";
        event = new Event(EventId, EventName, EventDate, EventTime, EventPlace, EventDiscription,
                EventDecoration, EventCompletion, userid);
        list.insertLast(event);
        eop.eventadding(event);
        System.out.println(event.toString());

    }

    public static void addAttendee(Attendee attendee, int adminid) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter event name:");
        String eventName = sc.nextLine();
        System.out.println("Enter event time:");
        String eventTime = sc.nextLine();
        int eventId = eop.getEventIdByNameAndTime(eventName, eventTime);

        System.out.println("Enter attendee name:");
        String attendeeName = sc.nextLine();
        System.out.println("Enter attendee age:");
        int attendeeage = sc.nextInt();
        sc.nextLine();

        String attendeeEmail = "";
        boolean correctemail = false;
        while (!correctemail) {
            try {
                System.out.println("Enter attendee email:");
                attendeeEmail = sc.nextLine();
                if (!(attendeeEmail.contains("@") && attendeeEmail.contains("gmail.com")
                        && attendeeEmail.endsWith("gmail.com"))) {
                    throw new EmailException("Please enter appropriate email address");
                }
            } catch (EmailException e) {
                System.out.println(e.getMessage());
                continue;
            }
            correctemail = true;
        }

        String attendeePhone = "";
        boolean correctphone = false;
        while (!correctphone) {
            try {
                System.out.println("Enter attendee phone:");
                attendeePhone = sc.nextLine();
                if (!(attendeePhone.length() == 10 && (attendeePhone.charAt(0) == '9' || attendeePhone.charAt(0) == '8'
                        || attendeePhone.charAt(0) == '7' || attendeePhone.charAt(0) == '6'))) {
                    throw new PhonenumException("Please enter appropriate mobile number");
                }
            } catch (PhonenumException e) {
                System.out.println(e.getMessage());
                continue;
            }
            correctphone = true;
        }
        attendee = new Attendee(eventId, attendeeName, attendeeage, attendeeEmail, attendeePhone, adminid);
        aop.addAttendee(attendee);
        System.out.println("attendee added successfuly...!");

    }

    public static void createuser(User user) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter User name:");
        String name = sc.nextLine();
        System.out.println("Enter age:");
        int age = sc.nextInt();
        sc.nextLine();

        String phonenum = "";
        boolean correctphone = false;
        while (!correctphone) {
            try {
                System.out.println("Enter phonenum:");
                phonenum = sc.nextLine();
                if (!(phonenum.length() == 10 && (phonenum.charAt(0) == '9' || phonenum.charAt(0) == '8'
                        || phonenum.charAt(0) == '7' || phonenum.charAt(0) == '6'))) {
                    throw new PhonenumException("Please enter appropriate mobile number:");
                }
            } catch (PhonenumException e) {
                System.out.println(e.getMessage());
                continue;
            }
            correctphone = true;

        }
        String Email = "";
        boolean correctemail = false;
        while (!correctemail) {
            try {
                System.out.println("Enter email:");
                Email = sc.nextLine();
                if (!(Email.contains("@") && Email.contains("gmail.com") && Email.endsWith("gmail.com"))) {
                    throw new EmailException("Please enter appropriate email id");
                }
            } catch (EmailException e) {
                System.out.println(e.getMessage());
                continue;
            }
            correctemail = true;
        }
        System.out.println("Enter password:");
        password = sc.nextLine();
        user = new User(name, age, phonenum, Email, password);
        uop.addUser(user);
        System.out.println("new account created successfuly...!");
    }

    public static void createAdmin(Admin admin) throws Exception {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter Admin's name:");
        String name = sc.nextLine();
        System.out.println("Enter Admin's age:");
        int age = sc.nextInt();
        sc.nextLine();
        String phonenum = "";
        boolean correctphone = false;
        while (!correctphone) {
            try {
                System.out.println("Enter phonenum:");
                phonenum = sc.nextLine();
                if (!(phonenum.length() == 10 && (phonenum.charAt(0) == '9' || phonenum.charAt(0) == '8'
                        || phonenum.charAt(0) == '7' || phonenum.charAt(0) == '6'))) {
                    throw new PhonenumException("Please enter appropriate mobile number");
                }
            } catch (PhonenumException e) {
                System.out.println(e.getMessage());
                continue;
            }
            correctphone = true;

        }
        String Email = "";
        boolean correctemail = false;
        while (!correctemail) {
            try {
                System.out.println("Enter email:");
                Email = sc.nextLine();
                if (!(Email.contains("@") && Email.contains("gmail.com") && Email.endsWith("gmail.com"))) {
                    throw new EmailException("Please enter appropriate email id");
                }
            } catch (EmailException e) {
                System.out.println(e.getMessage());
                continue;
            }
            correctemail = true;
        }
        System.out.println("Enter password:");
        password = sc.nextLine();
        admin = new Admin(name, age, phonenum, Email, password);
        adop.addAdmin(admin);
        System.out.println("Admin added successfuly...!");
    }

}

class dateException extends RuntimeException {
    dateException(String s) {
        super(s);
    }

}

class timeException extends RuntimeException {
    timeException(String s) {
        super(s);
    }
}

class EmailException extends RuntimeException {
    EmailException(String s) {
        super(s);
    }
}

class PhonenumException extends RuntimeException {
    PhonenumException(String s) {
        super(s);
    }
}
