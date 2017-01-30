package mjkarbasian.moshtarimadar.Sales;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import mjkarbasian.moshtarimadar.Adapters.PaymentAdapter;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;


/**
 * Created by Unique on 20/12/2016.
 */
public class CardViewPayments extends Fragment {

    //region declare values
    PaymentAdapter mPaymentAdapter;
    ArrayList<Map<String, String>> mPaymentListHashMap;
    View view;
    int index;
    LinearLayout mLinearLayoutPayments;

    String activity;
    String _id;
    String _amount;
    //endregion declare values

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_card_view_payments, container, false);
        activity = getArguments().getString("activity").toString();

        mPaymentAdapter = new PaymentAdapter(getActivity(), mPaymentListHashMap);
        mLinearLayoutPayments = (LinearLayout) view.findViewById(R.id.linear_layout_fragment_card_view_payments);

        setEmptyText();
        mPaymentListHashMap = null;
        return view;
    }

    public void getPaymentAdapter(ArrayList<Map<String, String>> list) {
        mPaymentListHashMap = list;
        mPaymentAdapter = new PaymentAdapter(getActivity(), mPaymentListHashMap);
        mLinearLayoutPayments.removeAllViews();

        for (int i = 0; i < mPaymentAdapter.getCount(); i++) {
            final View item = mPaymentAdapter.getView(i, null, null);
            item.setTag(i);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //region setOnClickListener
                    final CheckBox checkBox = (CheckBox) item.findViewById(R.id.payment_list_for_sale_is_passed_check_box);
                    checkBox.setEnabled(true);
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            Map<String, String> selectedPayment = mPaymentListHashMap.get((Integer) item.getTag());
                            selectedPayment.put("isPass", String.valueOf(checkBox.isChecked()));
                            DetailSaleView dSActivity = (DetailSaleView) getActivity();
                            dSActivity.setPaymentMap(mPaymentListHashMap);
                        }
                    });
                    //endregion setOnClickListener
                }
            });

            item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //region setOnLongClickListener
                    if (item != null) {
                        index = (Integer) item.getTag();
                        _id = mPaymentListHashMap.get(index).get("id");
                        _amount = mPaymentListHashMap.get(index).get("amount");

                        new AlertDialog.Builder(getActivity())
                                .setTitle(getActivity().getResources().getString(R.string.confirm_title))
                                .setMessage(String.format("%s %s",
                                        getActivity().getString(R.string.confirm_delete_payment),
                                        _amount))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        mPaymentListHashMap.remove(Utility.indexOfRowsInMap(mPaymentListHashMap, "id", _id));

                                        getPaymentAdapter(mPaymentListHashMap);

                                        if (activity.equals("insert"))
                                            ((DetailSaleInsert) getActivity()).setValuesOfFactor();
                                        else if (activity.equals("view"))
                                            ((DetailSaleView) getActivity()).setValuesOfFactor();
                                    }
                                })
                                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                    }
                                }).show();
                    }

                    return true;
                    //endregion setOnLongClickListener
                }
            });

            mLinearLayoutPayments.addView(item);
        }

        if (activity.equals("insert"))
            ((DetailSaleInsert) getActivity()).setValuesOfFactor();
        else if (activity.equals("view"))
            ((DetailSaleView) getActivity()).setValuesOfFactor();

        setEmptyText();
    }

    private void setEmptyText() {
        try {
            TextView emptyText = (TextView) view.findViewById(R.id.empty_text_view);
            emptyText.setText(getActivity().getResources().getString(R.string.empty_list_text));

            if (mPaymentListHashMap.size() == 0)
                emptyText.setVisibility(View.VISIBLE);
            else
                emptyText.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
        }
    }
}
