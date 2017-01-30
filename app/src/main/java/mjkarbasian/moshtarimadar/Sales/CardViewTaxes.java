package mjkarbasian.moshtarimadar.Sales;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import mjkarbasian.moshtarimadar.Adapters.TaxAdapter;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by Unique on 20/12/2016.
 */
public class CardViewTaxes extends Fragment {

    //region declare values
    TaxAdapter mTaxAdapter;
    ArrayList<Map<String, String>> mTaxListHashMap;
    View view;
    int index;
    LinearLayout mLinearLayoutTax;

    String activity;
    String _id;
    String _amount;
    //endregion declare values

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_card_view_taxes, container, false);
        activity = getArguments().getString("activity").toString();
        mTaxAdapter = new TaxAdapter(getActivity(), mTaxListHashMap);
        mLinearLayoutTax = (LinearLayout) view.findViewById(R.id.linear_layout_fragment_card_view_taxes);

        return view;
    }

    public void getTaxAdapter(ArrayList<Map<String, String>> list) {
        mTaxListHashMap = list;
        mTaxAdapter = new TaxAdapter(getActivity(), mTaxListHashMap);
        mLinearLayoutTax.removeAllViews();

        for (int i = 0; i < mTaxAdapter.getCount(); i++) {
            final View item = mTaxAdapter.getView(i, null, null);
            item.setTag(i);

            item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //region setOnLongClickListener
                    if (item != null) {
                        index = (Integer) item.getTag();
                        _id = mTaxListHashMap.get(index).get("id");
                        _amount = mTaxListHashMap.get(index).get("amount");

                        new AlertDialog.Builder(getActivity())
                                .setTitle(getActivity().getResources().getString(R.string.confirm_title))
                                .setMessage(String.format("%s %s",
                                        getActivity().getResources().getString(R.string.confirm_delete_tax),
                                        _amount))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        mTaxListHashMap.remove(Utility.indexOfRowsInMap(mTaxListHashMap, "id", _id));

                                        getTaxAdapter(mTaxListHashMap);

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

            mLinearLayoutTax.addView(item);
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

            if (mTaxListHashMap.size() == 0)
                emptyText.setVisibility(View.VISIBLE);
            else
                emptyText.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
        }
    }
}
