/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author ASUS
 */
public class ItemResult {
    private String tittle;
    private String sourceNews;

    public ItemResult() {
    }

    public ItemResult(String tittle, String sourceNews) {
        this.tittle = tittle;
        this.sourceNews = sourceNews;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getSourceNews() {
        return sourceNews;
    }

    public void setSourceNews(String sourceNews) {
        this.sourceNews = sourceNews;
    }

    @Override
    public String toString() {
        return  "<html><b>"+ tittle + "</b><br>"+sourceNews+"</html>";
    }
    
   
    
}
