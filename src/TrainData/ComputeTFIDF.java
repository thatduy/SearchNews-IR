/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package TrainData;

import Main.Constants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.parser.JSONParser;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Nammm Mèooo
 */
public class ComputeTFIDF {

    public ComputeTFIDF() {
        maxtrixx = new JSONObject();
    }

    public int countWordInDoc(String[] data, String word) {
        int count = 0;
        for (String s : data) {
            if (s.equalsIgnoreCase(word)) {
                count++;
            }
        }
        return count;
    }

    public static JSONObject maxtrixx;

    public void computeIDF(ArrayList<File> files) {
        files.forEach((file) -> {
            try {
                String text = new String(Files.readAllBytes(Paths.get("news_dataset/" + file.getName())), StandardCharsets.UTF_16);
                String[] data = text.toLowerCase().replaceAll(Constants.re, " ").split(" ");
                List<String> deDupStringList = new ArrayList<>(new HashSet<>(Arrays.asList(data)));
                deDupStringList.forEach((item) -> {
                    if (maxtrixx.get(item) != null) {
                        maxtrixx.put(item, (int) maxtrixx.get(item) + 1);
                    } else {
                        maxtrixx.put(item, 1);
                    }
                });
            } catch (IOException ex) {
            }
        });
    }

    public JSONObject compute_tfidf_query(JSONObject matrix, String query) {
        String[] query2 = null;
        JSONObject tf_idf = new JSONObject();
        query2 = query.toLowerCase().replaceAll(Constants.re, " ").split(" ");
        for (String item : query2) {
            long c = 1;
            if (matrix.get(item) != null) {
                c = (long) matrix.get(item);
                double count = countWordInDoc(query2, item);
                double tf = count / query2.length;
                double idf = 1 + Math.log10(43303 / c);
                tf_idf.put(item, tf * idf);
            }

        }
        try {
            File resultTFIDF = new File("./Result_TF_IDF_Query");
            if (!resultTFIDF.exists()) {
                resultTFIDF.mkdir();
            }
            Writer w = new OutputStreamWriter(new FileOutputStream("./Result_TF_IDF_Query/tf_idf_vector_query.txt"), "UTF-16");
            tf_idf.writeJSONString(w);
            w.flush();
            w.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return tf_idf;
    }

    public void compute_all_docs(JSONObject matrix) throws Exception {
        ArrayList<Thread> aThreads = new ArrayList<>();
        File dataset = new File("./news_dataset");
        int size = dataset.listFiles().length;
        int count = 0;
        ArrayList<String> files = new ArrayList<>();
        for (File fNews : dataset.listFiles()) {
            
                    count++;
                    size--;
                    files.add(fNews.getName());
                    if (count == 1000) {
                        System.out.println("1000");
                        Thread t = new Thread(new ThreadSaveFile(matrix, files));
                        t.start();
                        aThreads.add(t);
                        count = 0;
                        files = new ArrayList<>();
                    }
                    if (size == 0){
                        System.out.println("0");
                        Thread t = new Thread(new ThreadSaveFile(matrix, files));
                        t.start();
                        aThreads.add(t);
                        break;
                    }
        }
        for (Thread t : aThreads) {
            t.join();
        }
    }

    public JSONObject getMatrix() {
        try {
            JSONParser parser = new JSONParser();
//            String text = new String(Files.readAllBytes(Paths.get("stopwords_en.txt")), StandardCharsets.UTF_8);
//            String[] stop = text.replaceAll("[^A-Za-z]+", " ").split(" ");
            File f = new File("./CountIDF");
            if (!f.exists()) {
                f.mkdir();
                System.out.print("Computing Inverted Index, please wait 10s....");
                ArrayList<Thread> threads = new ArrayList<>();
                File dataset = new File("./news_dataset");
                int size = dataset.listFiles().length;
                int count = 0;
                ArrayList<File> files = new ArrayList<>();
                for (File fNews : dataset.listFiles()) {
                    count++;
                    size--;
                    files.add(fNews);
                    if (count == 1000) {
                        Thread t = new Thread(() -> {
                            try {
                                computeIDF(files);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                        t.start();
                        threads.add(t);
                        count = 0;
                        files.clear();
                    }
                    if (size == 0){
                        Thread t = new Thread(() -> {
                            try {
                                computeIDF(files);
                            } catch (Exception ex) {
                                ex.printStackTrace();
                            }
                        });
                        t.start();
                        threads.add(t);
                        break;
                    }

                }
                for (Thread t : threads) {
                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    Writer w = new OutputStreamWriter(new FileOutputStream("./CountIDF/count_idf.txt"), "UTF-16");
                    maxtrixx.writeJSONString(w);
                    w.flush();
                    w.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.print("Done inverted Index");
            } else {
                try {
                    maxtrixx = (JSONObject) parser.parse(new InputStreamReader(new FileInputStream("./CountIDF/count_idf.txt"), StandardCharsets.UTF_16));
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return maxtrixx;

    }
}
