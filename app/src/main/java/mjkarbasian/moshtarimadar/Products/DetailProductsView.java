package mjkarbasian.moshtarimadar.Products;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import mjkarbasian.moshtarimadar.Helpers.Samples;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class DetailProductsView extends Fragment {

    ListView mListView;

    public DetailProductsView() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Intent intent = getActivity().getIntent();
        String productCode = intent.getStringExtra("productCode");
        int position = intent.getIntExtra("position", 0);

        View view = inflater.inflate(R.layout.fragment_detail_products, container, false);

        TextView productName = (TextView) view.findViewById(R.id.detail_product_name);
        TextView Code = (TextView) view.findViewById(R.id.detail_product_code);
        TextView unit = (TextView) view.findViewById(R.id.detail_product_unit);
        productName.setText(Samples.products.get(2).get(position));
        Code.setText(Utility.doubleFormatter(Double.parseDouble(productCode)));
        unit.setText(getActivity().getResources().getString(R.string.sample_unit));
        mListView = (ListView) view.findViewById(R.id.listview_detail_product);

        //dynamically change cards height but it must modify
        CardView priceList = (CardView) view.findViewById(R.id.card_detail_price_list);
        ViewGroup.LayoutParams layoutParamsTax = priceList.getLayoutParams();
        layoutParamsTax.height = Utility.dipConverter(mListView.getCount() * 50 + 30, getActivity());//this is in pixels Must item height recognize dynamically
        priceList.setLayoutParams(layoutParamsTax);

        return view;
    }
}
