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
 * Created by family on 7/30/2016.
 */
public class CustomerBillAdapter extends CursorAdapter {

    //region declare values
    String[] mProjection;

    String saleId;
    String saleCode;
    String dueDate;

    Long totalDue;
    Cursor mCursor;
    private LayoutInflater cursorInflater;
    //endregion declare values

    public CustomerBillAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_item_bills, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewDate = (TextView) view.findViewById(R.id.item_list_bill_due_date);
        TextView textViewPurchaseAmount = (TextView) view.findViewById(R.id.item_list_bill_purchase_amount);
        TextView textViewSaleCode = (TextView) view.findViewById(R.id.item_list_bill_sale_code);
        ImageView tagImageView = (ImageView) view.findViewById(R.id.item_list_bill_tag_image);

        saleId = cursor.getString(cursor.getColumnIndex(KasebContract.Sales._ID));
        saleCode = cursor.getString(cursor.getColumnIndex(KasebContract.Sales.COLUMN_SALE_CODE));

        mProjection = new String[]{
                KasebContract.DetailSale._ID,
                KasebContract.DetailSale.COLUMN_DATE,
                KasebContract.DetailSale.COLUMN_TOTAL_DUE,
                KasebContract.DetailSale.COLUMN_IS_BALANCED};

        mCursor = context.getContentResolver().query(
                KasebContract.DetailSale.saleDetailSale(Long.parseLong(saleId)),
                mProjection,
                null,
                null,
                null);

        if (mCursor != null) {
            if (mCursor.moveToFirst()) {
                dueDate = mCursor.getString(mCursor.getColumnIndex(KasebContract.DetailSale.COLUMN_DATE));
                totalDue = mCursor.getLong(mCursor.getColumnIndex(KasebContract.DetailSale.COLUMN_TOTAL_DUE));
                tagImageView.setVisibility(View.VISIBLE);
                if (mCursor.getString(mCursor.getColumnIndex(KasebContract.DetailSale.COLUMN_IS_BALANCED)).equals("1"))
                    tagImageView.setImageResource(R.drawable.marked_circle);
                else
                    tagImageView.setImageResource(R.drawable.open_marked_circle);
            }
        }

        mCursor.close();

        textViewDate.setText(Utility.localePersianDate(dueDate));
        textViewPurchaseAmount.setText(Utility.formatPurchase(
                context,
                Utility.DecimalSeperation(context, Long.valueOf(String.format("%.0f", (float) totalDue)))));
        textViewSaleCode.setText(saleCode);
    }
}
