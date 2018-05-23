/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingnews;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author ASUS
 */
public class SearchingNews {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws ParseException, IOException, Exception {

        try {
            new IRS().searchWithQuery("What is your recommendation for a good hard-disk driver software for\n" +
"non-Apple drives? I would mainly need it for a SyQuest removable media");
        } catch (InterruptedException ex) {
            Logger.getLogger(SearchingNews.class.getName()).log(Level.SEVERE, null, ex);
        }
      

    }

}
