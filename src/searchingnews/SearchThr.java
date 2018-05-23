/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package searchingnews;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import org.json.simple.JSONObject;

/**
 *
 * @author homan
 */
public class SearchThr implements Runnable {

    private String subFolder;
    private JSONObject vectorQuery;

    public SearchThr(String subfolder, JSONObject vectorQuery) {
        this.subFolder = subfolder;
        this.vectorQuery = vectorQuery;
    }

    public void search() throws InterruptedException {
        int count = 0;
        ArrayList<String> files = new ArrayList<>();
        File directory = new File("./TF_IDF_DOCS/" + subFolder);
        long size = directory.listFiles().length;
        ArrayList<Thread> threads = new ArrayList<>();
        for (File file : directory.listFiles()) {
            count = count + 1;
            size = size - 1;
            files.add(file.getName());
            if (count == 400) {
                Thread t = new Thread(new ThreadSearch(subFolder, files, vectorQuery));
                t.start();
                count = 0;
                files = new ArrayList<>();
                threads.add(t);

            }
            if (size == 0) {
                Thread t = new Thread(new ThreadSearch(subFolder, files, vectorQuery));
                t.start();
                threads.add(t);
                break;
            }
        }
        for (Thread t1 : threads) {
            t1.join();
        }
    }

    public void run() {
        try {
            search();
        } catch (InterruptedException ie) {
            System.err.println(ie.toString());
        }
    }
}
