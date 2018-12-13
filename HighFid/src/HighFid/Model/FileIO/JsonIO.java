package HighFid.Model.FileIO;

//Personal Imports
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

//Java Imports
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The JsonIO for the Model.
 * This class allows saving and loading of JSON files.
 *
 * @author Tim Mesotten
 */
public class JsonIO {

    //Public methods
    /**
     * Public function saveJSONFile
     * Saves to a .json-file
     *
     * @param fileName          the directory of the file
     * @param JSONModelObject   the object that has to be saved
     * @throws Exception        when something goes wrong with loading
     */
    public static void saveJSONFile(String fileName, JSONObject JSONModelObject) throws Exception {
        //Check extension
        int j = fileName.lastIndexOf('.');
        if (j == -1) {
            fileName = fileName + ".json";
        } else {
            fileName = fileName.substring(0, j) + ".json";
        }

        //Write to file
        try {
            FileWriter file = new FileWriter(fileName);
            file.write(JSONModelObject.toJSONString());
            file.close();
        } catch (IOException e) {
            //Write error
            System.out.println("JsonIO: saveJSONFile: Something went wrong when saving, error message: \"" + e.getMessage() + "\"");
            throw e;
        }
    }

    /**
     * Public function readJSONFile
     * Reads from a .json-file
     *
     * @param fileName      the directory of the file
     * @return JSONObject   the loaded object
     * @throws Exception    when something goes wrong with loading
     */
    public static JSONObject readJSONFile(String fileName) throws Exception {
        JSONObject JSONNautilusObject = null;
        try {
            //Check Extension
            String extension;
            int i = fileName.lastIndexOf('.');
            if (i > 0) {
                extension = fileName.substring(i + 1);
            } else {
                throw new Exception("This is not a file.");
            }
            if (!extension.equals("json")) {
                throw new Exception("The file is not a JSON file.");
            } else {
                //Read from file
                FileReader file = new FileReader(fileName);
                JSONParser parser = new JSONParser();
                JSONNautilusObject = (JSONObject) parser.parse(file);
            }
        } catch (Exception e) {
            //Read error
            System.out.println("JsonIO: readJSONFile: Something went wrong when loading, error message: \"" + e.getMessage() + "\"");
            throw e;
        }
        return JSONNautilusObject;
    }
}
