import java.io.File;
import java.util.Scanner;

public class Teacher extends User{

    private String firstName;
    private String lastName;

    public Teacher(String username, String password, UserRole role, String firstName, String lastName) {
        super(username, password, UserRole.Teacher);
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    private static String findTeacherInFile (User user){
        String fileName = "/Users/yanidrenchev/Desktop/Teachers.txt";
        File file = new File(fileName);
        Scanner scanner;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                line = line.trim();
                String [] parts = line.split(" - ");
                if (parts[0].equals(user.getUsername())){
                    return parts[1] + " " + parts[2];
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static Teacher findByUsername(String username) {
        User user = User.findUserByUsername(username);
        if (user == null) {
            return null;
        }
        if (user.getRole() != UserRole.Teacher) {
            return null;
        }
        String firstAndLastName = findTeacherInFile(user);
        if (firstAndLastName.isEmpty()) {
            return null;
        }
        String [] parts = firstAndLastName.split(" ");
        return new Teacher(user.getUsername(), user.getPassword(), UserRole.Teacher, parts[0], parts[1]);
    }

    @Override public String toString() {
        return firstName + " " + lastName;
    }

}
