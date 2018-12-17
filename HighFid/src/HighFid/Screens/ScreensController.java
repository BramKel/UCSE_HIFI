package HighFid.Screens;

//Personal Imports
import HighFid.Model.Event;
import HighFid.Model.Model;
import HighFid.Model.Sport;
import HighFid.Screens.Calendar.CalendarController;
import HighFid.Screens.ChallengeDetail.ChallengeDetailController;
import HighFid.Screens.EventDetail.EventDetailController;
import HighFid.Screens.EventEnrolment.EventEnrolmentController;
import HighFid.Screens.EventsPage.EventsPageController;
import HighFid.Screens.MainMenu.MainMenuController;
import HighFid.Screens.SportDetail.SportDetailController;
import HighFid.Screens.SportEditor.SportEditorController;
import HighFid.Screens.SportEnrolment.SportEnrolmentController;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

//Java Imports
import java.util.HashMap;

/**
 * Class ScreensController
 * Controller that allows switching between screens
 *
 * @author Tim Mesotten
 */
public class ScreensController extends StackPane {

    //Private members
    private Model _model;
    private HashMap<String, Node> screens = new HashMap<>();

    //Update components when neccesary
    private boolean newLogin;

    private String[] screenHistory = new String[0];



    /**
     * Controller
     * Creates a new ScreensController
     */
    public ScreensController(Model model) {
        super();
        _model = model;
        newLogin = true;

        //ADD NEW SCREENS HERE
        loadScreen("Login", "Login/Login.fxml");
        loadScreen("MainMenu", "MainMenu/MainMenu.fxml");
        loadScreen("ChallengeOverview", "ChallengeOverview/ChallengeOverview.fxml");
        loadScreen("Profile", "Profile/Profile.fxml");
        loadScreen("SportKaart", "SportKaart/SportKaart.fxml");
        loadScreen("Enrolment", "Enrolment/Enrolment.fxml");
        loadScreen("EventsPage", "EventsPage/EventsPage.fxml");
        loadScreen("Inschrijvingen", "Inschrijvingen/Inschrijvingen.fxml");
        loadScreen("ScanQR", "ScanQR/ScanQR.fxml");
    }

    //Setter
    public void setLogin(boolean login) {
        newLogin = login;
        if(newLogin){
            unloadScreen("Profile");
            loadScreen("Profile", "Profile/Profile.fxml");
            unloadScreen("SportKaart");
            loadScreen("SportKaart", "SportKaart/SportKaart.fxml");
            System.out.println("Reloaded");
            newLogin = false;
        }
    }

    /**
     * Public function getScreen
     * Retrieve a specific screen
     *
     * @param name the name of the screen
     * @return the screen that corresponds to the given name
     */
    public Node getScreen(String name) {
        return screens.get(name);
    }

    /**
     * Public function loadScreen
     * Load FXML-file and add screen to the collection
     *
     * @param name the name of the screen
     * @param resource the FXML-file of the screen
     * @return true if screen was added, else false
     */
    private boolean loadScreen(String name, String resource) {
        try {
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource(resource));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            screens.put(name, loadScreen);
            return true;
        } catch(Exception e) {
            System.out.println("ScreensController: loadScreen, error message: " + e.getMessage());
            return false;
        }
    }

    /**
     * Public function setScreen
     * Display the required screen
     *
     * @param name the name of the screen
     * @return true if screen is successfully displayed, else false
     */
    private boolean setScreen(final String name) {
        if (screens.get(name) != null) {   //screen loaded
            final DoubleProperty opacity = opacityProperty();

            if (!getChildren().isEmpty()) {    //if there is more than one screen
                Timeline fade = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 1.0)),
                        new KeyFrame(new Duration(200), t -> {
                            getChildren().remove(0);                    //remove the displayed screen
                            getChildren().add(0, screens.get(name));     //add the screen
                            Timeline fadeIn = new Timeline(
                                    new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                                    new KeyFrame(new Duration(400), new KeyValue(opacity, 1.0)));
                            fadeIn.play();
                        }, new KeyValue(opacity, 0.0)));
                fade.play();

            } else {
                setOpacity(0.0);
                getChildren().add(screens.get(name));       //no one else been displayed, then just show
                Timeline fadeIn = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(opacity, 0.0)),
                        new KeyFrame(new Duration(200), new KeyValue(opacity, 1.0)));
                fadeIn.play();
            }
            AddToScreenHistory(name);
            return true;
        } else {
            System.out.println("Screen hasn't been loaded!");
            return false;
        }
    }
    private void AddToScreenHistory(String name ) {
        if(screenHistory.length == 0 || name.compareTo(screenHistory[screenHistory.length - 1]) != 0)
        {
            int amount = screenHistory.length;
            String []newScreens = new String[amount + 1];
            for(int i = 0; i < amount; i++) {
                newScreens[i] = screenHistory[i];
            }
            newScreens[amount] = name;
            screenHistory = newScreens;
        }
    }
    public void goToPreviousScreen()
    {
        int amount = screenHistory.length - 1;
        String[] newScreens = new String[amount];
        for(int i = 0; i < amount; i++) {
            newScreens[i] = screenHistory[i];
        }
        screenHistory = newScreens;
        if(screenHistory[amount-1].contains("SportDetail")) {
            //recheck for enrolment
            ShowSportDetail(screenHistory[amount - 1].substring(11));
        } else if(screenHistory[amount-1].contains("EventDetail")) {
            ShowEventDetail(screenHistory[amount-1].substring(11));
        } else {
            setScreen(screenHistory[amount-1]);
        }
    }
    /**
     * Public function unloadScreen
     * Removes a given screen from the collection
     *
     * @param name the name of the screen
     * @return true if screen was successfully removed, else false
     */
    private boolean unloadScreen(String name) {
        if (screens.remove(name) == null) {
            System.out.println("Screen didn't exist");
            return false;
        } else {
            return true;
        }
    }

    //ADD NAVIGATION METHODS HERE
    public void showLogin() {
        setScreen("Login");
    }

    public void showEventsPage() {
        if(screens.containsKey("EventsPage"))
            screens.remove("EventsPage");
        try{
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("EventsPage/EventsPage.fxml"));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            ((EventsPageController) myScreenController).Refresh();
            screens.put("EventsPage", loadScreen);

            setScreen("EventsPage");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        setScreen("EventsPage");
    }

    public void showMainMenu(){
        if(screens.containsKey("MainMenu"))
            screens.remove("MainMenu");
        try{
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("MainMenu/MainMenu.fxml"));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            ((MainMenuController) myScreenController).Refresh();
            screens.put("MainMenu", loadScreen);

            setScreen("MainMenu");
        } catch (Exception e) {
            System.out.println(e.toString());
        }
        setScreen("MainMenu");
    }

    public void showChallengeOverview(){
        setScreen("ChallengeOverview");
    }

    public void ShowSportDetail(String name) {
        if(screens.containsKey("SportDetail" + name))
            screens.remove("SportDetail" + name);

        try{
            Sport s = _model.sportByName(name);
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("SportDetail/SportDetail.fxml"));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            ((SportDetailController) myScreenController).ShowSport(s);
            screens.put("SportDetail" + name, loadScreen);

            setScreen("SportDetail"+name);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void ShowSportEditor(Sport s) {
        if(screens.containsKey("SportEditor" + s.name))
            screens.remove("SportEditor" + s.name);

        try{
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("SportEditor/SportEditor.fxml"));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            ((SportEditorController) myScreenController).ShowSport(s);
            screens.put("SportEditor" + s.name, loadScreen);

            setScreen("SportEditor"+s.name);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void ShowEventDetail(String name) {
        if(screens.containsKey("EventDetail" + name))
            screens.remove("EventDetail" + name);

        try{
            Event e = _model.eventByName(name);
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("EventDetail/EventDetail.fxml"));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            ((EventDetailController) myScreenController).ShowEvent(e);
            screens.put("EventDetail" + name, loadScreen);

            setScreen("EventDetail"+name);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }

    public void showAchievementDetail(int id) {
        if(screens.containsKey("ChallengeDetail" + id))
            screens.remove("ChallengeDetail" + id);

        try{
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("ChallengeDetail/ChallengeDetail.fxml"));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            ((ChallengeDetailController) myScreenController).ShowDetail(id);
            screens.put("ChallengeDetail" + id, loadScreen);

            setScreen("ChallengeDetail"+id);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void showSportEnrollment(String name) {
        if(screens.containsKey("SportEnrollment" + name))
            screens.remove("SportEnrollment" + name);
        try {
            Sport s = _model.sportByName(name);
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("SportEnrolment/SportEnrolment.fxml"));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            ((SportEnrolmentController) myScreenController).ShowSport(s);
            screens.put("SportEnrollment" + name, loadScreen);

            setScreen("SportEnrollment"+name);
        } catch (Exception e) {
            System.out.println("ScreensController: showSportEnrollment, Error message: " + e.getMessage());
        }
    }
    public void showEventEnrollment(String name) {
        if(screens.containsKey("EventEnrollment" + name))
            screens.remove("EventEnrollment" + name);
        try {
            Event e = _model.eventByName(name);
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("EventEnrolment/EventEnrolment.fxml"));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            ((EventEnrolmentController) myScreenController).ShowEvent(e);
            screens.put("EventEnrollment" + name, loadScreen);

            setScreen("EventEnrollment"+name);
        } catch (Exception e) {
            System.out.println("ScreensController: showEventEnrollment, Error message: " + e.getMessage());
        }
    }


    public void showCalendar(int month) {
        if(screens.containsKey("Calendar" + month))
            screens.remove("Calendar" + month);

        try{
            FXMLLoader myLoader = new FXMLLoader(getClass().getResource("Calendar/Calendar.fxml"));
            Parent loadScreen = myLoader.load();
            ControlledScreen myScreenController = myLoader.getController();
            myScreenController.setScreenParent(this);
            myScreenController.setModel(_model);
            ((CalendarController) myScreenController).buildCalendar(month);
            screens.put("Calendar" + month, loadScreen);

            setScreen("Calendar"+month);
        } catch (Exception e) {
            System.out.println(e.toString());
        }
    }
    public void showProfile() {
        setScreen("Profile");
    }

    public void showSportKaart() {
        setScreen("SportKaart");
    }

    public void showEnrollment() {
        setScreen("Enrolment");
    }

    public void showInschrijvingen() { setScreen("Inschrijvingen"); }

    public void showScanQR() { setScreen("ScanQR"); }
}
