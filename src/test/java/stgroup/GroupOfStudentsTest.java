package stgroup;

import java.util.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class StGroupTest {

    Group group;

    //выполняется перед каждым тестом
    @BeforeEach
    public void prepareData(){
        group = new Group(10001,
                new ArrayList<>(Arrays.asList ("Шаров Антон Кириллович","Мочалов Артем Дмитриевич","Тищенко Анастасия Артемовна")),
                new ArrayList<>(Arrays.asList ("Алгебра","Физика","Геометрия","Русский язык")),
                new ArrayList<>(Arrays.asList (2,5,3,6,2,4,4,4,4,5,2,5)));
    }

    @Test
    public void ManagedToAddAStudent() {
        String testName = "Шалак Егор Алексеевич";
        assertTrue(group.addStudent(testName));
        List<Student> students = group.getGroup();
        assertEquals(testName, students.get(students.size()-1).studentName);
    }

    @Test
    public void FailedToAddAStudent() {
        assertFalse(group.addStudent(null));
        String testName = "Шаров Антон Кириллович";
        assertFalse(group.addStudent(testName));
        List<Student> students = group.getGroup();
        assertEquals("Тищенко Анастасия Артемовна", students.get(students.size()-1).studentName);
    }

    @Test
    public void ManagedToDeleteAStudent() {
        String nameOfFirstStudent = "Шаров Антон Кириллович";
        assertTrue(group.deleteStudent(nameOfFirstStudent));
        List<Student> students = group.getGroup();
        assertEquals("Мочалов Артем Дмитриевич", students.get(0).studentName);
    }

    @Test
    public void FailedToDeleteStudent(){
        assertFalse(group.deleteStudent(null));
        String testName = "Шалак Егор Алексеевич";
        assertFalse(group.deleteStudent(testName));
    }

    @Test
    public void ManagedToAddASubject() {
        String nameOfSubject = "обж";
        assertTrue(group.addSubject(nameOfSubject));
        List<Student> students = group.getGroup();
        assertTrue(students.get(0).rating.containsKey(nameOfSubject));
    }

    @Test
    public void failedToAddASubject() {
        assertFalse(group.addSubject(null));
        String nameOfSubject = "Алгебра";
        assertFalse(group.addSubject(nameOfSubject));
    }

    @Test
    public void ManagedToDeleteASubject () {
        String nameOfSubject = "Алгебра";
        assertTrue(group.deleteSubject(nameOfSubject));
        List<Student> students = group.getGroup();
        assertFalse(students.get(0).rating.containsKey(nameOfSubject));
    }

    @Test
    public void FailedToDeleteASubject (){
        assertFalse(group.deleteSubject(null));
        String nameOfSubject = "обж";
        assertFalse(group.deleteSubject(nameOfSubject));
    }

    @Test
    public void ManagedToDeleteAMark() {
        List<Student> students = group.getGroup();
        Student student = students.get(0);
        assertTrue(student.deleteMark("Алгебра"));
    }

    @Test
    public void FailedToDeleteAMark() {
        String nameOfUnknownSubject = "обж";
        List<Student> students = group.getGroup();
        Student student = students.get(0);
        assertFalse(student.deleteMark(nameOfUnknownSubject));
    }

    @Test
    public void ManageToAddAMark(){
        assertTrue(group.addStudent("Илья"));
        List<Student> students = group.getGroup();
        Student student = students.get(students.size()-1);
        assertTrue(student.addMark(5,  "Алгебра"));
    }

    @Test
    public void FailedToAddAMark(){
        List<Student> students = group.getGroup();
        Student student = students.get(0);
        assertFalse(student.addMark(5, "Алгебра"));
        assertFalse(student.addMark(10, "Алгебра"));
    }

    @Test
    public void ManagedToChangeMark() {
        List<Student> students = group.getGroup();
        Student student = students.get(0);
        assertTrue(student.changeMark(5,  "Алгебра"));
    }

    @Test
    public void FailedToChangeMark() {
        assertTrue(group.addStudent("Илья"));
        List<Student> students = group.getGroup();
        Student student = students.get(students.size()-1);
        assertFalse(student.changeMark(5,  "Алгебра"));
        assertFalse(student.changeMark(10,  "Алгебра"));
    }
}