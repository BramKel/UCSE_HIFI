package HighFid.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Event {
    public String naam;
    public String beschrijving;
    public Date date;
    public SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    public boolean isRemoved = false;


    public int prijsMK, prijsZK;
    public Event(String naam, String beschrijving, int prijsMK, int prijsZK, Date date) {
        this.naam = naam;
        this.beschrijving = beschrijving;
        this.prijsMK = prijsMK;
        this.prijsZK = prijsZK;
        this.date = date;
    }
    public Event() {
        this.naam = "";
        this.beschrijving = "";
        try {
            this.date = dateFormat.parse("01-01-1970");
        } catch (ParseException e) {
            e.printStackTrace();
            this.date = null;
        }
        this.prijsMK = 0;
        this.prijsZK = 0;
    }
    public String getDateStr() {
        return dateFormat.format(date);
    }
    public JSONObject toJSON() {
        JSONObject JSONSport = new JSONObject();
        JSONSport.put("name", this.naam);
        JSONSport.put("descr", this.beschrijving);
        JSONSport.put("prijsMK", "" + prijsMK);
        JSONSport.put("prijsZK", "" + prijsZK);
        JSONSport.put("isRemoved", Boolean.toString(isRemoved));

        JSONSport.put("datum", dateFormat.format(date));
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
        try{
            this.naam = (String) JSONSport.get("name");
            this.beschrijving = (String) JSONSport.get("descr");
            this.prijsMK = Integer.parseInt((String) JSONSport.get("prijsMK"));
            this.prijsZK = Integer.parseInt((String) JSONSport.get("prijsZK"));
            this.date = dateFormat.parse((String) JSONSport.get("datum"));
            this.isRemoved = Boolean.valueOf((String) JSONSport.get("isRemoved"));
        } catch (Exception e){
            System.out.println(e.toString());
        }

    }
}
