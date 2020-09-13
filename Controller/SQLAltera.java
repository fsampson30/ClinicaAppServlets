package Controller;

import Model.Paciente;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLAltera {

    private static ClasseConstantes c = new ClasseConstantes();
    private String criaSenhaPacienteCadastrado = "UPDATE pacientes set senha = ? where cpf = ?";

    public boolean criaSenhaPacienteCadastrado(Paciente paciente) {
        boolean ok = false;
        try {
            Class.forName(c.getDriver());
            Connection db = DriverManager.getConnection(c.getUrl(), c.getUser(), c.getPass());
            PreparedStatement ps = db.prepareStatement(criaSenhaPacienteCadastrado);
            ps.setString(1, paciente.getSenha());
            ps.setString(2, paciente.getCpf());
            int resultado = ps.executeUpdate();
            if (resultado == 1) {
                ok = true;
            } else {
                System.out.println(resultado);
            }
            ps.close();
            db.close();
        } catch (SQLException e) {
            System.out.println("Problemas de acesso a Banco de Dados");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            System.out.println("Driver não encontrado");
        }
        return ok;
    }

}
