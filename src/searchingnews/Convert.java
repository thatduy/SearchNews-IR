/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingnews;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
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
public class Convert {

    /**
     * @param args the command line arguments
     */
//    public static void main(String[] args) {
//        String s = "Vo Duy That ##".replaceAll("[^A-Za-z]+", " ");
//        JSONObject a = new JSONObject();
//        a.put("1", 1);
//        if (a.get("2") == null) {
//            System.out.println("DM null");
//        }
//
//    }
    public Convert() {
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

    public void compute_tfidf_doc(JSONObject matrix, String subfolder) throws Exception {
        File sub = new File("./20_newsgroups/" + subfolder);
        int count = 0;
        ArrayList<String> files = new ArrayList<>();
        int size = sub.listFiles().length;
        ArrayList<Thread> threads = new ArrayList<>();
        for (File file : sub.listFiles()) {
            count++;
            size--;
            files.add(file.getName());
            if (count == 400) {
                Thread t = new Thread(new ThreadSaveFile(matrix, subfolder, files));
                threads.add(t);
                count = 0;
                files = new ArrayList<>();
                t.start();

            }
            if (size == 0) {
                Thread t = new Thread(new ThreadSaveFile(matrix, subfolder, files));
                threads.add(t);
                t.start();
                break;
            }
        }

        for (Thread t : threads) {
            t.join();
        }
    }
    public static JSONObject maxtrixx;

    public void computeIDF(String subFolder, String[] stop) {
        List<String> stopList = Arrays.asList(stop);
        for (File file : new File("./20_newsgroups/" + subFolder).listFiles()) {
            try {
                String text = new String(Files.readAllBytes(Paths.get("20_newsgroups/" + subFolder + "/" + file.getName())), StandardCharsets.UTF_8);
                String[] data = text.toLowerCase().replaceAll("[^A-Za-z]+", " ").split(" ");
                List<String> deDupStringList = new ArrayList<>(new HashSet<>(Arrays.asList(data)));
                for (String item : deDupStringList) {
                    if (!stopList.contains(item)) {
                        if (maxtrixx.get(item) != null) {
                            maxtrixx.put(item, (int) maxtrixx.get(item) + 1);
                        } else {
                            maxtrixx.put(item, 1);
                        }

                    }
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }

        }
    }

    public JSONObject compute_tfidf_query(JSONObject matrix, String query) {
        String[] query2 = null;
        JSONObject tf_idf = new JSONObject();
        query2 = query.toLowerCase().replaceAll("[^A-Za-z]+", " ").split(" ");
        for (String item : query2) {
            long c = 1;
            if (matrix.get(item) != null) {
                c = (long) matrix.get(item);
                double count = countWordInDoc(query2, item);
                double tf = count / query2.length;
                double idf = 1 + Math.log10(20000 / c);
                tf_idf.put(item, tf * idf);
            }

        }
        try {
            File resultTFIDF = new File("./Result_TF_IDF_Query");
            if (!resultTFIDF.exists()) {
                resultTFIDF.mkdir();
            }
            Writer w = new OutputStreamWriter(new FileOutputStream("./Result_TF_IDF_Query/tf_idf_vector_query.txt"), "UTF-8");
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
        for (File subFol : new File("./20_newsgroups").listFiles()) {
            new File("./TF_IDF_DOCS/" + subFol.getName()).mkdir();
            Thread t = new Thread(() -> {
                try {
                    compute_tfidf_doc(matrix, subFol.getName());
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            });
            t.start();
            aThreads.add(t);
        }
        for (Thread t : aThreads) {
            t.join();
        }
    }

    public JSONObject getMatrix() {
        try {
            JSONParser parser = new JSONParser();
            String text = new String(Files.readAllBytes(Paths.get("stopwords_en.txt")), StandardCharsets.UTF_8);
            String[] stop = text.replaceAll("[^A-Za-z]+", " ").split(" ");
            File f = new File("./CountIDF");
            if (!f.exists()) {
                f.mkdir();
                System.out.print("Computing Inverted Index, please wait 20s....");
                ArrayList<Thread> threads = new ArrayList<>();
                for (File sub : new File("./20_newsgroups").listFiles()) {
                    Thread t = new Thread(() -> {
                        try {
                            computeIDF(sub.getName(), stop);
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    });
                    t.start();
                    threads.add(t);
                }
                for (Thread t : threads) {
                    try {
                        t.join();
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                try {
                    Writer w = new OutputStreamWriter(new FileOutputStream("./CountIDF/count_idf.txt"), "UTF-8");
                    maxtrixx.writeJSONString(w);
                    w.flush();
                    w.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.print("Done inverted  Index");
            } else {
                try {
                    maxtrixx = (JSONObject) parser.parse(new FileReader("./CountIDF/count_idf.txt"));
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
