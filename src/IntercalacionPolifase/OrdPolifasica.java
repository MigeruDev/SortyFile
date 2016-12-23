package IntercalacionPolifase;


import com.csvreader.CsvWriter;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author jeffer
 */
public class OrdPolifasica {
    public int sort(int campo, String rutaOriginal) throws IOException, FileNotFoundException, ParseException {
        rutaOriginal += ".csv";
        String rutaF1="F1.csv";
        String rutaF2 = "F2.csv";
        String rutaF3="F3.csv";
        int pasada = 1;
        int z=1,w=1;
        while(z!=0 && w!=0){
           // System.out.println(part+":part");
           OrdenacionPolifasica.particion(rutaOriginal, rutaF1, rutaF2, campo);
           OrdenacionPolifasica.Fusiona(rutaOriginal, rutaF1, rutaF2, campo);
           w=(int) contar(rutaF2);
           z=(int) contar(rutaF1);
           pasada++;
        }
        OrdenacionPolifasica.eliminarAux(rutaF1, rutaF2);
        return pasada;
    }
    
    
    public static long contar(String ruta) throws IOException{
            long count=0;
            FileReader fr = new FileReader(ruta);
            BufferedReader bf = new BufferedReader(fr);
            boolean sCad;
            while (sCad = bf.readLine()!=null)
            {
                count++;
            }
            fr.close();
            bf.close();
            return count; 

    }   
}
