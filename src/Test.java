/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author The Worst One
 */

import IntercalacionDirecta.OrdDirecta;
import IntercalacionNatural.OrdNatural;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;

public class Test {
    public static void main(String[] args) throws IOException, FileNotFoundException, ParseException {
        OrdNatural natural = new OrdNatural();
        OrdDirecta directa = new OrdDirecta();
        directa.sort(1, "Random");
        //natural.sort(3);
    }
}
