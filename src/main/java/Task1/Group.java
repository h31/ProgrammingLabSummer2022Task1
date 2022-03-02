package Task1;

import org.jetbrains.annotations.NotNull;


import java.util.*;

import static java.util.List.of;

public class Group {
    //Идея в том, что класс Группа, содержит внутри себя объекты класса Студенты
    private  ArrayList<String> studentArray;
    private Map<String, Student> studentMap;
    private final String groupNumber;
    private ArrayList<Student> groupList;

    Group(String groupNumber, ArrayList<String> studentArray) {
        if (groupNumber == null) throw new IllegalArgumentException("Номер группы не может быть null");
        if (studentArray== null) throw new IllegalArgumentException("Если вы задаете список студентов, он не может быть null");
        this.studentArray = studentArray;
        this.groupNumber = groupNumber;
    }

    public void addStudent(Student student) {
        if(student!= null) student.setGroup(this);
        else throw new IllegalArgumentException("Студент не может быть null");
    }

    void add(Student student) {
        groupList.add(student);
        studentArray.add(student.toFSP());
        studentMap.put(student.toFSP(), student);
    }

    Group(String groupNumber) {
        if (groupNumber == null) throw new IllegalArgumentException("Номер группы не может быть null");
        this.groupNumber = groupNumber;
        this.studentArray = new ArrayList<String>();
        this.studentMap = new HashMap<String, Student>();
        this.groupList = new ArrayList<Student>();
    }

    public String getStudents() {
        StringBuilder res = new StringBuilder();
        this.studentArray.forEach(student -> res.append(student + ", "));
        return res.substring(0,res.length() - 2 );
    }

    public void removeStudent(@NotNull Student student) {
        this.studentArray.remove(student.toFSP());
        studentMap.remove(student.toFSP());
        student.retired();
    }

    public Map getMap() {
        return this.studentMap;
    }

    public String name() {
        return this.groupNumber;
    }

    public String getStudentsMarks() {
        StringBuilder res = new StringBuilder();
        res.append(this.groupNumber + " :" + "\n");
        this.groupList.forEach(student ->
                res.append(student.toFSP() + " " + student.getAllMarks() + "\n"));
        return res.toString();
    }
}

