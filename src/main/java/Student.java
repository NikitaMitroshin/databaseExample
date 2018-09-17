public class Student {

    private int id;
    private String grouppa;
    private String faculty;

    public Student(int id, String grouppa, String Faculty) {
        this.id = id;
        this.grouppa = grouppa;
        this.faculty = Faculty;
    }

    private Student() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getGrouppa() {
        return grouppa;
    }

    public void setGrouppa(String grouppa) {
        this.grouppa = grouppa;
    }

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
}
