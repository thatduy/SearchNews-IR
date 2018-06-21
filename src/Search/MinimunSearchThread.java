/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import java.io.File;
import java.util.ArrayList;
import org.json.simple.JSONObject;

/**
 *
 * @author homan
 */
public class MinimunSearchThread implements Runnable {

    private final JSONObject vectorQuery;

    public MinimunSearchThread(JSONObject vectorQuery) {
        this.vectorQuery = vectorQuery;
    }

    public void search() throws InterruptedException {
        int count = 0;
        ArrayList<String> files = new ArrayList<>();
        File directory = new File("./TF_IDF_DOCS" );
        long size = directory.listFiles().length;
        ArrayList<Thread> threads = new ArrayList<>();
        for (File file : directory.listFiles()) {
            count = count + 1;
            size = size - 1;
            files.add(file.getName());
            if (count == 1000) {
                Thread t = new Thread(new SearchThreadSaveResult(files, vectorQuery));
                t.start();
                count = 0;
                files = new ArrayList<>();
                threads.add(t);

            }
            if (size == 0) {
                Thread t = new Thread(new SearchThreadSaveResult(files, vectorQuery));
                t.start();
                threads.add(t);
                break;
            }
        }
        for (Thread t1 : threads) {
            t1.join();
        }
    }

    @Override
    public void run() {
        try {
            search();
        } catch (InterruptedException ie) {
            System.err.println(ie.toString());
        }
    }
}
