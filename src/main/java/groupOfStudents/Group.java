package groupOfStudents;


import java.util.*;


public class Group {
    //Идея в том, что класс Группа, содержит внутри себя объекты класса Студенты
    private Map<String, StudentDiary> groupJournal = new HashMap<>();
    private final String groupNumber;


    public Group(String groupNumber) {
        if (groupNumber == null || groupNumber.equals(""))
            throw new NullPointerException("Номер группы не может быть null или пустым");
        if (!groupNumber.matches("[БЗ]??\\d{7}/\\d{5}"))
            throw new IllegalArgumentException("Данный номер группы не соответствует формату");
        this.groupNumber = groupNumber;
    }

    public void addStudent(StudentDiary studentDiary) {
        if (studentDiary == null || (studentDiary.getName().equals("") || studentDiary.getName() == null)) {
            throw new NullPointerException("Студент и его имя не может быть null/пустой строкой");
        } else {
            groupJournal.put(studentDiary.getName(), studentDiary);
        }
    }


    public Set<String> getStudents() {
        //При таком подходе метод возвращает отсортированную последовательность
        return groupJournal.keySet();
    }

    public void removeStudent(String student) {
        if (groupJournal.remove(student) == null) {
            throw new NullPointerException("Данный ученик не найден!");
        }
    }

    public Map<String, StudentDiary> getGroupJournal() {
        return groupJournal;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    //Редактирование оценок по ФИО
    public void addMark(String student, String subject, Integer mark, String description) {
        Objects.requireNonNull(description, "Описание работы не может быть null");
        Objects.requireNonNull(mark, "Оценка не может быть null");
        if (2 > mark || mark > 5) throw new IllegalArgumentException("Оценка должна быть в пределах 2-5");
        if (groupJournal.get(student) == null) throw new NullPointerException("Данного студента нет в группе");
        groupJournal.get(student).addMark(subject, mark, description);

    }

    public void deleteMark(String student, String subject, String description) {
        groupJournal.get(student).deleteMark(subject, description);
    }

    public void changeMark(String student, String subject, String description,
                           Integer markToInsert) {
        if (2 > markToInsert || markToInsert > 5)
            throw new IllegalArgumentException("Оценка должна быть в пределах 2-5");
        groupJournal.get(student).changeMark(subject, description, markToInsert);
    }

    public Integer getSubjectMark(String student, String subject, String description) {
        return groupJournal.get(student).getSubjectMark(subject, description);
    }

    //Все оценки по предмету
    public HashMap<String, Integer> getSubjectMarks(String student, String subject) {
        return groupJournal.get(student).getSubjectMarks(subject);
    }

    //Удобный вывод всех студентов группы с их оценками
    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append(groupNumber).append(" :").append("\n");
        groupJournal.forEach((k, v) ->
                res.append(("\n" + k + " " + v.getAllMarksAsString()).trim()).append("\n"));
        return res.toString().trim();
    }

    public Map<String, HashMap<String, Integer>> getStudentMarks(String student) {
        if ((student == null) || (student.equals("")) || (groupJournal.get(student) == null))
            throw new IllegalArgumentException("Такого студента нет");
        return groupJournal.get(student).getMarks();
    }

    //Редактирование списка предметов по ФИО
    public void addSubject(String student, String subject) {
        if (groupJournal.get(student) == null) {
            throw new NullPointerException("Студент не найден");
        }
        groupJournal.get(student).addSubject(subject);
    }

    public void deleteSubject(String student, String subject) {
        if (groupJournal.get(student) == null) {
            throw new NullPointerException("Студент не найден");
        }
        groupJournal.get(student).deleteSubject(subject);
    }

    //Редактирование списка предметов всей группы
    public void addGroupSubject(String subject) {
        this.groupJournal.forEach((k, v) ->
                v.addSubject(subject));
    }

    public void deleteGroupSubject(String subject) {
        this.groupJournal.forEach((k, v) ->
                v.deleteSubject(subject));
    }

    // Сортировка по алфавитному порядку как студентов, так и предметов
    public void toSorted() {
        TreeMap<String, StudentDiary> sorted = new TreeMap<>(groupJournal);
        sorted.forEach((k, v) -> v.sorted());
        groupJournal = sorted;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        if (this.hashCode() != group.hashCode()) return false;
        return groupJournal.equals(group.getGroupJournal()) && groupNumber.equals(group.groupNumber) &&
                this.getGroupJournal().entrySet().stream().allMatch(e ->
                        e.getValue().equals(group.getGroupJournal().get(e.getKey())));
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + getGroupJournal().hashCode() + groupNumber.hashCode();
        return result;
    }
}

