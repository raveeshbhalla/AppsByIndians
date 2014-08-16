package apps.indians;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Raveesh on 09/08/14.
 */
public class App {
    public String NAME;
    public String TEXT;
    public String PACKAGE;
    public String DATE;

    public App(JSONObject object) throws JSONException {
        NAME = object.getString("Name");
        TEXT = object.getString("Text");
        DATE = object.getString("Date");
        PACKAGE = object.getString("Package");
    }
}
