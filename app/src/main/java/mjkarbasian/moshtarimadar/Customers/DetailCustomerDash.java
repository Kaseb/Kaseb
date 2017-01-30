package mjkarbasian.moshtarimadar.Customers;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 7/27/2016.
 */
public class DetailCustomerDash extends Fragment {

    //region declare values
    int salesCount;
    Uri uriCursor;
    Cursor mCursor;

    String[] mProjectionCustomerCursor;
    String[] mProjectionDetailSaleCursor;
    String[] mSelectionDetailSaleCursor;

    Long totalBalanceCustomer = 0l;
    Long totalDueCustomer = 0l;
    Long totalPaidCustomer = 0l;

    String stateId;
    String mWhereStatement;
    //endregion declare values

    public DetailCustomerDash() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.customer_dash, container, false);

        ImageView imageViewState = (ImageView) view.findViewById(R.id.customer_dash_membership);
        TextView customerPurchase = (TextView) view.findViewById(R.id.customer_dash_total_purchase);
        TextView customerBalance = (TextView) view.findViewById(R.id.customer_dash_total_balance);
        TextView customerNumberPurchase = (TextView) view.findViewById(R.id.customer_dash_number_purchase);

        uriCursor = Uri.parse(getArguments().getString("uriCursor"));

        mProjectionCustomerCursor = new String[]{
                KasebContract.Customers._ID,
                KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE,
                KasebContract.Customers.COLUMN_PHONE_FAX,
                KasebContract.Customers.COLUMN_PHONE_HOME,
                KasebContract.Customers.COLUMN_STATE_ID};


        mCursor = getContext().getContentResolver().query(
                KasebContract.Sales.CONTENT_URI,
                new String[]{
                        KasebContract.Sales._ID},
                KasebContract.Sales.COLUMN_IS_DELETED + " = ? and " + KasebContract.Sales.COLUMN_CUSTOMER_ID + " = ? ",
                new String[]{"0", uriCursor.getLastPathSegment()},
                null
        );

        salesCount = mCursor.getCount();

        if (salesCount > 0) {
            mSelectionDetailSaleCursor = new String[salesCount];

            mCursor.moveToFirst();
            for (int i = 0; i < mCursor.getCount(); i++) {
                mSelectionDetailSaleCursor[i] = String.valueOf(mCursor.getString(mCursor.getColumnIndex(KasebContract.Sales._ID)));
                mCursor.moveToNext();
            }

            mProjectionDetailSaleCursor = new String[]{
                    KasebContract.DetailSale._ID,
                    KasebContract.DetailSale.COLUMN_TOTAL_DUE,
                    KasebContract.DetailSale.COLUMN_TOTAL_PAID,
                    KasebContract.DetailSale.COLUMN_SUB_TOTAL};

            mWhereStatement = KasebContract.DetailSale.COLUMN_SALE_ID + " IN (" + Utility.makePlaceholders(salesCount) + ")";

            Cursor DetailSaleCursor = getContext().getContentResolver().query(
                    KasebContract.DetailSale.CONTENT_URI,
                    mProjectionDetailSaleCursor,
                    mWhereStatement,
                    mSelectionDetailSaleCursor,
                    null
            );

            DetailSaleCursor.moveToFirst();
            for (int i = 0; i < DetailSaleCursor.getCount(); i++) {
                totalDueCustomer += DetailSaleCursor.getLong(
                        DetailSaleCursor.getColumnIndex(KasebContract.DetailSale.COLUMN_TOTAL_DUE));

                totalPaidCustomer += DetailSaleCursor.getLong(
                        DetailSaleCursor.getColumnIndex(
                                KasebContract.DetailSale.COLUMN_TOTAL_PAID));

                DetailSaleCursor.moveToNext();
            }
            totalBalanceCustomer += totalDueCustomer - totalPaidCustomer;

            customerPurchase.setText(
                    Utility.formatPurchase(getActivity(),
                            Utility.DecimalSeperation(getContext(),
                                    totalDueCustomer)));

            customerBalance.setText(
                    Utility.formatPurchase(getContext(),
                            Utility.DecimalSeperation(getContext(),
                                    totalBalanceCustomer)));

            customerNumberPurchase.setText(Utility.doubleFormatter(salesCount));
        } else {
            customerPurchase.setText(
                    Utility.formatPurchase(getActivity(),
                            Utility.DecimalSeperation(getContext(),
                                    0)));

            customerBalance.setText(
                    Utility.formatPurchase(getContext(),
                            Utility.DecimalSeperation(getContext(),
                                    0)));

            customerNumberPurchase.setText(String.valueOf(salesCount));
        }


        Cursor customerCursor = getContext().getContentResolver().query(
                uriCursor,
                mProjectionCustomerCursor,
                null,
                null,
                null
        );

        //region read customer State
        customerCursor.moveToFirst();
        stateId = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_STATE_ID));

        String selection = KasebContract.State._ID + " = ?";
        String[] selecArg = new String[]{String.valueOf(customerCursor.getInt(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_STATE_ID)))};
        Cursor colorCursor = getContext().getContentResolver().query(KasebContract.State.CONTENT_URI,
                new String[]{KasebContract.State._ID, KasebContract.State.COLUMN_STATE_COLOR}, selection, selecArg, null);
        if (colorCursor.moveToFirst())
            imageViewState.setColorFilter(colorCursor.getInt(colorCursor.getColumnIndex(KasebContract.State.COLUMN_STATE_COLOR)));
        //endregion
        colorCursor.close();
        customerCursor.close();
        return view;
    }
}