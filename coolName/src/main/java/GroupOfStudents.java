import java.util.*;


public class GroupOfStudents {
    private final Map<String, String> rating = new HashMap<>();

    public int size() {
        return rating.size();
    }

    void addStudent (String student, String subjects) {
        if (student.isBlank() || rating.containsKey(student))
            throw new IllegalArgumentException();
        rating.put(student, subjects);
    }


    void deleteStudent (String student) {
        if (student.isBlank() || !rating.containsKey(student))
            throw new IllegalArgumentException();
        rating.remove(student);
    }

    void changeMark (String student, String subject, int mark) {
        if (student.isBlank() || subject.isBlank() || mark <= 0 || !rating.containsKey(student))
            throw new IllegalArgumentException();
        int i = 0;
        String oldItems = rating.get(student);
        String[] items = rating.get(student).split(", ");
        StringBuilder newItems = new StringBuilder();
        if (items.length == 0)
            throw new IllegalArgumentException();
        for (String item : items) {
            if (item.isBlank())
                throw new IllegalArgumentException();
            if (item.split(" ").length != 2)
                throw new IllegalArgumentException();
            if (item.split(" ")[0].equals(subject)) {
                newItems.append(item.split(" ")[0]);
                newItems.append(" ");
                newItems.append(mark);
            } else {
                newItems.append(item);
            }
            if (i != items.length - 1) newItems.append(", ");
            i++;
        }
        if (oldItems.equals(newItems.toString()))
            throw new IllegalArgumentException();
        rating.put(student, newItems.toString());
    }


    void addMark (String student, String subject, int mark) {
        if (student.isBlank() || subject.isBlank() || mark <= 0 || !rating.containsKey(student))
            throw new IllegalArgumentException();
        int i = 0;
        String[] items = rating.get(student).split(", ");
        String oldItems = rating.get(student);
        StringBuilder newItems = new StringBuilder();
        if (items.length == 0)
            throw new IllegalArgumentException();
        for (String item : items) {
            if (item.isBlank())
                throw new IllegalArgumentException();
            if (item.split(" ").length < 1)
                throw new IllegalArgumentException();
            else if (item.split(" ").length == 1) {
                newItems.append(item.split(" ")[0]);
                newItems.append(" ");
                newItems.append(mark);
                if (i != items.length - 1) newItems.append(", ");
                i++;
            } else {
                newItems.append(item);
                if (i != items.length - 1) newItems.append(", ");
                i++;
            }
        }
        if (oldItems.equals(newItems.toString()))
            throw new IllegalArgumentException();
        rating.put(student, newItems.toString());
    }



    void deleteMark (String student, String subject) {
        if (student.isBlank() || subject.isBlank() || !rating.containsKey(student))
            throw new IllegalArgumentException();
        int i = 0;
        String[] items = rating.get(student).split(", ");
        String oldItems = rating.get(student);
        StringBuilder newItems = new StringBuilder();
        if (items.length == 0)
            throw new IllegalArgumentException();
        for (String item : items) {
            if (item.isBlank())
                throw new IllegalArgumentException();
            if (item.split(" ").length < 1)
                throw new IllegalArgumentException();
            if (item.split(" ")[0].equals(subject)) {
                newItems.append(subject);
            } else {
                newItems.append(item);
            }
            if (i != items.length - 1) newItems.append(", ");
            i++;
        }
        if (oldItems.equals(newItems.toString()))
            throw new IllegalArgumentException();
        rating.put(student, newItems.toString());
    }

    public String search(String student) {
        if (student.isBlank() || !rating.containsKey(student))
            throw new IllegalArgumentException();
        return rating.get(student);
    }



    void addSubject (String subject) {
        if (subject.isBlank())
            throw new IllegalArgumentException();
        for (Map.Entry<String, String> entry: rating.entrySet()) {
            String student = entry.getKey();
            String subjects = entry.getValue();
            String newSubjects;
            if (subjects.isBlank()) {
                newSubjects = subjects + subject;
            } else {
                newSubjects = subjects + ", " + subject;
            }
            rating.put(student, newSubjects);
        }
    }
}