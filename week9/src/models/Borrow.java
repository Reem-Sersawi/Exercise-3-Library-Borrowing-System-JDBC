/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package models;


/**
 *
 * @author aya
 */
public class Borrow {

    private int     borrowId;
    private int     studentId;
    private int     bookId;
    private String  borrowDate;   
    private String  returnDate;   
    private boolean  status;

    

    public Borrow() {}

    public Borrow(int studentId, int bookId, 
                  String borrowDate) {
        this.studentId  = studentId;
        this.bookId     = bookId;
        this.borrowDate = borrowDate;
    }

    public Borrow(int borrowId, int studentId, int bookId,
                  String borrowDate, String returnDate, boolean status) {
        this.borrowId   = borrowId;
        this.studentId  = studentId;
        this.bookId     = bookId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.status     = status;
    }

   

    public int getBorrowId()                      { return borrowId; }
    public void setBorrowId(int borrowId)         { this.borrowId = borrowId; }

    public int getStudentId()                     { return studentId; }
    public void setStudentId(int studentId)       { this.studentId = studentId; }

    public int getBookId()                        { return bookId; }
    public void setBookId(int bookId)             { this.bookId = bookId; }

    public String getBorrowDate()                 { return borrowDate; }
    public void setBorrowDate(String borrowDate)  { this.borrowDate = borrowDate; }

    public String getReturnDate()                 { return returnDate; }
    public void setReturnDate(String returnDate)  { this.returnDate = returnDate; }

    public boolean getStatus()                     { return status; }
    public void setStatus(boolean status)          { this.status = status; }

   

    @Override
    public String toString() {
        return
               "borrowId="    + borrowId   +
               ", studentId=" + studentId  +
               ", bookId="    + bookId     +
               ", borrowDate="+ borrowDate +
               ", returnDate="+ returnDate + 
               ", status="    + status +"\n";
             
}
}