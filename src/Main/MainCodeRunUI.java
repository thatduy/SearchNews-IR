/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Search.QuerySearch;
import TrainData.ComputeTFIDF;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JOptionPane;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ASUS
 */
public class MainCodeRunUI {

    /**
     * @param args the command line arguments
     * @throws org.json.simple.parser.ParseException
     */
       public static void main(String[] args) throws ParseException, Exception {

       new MainUI();
       //New process, command line to python
     // new QuerySearch().searchWithQuery("Iphone X");

            
        //new ComputeTFIDF().getMatrix();

    }

}
