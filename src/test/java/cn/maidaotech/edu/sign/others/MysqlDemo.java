package cn.maidaotech.edu.sign.others;

import java.sql.*;

/**
 * @program: sign-api
 * @description:
 * @author: like
 * @create: 2019-05-29 11:17
 **/
public class MysqlDemo {
    public static void main(String[] args) {
        String url ="jdbc:mysql://localhost:3306/test?characterEncoding=UTF-8&useUnicode=true&serverTimezone=GMT%2B8&allowMultiQueries=true";
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection(url,"root","root");
            String sql = "select * from user where mobile=?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1,"15738888063");
            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                String mobile=rs.getString("mobile");
                System.out.println(mobile);
            }
            rs.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
