package stgroup;

import java.util.*;

public class Student {
    public final String studentName;
    Map<String, Integer> rating= new HashMap<>();

    //конструктор1
    public Student(String student, List<String> subjects, List<Integer> marks){
        studentName = student;
        for (int i = 0; i < subjects.size(); i++){
            rating.put(subjects.get(i), marks.get(i));
        }
    }

    //конструктор2
    public Student(String name, List<String> subjects){
        studentName = name;
        for (String subject : subjects) {
            rating.put(subject, null);
        }
    }

    //удаляем оценку
    boolean deleteMark(String subject) {
        if (rating.containsKey(subject)){
            rating.put(subject, null);
            return true;
        }
        return false;
    }

    //добавляем оценку
    boolean addMark (Integer delMar, String subject){
        if (!(delMar >= 2 && delMar <= 5)){
            return false;
        }
        if (rating.isEmpty() || rating.get(subject) == null){
            rating.put(subject, delMar);
            return true;
        }
        return false;
    }

    //изменяем оценку
    boolean changeMark(Integer delMar, String subject){
        if (!(delMar >= 2 && delMar <= 5)){
            return false;
        }
        if (rating.get(subject) != null){
            rating.put(subject, delMar);
            return true;
        }
        return false;
    }

}
