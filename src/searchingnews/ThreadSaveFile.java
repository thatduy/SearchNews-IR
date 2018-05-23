/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingnews;

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
    private String folder;
    private ArrayList<String> files;

    public ThreadSaveFile(JSONObject matrix, String folder, ArrayList<String> files) {
        this.matrix = matrix;
        this.folder = folder;
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
                String text = new String(Files.readAllBytes(Paths.get("20_newsgroups/" + folder + "/" + file)),
                        StandardCharsets.UTF_8);
                String[] data = text.replaceAll("[^A-Za-z]+", " ").split(" ");

                for (String item : data) {
                    if (matrix.get(item) != null) {
                        double count = countWordInDoc(data, item);
                        double tf = count / data.length;
                        double idf = Math.log10(20000 / Integer.parseInt(matrix.get(item).toString())) + 1;
                        tf_idf.put(item, tf * idf);
                    }
                }
                try {
                    Writer w = new OutputStreamWriter(new FileOutputStream("TF_IDF_DOCS/" + folder + "/" + file + ".txt"), "UTF-8");
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
