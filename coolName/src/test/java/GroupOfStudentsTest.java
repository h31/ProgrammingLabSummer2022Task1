import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupOfStudentsTest {


    private GroupOfStudents testGroup() {
        GroupOfStudents exampleGroup = new GroupOfStudents();
        exampleGroup.addStudent("Макаров Иван Дмитриевич", "Биология 5, Фармацевтика 3, Политология 1");
        exampleGroup.addStudent("Мартынов Аскольд Никитевич", "Биология 5, Фармацевтика 3");
        exampleGroup.addStudent("Алексеев Юлий Станиславович", "Фармацевтика 3, Политология 1");
        exampleGroup.addStudent("Соловьёв Велор Владленович", "Фармацевтика 3, Философия");
        return exampleGroup;
    }

    @Test
    void addStudent() {
        GroupOfStudents exampleGroup = testGroup();
        exampleGroup.addStudent("Веселов Венедикт Донатович", "Биология 5, Философия 4, Геометрия 2");
        assertEquals("Биология 5, Философия 4, Геометрия 2",
                exampleGroup.search("Веселов Венедикт Донатович"));
        assertThrows(IllegalArgumentException.class,
                () -> exampleGroup.addStudent("Веселов Венедикт Донатович", "Биология 5, Философия 4, Геометрия 2"));
        assertThrows(IllegalArgumentException.class,
                () -> exampleGroup.addStudent("", "Биология 5, Фармацевтика 3, Политология 1"));
    }

    @Test
    void deleteStudent() {
        GroupOfStudents exampleGroup = testGroup();
        exampleGroup.deleteStudent("Макаров Иван Дмитриевич");
        assertEquals(3, exampleGroup.size());
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.deleteStudent(""));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.deleteStudent("Павлов Сергей Кимович"));
    }

    @Test
    void addMark() {
        GroupOfStudents exampleGroup = testGroup();
        exampleGroup.addStudent("Павлов Сергей Кимович", "Биология 5, Геометрия, Философия 4");
        exampleGroup.addMark("Соловьёв Велор Владленович", "Философия", 4);
        exampleGroup.addMark("Павлов Сергей Кимович", "Геометрия", 5);
        assertEquals("Биология 5, Геометрия 5, Философия 4", exampleGroup.search("Павлов Сергей Кимович"));
        assertEquals("Фармацевтика 3, Философия 4", exampleGroup.search("Соловьёв Велор Владленович"));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.addMark("", "Геометрия", 5));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.addMark("Павлов Сергей Кимович", "", 5));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.addMark("Павлов Сергей Кимович", "Геометрия", -1));
    }

    @Test
    void deleteMark() {
        GroupOfStudents exampleGroup = testGroup();
        exampleGroup.addStudent("Павлов Сергей Кимович", "Биология 5, Геометрия 3, Философия 4");
        exampleGroup.deleteMark("Павлов Сергей Кимович", "Философия");
        exampleGroup.deleteMark("Соловьёв Велор Владленович", "Фармацевтика");
        assertEquals("Биология 5, Геометрия 3, Философия", exampleGroup.search("Павлов Сергей Кимович"));
        assertEquals("Фармацевтика, Философия", exampleGroup.search("Соловьёв Велор Владленович"));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.deleteMark("", "Биология"));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.deleteMark("Соловьёв Велор Владленович", ""));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.deleteMark("Соловьёв Велор Владленович", "Политология"));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.deleteMark("Красильников Антон Геннадиевич", "Политология"));
    }

    @Test
    void changeMark() {
        GroupOfStudents exampleGroup = testGroup();
        exampleGroup.changeMark("Макаров Иван Дмитриевич", "Биология", 2);
        exampleGroup.changeMark("Мартынов Аскольд Никитевич", "Биология", 3);
        exampleGroup.changeMark("Алексеев Юлий Станиславович", "Политология", 5);
        assertEquals("Биология 2, Фармацевтика 3, Политология 1", exampleGroup.search("Макаров Иван Дмитриевич"));
        assertEquals("Биология 3, Фармацевтика 3", exampleGroup.search("Мартынов Аскольд Никитевич"));
        assertEquals("Фармацевтика 3, Политология 5", exampleGroup.search("Алексеев Юлий Станиславович"));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.changeMark("", "Политология", 4));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.changeMark("Алексеев Юлий Станиславович", "", 4));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.changeMark("Красильников Антон Геннадиевич", "Фармацевтика", 4));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.changeMark("Алексеев Юлий Станиславович", "Фармацевтика", -4));
    }

    @Test
    void addSubject() {
        GroupOfStudents exampleGroup = testGroup();
        exampleGroup.addStudent("Крюков Климент Игоревич", "");
        exampleGroup.addSubject("Астрономия");
        assertEquals("Биология 5, Фармацевтика 3, Политология 1, Астрономия", exampleGroup.search("Макаров Иван Дмитриевич"));
        assertEquals("Биология 5, Фармацевтика 3, Астрономия", exampleGroup.search("Мартынов Аскольд Никитевич"));
        assertEquals("Фармацевтика 3, Политология 1, Астрономия", exampleGroup.search("Алексеев Юлий Станиславович"));
        assertEquals("Фармацевтика 3, Философия, Астрономия", exampleGroup.search("Соловьёв Велор Владленович"));
        assertEquals("Астрономия", exampleGroup.search("Крюков Климент Игоревич"));
        assertThrows(IllegalArgumentException.class, () -> exampleGroup.addSubject(""));
    }
}