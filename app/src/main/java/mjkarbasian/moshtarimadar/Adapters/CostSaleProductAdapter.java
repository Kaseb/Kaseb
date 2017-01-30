package mjkarbasian.moshtarimadar.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by Unique on 11/11/2016.
 */
public class CostSaleProductAdapter extends CursorAdapter {

    String witchActivity;
    String firstName;
    String lastName;

    String name;
    String code;
    String date;
    Long amount;
    Cursor mCursor;
    private LayoutInflater cursorInflater;

    public CostSaleProductAdapter(Context context, Cursor c, int flags, String tableName) {
        super(context, c, flags);
        witchActivity = tableName;
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_item_cost_sale_product, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewName = (TextView) view.findViewById(R.id.item_list_name);
        TextView textViewCode = (TextView) view.findViewById(R.id.item_list_code);
        TextView textViewDate = (TextView) view.findViewById(R.id.item_list_date);
        TextView textViewAmount = (TextView) view.findViewById(R.id.item_list_amount);
        ImageView tagImageView = (ImageView)view.findViewById(R.id.cost_sale_product_tag_image);

        switch (witchActivity) {
            case "cost": {
                name = cursor.getString(cursor.getColumnIndex(KasebContract.Costs.COLUMN_COST_NAME));
                code = cursor.getString(cursor.getColumnIndex(KasebContract.Costs.COLUMN_COST_CODE));
                date = cursor.getString(cursor.getColumnIndex(KasebContract.Costs.COLUMN_DATE));
                amount = cursor.getLong(cursor.getColumnIndex(KasebContract.Costs.COLUMN_AMOUNT));
                break;
            }
            case "sale": {
                //region read customer FirstName & LastName
                String customerId = cursor.getString(cursor.getColumnIndex(KasebContract.Sales.COLUMN_CUSTOMER_ID));

                mCursor = context.getContentResolver().query(
                        KasebContract.Customers.buildCustomerUri(Long.parseLong(customerId)),
                        new String[]{KasebContract.Customers.COLUMN_FIRST_NAME, KasebContract.Customers.COLUMN_LAST_NAME},
                        null,
                        null,
                        null);

                if (mCursor != null) {
                    if (mCursor.moveToFirst()) {
                        firstName = mCursor.getString(mCursor.getColumnIndex(KasebContract.Customers.COLUMN_FIRST_NAME));
                        lastName = mCursor.getString(mCursor.getColumnIndex(KasebContract.Customers.COLUMN_LAST_NAME));
                        name = firstName + "   " + lastName;
                    }
                }
                //endregion

                //region read DetailSale Date & TotalPaid
                String saleId = cursor.getString(cursor.getColumnIndex(KasebContract.Sales._ID));

                mCursor = context.getContentResolver().query(
                        KasebContract.DetailSale.buildDetailSaleUri(Long.parseLong(saleId)),
                        new String[]{KasebContract.DetailSale.COLUMN_DATE, KasebContract.DetailSale.COLUMN_TOTAL_DUE,KasebContract.DetailSale.COLUMN_IS_BALANCED},
                        null,
                        null,
                        null);

                if (mCursor != null) {
                    if (mCursor.moveToFirst()) {
                        date = mCursor.getString(mCursor.getColumnIndex(KasebContract.DetailSale.COLUMN_DATE));
                        amount = mCursor.getLong(mCursor.getColumnIndex(KasebContract.DetailSale.COLUMN_TOTAL_DUE));
                        tagImageView.setVisibility(View.VISIBLE);
                        //check for isBalance=true
                        if(mCursor.getString(mCursor.getColumnIndex(KasebContract.DetailSale.COLUMN_IS_BALANCED)).equals("1"))
                            tagImageView.setImageResource(R.drawable.marked_circle);
                        else
                            tagImageView.setImageResource(R.drawable.open_marked_circle);
                    }
                }
                //endregion

                code = cursor.getString(cursor.getColumnIndex(KasebContract.Sales.COLUMN_SALE_CODE));

                break;
            }
            case "product": {

                //region read ProductHistory Date & SalePrice
                String productId = cursor.getString(cursor.getColumnIndex(KasebContract.Products._ID));

                mCursor = context.getContentResolver().query(
                        KasebContract.ProductHistory.aProductHistory(Long.parseLong(productId)),
                        new String[]{
                                KasebContract.ProductHistory.COLUMN_DATE,
                                KasebContract.ProductHistory.COLUMN_SALE_PRICE},
                        null,
                        null,
                        null);

                if (mCursor != null) {
                    if (mCursor.moveToLast()) {
                        date = mCursor.getString(mCursor.getColumnIndex(KasebContract.ProductHistory.COLUMN_DATE));
                        amount = mCursor.getLong(mCursor.getColumnIndex(KasebContract.ProductHistory.COLUMN_SALE_PRICE));
                    }
                }
                //endregion

                name = cursor.getString(cursor.getColumnIndex(KasebContract.Products.COLUMN_PRODUCT_NAME));
                code = cursor.getString(cursor.getColumnIndex(KasebContract.Products.COLUMN_PRODUCT_CODE));
                break;
            }
        }

        textViewName.setText(name);
        textViewCode.setText(code);
        textViewDate.setText(Utility.localePersianDate(date));
        textViewAmount.setText(Utility.formatPurchase(
                context,
                Utility.DecimalSeperation(context, Long.valueOf(String.format("%.0f", (float) amount)))));
    }
}
