import java.security.KeyPair;
import java.util.*;

enum AdminCommand {
    AddUser, DeleteUser, AddGrade, ErrorCommand, ListGrades
}

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static User currentUser = null;
    static List<Student> students = User.loadStudents();


    private static AdminCommand parseCommand(String command) {
        String commandName = command.toLowerCase();
        return switch (commandName) {
            case "adduser" -> AdminCommand.AddUser;
            case "deleteuser" -> AdminCommand.DeleteUser;
            case "addgrade" -> AdminCommand.AddGrade;
            case "listgrades" -> AdminCommand.ListGrades;
            default -> AdminCommand.ErrorCommand;
        };
    }

    public static void adminCommands() {
        while (true) {
            String line = scanner.nextLine();
            AdminCommand cmd = parseCommand(line);
            if (cmd == AdminCommand.ErrorCommand) {
                System.out.println("Invalid command");
                continue;
            }
            switch (cmd) {
                case AddUser:
                    System.out.println("Enter user name");
                    String userName = scanner.nextLine();
                    System.out.println("Enter password");
                    String password = scanner.nextLine();
                    System.out.println("Enter Role");
                    String role = scanner.nextLine();
                    while (!User.validateRole(role)) {
                        System.out.println("Enter Valid Role");
                        role = scanner.nextLine();
                    }
                    UserRole role2 = User.parseRole(role);
                    User userToAdd = new User(userName, password, role2);
                    User.addUser(userToAdd);
                    if (role2 == UserRole.Student) {
                        students.add(new Student(userName, password));
                    }
                    System.out.println("User " + userToAdd.getUsername() + " has been added");
                    break;
                case DeleteUser:
                    System.out.println("Enter user name");
                    String username = scanner.nextLine();
                    User user = User.findUserByUsername(username);
                    if (user == null) {
                        System.out.println("User not found");
                        break;
                    }
                    if (User.deleteUser(username)) {
                        System.out.println("User " + username + " has been deleted");
                        students.remove(user);
                    } else {
                        System.out.println("User " + username + " has not been deleted");
                    }

                    break;
                case AddGrade:
                    System.out.println("Enter subject name");
                    String subject = scanner.nextLine();
                    System.out.println("Enter grade");
                    String grade = scanner.nextLine();
                    double gradeDouble = Double.parseDouble(grade);
                    System.out.println("Enter username");
                    String username2 = scanner.nextLine();
                    User user2 = User.findUserByUsername(username2);
                    if (user2.getRole() != UserRole.Student) {
                        System.out.println("Not a student");
                        break;
                    } else {
                        Teacher teacher = new Teacher();
                        Subject subject1 = new Subject(subject, teacher);
                        for (Student student : students) {
                            if (student.getUsername().equals(username2)) {
                                student.addGrade(subject1, gradeDouble);
                            }
                        }
                        System.out.println("Grade for " + username2 + " has been added");
                    }
                    break;
                case ListGrades:
                    System.out.println("Enter user name");
                    String userName2 = scanner.nextLine();
                    User user3 = User.findUserByUsername(userName2);
                    if (user3 == null) {
                        System.out.println("User not found");
                        break;
                    }
                    for (Student student : students) {
                        if (student.getUsername().equals(userName2)) {
                            Map<Subject, List<Double>> map;
                            // Key - > Value
                            // Subject -> List<Double>

                            // map.keySet() - returns the SEt of keys

                            map = student.getAllGrades();
                            Set<Subject> subjects = map.keySet();
                            for (Subject subject1 : subjects) {
                                List<Double> grades = map.get(subject1);
                                System.out.println("Grades for " + subject1 + " are: ");
                                for (Double grade2 : grades) {
                                    System.out.println(grade2);
                                }
                            }
                        }
                    }
                    break;

            }
        }
    }

    public static void main(String[] args) {
        User user;
        while (true) {
            System.out.println("Enter username");
            String userName = scanner.nextLine();
            System.out.println("Enter password");
            String password = scanner.nextLine();
            User userToCheck = new User(userName, password, null);
            user = User.checkUser(userToCheck);
            if (user != null) {
                System.out.println("User found");
                currentUser = user;
                break;
            }
            System.out.println("User not found try again");
        }
        switch (user.getRole()) {
            case UserRole.Admin:
                adminCommands();
                break;
        }
    }
}