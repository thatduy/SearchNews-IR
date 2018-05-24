/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Main;

import Search.QuerySearch;
import TrainData.ComputeTFIDF;
import static TrainData.ComputeTFIDF.maxtrixx;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ASUS
 */
public class MainCodeRunUI {

    /**
     * @param args the command line arguments
     */
       public static void main(String[] args) throws ParseException, IOException, Exception {

       new MainUI();
      // new QuerySearch().searchWithQuery("Hiệp hội Thép Việt Nam (VSA) cho biết các doanh nghiệp Việt Nam");
      

    }

}
