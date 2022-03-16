package groupOfStudents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StudentMarks {
    private String groupNumber;
    private final String name;
    private Map<String, List<Integer>> subjectMarks;


    public StudentMarks(String name) {
        this.name = name;
        subjectMarks = new HashMap<String, List<Integer>>();
    }

    public String getName() {
        return name;
    }

    //Задача не требует перевода студентов между группами поэтому убрал этот функционал
    public void setGroup(Group group) {
        groupNumber = group.getGroupNumber();
        group.add(this);
    }

    public String getGroup() {
        return this.groupNumber;
    }

    public void retired() { // Метод, который реализует отчисление, по сути
        this.groupNumber = null;
    } //Отчисление


    public void addSubject(String subject) {
        if (!subjectMarks.containsKey(subject)) {
            subjectMarks.put(subject, new ArrayList<Integer>());
        } else {
            throw new IllegalArgumentException("Такой предмет уже есть у студента " + name + "!");
        }
    }

    public void deleteSubject(String subject) {
        if (subjectMarks.containsKey(subject)) {
            subjectMarks.remove(subject);
        } else throw new IllegalArgumentException("Предмет не найден");
    }

    public Map<String, List<Integer>> getAllMarks() {
        return (subjectMarks);
    }

    public List<Integer> getSubjectMarks(String subject) {
        if (subjectMarks.containsKey((subject))){
            return subjectMarks.get(subject);
        } else throw new IllegalArgumentException("Предмет не найдет");

    }

    public void addMark(String subject, Integer mark) {
        if (!subjectMarks.containsKey(subject)) {
            throw new IllegalArgumentException("Предмет не найден");
        }
        List<Integer> list = subjectMarks.get(subject);
        list.add(mark);
        subjectMarks.replace(subject, list);
    }

    public void addMarks(String subject, List<Integer> list) {
        if (!subjectMarks.containsKey(subject)) {
            throw new IllegalArgumentException("Предмет не найден");
        }
        List<Integer> integerList = subjectMarks.get(subject);
        integerList.addAll(list);
        subjectMarks.replace(subject, integerList);
    }

    public void deleteMark(String subject, Integer mark) {
        if (subjectMarks.containsKey(subject)) {
            if (subjectMarks.get(subject).contains(mark)) {
                subjectMarks.get(subject).remove(mark);
            } else throw new IllegalArgumentException("Оценка не найдена");

        } else throw new IllegalArgumentException("Предмет не найден у студента " + name + "!" );
    }

    public void changeMark(String subject, Integer mark1, Integer mark2) {
        if (subjectMarks.containsKey(subject)) {
            if (subjectMarks.get(subject).contains(mark1)) {
                subjectMarks.get(subject).set(subjectMarks.get(subject).indexOf(mark1), mark2);
            } else throw new IllegalArgumentException("Оценка не найдена");
        } else throw new IllegalArgumentException("Предмет не найден.");
    }
}

