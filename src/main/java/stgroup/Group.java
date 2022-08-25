package stgroup;

import java.util.*;

public class Group {
    private final int numberOfGroup;
    private final Set<String> subjects = new HashSet<>();
    private final Map<String, Marks> group = new HashMap<>();

    Map<String, Marks> getGroup (){
        return group;
    }
    Set<String> getSubjects(){
        return subjects;
    }

    //конструктор (номер группы)
    public Group (int numberOfGroup){
        this.numberOfGroup = numberOfGroup;
    }

    //добавить студента
    boolean addStudent (String nameOfStudent){
        if (nameOfStudent == null || group.containsKey(nameOfStudent)){
            return false;
        }
        group.put(nameOfStudent, new Marks(subjects));
        return true;
    }

    //удалить студента
    boolean deleteStudent(String nameOfStudent){
        if (nameOfStudent == null || !group.containsKey(nameOfStudent)){
            return false;
        }
        group.remove(nameOfStudent);
        return true;
    }

    //добавить предмет
    boolean addSubject (String nameOfSubject){
        if (nameOfSubject == null || subjects.contains(nameOfSubject)){
            return false;
        }
        subjects.add(nameOfSubject);
        //добавляем этот предмет всем студентам
        for (Map.Entry<String, Marks> entry: group.entrySet())
        {
            entry.getValue().getMarks().put(nameOfSubject, null);
        }
        return true;
    }

    //удалить предмет
    boolean deleteSubject (String nameOfSubject){
        if (nameOfSubject == null || !subjects.contains(nameOfSubject)){
            return false;
        }
        subjects.remove(nameOfSubject);
        //удаляем предмет у всех студентов
        for (Map.Entry<String, Marks> entry: group.entrySet())
        {
            entry.getValue().getMarks().remove(nameOfSubject);
        }
        return true;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Group group1 = (Group) o;

        if (numberOfGroup != group1.numberOfGroup) return false;
        if (!Objects.equals(subjects, group1.subjects)) return false;
        return Objects.equals(group, group1.group);
    }

    @Override
    public int hashCode() {
        int result = numberOfGroup;
        result = 31 * result + subjects.hashCode();
        result = 31 * result + group.hashCode();
        return result;
    }
}
