/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IntercalacionDirecta;
import com.csvreader.CsvReader; 
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
/**
 *
 * @author The Worst One
 */
public class OrdDirecta {
    
    public int  sort(int campo, String rutaOriginal) throws FileNotFoundException, IOException, ParseException
    {
        rutaOriginal += ".csv";
        String rutaF1 = "F1.csv";
        String rutaF2 = "F2.csv";
        
        CsvReader Original = new CsvReader(rutaOriginal, ',');
        
        int part = 1;
        int pasada = 1;
        int n = OrdenacionDirecta.Longitud(Original);
        Original.close();
        while (part<n)
        {
            OrdenacionDirecta.Particiona(rutaOriginal, rutaF1, rutaF2, part,n);
             
            OrdenacionDirecta.Fusiona(rutaOriginal, rutaF1, rutaF2, part, campo);
            part = part * 2;
            pasada++;
        }
        OrdenacionDirecta.DeleteFile(rutaF1, rutaF2);
        System.out.println("ARCHIVO ORDENADO");
        return pasada;
    }
}
