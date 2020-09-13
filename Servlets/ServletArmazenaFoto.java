package Servlets;

import Controller.MontaData;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "ServletArmazenaFoto", urlPatterns = {"/ServletArmazenaFoto"})

public class ServletArmazenaFoto extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int length = request.getContentLength();
            ServletInputStream sin = request.getInputStream();
            byte[] input = new byte[length];
            int c, count = 0;
            while ((c = sin.read(input, count, input.length - count)) != -1) {
                count += c;
            }
            sin.close();
            MontaData cal = new MontaData();
            
            File dir = new File("E:/SonusApp/Fotos"+File.separator+cal.retornaAno()+File.separator+cal.retornaMes()+File.separator+cal.retornaDia());                    
            dir.mkdirs();
            File file = new File(dir, cal.retornaNomeFoto());      
            System.out.println(dir);

            FileOutputStream fis = new FileOutputStream(file);
            if (!file.exists()) {
                file.createNewFile();
            }
            fis.write(input);
            fis.flush();
            fis.close();

            response.setStatus(HttpServletResponse.SC_OK);
            OutputStreamWriter writer = new OutputStreamWriter(response.getOutputStream());
            writer.write("2");
            writer.flush();
            writer.close();

        } catch (IOException e) {
            try {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print(e.getMessage());
                response.getWriter().close();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        }
    }
}
