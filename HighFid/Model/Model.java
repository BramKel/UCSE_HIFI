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

    /**
     * Constructor
     * Creates a new Model
     */
    Model() {
        this.setName("User-Centered Software Engineering");
    }

    //Getters & Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
        return JSONModel.get("name") != null;
        //Check deeper classes via CLASS.checkJSON(JSONModel.get("what you need"))
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
                return true;
            } else {
                return false;
            }
        } catch(Exception e){
            System.out.println("Model: romJSON: " + e.getMessage());
            return false;
        }
    }
}
