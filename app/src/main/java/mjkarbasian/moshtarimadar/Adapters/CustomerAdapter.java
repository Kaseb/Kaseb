package mjkarbasian.moshtarimadar.Adapters;

import android.content.Context;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mjkarbasian.moshtarimadar.Customers.Customers;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.RoundImageView;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;


/**
 * Created by family on 6/23/2016.
 */
public class CustomerAdapter extends CursorAdapter {

    String name;
    private LayoutInflater cursorInflater;

    public CustomerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_item_customers, parent, false);
    }

    @Override
    public void bindView(View view, final Context context, Cursor cursor) {
        TextView textViewName = (TextView) view.findViewById(R.id.item_list_customer_name);
        TextView textViewAmount = (TextView) view.findViewById(R.id.item_list_purchase_amount);
        ImageView imageViewState = (ImageView) view.findViewById(R.id.item_list_customer_state);
        final RoundImageView imageViewAvatar = (RoundImageView) view.findViewById(R.id.item_list_customer_avatar);
        final Long _id = cursor.getLong(cursor.getColumnIndex(KasebContract.Customers._ID));
        final byte[] imagegBytes = cursor.getBlob(cursor.getColumnIndex(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE));

        imageViewAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Customers customersActivity = (Customers) context;
                    customersActivity.pic_selector(imageViewAvatar, _id);
                } catch (Exception e) {
                }
            }
        });

        imageViewAvatar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                try {
                    if (imagegBytes.length == 0)
                        return false;
                    else {
                        Customers customersActivity = (Customers) context;
                        customersActivity.pic_deleter(imageViewAvatar, _id);
                        return true;
                    }
                } catch (Exception e) {
                    return false;
                }
            }
        });

        try {
            Boolean mWhat = false;
            if (imagegBytes == null)
                mWhat = true;
            else if (imagegBytes.length == 0)
                mWhat = true;

            if (mWhat)
                imageViewAvatar.setImageDrawable(context.getResources().getDrawable(
                        context.getResources().getIdentifier("@drawable/kaseb_pic", null, context.getPackageName())));
            else {
                imageViewAvatar.setImageBitmap(BitmapFactory.decodeByteArray(imagegBytes, 0, imagegBytes.length));
            }
        } catch (Exception e) {
        }

        String selection = KasebContract.State._ID + " = ?";
        String[] selecArg = new String[]{String.valueOf(cursor.getInt(cursor.getColumnIndex(KasebContract.Customers.COLUMN_STATE_ID)))};
        Cursor colorCursor = context.getContentResolver().query(KasebContract.State.CONTENT_URI,
                new String[]{KasebContract.State._ID, KasebContract.State.COLUMN_STATE_COLOR}, selection, selecArg, null);
        if (colorCursor.moveToFirst())
            imageViewState.setColorFilter(colorCursor.getInt(colorCursor.getColumnIndex(KasebContract.State.COLUMN_STATE_COLOR)));

        name = cursor.getString(cursor.getColumnIndex(KasebContract.Customers.COLUMN_FIRST_NAME)) + "   " +
                cursor.getString(cursor.getColumnIndex(KasebContract.Customers.COLUMN_LAST_NAME));

        Cursor mCursor = context.getContentResolver().query(
                KasebContract.Sales.CONTENT_URI,
                new String[]{
                        KasebContract.Sales._ID},
                KasebContract.Sales.COLUMN_IS_DELETED + " = ? and " + KasebContract.Sales.COLUMN_CUSTOMER_ID + " = ? ",
                new String[]{"0", cursor.getString(cursor.getColumnIndex(KasebContract.Customers._ID))},
                null);

        Long totalDue = 0l;
        if (mCursor != null)
            if (mCursor.moveToFirst())
                for (int i = 0; i < mCursor.getCount(); i++) {
                    Cursor mCursor1 = context.getContentResolver().query(
                            KasebContract.DetailSale.saleDetailSale(
                                    mCursor.getLong(cursor.getColumnIndex(KasebContract.Sales._ID))),
                            new String[]{
                                    KasebContract.DetailSale._ID,
                                    KasebContract.DetailSale.COLUMN_TOTAL_DUE},
                            null,
                            null,
                            null);

                    if (mCursor1 != null)
                        if (mCursor1.moveToFirst())
                            totalDue += mCursor1.getLong(mCursor1.getColumnIndex(KasebContract.DetailSale.COLUMN_TOTAL_DUE));

                    mCursor.moveToNext();
                }
        textViewName.setText(name);
        textViewAmount.setText(Utility.formatPurchase(
                context,
                Utility.DecimalSeperation(context, Long.valueOf(String.format("%.0f", (float) totalDue)))));
    }
}
