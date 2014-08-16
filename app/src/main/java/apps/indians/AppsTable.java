package apps.indians;

import android.content.ContentValues;
import android.database.Cursor;
import android.util.Log;

/**
 * Created by Raveesh on 09/08/14.
 */
public class AppsTable {

    private static String TABLE = "AppsTable";
    public static String ID = "ID";
    public static String NAME = "Name";
    public static String TEXT = "Text";
    public static String DATE = "Date";
    public static String PACKAGE = "Package";

    public static String CREATE_TABLE = "CREATE TABLE " + TABLE + " (" + ID + ", " + NAME + ", " + TEXT + ", " + DATE + ", " + PACKAGE + ")";

    /**
     * Store an App's data in the database
     *
     * @param app
     */
    public static void insert(App app) {
        ContentValues values = new ContentValues();
        values.put(NAME, app.NAME);
        values.put(TEXT, app.TEXT);
        values.put(DATE, app.DATE);
        values.put(PACKAGE, app.PACKAGE);

        /**
         * First checking if the app already exists in the database, by searching for it's package name.
         * If it does, it's values is updated with the new content. If it doesn't, the app is stored.
         */
        if (update(app.PACKAGE, values) == 0) {
            Helper.DB.insert(TABLE, null, values);
            Log.d("AppsTable",String.format("App %s has been inserted to database",app.NAME));
        }
        else{
            Log.d("AppsTable",String.format("App %s already exists in database, contents have been updated",app.NAME));
        }
    }

    /**
     * Update the contents of an app in the database if it has already been stored
     *
     * @param packageName
     * @param values
     * @return The number of rows affected. Hence, if an app isn't already stored, return would be 0
     */
    public static long update(String packageName, ContentValues values) {
        if (values != null)
            return Helper.DB.update(TABLE, values, PACKAGE + " LIKE '" + packageName+"'", null);
        else
            return 0;
    }

    /**
     * Returns a Cursor pointing to all the apps stored
     *
     * @return
     */
    public static Cursor getAllApps() {
        String select = "SELECT * FROM " + TABLE + " ORDER BY " + DATE + " DESC";
        return Helper.DB.rawQuery(select,null);
    }
}
