package Task1;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GroupTest {
    @Test
    void constructorTest() {
        assertThrows(IllegalArgumentException.class, () -> {
            String ABC = null;
            Group IT = new Group(ABC);
        });
    }

    @Test
    void addChangeDeleteTest() {
        Group IT = new Group("3530901/10001");
        Student Basov = new Student("Басов", "Софрон", "Александрович");
        Student Nullik = null;
        Student Oleg = new Student("Олегов", "Олег", "Олегович");
        IT.addStudent(Basov);
        IT.addStudent(Oleg);
        //Проверка добавления и удаления студента
        assertEquals("Басов Софрон Александрович, Олегов Олег Олегович", IT.getStudents());
        IT.removeStudent(Oleg);
        assertEquals("Басов Софрон Александрович", IT.getStudents());
        assertThrows(IllegalArgumentException.class, () ->
                IT.addStudent(Nullik));

        //Добавление оценки в несуществующий предмет
        assertThrows(IllegalArgumentException.class, () -> Basov.addMark("Физика", List.of(3, 4, 5)));

        //Проверка добавления предмета и оценки
        Basov.addSubject("Физика");
        Basov.addMark("Физика", List.of(3, 4, 5));
        assertEquals(List.of(3, 4, 5), Basov.getSubjectMarks("Физика"));

        //Замена несуществующей оценки
        Basov.addSubject("Английский");
        Basov.addMark("Английский", List.of(5, 4, 5, 5));
        assertThrows(IllegalArgumentException.class, () ->
                Basov.changeMark("Английский", 1, 5));

        //Легитимное изменение оценки
        Basov.changeMark("Английский", 4, 5);
        assertEquals(List.of(5, 5, 5, 5), Basov.getSubjectMarks("Английский"));

        //Удаление несуществующей оценки, оценки в несуществующем предмете
        assertThrows(IllegalArgumentException.class, () ->
                Basov.deleteMark("Физика", 2));
        assertThrows(IllegalArgumentException.class, () ->
                Basov.deleteMark("Астрономия", 5));

        //Удаление предмета легитимное и с ошибкой
        assertThrows(IllegalArgumentException.class, () ->
                Basov.deleteSubject("Экономика"));
        Basov.deleteSubject("Физика"); //эх.. если бы
        assertFalse(Basov.getAllMarks().containsKey("Физика"));
    }

}