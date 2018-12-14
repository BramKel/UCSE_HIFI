package HighFid.Model;

//Personal Imports
import HighFid.Model.FileIO.JsonIO;
import org.json.simple.JSONObject;

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
    private Sport sport;

    /**
     * Constructor
     * Creates a new Model
     */
    public Model() {
        this.setProfile(new Profile());

        //this.fromJSON("Test.json");
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
            JSONModel.put("sport", sport.toJSON());
            JsonIO.saveJSONFile(fileName, JSONModel);
            return true;
        }catch(Exception e){
            System.out.println("Model: toJSON: " + e.getMessage());
            return false;
        }
    }

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
                this.sport = new Sport((JSONObject) JSONModel.get("sport"));
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
