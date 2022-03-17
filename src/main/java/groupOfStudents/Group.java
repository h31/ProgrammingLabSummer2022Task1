package groupOfStudents;



import java.util.*;


public class Group {
    //Идея в том, что класс Группа, содержит внутри себя объекты класса Студенты
    private final HashMap<String, StudentMarks> studentMap;
    private final String groupNumber;


    Group(String groupNumber) {
        if (groupNumber == null) throw new IllegalArgumentException("Номер группы не может быть null");
        this.groupNumber = groupNumber;

        studentMap = new HashMap<>();
    }

    public void addStudent(StudentMarks student) {
        if(student!= null) student.setGroup(this);
        else throw new IllegalArgumentException("Студент не может быть null");
    }

    public void add(StudentMarks student) {
        studentMap.put(student.getName(), student);
    }


    public String getStudents() {
        return studentMap.keySet().toString().replace("[","").replace("]","");
    }

    public void removeStudent(StudentMarks student) {
        if (studentMap.containsKey(student.getName())) {
            studentMap.remove(student.getName());
            student.retired();
        } else throw new IllegalArgumentException("Данный ученик не найден!");
    }

    public HashMap<String, StudentMarks> getMap() {
        return studentMap;
    }

    public String getGroupNumber() {
        return groupNumber;
    }

    //Редактирование оценок по ФИО
    public void addMark(String student, String subject, Integer mark)
    {studentMap.get(student).addMark(subject, mark);}

    public void addMarks(String student, String subject, List<Integer> marks)
    {studentMap.get(student).addMarks(subject, marks);}

    public void deleteMark(String student, String subject, Integer mark)
    {studentMap.get(student).deleteMark(subject, mark);}

    public void changeMark(String student, String subject, Integer markToDelete, Integer markToInsert)
    {studentMap.get(student).changeMark(subject, markToDelete, markToInsert);}

    public List<Integer> getSubjectMarks(String student, String subject){return studentMap.get(student).getSubjectMarks(subject);}
    public String getGroupMarks() {
        StringBuilder res = new StringBuilder();
        res.append(groupNumber).append(" :").append("\n");
        this.studentMap.forEach((k,v) ->
                res.append(k).append(" ").append(v.getAllMarks()).append("\n"));
        return res.toString();
    }
    public Map<String, List<Integer>> getAllMarks(String student) {return studentMap.get(student).getAllMarks();}
    //Редактирование списка предметов по ФИО
    public void addSubject(String student, String subject){studentMap.get(student).addSubject(subject);}
    public void deleteSubject(String student, String subject){studentMap.get(student).deleteSubject(subject);}

    //Редактирование списка предметов всей группы
    public void addGroupSubject(String subject){
        this.studentMap.forEach((k,v) ->
                v.addSubject(subject));
    }
    public void deleteGroupSubject(String subject){
        this.studentMap.forEach((k,v) ->
                v.deleteSubject(subject));
    }
}

