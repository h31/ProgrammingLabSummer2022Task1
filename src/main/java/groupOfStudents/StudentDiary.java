package groupOfStudents;


import java.util.*;

public class StudentDiary {
    private final String name;
    private Map<String, HashMap<String, Integer>> subjectsMarks = new HashMap<>();


    public StudentDiary(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }


    public void addSubject(String subject) {
        if (subject == null) throw new NullPointerException("Предмет не может быть null");
        //Через put можно понять содержался ли предмет до этого, но в таком случае список оценок за предмет обнулится
        if (subjectsMarks.containsKey(subject))
            throw new IllegalArgumentException("Такой предмет уже есть у студента " + name + "!");
        else subjectsMarks.put(subject, new HashMap<>());

    }

    public void deleteSubject(String subject) {
        if (subjectsMarks.remove(subject) == null)
            throw new IllegalArgumentException("Предмет не найден");

    }

    public Map<String, HashMap<String, Integer>> getMarks() {
        return subjectsMarks;
    }

    public HashMap<String, Integer> getSubjectMarks(String subject) {
        if (subjectsMarks.get(subject) == null)
            throw new IllegalArgumentException("Предмет не найдет");
        return subjectsMarks.get(subject);
    }

    public Integer getSubjectMark(String subject, String description) {
        //Не нужно проверок на null, так как мы не можем добавить предмет и описание равное null
        return subjectsMarks.get(subject).get(description);
    }

    public void addMark(String subject, Integer mark, String description) {
        if (!subjectsMarks.containsKey(subject))
            this.addSubject(subject);

        subjectsMarks.get(subject).put(description, mark);
    }

    public void deleteMark(String subject, String description) {
        if (subjectsMarks.containsKey(subject)) {
            if (subjectsMarks.get(subject).remove(description) == null)
                throw new IllegalArgumentException("Оценка не найдена");
        } else throw new IllegalArgumentException("Предмет не найден у студента " + name + "!");
    }

    public void changeMark(String subject, String description, Integer mark) {
        if (subjectsMarks.containsKey(subject)) {
            if (subjectsMarks.get(subject).containsKey(description)) subjectsMarks.get(subject).put(description, mark);
            else throw new IllegalArgumentException("Оценка не найдена");
        } else {
            throw new IllegalArgumentException("Предмет не найден.");
        }
    }

    public void sorted() {
        subjectsMarks = new TreeMap<>(subjectsMarks);
    }

    public StringBuilder getAllMarksAsString() {
        StringBuilder res = new StringBuilder();
        subjectsMarks.forEach((k, v) -> {
            res.append("\n" + k + ": \n");
            v.forEach((d, s) -> res.append(d + " " + s.toString() + "; "));
        });
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StudentDiary diary = (StudentDiary) o;
        if (this.hashCode() != diary.hashCode()) return false;
        return subjectsMarks.equals(diary.getMarks()) && name.equals(diary.getName())
                && this.getMarks().entrySet().stream().allMatch(e ->
                e.getValue().entrySet().stream().allMatch(p ->
                        p.getValue().equals(diary.getMarks().get(e.getKey()).get(p.getKey())))
        );
    }

    @Override
    public int hashCode() {
        int prime = 31;
        int result = 1;
        result = prime * result + name.hashCode() + subjectsMarks.hashCode();
        return result;
    }

}

