package stgroup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupOfStudentsTest {
    Group group;
    Marks marksOfFirstStudent;

    //выполняется перед каждым тестом
    @BeforeEach
    public void prepareData(){
        group = new Group(10001);
        group.addSubject("Алгебра");
        group.addSubject("Физика");
        group.addStudent("Шаров Антон Кириллович");
        group.getGroup().get("Шаров Антон Кириллович").getMarks().put("Алгебра", 4);
    }

    @Test
    public void ManagedToAddAStudent() {
        assertTrue(group.addStudent("Зубенко Михаил Петрович"));
        assertTrue(group.getGroup().containsKey("Зубенко Михаил Петрович"));
    }

    @Test
    public void FailedToAddAStudent() {
        assertFalse(group.addStudent(null));
        assertFalse(group.addStudent("Шаров Антон Кириллович"));
    }

    @Test
    public void ManagedToDeleteAStudent() {
        assertTrue(group.deleteStudent("Шаров Антон Кириллович"));
        assertFalse(group.getGroup().containsKey("Шаров Антон Кириллович"));
    }

    @Test
    public void FailedToDeleteStudent(){
        assertFalse(group.deleteStudent(null));
        assertFalse(group.deleteStudent("Зубенко Михаил Петрович"));
    }

    @Test
    public void ManagedToAddASubject() {
        assertTrue(group.addSubject("обж"));
        assertTrue(group.getSubjects().contains("обж"));
    }

    @Test
    public void failedToAddASubject() {
        assertFalse(group.addSubject(null));
        assertFalse(group.addSubject("Алгебра"));
    }

    @Test
    public void ManagedToDeleteASubject () {
        assertTrue(group.deleteSubject("Алгебра"));
        assertFalse(group.getSubjects().contains("Алгебра"));
    }

    @Test
    public void FailedToDeleteASubject (){
        assertFalse(group.deleteSubject(null));
        assertFalse(group.deleteSubject("обж"));
    }

    @Test
    public void ManagedToDeleteAMark() {
        marksOfFirstStudent = group.getGroup().get("Шаров Антон Кириллович");
        assertTrue(marksOfFirstStudent.deleteMark("Алгебра"));
        assertNull(marksOfFirstStudent.getMarks().get("Алгебра"));
    }

    @Test
    public void FailedToDeleteAMark() {
        marksOfFirstStudent = group.getGroup().get("Шаров Антон Кириллович");
        assertFalse(marksOfFirstStudent.deleteMark("обж"));
        assertEquals(4, marksOfFirstStudent.getMarks().get("Алгебра"));
    }

    @Test
    public void ManageToAddAMark(){
        marksOfFirstStudent = group.getGroup().get("Шаров Антон Кириллович");
        assertTrue(marksOfFirstStudent.addMark("Физика",5));
        assertEquals(5, marksOfFirstStudent.getMarks().get("Физика"));
    }

    @Test
    public void FailedToAddAMark(){
        marksOfFirstStudent = group.getGroup().get("Шаров Антон Кириллович");
        assertFalse(marksOfFirstStudent.addMark("Алгебра", 5));
        assertFalse(marksOfFirstStudent.addMark("Физика",10));
        assertEquals(4, marksOfFirstStudent.getMarks().get("Алгебра"));
        assertNull(marksOfFirstStudent.getMarks().get("Физика"));
    }

    @Test
    public void ManagedToChangeMark() {
        marksOfFirstStudent = group.getGroup().get("Шаров Антон Кириллович");
        assertTrue(marksOfFirstStudent.changeMark("Алгебра",5));
        assertEquals(5, marksOfFirstStudent.getMarks().get("Алгебра"));
    }

    @Test
    public void FailedToChangeMark() {
        marksOfFirstStudent = group.getGroup().get("Шаров Антон Кириллович");
        assertFalse(marksOfFirstStudent.changeMark("Физика",5));
        assertFalse(marksOfFirstStudent.changeMark("Алгебра",10));
        assertEquals(4, marksOfFirstStudent.getMarks().get("Алгебра"));
        assertNull(marksOfFirstStudent.getMarks().get("Физика"));
    }

    @Test
    public void EqualsTrue(){
        Group group1 = new Group(1);
        Group group2 = new Group(1);
        assertTrue(group1.addSubject("Алгебра"));
        assertTrue(group2.addSubject("Алгебра"));
        assertEquals(group1,group2);
        assertTrue(group1.addStudent("Зубенко Михаил Петрович"));
        assertTrue(group2.addStudent("Зубенко Михаил Петрович"));
        assertEquals(group1, group2);
        assertTrue(group1.getGroup().get("Зубенко Михаил Петрович").addMark("Алгебра", 3));
        assertTrue(group2.getGroup().get("Зубенко Михаил Петрович").addMark("Алгебра", 3));
        assertEquals(group1, group2);
    }

    @Test
    public void EqualsFalse(){
        Group group1 = new Group(1);
        Group group2 = new Group(2);
        assertTrue(group1.addSubject("Алгебра"));
        assertTrue(group2.addSubject("Физика"));
        assertNotEquals(group1,group2);
        assertTrue(group1.addStudent("Зубенко Михаил Петрович"));
        assertTrue(group2.addStudent("Олегов Олег Олегович"));
        assertNotEquals(group1, group2);
        assertTrue(group1.getGroup().get("Зубенко Михаил Петрович").addMark("Алгебра", 3));
        assertTrue(group2.getGroup().get("Олегов Олег Олегович").addMark("Физика", 5));
        assertNotEquals(group1, group2);
    }

}