package Servlets;

import Controller.SQLSeleciona;
import Model.Paciente;
import com.google.gson.Gson;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletValidaLoginPaciente", urlPatterns = {"/ServletValidaLoginPaciente"})

public class ServletValidaLoginPaciente extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int length = request.getContentLength();
            byte[] input = new byte[length];
            ServletInputStream sin = request.getInputStream();
            int c, count = 0;
            while ((c = sin.read(input, count, input.length - count)) != -1) {
                count += c;
            }
            sin.close();

            String parametro = new String(input);
            String resposta;
            parametro = URLDecoder.decode(parametro, "UTF-8");
            Gson gson = new Gson();
            Paciente paciente = new Paciente();
            paciente = gson.fromJson(parametro, Paciente.class);

            SQLSeleciona sql = new SQLSeleciona();
            resposta = sql.validaLoginPaciente(paciente.getCpf(), paciente.getSenha());
            System.out.println(resposta);
            response.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            writer.write(resposta);
            writer.flush();
            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
