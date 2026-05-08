/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;

/**
 *
 * @author aya
 */
public class Student {
    private int    studentId;
    private String name;

    // ─── Constructors ───────────────────────────────────────────

    public Student() {}

    

    public Student(int studentId, String name) {
        this.studentId      = studentId;
        this.name           = name;
    }

    // ─── Getters & Setters ──────────────────────────────────────

    public int getStudentId()                        { return studentId; }
    public void setStudentId(int studentId)          { this.studentId = studentId; }

    public String getName()                          { return name; }
    public void setName(String name)                 { this.name = name; }



    // ─── toString ───────────────────────────────────────────────

    @Override
    public String toString() {
        return 
               "studentId="   + studentId +
               ", name="      + name       +"}\n";
    
}
    
}
