package mjkarbasian.moshtarimadar.Debtors;

import android.app.ActivityOptions;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import mjkarbasian.moshtarimadar.Adapters.DebtorAdapter;
import mjkarbasian.moshtarimadar.Customers.DetailCustomer;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 12/19/2016.
 */
public class DebtorsList extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    DebtorAdapter mDebaterAdapter;
    ListView mListView;
    private String searchQuery;
    private int FRAGMENT_DEBATERS_LIST_LOADER = 9;
    private String LOG_TAG = DebtorsList.class.getSimpleName();
    private String[] mProjection = new String[]{
            KasebContract.Sales.TABLE_NAME + "." + KasebContract.Sales._ID,
            KasebContract.Sales.TABLE_NAME + "."+ KasebContract.Sales.COLUMN_CUSTOMER_ID,
            "sum(" + KasebContract.DetailSale.TABLE_NAME + "."+KasebContract.DetailSale.COLUMN_TOTAL_DUE + ") as total",
            "sum(" + KasebContract.DetailSale.TABLE_NAME + "."+KasebContract.DetailSale.COLUMN_TOTAL_PAID + ") as total"
    };
    private String sortOrder;

    public DebtorsList() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_debtors, container, false);
        mListView =(ListView) rootView.findViewById(R.id.list_view_debater);
        mDebaterAdapter = new DebtorAdapter(getActivity(), null, 0);
        mListView.setAdapter(mDebaterAdapter);
        TextView emptyText = (TextView) rootView.findViewById(R.id.empty_text_view);
        emptyText.setText(getActivity().getResources().getString(R.string.empty_list_text));
        emptyText.setVisibility(View.VISIBLE);
        mListView.setEmptyView(emptyText);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    View sharedView = view.findViewById(R.id.item_card_customer_avater);
                    Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, sharedView.getTransitionName()).toBundle();

                    Intent intent = new Intent(getActivity(), DetailCustomer.class)
                            .setData(KasebContract.Customers.buildCustomerUri(cursor.
                                    getLong(cursor.getColumnIndex(KasebContract.Sales.TABLE_NAME + "." + KasebContract.Sales.COLUMN_CUSTOMER_ID))));
                    startActivity(intent, bundle);
                }
            }
        });
        return rootView;
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, "onStart");
        super.onStart();
        updateList();
    }

    public void getSearchQuery(String query) {
        searchQuery = query;
        //add a query to fill in selection arg...
        updateList();
    }

    public void getSortOrder(int id) {
        switch (id) {
            case R.id.menu_sort_newest:
                sortOrder = null;
                break;
            case R.id.menu_sort_rating:
                sortOrder = null;
                break;
        }
        updateList();
    }

    private void updateList() {
        getLoaderManager().restartLoader(FRAGMENT_DEBATERS_LIST_LOADER, null, this);
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FRAGMENT_DEBATERS_LIST_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        String whereClause;
        String[] selectArg = null;
        if (searchQuery != null) {
            whereClause = mProjection[1] + " LIKE ? " + " OR " + mProjection[2] + " LIKE ? " ;
            selectArg = new String[2];
            selectArg[0] = "%" + searchQuery + "%";
            selectArg[1] = "%" + searchQuery + "%";
        } else {
            whereClause = null;
        }
        return new CursorLoader(getActivity(), KasebContract.DetailSale.isBalanceDetailSale(false),
                mProjection, whereClause, selectArg, sortOrder);    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mDebaterAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mDebaterAdapter.swapCursor(null);

    }


}
