import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.sql.*;

public class TestDb {

    @Test
    public void createDB() {
        try (Connection con = DriverManager.getConnection("jdbc:h2:.\\Office.mv.db")) {
            Statement stm = con.createStatement();
            stm.executeUpdate("DROP TABLE Department IF EXISTS");
            stm.executeUpdate("CREATE TABLE Department(ID INT PRIMARY KEY, NAME VARCHAR(255))");
            stm.executeUpdate("INSERT INTO Department VALUES(1,'Accounting')");
            stm.executeUpdate("INSERT INTO Department VALUES(2,'IT')");
            stm.executeUpdate("INSERT INTO Department VALUES(3,'HR')");

            stm.executeUpdate("DROP TABLE Employee IF EXISTS");
            stm.executeUpdate("CREATE TABLE Employee(ID INT PRIMARY KEY, NAME VARCHAR(255), DepartmentID INT)");
            stm.executeUpdate("INSERT INTO Employee VALUES(1,'Kate',1)");
            stm.executeUpdate("INSERT INTO Employee VALUES(2,'Ann',1)");

            stm.executeUpdate("INSERT INTO Employee VALUES(3,'Liz',2)");
            stm.executeUpdate("INSERT INTO Employee VALUES(4,'tom',2)");

            stm.executeUpdate("INSERT INTO Employee VALUES(5,'todd',3)");

        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Test
    public void test_searchDepartments() {
        String url = "jdbc:h2:.\\Office.mv.db";
        try (Connection conn = DriverManager.getConnection(url)){
            Statement stm = conn.createStatement();
            ResultSet rsl = stm.executeQuery("select * from department");
            System.out.println(rsl.getMetaData().getColumnName(1)
                    + "\t" + rsl.getMetaData().getColumnName(2));
            while (rsl.next()) {
                System.out.println(rsl.getInt("ID") + "\t" + rsl.getString("NAME"));
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Test
    public void test_deleteDepartment() {
        String url = "jdbc:h2:.\\Office.mv.db";
        try (Connection conn = DriverManager.getConnection(url)){
            Statement stm = conn.createStatement();
            int x = stm.executeUpdate("delete from department where id = 1");
            System.out.println("Deleted departments: " + x);
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

    @Test
    public void test_searchEmployees() {
        String url = "jdbc:h2:.\\Office.mv.db";
        try (Connection conn = DriverManager.getConnection(url)){

            int num1 = 0;
            int num2 = 0;

            Statement stm1 = conn.createStatement();
            ResultSet rsl1 = stm1.executeQuery("select count(*) as total from employee where DepartmentId is null");
            while (rsl1.next()) {
                System.out.println(num1 = rsl1.getInt("total"));
            }

            Statement stm2 = conn.createStatement();
            ResultSet rsl2 = stm2.executeQuery("select count(*) as total from employee where DepartmentId = 1");
            while (rsl2.next()) {
                System.out.println(num2 = rsl2.getInt("total"));
            }
            
            Assertions.assertTrue(num1 > num2);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }

//    @Test
//    public void test_searchEmployees() {
//        String url = "jdbc:h2:.\\Office.mv.db";
//        try (Connection conn = DriverManager.getConnection(url)){
//            Statement stm = conn.createStatement();
//            ResultSet rsl = stm.executeQuery("select * from employee");
//            while (rsl.next()) {
//                System.out.println(rsl.getInt("ID") + "\t" + rsl.getString("NAME")
//                        + "\t" + rsl.getInt("DepartmentID"));
//            }
//        } catch (SQLException ex) {
//            System.out.println(ex);
//        }
//    }

}