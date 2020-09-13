package Controller;

import Model.Mensagem;
import Model.Paciente;
import com.google.gson.Gson;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class SQLSeleciona {

    private String selecionaAgendamentosPaciente;
    private String validaLoginPaciente = "SELECT cpf, senha FROM pacientes where cpf = ?";
    private String selecionaMensagensCliente = "SELECT me.data_envio, mp.titulo_texto, me.texto_mensagem, concat(me.data_envio, ' - ' ,mp.titulo_texto, ' - ' , me.texto_mensagem) from mensagens_enviadas me join mensagens_padrao mp on mp.codigo_texto = me.codigo_texto where cpf =? order by idmensagens desc;";
    private String selecionaListaLaudos = "SELECT data_admissao, codigo_exame, nome_exame, nome_secao_exame, matricula, caminho_laudo  from LAUDOS la JOIN exame ex on la.codigo_exame = ex.idexame JOIN secao_exame sc on ex.codigo_secao = sc.idsecao_exame where cpf = ? ORDER BY data_admissao DESC;";
    private static ClasseConstantes c = new ClasseConstantes();
    private Paciente paciente = new Paciente();
    private ArrayList<Mensagem> msg = new ArrayList<>();
    private Mensagem msgSelecionada = new Mensagem();

    public String validaLoginPaciente(String cpf, String senha) {
        /*Retorno: 0 CPF não encontrado, 1 Senha não cadastrada, 2 Senha incorreta, 3 Login OK, 4 Outro erro*/
        ResultSet rs = null;
        Conexao con = new Conexao();
        System.out.println(cpf);
        try {
            Class.forName(c.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(validaLoginPaciente);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            while (rs.next()) {
                paciente.setCpf(rs.getString(1));
                paciente.setSenha(rs.getString(2));
            }
            if (paciente.getCpf() == null) {
                return "0";
            } else if (paciente.getSenha() == null) {
                return "1";
            } else if (paciente.getCpf().equals(cpf) && !paciente.getSenha().equals(senha)) {
                return "2";
            } else if (paciente.getCpf().equals(cpf) && paciente.getSenha().equals(senha)) {
                return "3";
            } else {
                return "4";
            }
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return "0";
    }

    public HashMap<String, ArrayList<String>> selecionaMensagensPaciente(String cpf) {
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        ArrayList<String> dataEnvio = new ArrayList<String>();
        ArrayList<String> tituloTexto = new ArrayList<String>();
        ArrayList<String> texto = new ArrayList<String>();        
        ArrayList<String> texto_concat = new ArrayList<String>();  
        ResultSet rs = null;
        Conexao con = new Conexao();
        try {
            Class.forName(c.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaMensagensCliente);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            while (rs.next()) {              
                dataEnvio.add(rs.getString(1));
                tituloTexto.add(rs.getString(2));    
                texto.add(rs.getString(3));    
                texto_concat.add(rs.getString(4));
            }
            map.put("data", dataEnvio);
            map.put("titulo", tituloTexto);     
            map.put("texto",texto);
            map.put("linha",texto_concat);
        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return map;
    }
    
    public HashMap<String, ArrayList<String>> retornaListaLaudos(String cpf){
        HashMap<String, ArrayList<String>> map = new HashMap<String, ArrayList<String>>();
        ArrayList<String> data = new ArrayList<String>();
        ArrayList<String> exame = new ArrayList<String>();
        ArrayList<String> nome_exame = new ArrayList<String>();
        ArrayList<String> secao = new ArrayList<String>();
        ArrayList<String> matricula = new ArrayList<String>();
        ArrayList<String> caminhoLaudo = new ArrayList<String>();
        ResultSet rs = null;
        Conexao con = new Conexao();
        try {
            Class.forName(c.getDriver());
            PreparedStatement ps = con.obter().prepareStatement(selecionaListaLaudos);
            ps.setString(1, cpf);
            rs = ps.executeQuery();
            while (rs.next()) {              
                data.add(rs.getString(1));
                exame.add(rs.getString(2));
                nome_exame.add(rs.getString(3));
                secao.add(rs.getString(4));
                matricula.add(rs.getString(5));
                caminhoLaudo.add(rs.getString(6));
            }             
            map.put("data", data);
            map.put("exame", exame);
            map.put("nome_exame", nome_exame);
            map.put("secao", secao);
            map.put("matricula", matricula);
            map.put("caminhoLaudo", caminhoLaudo);

        } catch (SQLException e) {
            System.out.println("Problemas de acesso ao Banco de Dados!!!");
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }                
        return map;        
    }
}
