package HighFid.Model;

import HighFid.Model.FileIO.JsonIO;
import org.json.simple.JSONObject;

import java.sql.Time;
import java.time.DayOfWeek;
import java.util.Date;

public class Sport {
    public String name, niveau, wanneer;
    int prijsMetKaart, prijsZonderkaart;
    public DayOfWeek[] days = new DayOfWeek[0];
    public Time[] beginTimes = new Time[0];
    public Time[] endTimes = new Time[0];
    //Date[] dates;
    public Sport(String name, String niveau, String wanneer, int prijsMetKaart, int prijsZonderkaart) {
        this.name = name;
        this.niveau = niveau;
        this.wanneer = wanneer;
        this.prijsMetKaart = prijsMetKaart;
        this.prijsZonderkaart = prijsZonderkaart;
    }
    public Sport() {

    }
    public boolean toJSON(String filename) {
        try{
            JSONObject JSONModel = new JSONObject();
            JSONModel.put("name", this.name);
            JSONModel.put("niveau", this.niveau);
            JSONModel.put("wanneer", this.wanneer);
            JSONModel.put("prijsMetKaart", "" + prijsMetKaart);
            JSONModel.put("prijsZonderKaart", "" + prijsZonderkaart);
            JSONModel.put("aantalSessies", "" + days.length);
            for(int i = 0; i < days.length; i++) {
                JSONModel.put("day" + i, days[i].name());
                JSONModel.put("beginTime"+i, beginTimes[i].toString());
                JSONModel.put("endTime"+i, endTimes[i].toString());
            }
            JsonIO.saveJSONFile(filename, JSONModel);
            return true;
        }catch(Exception e){
            System.out.println("Model: toJSON: " + e.getMessage());
            return false;
        }
    }
    public boolean fromJSON(String filename) {
        JSONObject JSONModel;
        try{
            JSONModel = JsonIO.readJSONFile(filename);
            this.name = (String) JSONModel.get("name");
            this.niveau = (String) JSONModel.get("niveau");
            this.wanneer = (String) JSONModel.get("wanneer");
            this.prijsMetKaart = Integer.parseInt((String) JSONModel.get("prijsMetKaart"));
            this.prijsZonderkaart = Integer.parseInt((String) JSONModel.get("prijsZonderKaart"));
            int amount = Integer.parseInt((String) JSONModel.get("aantalSessies"));
            for(int i = 0; i < amount; i++) {
                DayOfWeek day = DayOfWeek.valueOf((String) JSONModel.get("day" + i));
                Time begin = Time.valueOf((String) JSONModel.get("beginTime" +i));
                Time end = Time.valueOf((String) JSONModel.get("endTime" +i));
                AddSession(day,begin,end);
            }
            return true;
        } catch(Exception e){
            System.out.println("Model: romJSON: " + e.getMessage());
            return false;
        }
    }
    public void AddSession(DayOfWeek day, Time begin, Time end) {
        DayOfWeek[] newDays = new DayOfWeek[days.length+1];
        Time[] newBeginTimes = new Time[beginTimes.length+1];
        Time[] newendTimes = new Time[endTimes.length+1];
        for(int i = 0; i < days.length; i++) {
            newDays[i] = days[i];
            newBeginTimes[i] = beginTimes[i];
            newendTimes[i] = endTimes[i];
        }
        newDays[days.length] = day;
        days = newDays;

        newBeginTimes[beginTimes.length] = begin;
        beginTimes = newBeginTimes;

        newendTimes[endTimes.length] = begin;
        endTimes = newendTimes;
    }

}
