package groupOfStudents;


import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    @Test
    void constructorTest() {
        //Null номер группы
        assertThrows(NullPointerException.class, () -> new Group(null));
        //Неподходящий формат номера группы
        assertThrows(IllegalArgumentException.class, () -> new Group("абра-кадабра"));
    }

    static Group IT = new Group("3530901/10001");
    static StudentDiary Basov = new StudentDiary("Басов Софрон Александрович");
    static StudentDiary Oleg = new StudentDiary("Олегов Олег Олегович");

    @BeforeAll
    static void setUp() {
        IT.addStudent(Basov);
        Basov.addMark("Физкультура", 5, "Бег");
    }

    @Test
    void toStringTest() {
        assertEquals("3530901/10001 :\n" +
                "Басов Софрон Александрович \n" +
                "Физкультура: \n" +
                "Бег 5; \n" + "Английский: \n" +
                "Монолог 3; \n" +
                "Физика: \n" +
                "Экзамен 4; " + "Контрольная работа 5; \n" +
                "Экономика:", IT.toString());
    }

    @Test
    void groupInfoTest() {
        //Проверка номера группы
        assertEquals("3530901/10001", IT.getGroupNumber());
    }

    @Test
    void addStudentTest() {
        //Проверка добавления и удаления студента
        IT.addStudent(Oleg);
        assertEquals((Map.of("Басов Софрон Александрович", 1, "Олегов Олег Олегович", 1).keySet()),
                IT.getStudents());
        IT.removeStudent("Олегов Олег Олегович");
        assertEquals(Set.of("Басов Софрон Александрович"), IT.getStudents());
    }

    @Test
    void illegalStudentAdd() {
        //Добавление null студента, имени и пустого имени соответственно
        assertThrows(NullPointerException.class, () -> IT.addStudent(null));
        assertThrows(NullPointerException.class, () -> IT.addStudent(new StudentDiary(null)));
        assertThrows(NullPointerException.class, () -> IT.addStudent(new StudentDiary("")));
    }

    @Test
    void illegalStudentRemove() {
        //Удаление студента, которого нет в группе
        assertThrows(NullPointerException.class, () -> IT.removeStudent(new StudentDiary("Ян").getName()));
    }

    @Test
    void journalGetterTest() {
        //Проверка геттера журнала
        assertEquals(Map.of("Басов Софрон Александрович", Basov), IT.getGroupJournal());
    }
    @Test
    void subjectMarksGetterTest(){
        Group OJ = new Group("2323231/13234");
        OJ.addStudent(new StudentDiary("Другов Иван Вячеславович"));
        OJ.addMark("Другов Иван Вячеславович",
                "Физика", 5,"Экзамен");
        //Проверка геттера
       assertEquals(Map.of("Экзамен",5), OJ.getSubjectMarks("Другов Иван Вячеславович", "Физика"));
       //У Ивана нет такого предмета
        assertThrows(IllegalArgumentException.class,()-> OJ.getSubjectMarks("Другов Иван Вячеславович",
                "Искусство шпагата"));
    }

    @Test
    void illegalStudentMarksGetterTest() {
        //
        assertThrows(IllegalArgumentException.class, () -> IT.getStudentMarks(null));
    }

    @Test
    void nonExistingSubject() {
        //Добавление оценки в несуществующий предмет, проверяем, что предмет автоматически добавится
        IT.addMark("Басов Софрон Александрович", "Физика", 5, "Контрольная работа");
        assertEquals(5, IT.getSubjectMark("Басов Софрон Александрович",
                "Физика", "Контрольная работа"));
    }

    @Test
    void illegalSubjectAdd() {
        assertThrows(NullPointerException.class, () -> IT.addSubject("Аль'Му-Алим", "Анжуманя"));
        assertThrows(NullPointerException.class, () -> IT.deleteSubject("Аль'Му-Алим", "Пресс качат"));
    }

    @Test
    void addMarkTest() {
        //Проверка добавления предмета и оценки
        assertThrows(IllegalArgumentException.class, () ->
                IT.addSubject("Басов Софрон Александрович", "Физкультура"));
        IT.addGroupSubject("Экономика");
        assertTrue(IT.getStudentMarks("Басов Софрон Александрович").containsKey("Экономика"));
        //Добавление оценок
        IT.addMark("Басов Софрон Александрович", "Английский", 3, "Монолог");
        IT.addMark("Басов Софрон Александрович", "Физика", 4, "Экзамен");
        assertEquals(Map.of("Экзамен", 4),
                IT.getSubjectMarks("Басов Софрон Александрович", "Физика"));
        assertEquals(3, IT.getSubjectMark("Басов Софрон Александрович",
                "Английский", "Монолог"));
    }


    @Test
    void illegalAdd() {
        //Добавление неправильной оценки (не по формату и null описания и оценка, соответственно)
        assertThrows(IllegalArgumentException.class, () -> IT.addMark("Басов Софрон Александрович",
                "Физика", 6, "Домашняя работа"));
        assertThrows(NullPointerException.class, () -> IT.addMark("Басов Софрон Александрович",
                "Физика", null, "Доп.Сессия"));
        assertThrows(NullPointerException.class, () -> IT.addMark("Басов Софрон Александрович",
                "Физика", 5, null));
    }

    @Test
    void legalEdit() {
        IT.addMark("Басов Софрон Александрович", "Английский", 4, "Монолог");
        //Легитимное изменение оценки
        IT.changeMark("Басов Софрон Александрович", "Английский", "Монолог", 5);
        assertEquals(Map.of("Монолог", 5), IT.getSubjectMarks("Басов Софрон Александрович",
                "Английский"));
    }

    @Test
    void illegalEdit() {
        //Неправильный формат новой оценки
        assertThrows(IllegalArgumentException.class, () -> IT.changeMark("Басов Софрон Александрович", "Физкультура",
                "Бег", 10));
        //Замена оценки за несуществующую работу
        assertThrows(IllegalArgumentException.class, () ->
                IT.changeMark("Басов Софрон Александрович", "Физкультура",
                        "Скакалка", 5));
        //Замена оценка за несуществующий предмет
        assertThrows(IllegalArgumentException.class, () ->
                IT.changeMark("Басов Софрон Александрович", "Катание на ежах",
                        "Забег", 5));
    }

    @Test
    void legalAndNonlegalMarkRemove() {
        //Удаление несуществующей оценки, оценки в несуществующем предмете
        assertThrows(IllegalArgumentException.class, () ->
                IT.deleteMark("Басов Софрон Александрович", "Физика", "Промежуточная аттестация"));
        assertThrows(IllegalArgumentException.class, () ->
                IT.deleteMark("Басов Софрон Александрович", "Астрономия", "Карта звёздного неба"));
        //Легитимное удаление оценки
        IT.addMark("Басов Софрон Александрович", "Физика", 5, "Контрольная работа");
        IT.addMark("Басов Софрон Александрович", "Физика", 5, "Экзамен");
        IT.deleteMark("Басов Софрон Александрович", "Физика", "Экзамен");
        assertEquals(Map.of("Контрольная работа", 5), IT.getSubjectMarks("Басов Софрон Александрович", "Физика"));
    }

    @Test
    void subjectRemove() {
        IT.addGroupSubject("ИЗО");
        //Удаление предмета легитимное и с ошибкой
        assertThrows(IllegalArgumentException.class, () ->
                IT.deleteSubject("Басов Софрон Александрович", "Художественная гимнастика"));
        IT.deleteGroupSubject("ИЗО"); //эх.. если бы
        assertFalse(IT.getStudentMarks("Басов Софрон Александрович").containsKey("ИЗО"));
    }

    @Test
    @AfterAll
    static void sortedTest(){
        IT.addStudent(new StudentDiary("Ахо Аркадий Петрович"));
        //Проверяем, что не по порядку
        assertEquals(Map.of("Басов Софрон Александрович", 2,"Ахо Аркадий Петрович",3).keySet(),
                IT.getStudents());
        IT.toSorted();
        //Проверяем, что мапа отсортировалась
        assertEquals(Map.of("Ахо Аркадий Петрович", 2,"Басов Софрон Александрович",3).keySet(),
                IT.getStudents());
    }
    @Test
    void equalsTest(){
        Group HUM = new Group ("1234567/12345");
        HUM.addStudent(new StudentDiary("Васин Алексей Александрович"));
        Group RUS = new Group("1234567/12345");
        RUS.addStudent(new StudentDiary("Васин Алексей Александрович"));
        HUM.addMark("Васин Алексей Александрович", "Физика", 5, "Экзамен");
        RUS.addMark("Васин Алексей Александрович", "Физика", 5, "Экзамен");
        //Сравнение одинаковых по составу групп и разных
        assertEquals(HUM, RUS);
        assertNotEquals(HUM, IT);

    }
}