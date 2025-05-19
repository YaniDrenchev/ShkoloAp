import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Student extends User
{

    private String name;
    private int age;
    private String gender;
    private String address;
    private String email;
    private String phone;
    private int numberInKlass;
    private Klass klass;
    private Map<Subject, List<Double>> gradesBySubject = new HashMap<>();

    public Student(String name, int age,
                   String gender, String address,
                   String email, String phone,
                   int numberInKlass, String username, String password, Klass klass) {
        super(username, password, UserRole.Student);
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.numberInKlass = numberInKlass;
        this.klass = klass;
    }

    public Student (String username, String password) {
        super(username, password, UserRole.Student);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getNumberInKlass() {
        return numberInKlass;
    }

    public void setNumberInKlass(int numberInKlass) {
        this.numberInKlass = numberInKlass;
    }
    @Override
    public String toString() {
        return name + " Клас: " + klass + "Номер: " + numberInKlass;
    }

    public Klass getKlass() {
        return klass;
    }
    public void setKlass(Klass klass) {
        this.klass = klass;
    }
    public void addGrade(Subject subject, double grade) {
        gradesBySubject.computeIfAbsent(subject, k -> new ArrayList<>()).add(grade);
    }

    public List<Double> getGrades(Subject subject) {
        return gradesBySubject.getOrDefault(subject, Collections.emptyList());
    }

    public Map<Subject, List<Double>> getAllGrades() {
        return gradesBySubject;
    }

    public void loadGrades() {
        String fileName = "/Users/yanidrenchev/Desktop/" + this.getUsername() + ".txt";
        File file = new File(fileName);
        Scanner scanner;
        try{
            scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.trim().isEmpty()) continue;
                String[] parts = line.split(" - ");
                // [userNameOfTeacher, Subj, Grades]
                if (parts.length != 3) continue;

                Teacher teacher = null;
                for (Teacher teach : Main.teachers){
                    if (teach.getUsername().equals(parts[0])){
                        teacher = teach;
                    }
                }
                String subjectName = parts[1];
                String[] gradeStrings = parts[2].split(",");
                Subject subject = new Subject(subjectName, teacher);
                for (String gradeStr : gradeStrings) {
                    try {
                        double grade = Double.parseDouble(gradeStr.trim());
                        addGrade(subject, grade);
                    } catch (NumberFormatException e) {
                        // Invalid grade, skip
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Grade file not found for student: " + fileName);
        }
    }
}
