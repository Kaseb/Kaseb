package mjkarbasian.moshtarimadar.Setting;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.Others.DrawerActivity;
import mjkarbasian.moshtarimadar.R;

public class MySetting extends DrawerActivity {

    //region declare values
    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    //endregion declare values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fragmentManager.beginTransaction().replace(R.id.container, new PreferenceHeader()).commit();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        try {
            if (fragmentManager.findFragmentByTag("typeFragments").isResumed())
                super.onBackPressed();
            else
                Utility.activityOnBackExit(this);

        } catch (Exception e) {
            Utility.activityOnBackExit(this);
        }
    }
}
