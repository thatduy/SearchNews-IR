/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Search;

import org.json.simple.JSONObject;

public class ComputeAngle {

    public ComputeAngle() {
    }

    public double computeAngle(JSONObject vectorQuery, JSONObject vectorDoc) {
        double product = 0;
        double lenghtQuery = 0;
        double lenghtDoc = 0;
        double cos;
       // int containAll = 0;
        for (Object term : vectorQuery.keySet()) {
            double doc = 0;
            if(vectorDoc.get(term) != null){
               // containAll++;
                doc = (double) vectorDoc.get(term);
            }
            product = product + (double)vectorQuery.get(term) * doc;
            lenghtQuery = lenghtQuery + Math.pow((double) vectorQuery.get(term), 2);
            lenghtDoc = lenghtDoc + Math.pow( doc, 2);
        }
        lenghtQuery = Math.sqrt(lenghtQuery);
        lenghtDoc = Math.sqrt(lenghtDoc);
        double lenght = lenghtDoc * lenghtQuery;
        if (lenght == 0) {
            cos = -1;
        } else {
            cos = product / lenght;
        }
//        if(containAll == vectorQuery.size()){
//            return 0.0;
//        }
        return Math.acos(cos) * 180 / Math.PI;
    }
}
