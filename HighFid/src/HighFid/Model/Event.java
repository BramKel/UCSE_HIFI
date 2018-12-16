package HighFid.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Event {
    public String naam;
    public String beschrijving;

    public int prijsMK, prijsZK;
    public Event(String naam, String beschrijving, int prijsMK, int prijsZK) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijsMK = prijsMK;
        this.prijsZK = prijsZK;
    }
    public Event() {

    }
    public JSONObject toJSON() {
        JSONObject JSONSport = new JSONObject();
        JSONSport.put("name", this.naam);
        JSONSport.put("descr", this.beschrijving);
        JSONSport.put("prijsMK", "" + prijsMK);
        JSONSport.put("prijsZK", "" + prijsZK);
        return JSONSport;
    }
    public boolean ContainsSearchTerm(String search) {
        search = search.toLowerCase();
        if(naam.toLowerCase().contains(search))
            return true;
        if(beschrijving.toLowerCase().contains(search))
            return true;
        return false;
    }
    public void fromJSON(JSONObject JSONSport) {
        this.naam = (String) JSONSport.get("name");
        this.beschrijving = (String) JSONSport.get("descr");
        this.prijsMK = Integer.parseInt((String) JSONSport.get("prijsMK"));
        this.prijsZK = Integer.parseInt((String) JSONSport.get("prijsZK"));
    }
}
