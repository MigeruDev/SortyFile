
package IntercalacionPolifase;

import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jeffer
 */
public class OrdenacionPolifasica {
    public static void particion(String rutaOriginal,String rutaF1,String rutaF2, int campo) throws FileNotFoundException, IOException, ParseException{
        CsvReader F = new CsvReader(rutaOriginal,',');
        CsvWriter F1 = new CsvWriter(new FileWriter(rutaF1),',');
        CsvWriter F2 = new CsvWriter(new FileWriter(rutaF2),',');
        String R,Aux;
        boolean band=true;
        if (F.readRecord())
            band = false;
        R = F.get(0);
        F1.write(R);
        R = F.get(1);
        F1.write(R);
        R = F.get(2);
        F1.write(R);
        R = F.get(3);
        F1.write(R);
        R = F.get(campo);
        F1.endRecord();
        Aux=R;
        band=true;
        while(F.readRecord()){
            R=F.get(campo);
            if(orderIt(Aux, R, campo)){ //Integer.parseInt(R)>=Integer.parseInt(Aux)
                Aux=R;
                
                if(band==true){
                    
                    R = F.get(0);
                    F1.write(R);
                    R = F.get(1);
                    F1.write(R);
                    R = F.get(2);
                    F1.write(R);
                    R = F.get(3);
                    F1.write(R);
                    F1.endRecord();
                }
                else{
                    R = F.get(0);
                    F2.write(R);
                    R = F.get(1);
                    F2.write(R);
                    R = F.get(2);
                    F2.write(R);
                    R = F.get(3);
                    F2.write(R);
                    F2.endRecord(); 
                }
            }
            else{
                Aux=R;
                if(band==true){
                    R = F.get(0);
                    F2.write(R);
                    R = F.get(1);
                    F2.write(R);
                    R = F.get(2);
                    F2.write(R);
                    R = F.get(3);
                    F2.write(R);
                    F2.endRecord(); 
                    band=false;
                }
                else{
                    R = F.get(0);
                    F1.write(R);
                    R = F.get(1);
                    F1.write(R);
                    R = F.get(2);
                    F1.write(R);
                    R = F.get(3);
                    F1.write(R);
                    F1.endRecord();
                    band=true;
                }
            }
        }
        F.close();
        F1.close();
        F2.close();   
    }
           
     public static void Fusiona(String rutaOriginal, String rutaF1, String rutaF2, int campo) throws FileNotFoundException, IOException, ParseException{         
        int limit=1;
        CsvWriter Original = new CsvWriter(new FileWriter(rutaOriginal),',');
        CsvReader F1 = new CsvReader(rutaF1,',');
        CsvReader F2 = new CsvReader(rutaF2,',');
        String auxR1="", auxR2="";
        boolean end1=true, end2=true;
        
        if (F1.readRecord())
        {    
            auxR1=F1.get(campo);
            end1=false;
        }
        if (F2.readRecord())
        {    
            auxR2=F2.get(campo);
            end2=false;
        }
        
        
        while((!end1 || !end2)) // && limit < part
        {
            
            while(!end1 && !end2 && (orderIt(auxR1, F1.get(campo), campo) && orderIt(auxR2, F2.get(campo), campo)) )
            {
               // System.out.printf("\nEste es auxR1: %d. Este es auxR2: %d. --- Estos son F1.get: %s. F2.get: %s\n",
                 //       auxR1, auxR2, F1.get(0), F2.get(0));
                // Tools.orderIt(auxR1, F1.get(campo), campo) && Tools.orderIt(auxR2, F2.get(campo), campo)
                if ( orderIt(F1.get(campo),F2.get(campo), campo) ) // > de "<" orderIt(F1.get(campo),F2.get(campo), campo)
                {
                    //System.out.println("Escribe en ORI: "+F1.get(0)+". Desde F1. Cuando F1<F2");
                    Original.write(F1.get(0));
                    Original.write(F1.get(1));
                    Original.write(F1.get(2));
                    Original.write(F1.get(3));
                    auxR1 = F1.get(campo);
                    Original.endRecord();
                    end1=true;
                    if (F1.readRecord()){
                        if (orderIt(auxR1, F1.get(campo), campo)) // Tools.orderIt(auxR1, F1.get(campo), campo)
                            auxR1 = F1.get(campo);
                        end1=false;
                    }                        
                }else
                {
                    //System.out.println("Escribe en ORI: "+F2.get(0)+". Desde F2. Cuando F2<F1");
                    Original.write(F2.get(0));
                    Original.write(F2.get(1));
                    Original.write(F2.get(2));
                    Original.write(F2.get(3));
                    auxR2 = F2.get(campo);
                    Original.endRecord();
                    end2=true;
                    if (F2.readRecord()){
                        end2=false;
                        if ( orderIt(auxR2, F2.get(campo), campo) ) // Tools.orderIt(auxR2, F2.get(campo), campo)
                            auxR2 = F2.get(campo);
                        
                    }
                       
                }                
            }
            //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Antes de terminar tramoF1");
            while (!end1 && orderIt(auxR1, F1.get(campo), campo) ) // Tools.orderIt(auxR1, F1.get(campo), campo)
            {
                //System.out.println("Escribe en ORI: "+F1.get(0)+". Desde F1.");
                Original.write(F1.get(0));
                Original.write(F1.get(1));
                Original.write(F1.get(2));
                Original.write(F1.get(3));
                auxR1 = F1.get(campo);
                Original.endRecord();
                end1=true;
                if (F1.readRecord()){
                    end1=false;
                }       
            }
            //System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Antes de terminar tramoF2");
            while (!end2 && orderIt(auxR2, F2.get(campo),campo) ) // Tools.orderIt(auxR2, F2.get(campo),campo)
            {
                //System.out.println("Escribe en ORI: "+F2.get(0)+ ". Desde F2");
                Original.write(F2.get(0));
                Original.write(F2.get(1));
                Original.write(F2.get(2));
                Original.write(F2.get(3));
                auxR2 = F2.get(campo);
                Original.endRecord();
                end2=true;
                if (F2.readRecord()){
                    end2=false;
                }                   
            }
            if (!end1)            // El huevo de oro
                auxR1 = F1.get(campo);
            if (!end2)
                auxR2 = F2.get(campo);
            limit++;
        }  
        Original.close();
        F1.close();
        F2.close();         
     }
      public static boolean orderIt(String R1, String R2, int campo) throws ParseException
     {
         switch(campo)
         {
             case 0:
                 return Integer.parseInt(R1)<=Integer.parseInt(R2);
             case 1:
                 return R1.compareTo(R2)<=0;
             case 2:
                 if(R1.equals("TRUE")){
                     R1="0";
                 }
                 else{
                     R1="1";
                 }
                 if(R2.equals("TRUE")){
                     R2="0";
                 }
                 else{
                     R2="1";
                 }
                 return Integer.parseInt(R1)<=Integer.parseInt(R2);
             case 3:
                 SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy");
                try
                {
                   Date fecha1 = sd.parse(R1);
                   Date fecha2 = sd.parse(R2);
                   return fecha1.compareTo(fecha2)<=0;
                }
                catch (ParseException e)
                {
                   // Error, la cadena de texto no se puede convertir en fecha.
                }
                 /*Date date1 = sd.parse(R1);                 
                 Date date2 = sd.parse(R2);
                 return date1.compareTo(date2)<=0;*/
         }
         return false;
     }
      public static void eliminarAux(String rutaF1,String rutaF2) throws IOException{
        CsvWriter F1 = new CsvWriter(new FileWriter(rutaF1),',');
        CsvWriter F2 = new CsvWriter(new FileWriter(rutaF2),',');
        F1.close();
        F2.close();
        boolean existe = new File(rutaF1).exists();
        if (existe)
        {
            File ficheroAux = new File(rutaF1);
            ficheroAux.delete();
        }
        existe= new File(rutaF2).exists();
        if (existe)
        {
            File ficheroAux = new File(rutaF2);
            ficheroAux.delete();
        }
    }
  
}
