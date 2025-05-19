import javax.management.relation.Role;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

enum UserRole {
    Admin, Student, Teacher, Parent, ErrorRole
}

public class User {
    private String username;
    private String password;
    private UserRole role;

    public User(String username, String password, UserRole role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public static User findUserByUsername(String username) {
        File users = new File("/Users/yanidrenchev/Desktop/users.txt");
        Scanner fileReader;
        try {
            fileReader = new Scanner(users);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] elements = line.split("-");
            if (elements[0].equals(username)) {
                return new User(username, elements[1], parseRole(elements[2]));
            }
        }
        fileReader.close();
        return null;
    }

    public static boolean deleteUser(String username) {
        File users = new File("/Users/yanidrenchev/Desktop/users.txt");
        Scanner fileReader;
        try {
            fileReader = new Scanner(users);
        } catch (FileNotFoundException e) {
            return false;
        }
        File newFile = new File("/Users/yanidrenchev/Desktop/newUsers.txt");
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter(newFile);
        } catch (IOException e) {
            return false;
        }
        BufferedWriter bf = new BufferedWriter(fileWriter);
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] elements = line.split("-");
            if (elements[0].equals(username)) {
                continue;
            }
            try {
                bf.write(line + "\n");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            bf.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if (users.delete()) {
            if (newFile.renameTo(users)) {
                fileReader.close();
                return true;
            }
        }
        fileReader.close();
        return true;
    }

    public static List<Student> loadStudents() {
        File users = new File("/Users/yanidrenchev/Desktop/users.txt");
        Scanner fileReader;
        try {
            fileReader = new Scanner(users);
        } catch (FileNotFoundException e) {
            return null;
        }
        List<Student> students = new ArrayList<>();
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] elements = line.split("-");
            if (!Objects.equals(elements[2], "Student")) {
                continue;
            }
            Student newStudent = new Student(elements[0], elements[1]);
            students.add(newStudent);
            newStudent.loadGrades();
        }
        return students;
    }

    public static List<Teacher> loadTeachers() {
        File users = new File("/Users/yanidrenchev/Desktop/Teachers.txt");
        Scanner fileReader;
        try {
            fileReader = new Scanner(users);
        } catch (FileNotFoundException e) {
            return null;
        }
        List<Teacher> teachers = new ArrayList<>();
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] elements = line.split(" - ");
            User user = User.findUserByUsername(elements[0]);
            if (user == null) {
                continue;
            }
            Teacher newTeacher = new Teacher(user.getUsername(), user.getPassword(), user.getRole(), elements[1], elements[2]);
            teachers.add(newTeacher);
        }
        return teachers;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public static User checkUser(User user) {
        File users = new File("/Users/yanidrenchev/Desktop/users.txt");
        Scanner fileReader;
        try {
            fileReader = new Scanner(users);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        while (fileReader.hasNextLine()) {
            String line = fileReader.nextLine();
            String[] elements = line.split("-");
            if (elements[0].equals(user.getUsername()) && elements[1].equals(user.getPassword())) {
                User userToReturn = new User(user.getUsername(), user.getPassword(), parseRole(elements[2]));
                return userToReturn;
            }
        }
        fileReader.close();
        return null;
    }

    public static boolean addUser(User user) {
        FileWriter fileWriter;
        try {
            fileWriter = new FileWriter("/Users/yanidrenchev/Desktop/users.txt", true);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(user.toString() + "\n");
            bufferedWriter.close();
            fileWriter.close();
        } catch (IOException e) {
            return false;
        }
        return true;
    }

    public static boolean validateRole(String role) {
        if (role.equals("Student") || role.equals("Teacher") || role.equals("Parent") || role.equals("Admin")) {
            return true;
        }
        return false;
    }

    public static UserRole parseRole(String role) {
        return switch (role) {
            case "Student" -> UserRole.Student;
            case "Teacher" -> UserRole.Teacher;
            case "Parent" -> UserRole.Parent;
            case "Admin" -> UserRole.Admin;
            default -> UserRole.ErrorRole;
        };
    }

    @Override
    public String toString() {
        return username + '-' + password + '-' + role;
    }
}
