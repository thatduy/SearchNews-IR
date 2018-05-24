/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;



import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author homan
 */
public class FolderSearchThread implements Runnable {


    private ArrayList<String> files;
    private JSONObject vectorQuery;

    public FolderSearchThread(ArrayList<String> files, JSONObject vectorQuery) {

        this.files = files;
        this.vectorQuery = vectorQuery;
    }


    @Override
    public void run() {
        try {
            for (String file : files) {
            String fileDir = "./TF_IDF_DOCS/" + file; 
            JSONParser parser = new JSONParser();
            JSONObject vectorDoc = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream(fileDir), StandardCharsets.UTF_16));
            double angles = new ComputeAngle().computeAngle(vectorQuery, vectorDoc);
            if (angles != 180.0) {
                QuerySearch.resultSearch.put(file, angles);
            }
        }
        } catch (Exception ex) {
            Logger.getLogger(FolderSearchThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
