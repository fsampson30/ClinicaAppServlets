package Servlets;

import Controller.SQLSeleciona;
import Model.Mensagem;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletConsultaMensagensPaciente", urlPatterns = {"/ServletConsultaMensagensPaciente"})

public class ServletConsultaMensagensPaciente extends HttpServlet {

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
            SQLSeleciona sql = new SQLSeleciona();
            HashMap<String, ArrayList<String>> msg = sql.selecionaMensagensPaciente(parametro);
            System.out.println(msg.toString());
            
            Gson gson = new Gson();
            String resposta = gson.toJson(msg);
            
            System.out.println(resposta.toString());
            resposta = URLEncoder.encode(resposta, "UTF-8");
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
