package mjkarbasian.moshtarimadar.Debtors;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.Others.DrawerActivity;
import mjkarbasian.moshtarimadar.R;

public class Debtors extends DrawerActivity {

    FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment debatersList = new DebtorsList();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) handleIntent(intent);
        else
            fragmentManager.beginTransaction().replace(R.id.container, debatersList, "debatersList").commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        DebtorsList queryFragment = (DebtorsList) fragmentManager.findFragmentByTag("debatersList");
        queryFragment.getSearchQuery(query);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_debaters, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_button).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        return true;
    }

    public void call_debator(View view) {
        Snackbar.make(view, "call intent", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
        view.getId();
        view.getTag();
    }

    public void message_dabator(View view) {
        Snackbar.make(view, "message intent", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    public void share_dabator(View view) {
        Snackbar.make(view, "share intent", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    @Override
    public void onBackPressed() {
        Utility.activityOnBackExit(this);
    }
}
