package HighFid.Screens;

//Personal Imports
import HighFid.Model.Model;

/**
 * Interface ControlledScreen
 * Allows switching between screens
 *
 * @author Tim Mesotten
 */
public interface ControlledScreen {

    /**
     * Public Method setScreenParent
     * Allows injection of Parent screenPane
     *
     * @param screenPage the controller that contains the screens
     */
    public void setScreenParent(ScreensController screenPage);

    /**
     * Public Method setModel
     * Allows the screen to have access to the model
     *
     * @param model the model of the application
     */
    public void setModel(Model model);
}
