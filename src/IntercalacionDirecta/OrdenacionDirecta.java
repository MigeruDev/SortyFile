/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntercalacionDirecta;

import Tools.Tools;
import com.csvreader.CsvReader;
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

/**
 *
 * @author The Worst One
 */
public class OrdenacionDirecta {
    public static int Longitud(CsvReader file) throws IOException
    {
        int count=0;
        while (file.readRecord())
        {
            count++;
        }
        return count;
    }
    
    

    
    public static void Particiona(String rutaOriginal, String rutaF1, String rutaF2, int part, int n) throws IOException
    {
        CsvReader Original = new CsvReader(rutaOriginal,',');
        CsvWriter F1 = new CsvWriter(new FileWriter(rutaF1),',');
        CsvWriter F2 = new CsvWriter(new FileWriter(rutaF2),',');
        int K,L;
        String R;
        int cont=0;
        boolean flag=true;
        if (Original.readRecord())
            flag = false;
        while (!flag)
        {
            K = 0;
            while ((K<part) && !flag)
            {  
                R = Original.get(0);
                F1.write(R);
                R = Original.get(1);
                F1.write(R);
                R = Original.get(2);
                F1.write(R);
                R = Original.get(3);
                F1.write(R);
                F1.endRecord();
                K++;
                cont++;
                if(K==part || cont == n) // aqui falta controlar que sea la ultima linea para que flag sea false y salga de la particion sin agregar los "";;; como esta en la captura
                    flag = true;
                else{
                    if (Original.readRecord())
                        flag = false;
                }
            }
            if (Original.readRecord())
                flag = false;
            L=0;
            while (L<part && !flag)
            {
                R = Original.get(0);
                F2.write(R);
                R = Original.get(1);
                F2.write(R);
                R = Original.get(2);
                F2.write(R);
                R = Original.get(3);
                F2.write(R);   
                F2.endRecord();
                L++;
                cont++;
                if(L==part || cont == n) 
                    flag = true;
                else{
                    if (Original.readRecord() )
                        flag = false;
                }
            }
            if (Original.readRecord())
                flag = false;
        }
        Original.close();
        F1.close();
        F2.close();
    }
    public static void Fusiona (String rutaOriginal, String rutaF1, String rutaF2, int part, int campo) throws IOException, ParseException
    {
        
            /*File ficheroAux = new File(rutaOriginal);
            ficheroAux.delete(); */
        
        int K,L;
        String R1="", R2="";
        boolean B1=true, B2 = true;
        CsvWriter Original = new CsvWriter(new FileWriter(rutaOriginal),',');
        CsvReader F1 = new CsvReader(rutaF1,',');
        CsvReader F2 = new CsvReader(rutaF2,',');
        
        if (F1.readRecord())
        {
            R1 = F1.get(campo);
            B1 = false;
        }
        
        if (F2.readRecord())
        {
            R2 = F2.get(campo);
            B2 = false;
        }
        
        while (!B1 && !B2)
        {
            K=0; 
            L=0;
            while ((K<part && !B1) && (L<part && !B2))
            {
                if (Tools.orderIt(R1,R2,campo)) //(R1.compareTo(R2)<=0)//orderIt(R1, R2, campo)
                {
                    Original.write(F1.get(0));
                    Original.write(F1.get(1));
                    Original.write(F1.get(2));
                    Original.write(F1.get(3));
                    Original.endRecord();
                    B1 = true;
                    K++;
                    if (F1.readRecord())
                    {
                        R1 = F1.get(campo);
                        B1 = false;
                    }
                }else
                {
                    Original.write(F2.get(0));
                    Original.write(F2.get(1));
                    Original.write(F2.get(2));
                    Original.write(F2.get(3));
                    Original.endRecord();
                    B2=true;
                    L++;
                    if (F2.readRecord())
                    {
                        R2=F2.get(campo);
                        B2=false;
                    }
                }
            }
          
            while (K<part && !B1)
            {
                Original.write(F1.get(0));
                Original.write(F1.get(1));
                Original.write(F1.get(2));
                Original.write(F1.get(3));
                Original.endRecord();
                B1=true;
                K++;
                if (F1.readRecord())
                {
                    R1 = F1.get(campo);
                    B1 = false;
                }
            }
            while (L<part && !B2)
            {
                Original.write(F2.get(0));
                Original.write(F2.get(1));
                Original.write(F2.get(2));
                Original.write(F2.get(3));
                Original.endRecord();
                B2=true;
                L++;
                if (F2.readRecord())
                {
                    R2 = F2.get(campo);
                    B2 = false;
                }
            }            
        }
        
        if (!B1)
        {
            Original.write(F1.get(0));
            Original.write(F1.get(1));
            Original.write(F1.get(2));
            Original.write(F1.get(3));
            Original.endRecord();
        }
        if (!B2)
        {
            Original.write(F2.get(0));
            Original.write(F2.get(1));
            Original.write(F2.get(2));
            Original.write(F2.get(3));
            Original.endRecord();
        }
        
        while (F1.readRecord())
        {           
            //R1 = F1.get(1);           
            Original.write(F1.get(0));
            Original.write(F1.get(1));
            Original.write(F1.get(2));
            Original.write(F1.get(3));
            Original.endRecord();
        }
        
        while (F2.readRecord())
        {
            //R2 = F2.get(1);
            Original.write(F2.get(0));
            Original.write(F2.get(1));
            Original.write(F2.get(2));
            Original.write(F2.get(3));
            Original.endRecord();
        }
        
        Original.close();
        F1.close();
        F2.close();        
    }
    
    public static void DeleteFile(String rutaF1, String rutaF2)
    {
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
