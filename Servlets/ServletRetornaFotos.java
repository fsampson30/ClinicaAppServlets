package Servlets;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletRetornaFotos", urlPatterns = {"/ServletRetornaFotos"})

public class ServletRetornaFotos extends HttpServlet {

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
            parametro = URLDecoder.decode(parametro, "UTF-8");
            File caminhoFoto = new File(parametro);
            FileInputStream arquivo = new FileInputStream(caminhoFoto);
            byte [] foto = new byte [(int) caminhoFoto.length()];
            arquivo.read(foto);
            arquivo.close();
            
            response.setStatus(HttpServletResponse.SC_OK);
            DataOutputStream writer = new DataOutputStream(response.getOutputStream());                        
            writer.write(foto);
            writer.flush();
            writer.close();                                                  
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
