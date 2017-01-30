package mjkarbasian.moshtarimadar.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by Unique on 23/12/2016.
 */
public class TaxAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    List<Map<String, String>> mTaxDetailsListHashMap;
    Context mContext;

    public TaxAdapter(Context context, List<Map<String, String>> taxDetailsListHashMap) {
        super();
        mContext = context;
        mTaxDetailsListHashMap = taxDetailsListHashMap;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return (mTaxDetailsListHashMap != null ? mTaxDetailsListHashMap.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return mTaxDetailsListHashMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_tax_for_sale, null);
        }

        TextView amountText = (TextView) view.findViewById(R.id.tax_list_for_sale_amount);
        TextView percentText = (TextView) view.findViewById(R.id.tax_list_for_sale_tax_percent);
        TextView typeText = (TextView) view.findViewById(R.id.tax_list_for_sale_tax_type);

        amountText.setText(Utility.DecimalSeperation(mContext,
                Long.parseLong(mTaxDetailsListHashMap.get(position).get("amount"))));

        percentText.setText(
                mTaxDetailsListHashMap.get(position).get("percent") + " %");

        typeText.setText(
                mTaxDetailsListHashMap.get(position).get("type"));

        return view;
    }
}
