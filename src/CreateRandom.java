
import com.csvreader.CsvWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author The Worst One
 */
public class CreateRandom {
    public static void main(String[] args) throws IOException {
        
        String path = "Bucket\\Random.csv";
        
        String [] abecedario = {"a","b","c","d","e","f","h","i","j",
                                "k", "l", "m", "n", "o", "p", "q", 
                                 "r", "s", "t", "u", "v", "w", "x", "y", "z"};
        
        String [] dias = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
                          "10", "11", "12", "13", "14", "15", "16", "17", "18",
                            "19", "20","21","22","23","24","25","26","27","28",
                           "29","30","31"};
        
        String [] meses = {"01", "02", "03", "04", "05", "06", "07", "08", "09",
                          "10", "11", "12"};
        
        Random r = new Random(); 
        File original = new File(path);
        CsvWriter csvOriginal = new CsvWriter(new FileWriter(original),',');
        int a=0;
        while (a<1000000)
        {
            String word ="";
            for (int i=0; i<10;i++)
                word=word+(abecedario[r.nextInt(abecedario.length)]);
            csvOriginal.write(String.valueOf(r.nextInt(10000)));
            csvOriginal.write(word);
            csvOriginal.write(r.nextBoolean()?"TRUE":"FALSE");
            csvOriginal.write(dias[r.nextInt(dias.length)].concat("/")
                            .concat(meses[r.nextInt(meses.length)]).concat("/")
                            .concat(String.valueOf( r.nextInt(1016)+1000 ) ));
            csvOriginal.endRecord();
            a++;
        }
        csvOriginal.close();
    }
}
