package groupOfStudents;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        StudentMarks Dasha = new StudentMarks("Глухая Кукуха Тетерьевна");
        StudentMarks Natasha = new StudentMarks("Кондратьева Наталья Васильевна");
        Group IT = new Group("3530901/10001");
        Group HUM = new Group("353092/54043");
        StudentMarks Basov = new StudentMarks("Басов Софрон Александрович");
        Basov.addSubject("Физика");
        Basov.addSubject("Математика");
        Basov.addMarks("Физика", List.of(3,5,4,5));
        Basov.addMarks("Математика", List.of(5,4,5,3));
        System.out.println(Basov.getAllMarks());
        Dasha.addSubject("Математика");
        Natasha.addSubject("Английский");
        Dasha.addMarks("Математика", List.of(2,4,5));
        Natasha.addMark("Английский", 5);
        IT.addStudent(Basov);
        IT.addStudent(Dasha);
        System.out.println(IT.getGroupMarks());
    }
}
