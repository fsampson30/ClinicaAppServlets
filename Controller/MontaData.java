package Controller;

import java.util.Calendar;

public class MontaData {
    
    private Calendar calendario = Calendar.getInstance();
    
    public String retornaDia(){
        return "D"+calendario.get(Calendar.DAY_OF_MONTH);
    }
    
    public String retornaMes(){
        int mes = calendario.get(Calendar.MONTH) +1;
        return "M"+mes;
    }
    
    public String retornaAno(){
        return "A"+calendario.get(Calendar.YEAR);
    }
    
    public String retornaDataHoje(){        
        return calendario.get(Calendar.DAY_OF_MONTH) + "/"+calendario.get(Calendar.MONTH)+"/"+calendario.get(Calendar.YEAR);        
    }
    
    public String retornaNomeFoto(){
        return String.valueOf(calendario.getTimeInMillis())+".jpg";
    }
    
}
