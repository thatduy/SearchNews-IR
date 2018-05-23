/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingnews;



import java.io.FileReader;
import java.io.IOException;
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
public class ThreadSearch implements Runnable {

    private String subfolder;
    private ArrayList<String> files;
    private JSONObject vectorQuery;

    public ThreadSearch(String subfolder, ArrayList<String> files, JSONObject vectorQuery) {
        this.subfolder = subfolder;
        this.files = files;
        this.vectorQuery = vectorQuery;
    }


    public void run() {
        try {
            for (String file : files) {
            String fileDir = "./TF_IDF_DOCS/" + subfolder + "/" + file; 
            JSONParser parser = new JSONParser();
            JSONObject vectorDoc = (JSONObject) parser.parse(new FileReader(fileDir));
            double angles = new ComputeAngle().computeAngle(vectorQuery, vectorDoc);
            if (angles != 180.0) {
                IRS.resultSearch.put(subfolder+"::"+file, angles);
            }
        }
        } catch (Exception ex) {
            Logger.getLogger(ThreadSearch.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
