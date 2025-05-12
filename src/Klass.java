import java.util.List;

public class Klass {

    private String klassName;
    private List<Student> students;
    private Teacher mainTeacher;

    public Klass(String klassName, List<Student> students) {
        this.klassName = klassName;
        this.students = students;
    }

    public void addStudent(Student student) {
        students.add(student);
    }

    public int studentsCount(){
        return students.size();
    }

    @Override
    public String toString() {
        return klassName;
    }
}
