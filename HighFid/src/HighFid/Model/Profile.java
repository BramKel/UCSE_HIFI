package HighFid.Model;

//Personal Imports
import HighFid.Model.FileIO.JsonIO;
import org.json.simple.JSONObject;

/**
 * Class Profile
 * Represents a user profile
 *
 * @author Tim Mesotten
 */
public class Profile {

    //Public enum
    public enum ID_types {STUD_NO_SP, STUD_SP, SPORTLK, COORD, NONE}

    //Private members
    private ID_types id;
    private String fName;
    private String lName;
    private String org;
    private SportKaart sportKaart;

    /**
     * Constructor
     * Creates a new Profile
     */
    public Profile() {
        initialize();
    }

    //Private functions
    /**
     * Private method initialize
     * Initializes the profile
     */
    private void initialize() {
        this.setId(ID_types.NONE);
        this.setfName("");
        this.setlName("");
        this.setOrg("");
        this.setSportKaart(new SportKaart());
    }

    //Public functions
    /**
     * Public function checkProfile
     * Checks the login credentials of the user
     *
     * @param login the user login
     * @param pass the user password
     * @return true if valid idenfitication, else false
     */
    public boolean checkProfile(String login, String pass) {
        if(login.equals("StudentSK") && pass.equals("Test123")) {
            //STUDENT_SK
            this.setId(ID_types.STUD_SP);
            return true;
        } else if(login.equals("StudentNoSK") && pass.equals("Test123")) {
            //STIDENT_NO_SK
            this.setId(ID_types.STUD_NO_SP);
            return true;
        } else if(login.equals("SportLK") && pass.equals("Test123")) {
            //SPORTLK
            this.setId(ID_types.SPORTLK);
            return true;
        } else if(login.equals("Coord") && pass.equals("Test123")) {
            //COORD
            this.setId(ID_types.COORD);
            return true;
        } else {
            //Invalid
            this.setId(ID_types.NONE);
            return false;
        }
    }

    /**
     * Public function logout
     * Resets the user profile
     */
    public void logout(){
        initialize();
    }

    /**
     * Public function toJSON
     * Method that converts Profile to JSONObject
     *
     * @param fileName the name of the file
     * @return true if successful write, false otherwise
     */
    @SuppressWarnings("unchecked")
    public boolean toJSON(String fileName) {
        try{
            JSONObject JSONProfile = new JSONObject();
            JSONProfile.put("id", this.getId().toString());
            JSONProfile.put("fname", this.getfName());
            JSONProfile.put("lname", this.getlName());
            JSONProfile.put("org", this.getOrg());
            JSONProfile.put("sportkaart", this.sportKaart.toJSON());
            JsonIO.saveJSONFile(fileName, JSONProfile);
            return true;
        }catch(Exception e){
            System.out.println("Profile: toJSON: " + e.getMessage());
            return false;
        }
    }

    /**
     * Public static function checkJSON
     * Method that checks if Object has all the fields
     *
     * @param JSONProfile JSONObject that has to be checked
     * @return true if JSONProfile has required fields, false otherwise
     */
    public static boolean checkJSON(JSONObject JSONProfile) {
        boolean ok = JSONProfile.get("id") != null &&
                JSONProfile.get("fname") != null &&
                JSONProfile.get("lname") != null &&
                JSONProfile.get("org") != null &&
                JSONProfile.get("sportkaart") != null;
        if(ok) {
            ok = SportKaart.checkJSON((JSONObject) JSONProfile.get("sportkaart"));
        }
        return ok;
    }

    /**
     * Private function fromJSON
     * Method that converts JSONObject to Profile-object
     *
     * @param fileName The name of the file
     * @return True if successful read, false otherwise
     */
    public boolean fromJSON(String fileName) {
        JSONObject JSONProfile;
        try{
            JSONProfile = JsonIO.readJSONFile(fileName);
            if (Profile.checkJSON(JSONProfile)) {
                this.setId(ID_types.valueOf((String) JSONProfile.get("id")));
                this.setfName((String) JSONProfile.get("fname"));
                this.setlName((String) JSONProfile.get("lname"));
                this.setOrg((String) JSONProfile.get("org"));
                this.getSportKaart().fromJSON((JSONObject) JSONProfile.get("sportkaart"));
                return true;
            } else {
                return false;
            }
        } catch(Exception e){
            System.out.println("Profile: fromJSON: " + e.getMessage());
            return false;
        }
    }

    //Getters & Setters
    public ID_types getId() {
        return id;
    }
    public void setId(ID_types identifier) {
        this.id = identifier;
    }
    public String getfName() {
        return fName;
    }
    public void setfName(String fName) {
        this.fName = fName;
    }
    public String getlName() {
        return lName;
    }
    public void setlName(String lName) {
        this.lName = lName;
    }
    public String getOrg() {
        return org;
    }
    public void setOrg(String org) {
        this.org = org;
    }
    public SportKaart getSportKaart() {
        return sportKaart;
    }
    public void setSportKaart(SportKaart sportKaart) {
        this.sportKaart = sportKaart;
    }
}
