package mjkarbasian.moshtarimadar.Adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.RoundImageView;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by family on 12/19/2016.
 */
public class DebtorAdapter extends CursorAdapter {

    //region declare values
    private LayoutInflater cursorInflater;
    private android.content.Context mContext;

    //endregion declare values

    public DebtorAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
        cursorInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return cursorInflater.inflate(R.layout.list_item_debtors, parent, false);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        //region get debator name
        String[] customersProjection = new String[]{
                KasebContract.Customers.COLUMN_FIRST_NAME,
                KasebContract.Customers.COLUMN_LAST_NAME,
                KasebContract.Customers.COLUMN_CUSTOMER_PICTURE,
                KasebContract.Customers.COLUMN_PHONE_MOBILE
        };

        Uri customerUri = KasebContract.Customers.buildCustomerUri(cursor.
                getLong(cursor.getColumnIndex(KasebContract.Sales.COLUMN_CUSTOMER_ID)));

        final Cursor adapterCursor = mContext.getContentResolver().query(customerUri, customersProjection, null, null, null);
        String firstName = null;
        String lastName = null;
        byte[] imagegBytes = null;
        if (adapterCursor.moveToFirst()) {
            firstName = adapterCursor.getString(adapterCursor.getColumnIndex(KasebContract.Customers.COLUMN_FIRST_NAME));
            lastName = adapterCursor.getString(adapterCursor.getColumnIndex(KasebContract.Customers.COLUMN_LAST_NAME));
            imagegBytes = adapterCursor.getBlob(adapterCursor.getColumnIndex(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE));
        }

        TextView debatorNameText = (TextView) view.findViewById(R.id.item_card_debator_name);
        debatorNameText.setText(firstName + " " + lastName);
        //endregion

        //region set Image Avavtar
        RoundImageView imageViewAvatar = (RoundImageView) view.findViewById(R.id.item_card_customer_avater);

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
        //endregion set Image Avavtar

        //region set debator balance amount
        TextView debatorBalanceText = (TextView) view.findViewById(R.id.item_card_balance_amount);
        int dueSumIndex = 2;
        int paidSumIndex = 3;
        Float totalBalance = Float.parseFloat(cursor.getString(dueSumIndex)) - Float.parseFloat(cursor.getString(paidSumIndex));
        debatorBalanceText.setText(Utility.formatPurchase(mContext, Utility.DecimalSeperation(mContext, totalBalance)));
        //endregion

        //Card actions click handler
        ImageView phoneImage = (ImageView) view.findViewById(R.id.debtor_call);
        phoneImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel",
                            adapterCursor.getString(adapterCursor.getColumnIndex(KasebContract.Customers.COLUMN_PHONE_MOBILE)), null));
                    mContext.startActivity(intent);
                } catch (Exception e) {
                }
            }
        });

        ImageView shareImage = (ImageView) view.findViewById(R.id.debtor_share);
        final String finalBalanceAmount;
        finalBalanceAmount = String.format("%s %s", mContext.getResources().getString(R.string.debtor_share_text_to_send),
                Utility.formatPurchase(mContext, Utility.DecimalSeperation(mContext, totalBalance)));

        shareImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_TEXT, finalBalanceAmount);
                    mContext.startActivity(Intent.createChooser(intent, "Share with"));
                } catch (Exception e) {

                }
            }
        });

        ImageView messageImage = (ImageView) view.findViewById(R.id.debtor_message);
        messageImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.fromParts("sms",
                            adapterCursor.getString(adapterCursor.getColumnIndex(KasebContract.Customers.COLUMN_PHONE_MOBILE)), null));
                    intent.putExtra(Intent.EXTRA_TEXT, finalBalanceAmount);
                    mContext.startActivity(intent);
                } catch (Exception e) {
                }
            }
        });
        //endCard actions click handler
    }
}
