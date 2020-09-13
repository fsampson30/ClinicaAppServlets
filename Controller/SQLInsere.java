package Controller;

import Model.Agendamento;
import Model.Mensagem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SQLInsere {

    private String insereAgendamento = "insert into agendamentos (data_exame, turno, codigo_secao, cpf_paciente, data_solicitacao) values (?,?,?,?,?);";
    private String insereMensagem = "insert into mensagens_enviadas (codigo_texto, cpf, idusuario, data_envio, texto_mensagem, mensagem_lida) values (?,?,?,?,?,0);";
    private static ClasseConstantes c = new ClasseConstantes();

    public boolean insereAgendamento(Agendamento objeto) {
        boolean ok = false;
        try {
            Connection db = Conexao.obter();
            PreparedStatement ps = db.prepareStatement(insereAgendamento);
            ps.setString(1, objeto.getData_exame());
            ps.setString(2, objeto.getTurno());
            ps.setString(3, objeto.getCodigo_secao());
            ps.setString(4, objeto.getCpf_paciente());
            ps.setString(5, objeto.getData_solicitacao());
            int sucesso = ps.executeUpdate();
            if (sucesso == 1) {
                ok = true;
            } else {
                db.rollback();
            }
            ps.close();
            db.close();
            Conexao.fechar();
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados" + e);
        }
        return ok;
    }

    public boolean insereMensagemPaciente(Mensagem objeto) {
        boolean ok = false;
        try {
            Connection db = Conexao.obter();
            PreparedStatement ps = db.prepareStatement(insereMensagem);
            ps.setString(1, "99");
            ps.setString(2, objeto.getCpf());
            ps.setString(3, "99");
            ps.setString(4, objeto.getData_envio());
            ps.setString(5, objeto.getTexto());
            int sucesso = ps.executeUpdate();
            if (sucesso == 1) {
                ok = true;
            } else {
                db.rollback();
            }
            ps.close();
            db.close();
            Conexao.fechar();
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados" + e);
        }
        return ok;
    }

}
