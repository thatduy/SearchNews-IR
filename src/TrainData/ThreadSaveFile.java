/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainData;

import Main.Constants;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author Nammm Mèooo
 */
public class ThreadSaveFile implements Runnable {

    private JSONObject matrix;
    private ArrayList<String> files = new ArrayList<>();

    public ThreadSaveFile(JSONObject matrix, ArrayList<String> files) {
        this.matrix = matrix;

        this.files = files;
    }

    private int countWordInDoc(String[] data, String word) {
        int count = 0;
        for (String s : data) {
            if (s.equalsIgnoreCase(word)) {
                count++;
            }
        }
        return count;
    }

    @Override
    public void run() {
        JSONObject tf_idf = new JSONObject();
        for (String file : files) {
            try {
                String text = new String(Files.readAllBytes(Paths.get("news_dataset/" + file)),
                        StandardCharsets.UTF_16);
                String[] data = text.replaceAll(Constants.re, " ").split(" ");
                List<String> deDupStringList = new ArrayList<>(new HashSet<>(Arrays.asList(data)));
                for (String item : deDupStringList) {
                    if (matrix.get(item) != null) {
                        double count = countWordInDoc(data, item);
                        double tf = count / data.length;
                        double idf = Math.log10(43303 / Integer.parseInt(matrix.get(item).toString())) + 1;
                        tf_idf.put(item, tf * idf);
                    }
                }
                try {
                    Writer w = new OutputStreamWriter(new FileOutputStream("TF_IDF_DOCS/"  + file ), "UTF-16");
                    tf_idf.writeJSONString(w);
                    tf_idf = new JSONObject();
                    w.flush();
                    w.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

}
