package mjkarbasian.moshtarimadar.Customers;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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

import mjkarbasian.moshtarimadar.Adapters.CustomerBillAdapter;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.R;
import mjkarbasian.moshtarimadar.Sales.DetailSaleView;

/**
 * Created by family on 7/27/2016.
 */
public class DetailCustomerBill extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    final static int FRAGMENT_CUSTOMER_BILL_LOADER = 4;
    private final String LOG_TAG = CustomersLists.class.getSimpleName();
    CustomerBillAdapter mCustomerAdapter = null;
    ListView mListView;
    String[] mProjection;
    String[] mSelection;
    String mWhereStatement;
    Long customerId;

    public DetailCustomerBill() {
        super();
    }

    public void onCreate(Bundle savedInstanceState) {

        customerId = Long.parseLong(this.getArguments().getString("customerId"));

        mProjection = new String[]{
                KasebContract.Sales._ID,
                KasebContract.Sales.COLUMN_CUSTOMER_ID,
                KasebContract.Sales.COLUMN_SALE_CODE};

        mSelection = new String[]{
                String.valueOf(customerId),
                "0"};

        mWhereStatement = KasebContract.Sales.COLUMN_CUSTOMER_ID + " = ? and " + KasebContract.Sales.COLUMN_IS_DELETED + " = ? ";

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mCustomerAdapter = new CustomerBillAdapter(
                getActivity(),
                null,
                0);

        View rootView = inflater.inflate(R.layout.customer_bill, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view_customer_bills);
        mListView.setAdapter(mCustomerAdapter);
        TextView emptyText = (TextView) rootView.findViewById(R.id.empty_text_view);
        emptyText.setText(getActivity().getResources().getString(R.string.empty_list_text));
        emptyText.setVisibility(View.VISIBLE);
        mListView.setEmptyView(emptyText);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor mCursor = (Cursor) parent.getItemAtPosition(position);
                if (mCursor != null) {
                    Intent detailSale;
                    String saleCode = mCursor.getString(mCursor.getColumnIndex(KasebContract.Sales.COLUMN_SALE_CODE));
                    String saleId = null;
                    String customerId = null;

                    Cursor mCursor1 = getActivity().getContentResolver().query(
                            KasebContract.Sales.CONTENT_URI,
                            new String[]{
                                    KasebContract.Sales._ID,
                                    KasebContract.Sales.COLUMN_CUSTOMER_ID},
                            KasebContract.Sales.COLUMN_SALE_CODE + " = ? ",
                            new String[]{saleCode},
                            null);

                    if (mCursor1 != null)
                        if (mCursor1.moveToFirst()) {
                            saleId = mCursor1.getString(mCursor1.getColumnIndex(KasebContract.Sales._ID));
                            customerId = mCursor1.getString(mCursor1.getColumnIndex(KasebContract.Sales.COLUMN_CUSTOMER_ID));
                        }

                    detailSale = new Intent(getActivity(), DetailSaleView.class);
                    detailSale.putExtra("forViewAndUpdateSales", true);
                    detailSale.putExtra("saleId", saleId);
                    detailSale.putExtra("saleCode", saleCode);
                    detailSale.putExtra("customerId", customerId);
                    startActivity(detailSale);
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

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FRAGMENT_CUSTOMER_BILL_LOADER, null, this);
    }

    private void updateList() {
        getLoaderManager().restartLoader(FRAGMENT_CUSTOMER_BILL_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        return new CursorLoader(
                getActivity(),
                KasebContract.Sales.CONTENT_URI,
                mProjection,
                mWhereStatement,
                mSelection,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(LOG_TAG, "onLoadFinished");
        mCustomerAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "onLoadReset");
        mCustomerAdapter.swapCursor(null);
    }
}
