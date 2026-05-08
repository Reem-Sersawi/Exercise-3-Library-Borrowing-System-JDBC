/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import config.DBConnection;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author aya
 */
public class StudentDAO {
    
    public List<Integer> getAllStudentsids(){
        List<Integer> ids = new ArrayList<>();
        try {
            Connection conn = DBConnection.getInstance().getConnection();
            String sql ="SELECT student_id FROM students";
            Statement stat = conn.createStatement();
            ResultSet rs = stat.executeQuery(sql);
            while(rs.next()){
                Integer student_id =rs.getInt("student_id");
                ids.add(student_id);
            }
        } catch (SQLException ex) {
            System.getLogger(BookDAO.class.getName()).log(System.Logger.Level.ERROR, (String) null, ex);
        }
        return ids;
    }
    
}
