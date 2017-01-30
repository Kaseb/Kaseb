package mjkarbasian.moshtarimadar.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 8/25/2016.
 */
public class DetailProductAdapter extends CursorAdapter {

    Context mContext;
    private LayoutInflater cursorInflater;

    public DetailProductAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(
                Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_item_price_list, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView textViewBuyPrice = (TextView) view.findViewById(R.id.price_list_buy_price);
        TextView textViewSalePrice = (TextView) view.findViewById(R.id.price_list_sale_price);
        TextView textViewDate = (TextView) view.findViewById(R.id.price_list_date);
        TextView textViewQuantity = (TextView) view.findViewById(R.id.price_list_quantity);

        textViewBuyPrice.setText(Utility.DecimalSeperation(mContext, cursor.getLong(cursor.getColumnIndex(KasebContract.ProductHistory.COLUMN_COST))));
        textViewSalePrice.setText(Utility.DecimalSeperation(mContext, cursor.getLong(cursor.getColumnIndex(KasebContract.ProductHistory.COLUMN_SALE_PRICE))));
        textViewDate.setText(Utility.localePersianDate(cursor.getString(cursor.getColumnIndex(KasebContract.ProductHistory.COLUMN_DATE))));
        textViewQuantity.setText(Utility.doubleFormatter(cursor.getLong(cursor.getColumnIndex(KasebContract.ProductHistory.COLUMN_QUANTITY))));
    }
}
