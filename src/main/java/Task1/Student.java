package Task1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student {
    private String firstName;
    private String surName;
    private String patronymic;
    private Group group;
    private String name;
    private Map<String, List<Integer>> subjectMarks;


    public Student(String surName, String firstName, String patronymic) {
        this.firstName = firstName;
        this.surName = surName;
        this.patronymic = patronymic;
        this.name = this.toFSP();
        this.subjectMarks = new HashMap<String, List<Integer>>();
    }

    Student() {
        this.name = this.toFSP();
        this.subjectMarks = new HashMap<String, List<Integer>>();
    }

    public String getFirstName(String firstName) {
        return firstName;
    }

    public String getSurName(String surName) {
        return surName;
    }

    public String getPatronymic(String patronymic) {
        return patronymic;
    }

    public void setGroup(Group group) {
        if (this.group != null) {
            this.group.removeStudent(this);
        }
        this.group = group;
        this.group.add(this);
    }

    public Group getGroup() {
        return this.group;
    }

    public void retired() { // Метод, который реализует отчисление, по сути
        this.group = null;
    }

    public String toFSP() {
        return this.surName + " " + this.firstName + " " + this.patronymic;
    }

    public void addSubject(String subject) {
        this.subjectMarks.put(subject, new ArrayList<Integer>());
    }

    public void deleteSubject(String subject) {
        if (this.subjectMarks.containsKey(subject)) {
            this.subjectMarks.remove(subject);
        } else throw new IllegalArgumentException("Предмет не найден");
    }

    public Map<String, List<Integer>> getAllMarks() {
        return (this.subjectMarks);
    }

    public List<Integer> getSubjectMarks(String subject) {
        if (subjectMarks.containsKey((subject))){
            return this.subjectMarks.get(subject);
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

    public void addMark(String subject, List<Integer> list) {
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

        } else throw new IllegalArgumentException("Предмет не найден.");
    }

    public void changeMark(String subject, Integer mark1, Integer mark2) {
        if (subjectMarks.containsKey(subject)) {
            if (subjectMarks.get(subject).contains(mark1)) {
                subjectMarks.get(subject).set(subjectMarks.get(subject).indexOf(mark1), mark2);
            } else throw new IllegalArgumentException("Оценка не найдена");
        } else throw new IllegalArgumentException("Предмет не найден.");
    }
}

