package HighFid.Model;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Time;
import java.time.DayOfWeek;

public class Sport {
    public String name, niveau, wanneer, aanbod, description, quote;
    public int prijsMetKaart, prijsZonderkaart;
    public DayOfWeek[] days = new DayOfWeek[0];
    public Time[] beginTimes = new Time[0];
    public Time[] endTimes = new Time[0];
    //Date[] dates;


    public Sport(String name, String quote, String description, String aanbod, String niveau, String wanneer, int prijsMetKaart, int prijsZonderkaart) {
        this.name = name;
        this.niveau = niveau;
        this.wanneer = wanneer;
        this.aanbod =aanbod;
        this.prijsMetKaart = prijsMetKaart;
        this.prijsZonderkaart = prijsZonderkaart;
        this.description = description;
        this.quote = quote;
    }

    public Sport() {

    }

    public Sport(JSONObject JSONSport) {
        fromJSON(JSONSport);
    }

    public JSONObject toJSON() {
        JSONObject JSONSport = new JSONObject();
        JSONSport.put("name", this.name);
        JSONSport.put("quote", this.quote);
        JSONSport.put("descr", this.description);
        JSONSport.put("aanbod", this.aanbod);
        JSONSport.put("niveau", this.niveau);
        JSONSport.put("wanneer", this.wanneer);
        JSONSport.put("prijsMetKaart", "" + prijsMetKaart);
        JSONSport.put("prijsZonderKaart", "" + prijsZonderkaart);
        JSONArray dayList = new JSONArray();
        for(int i = 0; i < days.length; i++) {
            JSONObject JSONDay = new JSONObject();
            JSONDay.put("day", days[i].name());
            JSONDay.put("beginTime", beginTimes[i].toString());
            JSONDay.put("endTime", endTimes[i].toString());
            dayList.add(JSONDay);
        }
        JSONSport.put("daysList", dayList);
        return JSONSport;
    }

    public static boolean checkJSON(JSONObject JSONSport) {
        boolean ok = JSONSport.get("name") != null &&
                JSONSport.get("niveau") != null &&
                JSONSport.get("wanneer") != null &&
                JSONSport.get("prijsMetKaart") != null &&
                JSONSport.get("prijsZonderKaart") != null &&
                JSONSport.get("daysList") != null;
        if(ok) {
            JSONArray daysList = ((JSONArray) JSONSport.get("daysList"));
            for(int i = 0; ok && i < daysList.size(); ++i) {
                JSONObject JSONDay = ((JSONObject) daysList.get(i));
                ok = JSONDay.get("day") != null &&
                        JSONDay.get("beginTime") != null &&
                        JSONDay.get("endTime") != null;
            }
        }
        return ok;
    }

    public boolean fromJSON(JSONObject JSONSport) {
        this.name = (String) JSONSport.get("name");
        this.quote = (String) JSONSport.get("quote");
        this.description = (String) JSONSport.get("descr");
        this.aanbod = (String)JSONSport.get("aanbod");
        this.niveau = (String) JSONSport.get("niveau");
        this.wanneer = (String) JSONSport.get("wanneer");
        this.prijsMetKaart = Integer.parseInt((String) JSONSport.get("prijsMetKaart"));
        this.prijsZonderkaart = Integer.parseInt((String) JSONSport.get("prijsZonderKaart"));
        JSONArray daysList = ((JSONArray) JSONSport.get("daysList"));
        for(int i = 0; i < daysList.size(); ++i) {
            JSONObject JSONDay = ((JSONObject) daysList.get(i));
            DayOfWeek day = DayOfWeek.valueOf((String) JSONDay.get("day"));
            Time begin = Time.valueOf((String) JSONDay.get("beginTime"));
            Time end = Time.valueOf((String) JSONDay.get("endTime"));
            AddSession(day,begin,end);
        }
        return true;
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
