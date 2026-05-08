/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnection;
import java.util.List;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.util.ArrayList;



/**
 *
 * @author aya
 */
public class BookDAO {
   
    
    public List<Integer> getAllbooksids(){
        List<Integer> ids = new ArrayList<>();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql ="SELECT book_id FROM books";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                Integer book_id =rs.getInt("book_id");
                ids.add(book_id);
            }
        } catch (SQLException ex) {
            System.getLogger(BookDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return ids;
    }
    
    
   
    
}
