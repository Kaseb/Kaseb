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
 * Created by family on 7/21/2016.
 */
public class ProductAdapter extends BaseAdapter {

    private static LayoutInflater inflater = null;
    List<Map<String, String>> mProductDetailsListHashMap;
    private Context mContext;

    public ProductAdapter(Context context, List<Map<String, String>> productDetailsListHashMap) {
        super();
        mProductDetailsListHashMap = productDetailsListHashMap;
        inflater = (LayoutInflater) context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mContext = context;
    }

    @Override
    public int getCount() {
        return (mProductDetailsListHashMap != null ? mProductDetailsListHashMap.size() : 0);
    }

    @Override
    public Object getItem(int position) {
        return mProductDetailsListHashMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            view = inflater.inflate(R.layout.list_item_products_in_card_view_sales, null);
        }

        TextView nameText = (TextView) view.findViewById(R.id.item_list_product_name);
        TextView priceText = (TextView) view.findViewById(R.id.item_list_product_price);
        TextView quantityText = (TextView) view.findViewById(R.id.item_list_product_quantity);
        TextView totalCostText = (TextView) view.findViewById(R.id.item_list_product_total_cost);

        nameText.setText(
                mProductDetailsListHashMap.get(position).get("name"));

        priceText.setText(
                Utility.DecimalSeperation(mContext,
                        Long.parseLong(
                                mProductDetailsListHashMap.get(position).get("price"))));

        quantityText.setText(
                Utility.doubleFormatter(Double.parseDouble(mProductDetailsListHashMap.get(position).get("quantity"))));

        totalCostText.setText(
                Utility.DecimalSeperation(mContext,
                        Long.parseLong(String.valueOf(
                                Long.parseLong(
                                        mProductDetailsListHashMap.get(position).get("price"))
                                        * Long.parseLong(
                                        mProductDetailsListHashMap.get(position).get("quantity"))))));

        return view;
    }
}
