package mjkarbasian.moshtarimadar.Sales;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Map;

import mjkarbasian.moshtarimadar.Adapters.ProductAdapter;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;

/**
 * Created by Unique on 20/12/2016.
 */
public class CardViewProducts extends Fragment {

    //region declare values
    ProductAdapter mChosenProductAdapter;
    ArrayList<Map<String, String>> mProductListHashMap;
    View view;
    EditText howManyEditTextForEdit;
    TextInputLayout textInputLayouthowManyEditTextForEdit;
    int index;
    LinearLayout mLinearLayoutProducts;

    String _id;
    String _nameOfProduct;
    String activity;

    Long differneceOfBuy_Sale;
    Long mDetailSaleId = 0l;

    AlertDialog.Builder builder;
    AlertDialog dialogView;
    //endregion declare values

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_card_view_products, container, false);
        activity = getArguments().getString("activity").toString();
        mChosenProductAdapter = new ProductAdapter(getActivity(), mProductListHashMap);
        mLinearLayoutProducts = (LinearLayout) view.findViewById(R.id.linear_layout_fragment_card_view_products);

        try {
            mDetailSaleId = getArguments().getLong("detailSaleId");
        } catch (Exception e) {
            Log.i(CardViewProducts.class.getName(),
                    "DetailSaleView does not put DetailSaleId in Bundle of CardViewProducts Fragments.");
        }

        setEmptyText();
        mProductListHashMap = null;
        return view;
    }

    public void getChosenProductAdapter(ArrayList<Map<String, String>> list) {

        mProductListHashMap = list;
        mChosenProductAdapter = new ProductAdapter(getActivity(), mProductListHashMap);
        mLinearLayoutProducts.removeAllViews();

        for (int i = 0; i < mChosenProductAdapter.getCount(); i++) {
            final View item = mChosenProductAdapter.getView(i, null, null);
            item.setTag(i);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    //region setOnClickListener

                    //region Create AlertDialog
                    builder = new AlertDialog.Builder(getActivity())
                            .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_chosen_product_for_sale, null))
                            .setNegativeButton(R.string.discard_button, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    dialogView.dismiss();
                                }
                            }).setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                }
                            })
                            .setTitle(R.string.edit_quantity)
                            .setMessage(R.string.less_than_stock_explain_text);

                    dialogView = builder.create();
                    dialogView.show();

                    dialogView.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Boolean wantToCloseDialog = false;

                            //region AlertDialog.BUTTON_POSITIVE
                            try {
                                Long num = Long.parseLong(howManyEditTextForEdit.getText().toString());

                                if (differneceOfBuy_Sale >= num) {
                                    mProductListHashMap.set(index,
                                            Utility.setValueWithIndexInKeyOfMapRow(
                                                    mProductListHashMap.get(index),
                                                    "quantity",
                                                    String.valueOf(num)
                                            ));

                                    getChosenProductAdapter(mProductListHashMap);

                                    if (activity.equals("insert"))
                                        ((DetailSaleInsert) getActivity()).setValuesOfFactor();
                                    else if (activity.equals("view"))
                                        ((DetailSaleView) getActivity()).setValuesOfFactor();

                                    wantToCloseDialog = true;
                                } else
                                    textInputLayouthowManyEditTextForEdit.setError(getResources().getString(R.string.not_enough_stock));

                                if (wantToCloseDialog)
                                    dialogView.dismiss();
                            } catch (Exception e) {
                                textInputLayouthowManyEditTextForEdit.setError(getResources().getString(R.string.example_quantity));
                                howManyEditTextForEdit.setSelectAllOnFocus(true);
                                howManyEditTextForEdit.selectAll();
                                howManyEditTextForEdit.requestFocus();
                            }
                            //endregion AlertDialog.BUTTON_POSITIVE
                        }
                    });
                    //endregion Create AlertDialog

                    //region Set Views To Dialog
                    if (item != null) {
                        index = (Integer) item.getTag();
                        _id = mProductListHashMap.get(index).get("id");

                        if (activity.equals("insert"))
                            //in this line does not matter post 0l to second parameter of function because not use in this case
                            differneceOfBuy_Sale = Utility.checkNumberOfProductsForDetailSale(getActivity(),
                                    0l, "SaleInsert", Long.parseLong(_id));
                        else if (activity.equals("view"))
                            differneceOfBuy_Sale = Utility.checkNumberOfProductsForDetailSale(getActivity(),
                                    mDetailSaleId, "SaleView", Long.parseLong(_id));

                        howManyEditTextForEdit = (EditText) dialogView
                                .findViewById(R.id.edit_chosen_product_for_sale_number);

                        textInputLayouthowManyEditTextForEdit = (TextInputLayout) dialogView
                                .findViewById(R.id.text_input_layout_edit_chosen_product_for_sale_number);

                        howManyEditTextForEdit.setHint(getString(R.string.stock_product) + differneceOfBuy_Sale);
                    }
                    //endregion Set Views To Dialog

                    //endregion setOnClickListener
                }
            });

            item.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {

                    //region setOnLongClickListener
                    if (item != null) {
                        index = (Integer) item.getTag();
                        _id = mProductListHashMap.get(index).get("id");
                        _nameOfProduct = mProductListHashMap.get(index).get("name");

                        new AlertDialog.Builder(getActivity())
                                .setTitle(getActivity().getResources().getString(R.string.confirm_title))
                                .setMessage(String.format("%s %s",
                                        getActivity().getResources().getString(R.string.confirm_delete_product_list),
                                        _nameOfProduct))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        mProductListHashMap.remove(Utility.indexOfRowsInMap(mProductListHashMap, "id", _id));

                                        getChosenProductAdapter(mProductListHashMap);

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

            mLinearLayoutProducts.addView(item);
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

            if (mProductListHashMap.size() == 0)
                emptyText.setVisibility(View.VISIBLE);
            else
                emptyText.setVisibility(View.INVISIBLE);
        } catch (Exception e) {
        }
    }
}
