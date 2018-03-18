package dragger.universal.com.bookdatabase;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.orm.Database;
import com.orm.SugarApp;

/**
 * Created by Gheorghe Mitrea on 8/3/18.
 */

public class App extends MultiDexApplication {
    private Database database;
    private static App sugarContext;

    public void onCreate(){
        super.onCreate();
        sugarContext = this;
        this.database = new Database(this);
    }

    public void onTerminate(){
        if (this.database != null) {
            this.database.getDB().close();
        }
        super.onTerminate();
    }

    public static App getSugarContext(){
        return sugarContext;
    }

    protected Database getDatabase() {
        return database;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);


    }

}
