package HighFid.Model;

//Personal Imports
import org.json.simple.JSONObject;

/**
 * Class SportKaart
 * Represents a user's sportkaart
 *
 * @author Tim Mesotten
 */
public class SportKaart {

    //Public enum
    public enum SK_STATUS {Active, Inactive, NONE}

    //Private members
    private SK_STATUS skStatus;
    private String activated;
    private String validUntil;

    /**
     * Constructor
     * Creates a Sportkaart
     */
    public SportKaart() {
        initialize();
    }

    //Private methods
    /**
     * Private method initialize
     * Initializes the sportkaart
     */
    private void initialize() {
        this.setSkStatus(SK_STATUS.NONE);
        this.setActivated("");
        this.setValidUntil("");
    }

    //Public Methods
    public JSONObject toJSON() {
        JSONObject JSONSportkaart = new JSONObject();
        JSONSportkaart.put("skstatus", this.getSkStatus().toString());
        JSONSportkaart.put("activated", this.getActivated());
        JSONSportkaart.put("validuntil", this.getValidUntil());
        return JSONSportkaart;
    }

    public static boolean checkJSON(JSONObject JSONSportkaart) {
        boolean ok = JSONSportkaart.get("skstatus") != null &&
                JSONSportkaart.get("activated") != null &&
                JSONSportkaart.get("validuntil") != null;
        return ok;
    }

    public void fromJSON(JSONObject JSONSportkaart) {
        this.setSkStatus(SK_STATUS.valueOf((String) JSONSportkaart.get("skstatus")));
        this.setActivated((String) JSONSportkaart.get("activated"));
        this.setValidUntil((String) JSONSportkaart.get("validuntil"));
    }

    //Getters & Setters
    public SK_STATUS getSkStatus() {
        return skStatus;
    }
    public void setSkStatus(SK_STATUS skStatus) {
        this.skStatus = skStatus;
    }
    public String getActivated() {
        return activated;
    }
    public void setActivated(String activated) {
        this.activated = activated;
    }
    public String getValidUntil() {
        return validUntil;
    }
    public void setValidUntil(String validUntil) {
        this.validUntil = validUntil;
    }
}
