import java.util.List;

public class Subject {

    private String name;
    private Teacher teacher;
    List<Klass> klasses;

    public Subject(String name, Teacher teacher, List<Klass> klasses) {
        this.name = name;
        this.teacher = teacher;
        this.klasses = klasses;
    }

    public Subject(String name, Teacher teacher) {
        this.name = name;
        this.teacher = teacher;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public List<Klass> getKlasses() {
        return klasses;
    }

    public void setKlasses(List<Klass> klasses) {
        this.klasses = klasses;
    }

    @Override
    public String toString() {
        return name;
    }
}
