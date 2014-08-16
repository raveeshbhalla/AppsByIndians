package apps.indians;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Raveesh on 09/08/14.
 */
public class Helper extends SQLiteOpenHelper {

    public static SQLiteDatabase DB;
    private static final String DB_NAME = "AppsByIndiansDB";
    private static final int VERSION = 1;

    /**
     * A helper class to manage database creation and version management.
     *
     * @param context
     */
    public Helper(Context context) {
        super(context, DB_NAME, null, VERSION);
        DB = getWritableDatabase();
    }

    /**
     * Called when the SQLiteDatabase is created
     *
     * @param db SQLiteDatabase The database created
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        DB = db;

        /**
         * Create all the tables you would require over here
         */
        DB.execSQL(AppsTable.CREATE_TABLE);
    }

    /**
     * When a database structure is upgraded, this method is called. It must be utilized to appropriately
     * make the changes required, prior to which any data that needs to be saved should be done so.
     *
     * @param db SQLiteDatabase The database being used
     * @param oldVersion int The old database's version
     * @param newVersion int The new database version
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /**
         * No alteration code is prepared right now
         */
    }
}
