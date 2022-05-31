import java.util.*;


public class GroupOfStudents {
    private final Map<String, HashMap> rating = new HashMap<>();

    public int size() {
        return rating.size();
    }

    void addStudent (String student, String subjects) {
        if (student.isBlank() || rating.containsKey(student))
            throw new IllegalArgumentException();
        else {
            Map<String, String> allSubjects = new HashMap<>();
            String[] items = subjects.split(", ");
            for(String item: items) {
                if (item.split(" ").length < 2)
                    allSubjects.put(item.split(" ")[0], " ");
                else
                    allSubjects.put(item.split(" ")[0], item.split(" ")[1]);
            }
            rating.put(student, (HashMap) allSubjects);
        }
    }

    void deleteStudent (String student) {
        if (student.isBlank() || !rating.containsKey(student))
            throw new IllegalArgumentException();
        rating.remove(student);
    }

    void changeMark (String student, String subject, int mark) {
        if (student.isBlank() || subject.isBlank() || mark <= 0 || !rating.containsKey(student))
            throw new IllegalArgumentException();
        Map<String, String> items = rating.get(student);
        items.put(subject, Integer.toString(mark));
        rating.put(student, (HashMap) items);
    }


    void addMark (String student, String subject, int mark) {
        if (student.isBlank() || subject.isBlank() || mark <= 0 || !rating.containsKey(student))
            throw new IllegalArgumentException();
        Map<String, String> items = rating.get(student);
        items.put(subject, Integer.toString(mark));
        rating.put(student, (HashMap) items);
    }

    void deleteMark (String student, String subject) {
        if (student.isBlank() || subject.isBlank() || !rating.containsKey(student))
            throw new IllegalArgumentException();
        Map<String, String> items = rating.get(student);
        if (!items.containsKey(subject)) throw new IllegalArgumentException();
        if (!items.get(subject).equals(" ")) items.put(subject, " ");
        rating.put(student, (HashMap) items);
    }

    public String search(String student) {
        if (student.isBlank() || !rating.containsKey(student))
            throw new IllegalArgumentException();
        return rating.get(student).toString();
    }

    void deleteSubject (String subject) {
        if (subject.isBlank())
            throw new IllegalArgumentException();
        for (Map.Entry<String, HashMap> entry : rating.entrySet()) {
            String student = entry.getKey();
            Map<String, String> items = rating.get(student);
            items.remove(subject);
            rating.put(student, (HashMap) items);
        }
    }

    void addSubject (String subject) {
        if (subject.isBlank())
            throw new IllegalArgumentException();
        for (Map.Entry<String, HashMap> entry: rating.entrySet()) {
            String student = entry.getKey();
            Map<String, String> items = rating.get(student);
            items.put(subject, " ");
            items.remove("");
            rating.put(student, (HashMap) items);
        }
    }
}
