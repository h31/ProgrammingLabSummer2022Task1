package stgroup;
import java.util.*;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
class GroupOfStudentsTest {
    @Test
    void addStudent() { Group group = new Group(3530901/10001, new ArrayList<>(Arrays.asList ("Шаров Антон Кириллович","Мочалов Артем Дмитриевич","Тищенко Анастасия Артемовна")),
            new ArrayList<>(Arrays.asList ("Алгебра","Физика","Геометрия","Русский язык")), new ArrayList<>(Arrays.asList (2,5,3,6,2,4,4,4,4,5,2,5,4,2,4,5,5,5,3,1,3,1,4)));
        assertEquals(new ArrayList<>(Arrays.asList ("Шаров Антон Кириллович","Мочалов Артем Дмитриевич","Тищенко Анастасия Артемовна","Шалак Егор Алексеевич")),
                group.addStudent("Шалак Егор Алексеевич")); }
    @Test
    void deleteStudent() { Group group = new Group(3530901/10001, new ArrayList<>(Arrays.asList ("Шаров Антон Кириллович","Мочалов Артем Дмитриевич","Тищенко Анастасия Артемовна")),
            new ArrayList<>(Arrays.asList ("Алгебра","Физика","Геометрия","Русский язык")), new ArrayList<>(Arrays.asList (1,1,1,2,2,2,3,3,3,4,4,4,5,5,5,4,4,4,3,3,3,2,2,2,1,1,1)));
        assertEquals(new ArrayList<>(Arrays.asList ("Шаров Антон Кириллович","Мочалов Артем Дмитриевич")),group.deleteStudent("Тищенко Анастасия Артемовна")); }
    @Test
    void addSubject() { Group group = new Group(3530901/10001, new ArrayList<>(Arrays.asList ("Шаров Антон Кириллович","Мочалов Артем Дмитриевич","Тищенко Анастасия Артемовна")),
            new ArrayList<>(Arrays.asList ("Алгебра","Физика","Геометрия","Русский язык")), new ArrayList<>(Arrays.asList (4,5,1,2,2,4,1,3,5,2,5,4,1,2,4,2,1,4,2,3,1,4,3,1,5,2)));
        assertEquals(new ArrayList<>(Arrays.asList ("Алгебра","Физика","Геометрия","Русский язык","Литература")),group.addSubject("Литература")); }
    @Test
    void delSubject() { Group group = new Group(3530901/10001, new ArrayList<>(Arrays.asList ("Шаров Антон Кириллович","Мочалов Артем Дмитриевич","Тищенко Анастасия Артемовна")),
            new ArrayList<>(Arrays.asList ("Алгебра","Физика","Геометрия","Русский язык")), new ArrayList<>(Arrays.asList (2,2,3,3,4,2,5,3,2,4,3,5,3,2,4,5,2,3,5,2,4,4,5,5)));
        assertEquals(new ArrayList<>(Arrays.asList ("Алгебра","Физика","Русский язык")),group.deleteSubject("Геометрия")); }
    @Test
    void delMark() { Group group = new Group(3530901/10001, new ArrayList<>(Arrays.asList ("Шаров Антон Кириллович","Мочалов Артем Дмитриевич","Тищенко Анастасия Артемовна")),
            new ArrayList<>(Arrays.asList ("Алгебра","Физика","Геометрия","Русский язык")), new ArrayList<>(Arrays.asList (3,2,1,3,5,4,1,1,2,5,1,3,5,1,2)));
        assertEquals(new ArrayList<>(Arrays.asList (5,null,1,1)),
                group.deleteMark("Мочалов Артем Дмитриевич","Физика"));
        assertEquals(new ArrayList<>(Arrays.asList (3,2,1,null)),
                group.deleteMark("Шаров Антон Кириллович","Русский язык"));
        assertEquals(new ArrayList<>(Arrays.asList (2,5,null,3)),
                group.deleteMark("Тищенко Анастасия Артемовна","Геометрия")); }
    @Test
    void changeMark() { Group group = new Group(3530901/10001, new ArrayList<>(Arrays.asList ("Шаров Антон Кириллович","Мочалов Артем Дмитриевич","Тищенко Анастасия Артемовна")),
            new ArrayList<>(Arrays.asList ("Алгебра","Физика","Геометрия","Русский язык")), new ArrayList<>(Arrays.asList (3,2,1,3,5,4,3,2,3,4,5,4,5,4,3,5,4,3,5,4,3,5,4)));
        assertEquals(new ArrayList<>(Arrays.asList (5,3,3,2)),
                group.changeMark(3,"Мочалов Артем Дмитриевич","Физика"));
        assertEquals(new ArrayList<>(Arrays.asList (3,2,1,5)),
                group.changeMark(5,"Шаров Антон Кириллович","Русский язык"));
        assertEquals(new ArrayList<>(Arrays.asList (3,4,2,4)),
                group.changeMark(2,"Тищенко Анастасия Артемовна","Геометрия")); }
}