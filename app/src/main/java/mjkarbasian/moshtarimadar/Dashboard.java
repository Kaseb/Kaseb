package mjkarbasian.moshtarimadar;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.Toolbar;

import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.Others.DrawerActivity;

public class Dashboard extends DrawerActivity {

    //region declare values
    String kasebPREFERENCES = "kasebProfile";
    SharedPreferences kasebSharedPreferences;
    SharedPreferences.Editor editor;
    private KasebDashBoard kasebDashBoard = new KasebDashBoard();
    private FragmentManager fragmentManager = getSupportFragmentManager();
    //endregion declare values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Utility.initializer(this);

        //region handle sharepreference
        kasebSharedPreferences = getSharedPreferences(kasebPREFERENCES, MODE_PRIVATE);
        editor = kasebSharedPreferences.edit();

        try {
            String mTest = kasebSharedPreferences.getString("numberOfEntrance", null);
            editor.putString("numberOfEntrance",
                    String.valueOf(Double.valueOf(kasebSharedPreferences.getString("numberOfEntrance", null)) + 1));
            editor.apply();
        } catch (Exception e) {
            editor.putString("numberOfEntrance", "1");
            editor.apply();
        }
        //endregion handle sharepreference

        fragmentManager.beginTransaction().replace(R.id.container, kasebDashBoard, "KasebDashBoard").commit();
    }

    @Override
    public void onBackPressed() {
        Utility.activityOnBackExit(this);
    }
}
