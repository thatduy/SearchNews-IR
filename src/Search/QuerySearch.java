/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import Model.ItemResult;
import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import org.json.simple.JSONObject;
import TrainData.ComputeTFIDF;
import javax.swing.DefaultListModel;

/**
 *
 * @author homan
 */
public class QuerySearch {

    
    public QuerySearch() {
    }

    public static HashMap resultSearch = new HashMap();

    public Set searchWithQuery(String query) throws InterruptedException, Exception {
        resultSearch = new HashMap();
        ComputeTFIDF cv = new ComputeTFIDF();
        JSONObject matrix = cv.getMatrix();
        File tfidf = new File("./TF_IDF_DOCS");
        if (!tfidf.exists()) {
            tfidf.mkdir();
            cv.compute_all_docs(matrix);
        }
       
        JSONObject vectorQuery = cv.compute_tfidf_query(matrix, query);

        Thread t1 = new Thread(new MinimunSearchThread( vectorQuery));
        t1.start();
        t1.join();
        int i = 0;
        DefaultListModel<ItemResult> listModel = new DefaultListModel<>();
        for(Object kv : sortByValues(resultSearch).entrySet()){
            System.out.println(kv.toString());
            i++;
            if(i == 100){
                break;
            }
        }
        return sortByValues(resultSearch).entrySet();
    }

    public static <K, V extends Comparable<V>> Map<K, V> sortByValues(final Map<K, V> map) {
        Comparator<K> valueComparator = (K k1, K k2) -> {
            int compare = map.get(k2).compareTo(map.get(k1));
            if (compare == 0) {
                return 1;
            } else {
                return -compare;
            }
        };
        Map<K, V> sortedByValues = new TreeMap<>(valueComparator);
        sortedByValues.putAll(map);
        return sortedByValues;
    }
}
