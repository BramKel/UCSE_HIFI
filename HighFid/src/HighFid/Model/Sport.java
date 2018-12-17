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
    public String[] places = new String[0];
    public boolean isRemoved = false;
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
        JSONSport.put("isRemoved", Boolean.toString(isRemoved));
        JSONArray dayList = new JSONArray();
        for(int i = 0; i < days.length; i++) {
            JSONObject JSONDay = new JSONObject();
            JSONDay.put("day", days[i].name());
            JSONDay.put("beginTime", beginTimes[i].toString());
            JSONDay.put("endTime", endTimes[i].toString());
            JSONDay.put("place", places[i]);
            dayList.add(JSONDay);
        }
        JSONSport.put("daysList", dayList);
        return JSONSport;
    }

    public static boolean checkJSON(JSONObject JSONSport) {
        boolean ok = JSONSport.get("name") != null &&
                JSONSport.get("quote") != null &&
                JSONSport.get("descr") != null &&
                JSONSport.get("aanbod") != null &&
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
        this.isRemoved = Boolean.valueOf((String) JSONSport.get("isRemoved"));
        JSONArray daysList = ((JSONArray) JSONSport.get("daysList"));
        for(int i = 0; i < daysList.size(); ++i) {
            JSONObject JSONDay = ((JSONObject) daysList.get(i));
            DayOfWeek day = DayOfWeek.valueOf((String) JSONDay.get("day"));
            Time begin = Time.valueOf((String) JSONDay.get("beginTime"));
            Time end = Time.valueOf((String) JSONDay.get("endTime"));
            String place = (String) JSONDay.get("place");
            AddSession(day,begin,end, place);
        }
        return true;
    }

    private String DayToStr(DayOfWeek day) {
        String result = "";
        switch(day) {
            case MONDAY:
                result = "Maandag";
                break;
            case TUESDAY:
                result ="Dinsdag";
                break;
            case WEDNESDAY:
                result = "Woensdag";
                break;
            case THURSDAY:
                result = "Donderdag";
                break;
            case FRIDAY:
                result = "Vrijdag";
                break;
            case SATURDAY:
                result = "Zaterdag";
                break;
            case SUNDAY:
                result = "Zondag";
                break;
        }
        return result;
    }

    public boolean ContainsSearchTerm(String searchTerm) {
        searchTerm = searchTerm.toLowerCase();
        if(name.toLowerCase().contains(searchTerm))
            return true;
        if(quote.toLowerCase().contains(searchTerm))
            return true;
        if(aanbod.toLowerCase().contains(searchTerm))
            return true;
        if(niveau.toLowerCase().contains(searchTerm))
            return true;
        if(wanneer.toLowerCase().contains(searchTerm))
            return true;
        if((prijsMetKaart == 0 || prijsZonderkaart == 0) && searchTerm.compareTo("gratis") == 0)
            return true;
        for(int i = 0; i < days.length; i++) {
            if(DayToStr(days[i]).toLowerCase().contains(searchTerm))
                return true;
            if(beginTimes[i].toString().contains(searchTerm))
                return true;
            if(endTimes[i].toString().contains(searchTerm))
                return true;
            if(places[i].toLowerCase().contains(searchTerm))
                return true;
        }
        return false;
    }
    public boolean CheckPlace(String place) {
        for(int i = 0; i < places.length; i++) {
            if(places[i].toLowerCase().contains(place.toLowerCase()))
                return true;
        }
        return false;
    }

    public void AddSession(DayOfWeek day, Time begin, Time end, String place) {
        DayOfWeek[] newDays = new DayOfWeek[days.length+1];
        Time[] newBeginTimes = new Time[beginTimes.length+1];
        Time[] newendTimes = new Time[endTimes.length+1];
        String[] newPlaces = new String[places.length + 1];
        for(int i = 0; i < days.length; i++) {
            newDays[i] = days[i];
            newBeginTimes[i] = beginTimes[i];
            newendTimes[i] = endTimes[i];
            newPlaces[i] = places[i];
        }
        newDays[days.length] = day;
        days = newDays;

        newBeginTimes[beginTimes.length] = begin;
        beginTimes = newBeginTimes;

        newendTimes[endTimes.length] = end;
        endTimes = newendTimes;

        newPlaces[places.length] = place;
        places = newPlaces;
    }
}
