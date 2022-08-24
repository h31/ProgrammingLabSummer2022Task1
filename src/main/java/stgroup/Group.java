package stgroup;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Group {

    private final List<Student> group = new ArrayList<>();

    //конструктор
    public Group(int number, ArrayList<String> student, ArrayList<String> subjects, ArrayList<Integer> marks) {
        for (int i = 0; i < student.size(); i++) {
            //новый лист с оценками одного ученика
            List<Integer> mark = new ArrayList<>();
            for (int j = 0; j < subjects.size(); j++) {
                mark.add(marks.get(subjects.size() * i + j));
            }
            group.add(new Student(student.get(i), subjects, mark));
        }
    }

    List<Student> getGroup(){
        return group;
    }

    //добавляем студента
    boolean addStudent(String addStudent) {
        if (addStudent == null){
            return false;
        }
        //проходим по всем студентам из группы, если среди них уже есть наш,
        //то ничего не делаем, а если нет, то добавляем в группу
        for (Student student : group) {
            if (student.studentName.equals(addStudent)) {
                return false;
            }
        }
        //создаем лист с оценками, создаем нового ученика
        List<String> subjects = new ArrayList<>();
        if (group.size() != 0){
            for (Map.Entry<String, Integer> entry : group.get(0).rating.entrySet()) {
                subjects.add(entry.getKey());
            }
        }
        group.add(new Student(addStudent, subjects));
        return true;
    }

    //удаляем студента из группы
    boolean deleteStudent(String deleteName) {
        if (deleteName == null){
            return false;
        }
        //проходим по всем студентам в группе и если есть наш, то удаляем его
        for (Student student : group){
            if (student.studentName.equals(deleteName)){
                group.remove(student);
                return true;
            }
        }
        return false;
    }

    //добавляем предмет
    boolean addSubject(String addSubject) {
        if (addSubject == null){
            return false;
        }
        for (Map.Entry<String, Integer> entry : group.get(0).rating.entrySet()) {
            if (entry.getKey().equals(addSubject)) {
                //предмет уже существует
                return false;
            }
        }
        //добавляем пердмет ко всем существующим студентам
        for (Student student : group){
            student.rating.put(addSubject, null);
        }
        return true;
    }

    //удаляем предмет
    boolean deleteSubject(String deleteSubject) {
        if (deleteSubject == null){
            return false;
        }
        for (Map.Entry<String, Integer> entry : group.get(0).rating.entrySet()) {
            if (entry.getKey().equals(deleteSubject)) {
                //удаляем этот предмет у всех студентов
                for (Student student : group){
                    student.rating.remove(deleteSubject);
                }
                return true;
            }
        }
        return false;
    }
}
