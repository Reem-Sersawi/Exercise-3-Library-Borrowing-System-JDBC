/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controllers;


import dao.BookDAO;
import dao.BorrowDAO;
import dao.StudentDAO;
import java.net.URL;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import models.Borrow;

/**
 * FXML Controller class
 *
 * @author aya
 */
public class BorrowController implements Initializable {

@FXML
private TextField searchBookIdField;
@FXML
private TextField searchStudentIdField;
    @FXML
    private ComboBox<Integer> booksCombobox;
    @FXML
    private ComboBox<Integer> studentsCombobox;
    @FXML
    private DatePicker borrowDate;
    @FXML
    private DatePicker returnDate;
    @FXML
    private CheckBox status;
    @FXML
    private TableView<Borrow> table;
    @FXML
    private TableColumn<Borrow, Integer> borrowIdTC;
    @FXML
    private TableColumn<Borrow, Integer> bookIdTC;
    @FXML
    private TableColumn<Borrow, Integer> studentIdTC;
    @FXML
    private TableColumn<Borrow, String> borrowDateTC;
    @FXML
    private TableColumn<Borrow, String> returnDateTC;
    @FXML
    private TableColumn<Borrow, Boolean> statusTC;
    BookDAO bookdao = new BookDAO();
    StudentDAO studentdao = new StudentDAO();
    BorrowDAO borrowdao=new BorrowDAO();
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        borrowIdTC.setCellValueFactory(new PropertyValueFactory<>("borrowId"));
        bookIdTC.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        studentIdTC.setCellValueFactory(new PropertyValueFactory<>("studentId"));
        borrowDateTC.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateTC.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        statusTC.setCellValueFactory(new PropertyValueFactory<>("status"));
        
        
        List<Integer> ids = bookdao.getAllbooksids();
        booksCombobox.getItems().addAll(ids);
        
        List<Integer> student_ids = studentdao.getAllStudentsids();
        studentsCombobox.getItems().addAll(student_ids);
        
        table.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue)->{
                    if(newValue == null)  return;
                    booksCombobox.setValue(newValue.getBookId());
                    studentsCombobox.setValue(newValue.getStudentId());
                    borrowDate.setValue(LocalDate.parse(newValue.getBorrowDate()));
                    String rd = newValue.getReturnDate();
                    if(rd != null){
                        returnDate.setValue(LocalDate.parse(rd));
                    }else{
                        returnDate.setValue(null);
                    }
                    status.setSelected(newValue.getStatus());
                    
                    
                    
                    
                }
        
        );
        
        
        
        
    }    

    @FXML
    private void viewHandle(ActionEvent event) {
        List<Borrow> borrows = borrowdao.findAll();
        table.getItems().setAll(borrows);
    }

    @FXML
    private void borrowHandle(ActionEvent event) {
        if(borrowValidor()){
            Integer book_id = booksCombobox.getSelectionModel().getSelectedItem();
            Integer student_id = studentsCombobox.getSelectionModel().getSelectedItem();
            String bd = borrowDate.getValue().toString();
            Borrow b=new Borrow(student_id, book_id, bd);
            boolean success = borrowdao.insertOne(b);
            if(success){
                clear();
                viewHandle(event);
                showInfoAlert("success","BorrowAdded Successfully");
            }
        }else{
            
            showWarningAlert(
                    "Invalid input",
                    "Missing Data",
                    "Please select book id , student id and borrow date"
            );
            
            
        }
        
        
        
    }

    @FXML
    private void returnHandle(ActionEvent event) {   
        Borrow b = table.getSelectionModel().getSelectedItem();
        if(b == null){
            showWarningAlert("No Selection", "No Record Selected",
                    "please select a borrow record from the table");
        }else{
            if(returnDate.getValue() == null || !status.isSelected()){
                showWarningAlert("invalid Input", "Missing data", 
                        "please select both return date and status");
            }else{
                b.setReturnDate(returnDate.getValue().toString());
                b.setStatus(status.isSelected());
                boolean success = borrowdao.updateOne(b);
                if(success){
                    showInfoAlert("success", "book returned successfully");
                    clear();
                    viewHandle(event);
                }
            }
        }
        
        
        
        
        
        
    }

    @FXML
    private void deleteHandle(ActionEvent event) {
        Borrow b = table.getSelectionModel().getSelectedItem();
        if(b == null){
            showWarningAlert("No Selection", "No Record Selected",
                    "please select a borrow record from the table");
        }else{
            if(showConfirmationAlert("Delete Confirmation"
                    , "Are you sure",
                    "Do you want to delete this borrow record")){
                borrowdao.deleteOne(b);
                viewHandle(event);
                clear();
            }
         }
    }
    
    
    @FXML
private void borrowedBooksHandle(ActionEvent event) {
    // عرض الكتب المستعارة فقط
    List<Borrow> borrowedBooks = borrowdao.findBorrowedBooks();
    
    if (borrowedBooks.isEmpty()) {
        showInfoAlert("معلومات", "لا توجد كتب مستعارة حالياً");
        table.getItems().clear();
    } else {
        table.getItems().setAll(borrowedBooks);
        showInfoAlert("نجاح", "تم العثور على " + borrowedBooks.size() + " كتاب مستعار");
    }
}

@FXML
private void searchbyIds(ActionEvent event) {
    try {
        // قراءة القيم من حقول الإدخال
        Integer bookId = null;
        Integer studentId = null;
        
        if (searchBookIdField != null && !searchBookIdField.getText().trim().isEmpty()) {
            bookId = Integer.parseInt(searchBookIdField.getText().trim());
        }
        
        if (searchStudentIdField != null && !searchStudentIdField.getText().trim().isEmpty()) {
            studentId = Integer.parseInt(searchStudentIdField.getText().trim());
        }
        
        // التحقق من صحة الإدخال
        if (bookId == null && studentId == null) {
            showWarningAlert("بحث فارغ", "لا توجد معايير بحث", 
                "الرجاء إدخال Book ID أو Student ID للبحث");
            return;
        }
        
        // تنفيذ البحث
        List<Borrow> results = borrowdao.searchByBookIdAndStudentId(bookId, studentId);
        
        if (results.isEmpty()) {
            showInfoAlert("نتيجة البحث", "لا توجد سجلات تطابق معايير البحث");
            table.getItems().clear();
        } else {
            table.getItems().setAll(results);
            String msg = "تم العثور على " + results.size() + " سجل";
            if (bookId != null) msg += " (Book ID: " + bookId + ")";
            if (studentId != null) msg += " (Student ID: " + studentId + ")";
            showInfoAlert("نتيجة البحث", msg);
        }
        
    } catch (NumberFormatException e) {
        showWarningAlert("خطأ في الإدخال", "تنسيق غير صحيح", 
            "الرجاء إدخال أرقام صحيحة فقط في حقول البحث");
    }
}

// تحديث دالة showInfoAlert (لأن فيها خطأ - تستخدم WARNING بدل INFORMATION)
private void showInfoAlert(String title, String message){
    Alert alert = new Alert(Alert.AlertType.INFORMATION);  // غيرنا من WARNING إلى INFORMATION
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
}
    
    
    
    
    
    
    
    
    
    private void showWarningAlert(String title,String header,String message){
        
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(message);
        alert.showAndWait();
        
    }

   private boolean borrowValidor(){
       if(booksCombobox.getValue() == null || studentsCombobox.getValue() == null
               || borrowDate.getValue() == null)
           return false;
       return true;
       
   }
   
   private void clear(){
       booksCombobox.setValue(null);
       studentsCombobox.setValue(null);
       borrowDate.setValue(null);
       returnDate.setValue(null);
       status.setSelected(false);
       
       
   }
   
   private boolean showConfirmationAlert(String title,String header,String message){
       Alert alert =new Alert(Alert.AlertType.CONFIRMATION);
       alert.setTitle(title);
       alert.setHeaderText(header);
       alert.setContentText(message);
       Optional<ButtonType> result = alert.showAndWait();
       if(result.isPresent() && result.get() == ButtonType.OK){
           return true;
       }
       return false;
   }
   
       
}
