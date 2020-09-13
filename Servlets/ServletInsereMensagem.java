package Servlets;

import Controller.SQLInsere;
import Model.Mensagem;
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

@WebServlet(name = "ServletInsereMensagem", urlPatterns = {"/ServletInsereMensagem"})

public class ServletInsereMensagem extends HttpServlet {

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
            Mensagem msg = new Mensagem();
            msg = gson.fromJson(parametro, Mensagem.class);

            SQLInsere sqlInsere = new SQLInsere();
            boolean ok = sqlInsere.insereMensagemPaciente(msg);
            if (ok) {
                resposta = "1";
            } else {
                resposta = "0";
            }
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
