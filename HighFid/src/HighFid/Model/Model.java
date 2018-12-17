package HighFid.Model;

//Personal Imports
import HighFid.Model.FileIO.JsonIO;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.sql.Time;
import java.time.DayOfWeek;

/**
 * Class Model
 * Represents the dummy data of the application
 *
 * @author Tim Mesotten
 */
public class Model {

    //Private members
    private String name;
    private Profile profile;
    private Sport[] sports = new Sport[0];
    private Event[] events = new Event[0];

    //Using simple arrays now, but could easily be read in from an actual database after the prototype stage
    private String[] achievementNames = {"Eerste stapjes", "Groentje", "Expert", "Balsporter", "Atleet", "Zwemmer",
            "Danser", "Skivakantie", "Multidisciplinair"};
    private String[] achievementDesc = {"Neem deel aan 1 sessie van eender welke sport. (1/1)",
            "Neem deel aan 5 sessies van eender welke sport. (5/5)",
            "Neem deel aan 10 sessies van eender welke sport. (7/10)",
            "Neem deel aan 5 sessies van balsporten. (5/5)", "Neem deel aan 5 sessies van atletiek. (0/5)",
            "Neem deel aan 5 sessies van zwemmen. (1/5)", "Neem deel aan 5 sessies van dansen. (0/5)",
            "Ga mee op skivakantie. (1/1)", "Neem deel aan 3 sessies van verschillende sporten. (3/3)"};
    private boolean[] achievementGot = { true, true, false, true, false, false, false, true, true };

    /**
     * Constructor
     * Creates a new Model
     */
    public Model() {
        this.setProfile(new Profile());
        ReadSports();
        ReadEvents();
        //this.fromJSON("Test.json");
    }
    public void ReplaceSport(String oldName, Sport s) {
        if(oldName.length() == 0)
            oldName = "Badminton";
        for(int i = 0; i < sports.length; i++) {
            if(sports[i].name.toLowerCase().compareTo(oldName.toLowerCase()) == 0) {
                sports[i] = s;
                sports[i].isRemoved = false;
            }
        }
    }

    //Getters & Setters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Profile getProfile() {
        return profile;
    }
    public void setProfile(Profile profile) {
        this.profile = profile;
        if(profile.getId() != Profile.ID_types.COORD) {
            for(int i = 0; i < sports.length; i++)
                sports[i].isRemoved = false;
        }

    }
    public String[] getAchNames() { return this.achievementNames; }
    public String[] getAchDesc() { return this.achievementDesc; }
    public boolean[] getAchGot() { return this.achievementGot; }

    public void ReadSports() {
        try{
            JSONObject sports = JsonIO.readJSONFile("sports.json");
            JSONArray sportsList = ((JSONArray) sports.get("sportsList"));
            for(int i = 0; i < sportsList.size(); ++i) {
                JSONObject JSONSport = ((JSONObject) sportsList.get(i));
                Sport s = new Sport();
                s.fromJSON(JSONSport);
                AddSport(s);
            }
        } catch (Exception e) {
            System.out.println(e.toString());

        }

    }
    public Event[] getEvents() {return events;}
    public void ReadEvents() {
        try{
            JSONObject sports = JsonIO.readJSONFile("events.json");
            JSONArray sportsList = ((JSONArray) sports.get("eventsList"));
            for(int i = 0; i < sportsList.size(); ++i) {
                JSONObject JSONSport = ((JSONObject) sportsList.get(i));
                Event e = new Event();
                e.fromJSON(JSONSport);
                AddEvent(e);
            }
        } catch (Exception e) {
            System.out.println(e.toString());

        }
    }
    private void AddEvent(Event e) {
        Event[] newEvents = new Event[events.length +1];
        for(int i = 0; i < events.length; i++)
            newEvents[i] = events[i];
        newEvents[events.length] = e;
        events = newEvents;
    }
    private void AddSport(Sport s) {
        Sport[] newSports = new Sport[sports.length + 1];
        for(int i = 0; i < sports.length; i++) {
            newSports[i] = sports[i];
        }
        newSports[sports.length] = s;
        sports = newSports;
    }

    //Public functions
    /**
     * Public function toJSON
     * Method that converts Model to JSONObject
     *
     * @param fileName the name of the file
     * @return true if successful write, false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean toJSON(String fileName) {
        try{
            JSONObject JSONModel = new JSONObject();
            JSONModel.put("name", this.getName());
            //JSONModel.put("sport", sport.toJSON());
            JsonIO.saveJSONFile(fileName, JSONModel);
            return true;
        }catch(Exception e){
            System.out.println("Model: toJSON: " + e.getMessage());
            return false;
        }
    }
    public Sport[] getSports() {return sports;}
    /**
     * Public static function checkJSON
     * Method that checks if Object has all the fields
     *
     * @param JSONModel JSONObject that has to be checked
     * @return true if JSONModel has required fields, false otherwise
     */
    public static boolean checkJSON(JSONObject JSONModel) {
        boolean ok = JSONModel.get("name") != null &&
                JSONModel.get("sport") != null;
        if(ok) {
            ok = Sport.checkJSON((JSONObject) JSONModel.get("sport"));
        }
        return ok;
    }

    public Sport sportByName(String name) {
        for(int i = 0; i < sports.length; i++) {
            if(sports[i].name.compareTo(name) == 0)
                return sports[i];
        }
        return new Sport();
    }
    public Event eventByName(String name) {
        for(int i = 0; i < events.length; i++) {
            if(events[i].naam.compareTo(name) == 0)
                return events[i];
        }
        return new Event();
    }

    /**
     * Private function fromJSON
     * Method that converts JSONObject to Model-object
     *
     * @param fileName The name of the file
     * @return True if successful read, false otherwise
     */
    public boolean fromJSON(String fileName) {
        JSONObject JSONModel;
        try{
            JSONModel = JsonIO.readJSONFile(fileName);
            if (Model.checkJSON(JSONModel)) {
                this.setName((String) JSONModel.get("name"));
                //this.sport = new Sport((JSONObject) JSONModel.get("sport"));
                return true;
            } else {
                return false;
            }
        } catch(Exception e){
            System.out.println("Model: fromJSON: " + e.getMessage());
            return false;
        }
    }
}
