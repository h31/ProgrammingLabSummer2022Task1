package groupOfStudents;

import org.junit.jupiter.api.Test;

import java.util.List;

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
        StudentMarks Basov = new StudentMarks("Басов Софрон Александрович");
        StudentMarks Nullik = null;
        StudentMarks Oleg = new StudentMarks("Олегов Олег Олегович");
        IT.addStudent(Basov);
        IT.addStudent(Oleg);
        //Проверка номера группы
        assertEquals("3530901/10001",IT.getGroupNumber());
        //Проверка добавления и удаления студента
        assertEquals("Басов Софрон Александрович, Олегов Олег Олегович", IT.getStudents());
        IT.removeStudent(Oleg);
        assertEquals("Басов Софрон Александрович", IT.getStudents());
        assertThrows(IllegalArgumentException.class, () ->
                IT.addStudent(Nullik));

        //Добавление оценки в несуществующий предмет
        assertThrows(IllegalArgumentException.class, () -> IT.addMarks("Басов Софрон Александрович","Физика", List.of(3, 4, 5)));

        //Проверка добавления предмета и оценки
        IT.addSubject("Басов Софрон Александрович","Физика");
        IT.addMarks("Басов Софрон Александрович","Физика", List.of(3, 4));
        IT.addMark("Басов Софрон Александрович","Физика",5);
        assertEquals(List.of(3, 4, 5), IT.getSubjectMarks("Басов Софрон Александрович","Физика"));

        //Замена несуществующей оценки
        IT.addGroupSubject("Английский");
        IT.addMarks("Басов Софрон Александрович","Английский", List.of(5, 4, 5, 5));
        assertThrows(IllegalArgumentException.class, () ->
                IT.changeMark("Басов Софрон Александрович","Английский", 1, 5));

        //Легитимное изменение оценки
        IT.changeMark("Басов Софрон Александрович","Английский", 4, 5);
        assertEquals(List.of(5, 5, 5, 5), IT.getSubjectMarks("Басов Софрон Александрович","Английский"));

        //Удаление несуществующей оценки, оценки в несуществующем предмете
        assertThrows(IllegalArgumentException.class, () ->
                IT.deleteMark("Басов Софрон Александрович","Физика", 2));
        assertThrows(IllegalArgumentException.class, () ->
                IT.deleteMark("Басов Софрон Александрович","Астрономия", 5));

        //Удаление предмета легитимное и с ошибкой
        assertThrows(IllegalArgumentException.class, () ->
                IT.deleteSubject("Басов Софрон Александрович","Экономика"));
        IT.deleteGroupSubject("Физика"); //эх.. если бы
        assertFalse(IT.getAllMarks("Басов Софрон Александрович").containsKey("Физика"));
    }

}