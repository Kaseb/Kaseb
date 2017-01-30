package mjkarbasian.moshtarimadar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;


/**
 * Created by Unique on 23/12/2016.
 */
public class PaymentAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    List<Map<String, String>> mPaymentDetailsListHashMap;
    Context mContext;

    public PaymentAdapter(Context context, List<Map<String, String>> paymentDetailsListHashMap) {
        super();
        mContext = context;
        mPaymentDetailsListHashMap = paymentDetailsListHashMap;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (mPaymentDetailsListHashMap != null ? mPaymentDetailsListHashMap.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return mPaymentDetailsListHashMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null)
            view = inflater.inflate(R.layout.list_item_payment_for_sale, null);

        TextView amountText = (TextView) view.findViewById(R.id.payment_list_for_sale_amount);
        TextView duedateText = (TextView) view.findViewById(R.id.payment_list_for_sale_due_date);
        TextView typeText = (TextView) view.findViewById(R.id.payment_list_for_sale_payment_method);
        CheckBox isPassCheckBox = (CheckBox) view.findViewById(R.id.payment_list_for_sale_is_passed_check_box);

        amountText.setText(
                Utility.DecimalSeperation(mContext,
                        Long.parseLong(mPaymentDetailsListHashMap.get(position).get("amount"))));

        duedateText.setText(
                Utility.localePersianDate(mPaymentDetailsListHashMap.get(position).get("duedate")));

        typeText.setText(
                mPaymentDetailsListHashMap.get(position).get("type"));

        if (mPaymentDetailsListHashMap.get(position).get("type").equals(mContext.getString(R.string.cheque_title))) {
            isPassCheckBox.setVisibility(View.VISIBLE);
            isPassCheckBox.setChecked(Boolean.parseBoolean(mPaymentDetailsListHashMap.get(position).get("isPass")));
            isPassCheckBox.setEnabled(false);
        }
        return view;
    }

    @Override
    public boolean isEnabled(int position) {
        return super.isEnabled(position);
    }
}
