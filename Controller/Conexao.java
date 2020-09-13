package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {

    private static ClasseConstantes c = new ClasseConstantes();
    private static Connection con;

    public static Connection obter() {
        try {
            Class.forName(c.getDriver());

            con = DriverManager.getConnection(c.getUrl(), c.getUser(), c.getPass());
        } catch (SQLException e) {
            System.out.println("Erro de SQL Conexao" + e);
        } catch (NullPointerException e) {
            System.out.println("Erro Nulo Conexao" + e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return con;
    }

    public static void fechar() {
        try {
            con.close();
        } catch (SQLException e) {
            e.getMessage();
        }
    }
}
