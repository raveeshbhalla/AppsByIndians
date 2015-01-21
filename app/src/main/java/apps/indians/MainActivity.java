package apps.indians;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends Activity {

    private final String API_LINK = "http://appsculture.com/appsbyindians/apps.json";
    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * SQLiteOpenHelper object that we'll be using
         */
        Helper helper = new Helper(this);

        /**
         * Debugging your application? This is how you can see for yourself what's going on
         */
        Log.d("MainActivity","Inside onCreate");

        /**
         * Rabi should cover the UI elements in his talk. All I'm doing here is linking a TextView
         * object that has been set up in the activity_main XML file with the TextView object, so I
         * can manipulate it as per my requirements.
         */
        mTextView = (TextView) findViewById(R.id.appName);

        /**
         * To get the data from the server, we'll be using the Volley Library. A tutorial on how to use it
         * can be found at this link: https://developer.android.com/training/volley/index.html
         *
         * The RequestQueue manages worker threads for running the network operations, reading from and
         * writing to the cache, and parsing responses.
         */

        RequestQueue queue = Volley.newRequestQueue(this);

        /**
         * In the tough old days, a developer would need to create an AsyncTask to fetch data from a web service.
         * Now, it is as simple as the following lines
         */
        JsonObjectRequest request = new JsonObjectRequest
                (Request.Method.GET, API_LINK, null, new Response.Listener<JSONObject>() {

                    /**
                     * This method is called when a request successfully receives a response
                     *
                     * @param response JSONObject The response received from the server
                     */
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray posts = response.getJSONArray("posts");
                            int length = posts.length();

                            /**
                             * Creating an App object of the latest featured app, then setting it's name to the TextView
                             */
                            App app = new App(posts.getJSONObject(0));
                            AppsTable.insert(app);
                            mTextView.setText(app.NAME);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    /**
                     * This method is called when an error (such as a 404 or a 503) occurs while performing a request
                     * @param error
                     */
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("VOLLEY", "Error calling API:" + error.toString());
                    }
                });

        /**
         * Once the request has been defined, all a developer must do is add it to the RequestQueue
         */
        queue.add(request);

        /**
         * To see stored data, we'll remove the above request and instead get an App from the database
         */
        Cursor c = AppsTable.getAllApps();
        if (c.getCount()>0) {
            c.moveToFirst();
            String name = c.getString(c.getColumnIndex(AppsTable.NAME));
            mTextView.setText(name);
        }
    }
}
