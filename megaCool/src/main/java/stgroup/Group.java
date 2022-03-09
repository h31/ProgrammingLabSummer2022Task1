package stgroup;
import java.util.*;
class Group {
    private final List<String> student;
    private final List<String> subjects;
    private final List<Integer> marks;
    public int i;
    public Group(int number, ArrayList<String> student, ArrayList<String> subjects, ArrayList<Integer> marks) {
        this.student = student;
        this.subjects = subjects;
        this.marks = marks;
    }
    public List<String> addStudent (String addStudent) {
        if (student.contains(addStudent)) { throw new IllegalArgumentException();
        } else student.add(addStudent);
        return student;
    }
    public List<String> deleteStudent (String deleteName) {
        if (!student.contains(deleteName)) { throw new IllegalArgumentException();
        } else i = student.indexOf(deleteName);
        student.remove(deleteName);
        return (student);
    }
    public List<String> addSubject (String addSubject) {
        if (subjects.contains(addSubject)) { throw new IllegalArgumentException();
        } else subjects.add(addSubject);
        return subjects;
    }
    public List<String> deleteSubject (String ds) {
        if (!subjects.contains(ds)) { throw new IllegalArgumentException();
        } else i = subjects.indexOf(ds) + 1;
        subjects.remove(ds);
        return subjects;
    }
    public ArrayList<Integer> deleteMark (String students, String subject){
        ArrayList<Integer> res = new ArrayList<>();
        marks.set(subjects.indexOf(subject) + student.indexOf(students) * subjects.size(), null);
        for (int i = student.indexOf(students) * subjects.size(); i < (student.indexOf(students) + 1) * subjects.size(); i++) res.add(marks.get(i));
        return res;
    }
    public ArrayList<Integer> changeMark (Integer delMar, String students, String subject){
        ArrayList<Integer> res = new ArrayList<>();
        marks.set(subjects.indexOf(subject) + student.indexOf(students)*subjects.size(), delMar);
        for (int i = student.indexOf(students) * subjects.size(); i < (student.indexOf(students) + 1) * subjects.size(); i++) res.add(marks.get(i));
        return res;
    }
}