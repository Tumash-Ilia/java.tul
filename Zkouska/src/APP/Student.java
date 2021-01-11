package APP;

import java.util.ArrayList;

public class Student {

    String name;
    String surname;
    int id;
    static ArrayList<Student> students = new ArrayList<>();
    public Student() {
    }

    public Student(String name, String surname, int id) {
        this.name = name;
        this.surname = surname;
        this.id = id;
        students.add(this);
    }

    public void vypis(){
        for (Student student: students) {
            System.out.println(student.name + " " + student.surname + " " + student.id);
        }
    }

    public static ArrayList<Student> getStudents() {
        return students;
    }

    public static void main(String[] args) {
        Student st  = new Student();
        Student s = new Student("sss","sssss",2);
        s.vypis();

    }

}
