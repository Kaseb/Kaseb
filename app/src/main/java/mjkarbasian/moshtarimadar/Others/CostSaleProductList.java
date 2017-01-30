package mjkarbasian.moshtarimadar.Others;


import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import mjkarbasian.moshtarimadar.Adapters.CostSaleProductAdapter;
import mjkarbasian.moshtarimadar.Adapters.TypesSettingAdapter;
import mjkarbasian.moshtarimadar.Customers.Customers;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Data.KasebDbHelper;
import mjkarbasian.moshtarimadar.Data.KasebProvider;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.Products.DetailProducts;
import mjkarbasian.moshtarimadar.Products.Products;
import mjkarbasian.moshtarimadar.R;
import mjkarbasian.moshtarimadar.Sales.DetailSaleView;
import mjkarbasian.moshtarimadar.Sales.Sales;

/**
 * Created by Unique on 10/11/2016.
 */
public class CostSaleProductList extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //region declare Values
    final static int FRAGMENT_COST_SALE_PRODUCT_LOADER = 1;
    private final String LOG_TAG = CostSaleProductList.class.getSimpleName();
    FragmentManager fragmentManager;
    SharedPreferences kasebSharedPreferences;
    SharedPreferences.Editor editor;

    DetailProducts productHistory = new DetailProducts();
    Bundle productHistoryBundle = new Bundle();

    KasebDbHelper mOpenHelper;
    SQLiteDatabase mDb;

    AlertDialog.Builder builder;
    AlertDialog dialogView;
    AlertDialog.Builder builderTour;
    AlertDialog dialogViewTour;

    CostSaleProductAdapter mAdapter = null;
    ListView mListView;
    String[] mProjection;
    Cursor mCursor;
    Spinner costType;
    EditText costName;
    EditText costAmount;
    EditText costCode;
    EditText costDate;
    EditText costDescription;
    TypesSettingAdapter cursorAdapter = null;
    ContentValues costValues = new ContentValues();
    ContentValues saleValues = new ContentValues();
    TextInputLayout costNameTextInputLayout;
    TextInputLayout costAmountTextInputLayout;
    TextInputLayout costDateTextInputLayout;
    TextInputLayout costCodeTextInputLayout;
    FloatingActionButton fab;
    private String searchQuery;
    private String sortOrder;
    private int sortId;
    //endregion declare Values

    public CostSaleProductList() {
        super();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        String mTitleTourDialog = "";

        switch (getArguments().getString("witchActivity")) {
            case "cost": {
                //region cost
                mProjection = new String[]{
                        null,
                        KasebContract.Costs.COLUMN_COST_NAME,
                        KasebContract.Costs.COLUMN_AMOUNT,
                        KasebContract.Costs.COLUMN_COST_CODE,
                        KasebContract.Costs.COLUMN_DESCRIPTION,
                        KasebContract.Costs.COLUMN_COST_TYPE_ID,
                        KasebContract.Costs.COLUMN_DATE};
                break;
                //endregion cost
            }
            case "sale": {
                //region sale
                mProjection = new String[]{
                        null,
                        KasebContract.Sales.COLUMN_CUSTOMER_ID,
                        KasebContract.Sales.COLUMN_SALE_CODE};

                mTitleTourDialog = getResources().getString(R.string.title_sales);
                break;
                //endregion sale
            }
            case "product": {
                //region product
                mProjection = new String[]{
                        null,
                        KasebContract.Products.COLUMN_PRODUCT_NAME,
                        KasebContract.Products.COLUMN_PRODUCT_CODE};

                mOpenHelper = KasebProvider.mOpenHelper;
                mDb = mOpenHelper.getWritableDatabase();
                mTitleTourDialog = getResources().getString(R.string.title_products);
                break;
                //endregion product
            }
            default:
                break;
        }

        //region handle sharepreference
        kasebSharedPreferences = getActivity().getSharedPreferences(getString(R.string.kasebPreference), getActivity().MODE_PRIVATE);
        editor = kasebSharedPreferences.edit();
        //endregion handle sharepreference

        //region create alertdialog tour
        builderTour = new AlertDialog.Builder(getActivity())
                .setNegativeButton(R.string.back_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).setPositiveButton(R.string.next_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setNeutralButton(R.string.cancel_tour, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                })
                .setTitle(mTitleTourDialog);

        dialogViewTour = builderTour.create();
        //endregion create alertdialog tour

        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mAdapter = new CostSaleProductAdapter(
                getActivity(),
                null,
                0,
                getArguments().getString("witchActivity"));

        View rootView = inflater.inflate(R.layout.fragment_cost_sale_product, container, false);
        mListView = (ListView) rootView.findViewById(R.id.list_view_cost_sale_product);
        mListView.setAdapter(mAdapter);
        TextView emptyText = (TextView) rootView.findViewById(R.id.empty_text_view);
        emptyText.setText(getActivity().getResources().getString(R.string.empty_list_text));
        emptyText.setVisibility(View.VISIBLE);
        mListView.setEmptyView(emptyText);

        //hide fab to show it as animation
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_cost_sale_product);

        fab.hide();

        //region mListView setOnItemClickListener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (getArguments().getString("witchActivity")) {
                    case "cost": {
                        //region Cost
                        mCursor = (Cursor) parent.getItemAtPosition(position);
                        if (mCursor != null) {

                            //region Create Alert dialog
                            builder = new AlertDialog.Builder(getActivity())
                                    .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_edit_cost, null))
                                    .setNegativeButton(R.string.discard_button, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            dialogView.dismiss();
                                        }
                                    }).setPositiveButton(R.string.update_button, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    })
                                    .setTitle(R.string.title_edit_cost);

                            dialogView = builder.create();
                            dialogView.show();

                            dialogView.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Boolean wantToCloseDialog = false;

                                    //region update cost
                                    if (checkValidityWithChangeColorOfHelperText()) {
                                        costValues.put(KasebContract.Costs.COLUMN_COST_NAME, costName.getText().toString());
                                        costValues.put(KasebContract.Costs.COLUMN_COST_CODE, costCode.getText().toString());
                                        costValues.put(KasebContract.Costs.COLUMN_AMOUNT,
                                                Utility.convertFarsiNumbersToDecimal(costAmount.getText().toString()));
                                        costValues.put(KasebContract.Costs.COLUMN_DATE, costDate.getText().toString());
                                        costValues.put(KasebContract.Costs.COLUMN_DESCRIPTION, costDescription.getText().toString());

                                        getActivity().getContentResolver().update(
                                                KasebContract.Costs.CONTENT_URI,
                                                costValues,
                                                KasebContract.Costs._ID + " = ? ",
                                                new String[]{mCursor.getString(mCursor.getColumnIndex(KasebContract.Costs._ID))}
                                        );

                                        //just a message to show everything are under control
                                        Toast.makeText(getContext(),
                                                getContext().getResources().getString(R.string.msg_update_succeed), Toast.LENGTH_LONG).show();

                                        wantToCloseDialog = true;
                                        getHelperText();
                                    }
                                    //endregion update cost

                                    if (wantToCloseDialog)
                                        dialogView.dismiss();
                                }
                            });
                            //endregion Create Alert dialog

                            //region set Values in Alert dialog
                            costType = (Spinner) dialogView.findViewById(R.id.dialog_edit_cost_type_spinner);
                            costName = (EditText) dialogView.findViewById(R.id.dialog_edit_cost_name);
                            costAmount = (EditText) dialogView.findViewById(R.id.dialog_edit_cost_amount);
                            costCode = (EditText) dialogView.findViewById(R.id.dialog_edit_cost_code);
                            costDate = (EditText) dialogView.findViewById(R.id.dialog_edit_cost_date);
                            costDescription = (EditText) dialogView.findViewById(R.id.dialog_edit_cost_description);

                            costNameTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_dialog_edit_cost_name);
                            costCodeTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_dialog_edit_cost_code);
                            costAmountTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_dialog_edit_cost_amount);
                            costDateTextInputLayout = (TextInputLayout) dialogView.findViewById(R.id.text_input_layout_dialog_edit_cost_date);

                            costName.setText(mCursor.getString(mCursor.getColumnIndex(KasebContract.Costs.COLUMN_COST_NAME)));
                            costAmount.setText(mCursor.getString(mCursor.getColumnIndex(KasebContract.Costs.COLUMN_AMOUNT)));
                            costCode.setText(mCursor.getString(mCursor.getColumnIndex(KasebContract.Costs.COLUMN_COST_CODE)));
                            costDate.setText(mCursor.getString(mCursor.getColumnIndex(KasebContract.Costs.COLUMN_DATE)));
                            costDescription.setText(mCursor.getString(mCursor.getColumnIndex(KasebContract.Costs.COLUMN_DESCRIPTION)));
                            String costTypeid = mCursor.getString(mCursor.getColumnIndex(KasebContract.Costs.COLUMN_COST_TYPE_ID));

                            //region handle asterisk for necessary fields

                            //region cost name
                            Utility.setAsteriskToTextInputLayout(costNameTextInputLayout, getResources().getString(R.string.hint_cost_name), true);
                            //endregion cost name

                            costName.requestFocus();

                            //region cost code
                            Utility.setAsteriskToTextInputLayout(costCodeTextInputLayout, getResources().getString(R.string.hint_cost_code), true);
                            //endregion cost code

                            //region cost amount
                            Utility.setAsteriskToTextInputLayout(costAmountTextInputLayout, getResources().getString(R.string.hint_cost_amount), true);
                            //endregion cost amount

                            //region cost date
                            Utility.setAsteriskToTextInputLayout(costDateTextInputLayout, getResources().getString(R.string.hint_date), true);
                            //endregion cost date

                            //endregion handle asterisk for necessary fields

                            Cursor mCursor1 = getContext().getContentResolver().query(
                                    KasebContract.CostTypes.CONTENT_URI,
                                    new String[]{
                                            KasebContract.CostTypes._ID,
                                            KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER},
                                    null, null, null);

                            cursorAdapter = new TypesSettingAdapter(
                                    getContext(), mCursor1, 0,
                                    KasebContract.CostTypes.COLUMN_COST_TYPE_POINTER);

                            costType.setAdapter(cursorAdapter);

                            for (int i = 0; i < costType.getCount(); i++) {
                                Cursor nCursor = (Cursor) costType.getItemAtPosition(i);
                                if (nCursor != null &&
                                        nCursor.getString(
                                                nCursor.getColumnIndex(
                                                        KasebContract.CostTypes._ID))
                                                .equals(costTypeid)) {
                                    costType.setSelection(i);
                                    break;
                                }
                            }

                            costType.setOnItemSelectedListener(
                                    new AdapterView.OnItemSelectedListener() {
                                        @Override
                                        public void onItemSelected(AdapterView<?> arg0, View
                                                arg1, int arg2, long arg3) {

                                            costValues.put(KasebContract.Costs.COLUMN_COST_TYPE_ID, costType.getSelectedItemPosition() + 1);
                                        }

                                        @Override
                                        public void onNothingSelected(AdapterView<?> arg0) {
                                        }

                                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                        }
                                    });
                            //endregion set Values in Alert dialog
                        }
                        //endregion Cost
                        break;
                    }
                    case "sale": {
                        //region Sale
                        mCursor = (Cursor) parent.getItemAtPosition(position);
                        if (mCursor != null) {
                            View sharedView = view.findViewById(R.id.item_list_code);
                            Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, sharedView.getTransitionName()).toBundle();

                            Intent detailSale;
                            detailSale = new Intent(getActivity(), DetailSaleView.class);
                            detailSale.putExtra("forViewAndUpdateSales", true);
                            detailSale.putExtra("saleId", mCursor.getString(mCursor.getColumnIndex(KasebContract.Sales._ID)));
                            detailSale.putExtra("saleCode", mCursor.getString(mCursor.getColumnIndex(KasebContract.Sales.COLUMN_SALE_CODE)));
                            detailSale.putExtra("customerId", mCursor.getLong(mCursor.getColumnIndex(KasebContract.Sales.COLUMN_CUSTOMER_ID)));
                            startActivity(detailSale, bundle);
                        }
                        //endregion Sale
                        break;
                    }
                    case "product": {
                        //region Product
                        View sharedView = view.findViewById(R.id.item_list_code);
                        mCursor = (Cursor) parent.getItemAtPosition(position);
                        if (mCursor != null) {
                            productHistoryBundle.putString("productId", mCursor.getString(mCursor.getColumnIndex(KasebContract.Products._ID)));
                            productHistory.setArguments(productHistoryBundle);
                            fragmentManager = getActivity().getSupportFragmentManager();
                            fragmentManager.beginTransaction()
                                    .addSharedElement(sharedView, sharedView.getTransitionName())
                                    .addToBackStack(null)
                                    .replace(R.id.container, productHistory, "productFragment2Detail").commit();
                        }
                        //endregion Product
                        break;
                    }
                    default:
                        break;
                }
            }
        });
        //endregion mListView setOnItemClickListener

        //region mListView setOnItemLongClickListener
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                switch (getArguments().getString("witchActivity")) {
                    case "cost": {
                        //region Cost
                        mCursor = (Cursor) parent.getItemAtPosition(position);
                        if (mCursor != null) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle(getActivity().getResources().getString(R.string.confirm_title))
                                    .setMessage(getActivity().getResources().getString(R.string.confirm_message_delete_this_cost) +
                                            mCursor.getString(mCursor.getColumnIndex(KasebContract.Costs.COLUMN_COST_NAME)))
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            getActivity().getContentResolver().delete(
                                                    KasebContract.Costs.CONTENT_URI,
                                                    KasebContract.Costs._ID + " = ? ",
                                                    new String[]{mCursor.getString(mCursor.getColumnIndex(KasebContract.Costs._ID))}
                                            );

                                            //just a message to show everything are under control
                                            Toast.makeText(getContext(),
                                                    getContext().getResources().getString(R.string.msg_delete_succeed), Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    }).show();
                        }
                        //endregion Cost
                        break;
                    }
                    case "sale": {
                        //region Sale
                        mCursor = (Cursor) parent.getItemAtPosition(position);
                        if (mCursor != null) {
                            new AlertDialog.Builder(getActivity())
                                    .setTitle(getActivity().getResources().getString(R.string.confirm_title))
                                    .setMessage(getActivity().getResources().getString(R.string.confirm_message_delete_sale) +
                                            mCursor.getString(mCursor.getColumnIndex(KasebContract.Sales.COLUMN_SALE_CODE)))
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                            saleValues.put(KasebContract.Sales.COLUMN_IS_DELETED, 1);

                                            getContext().getContentResolver().update(
                                                    KasebContract.Sales.CONTENT_URI,
                                                    saleValues,
                                                    KasebContract.Sales._ID + " = ? ",
                                                    new String[]{
                                                            mCursor.getString(
                                                                    mCursor.getColumnIndex(KasebContract.Sales._ID))}
                                            );

                                            //just a message to show everything are under control
                                            Toast.makeText(getContext(),
                                                    getContext().getResources().getString(R.string.msg_delete_succeed), Toast.LENGTH_LONG).show();
                                        }
                                    })
                                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int whichButton) {
                                        }
                                    }).show();
                        }
                        //endregion Sale
                        break;
                    }
                    case "product": {
                        //region Product
                        mCursor = (Cursor) parent.getItemAtPosition(position);
                        if (mCursor != null) {
                            long productId = mCursor.getLong(mCursor.getColumnIndex(KasebContract.Products._ID));

                            int numberUseOfThisProduct = getContext().getContentResolver().query(
                                    KasebContract.DetailSaleProducts.uriDetailSaleProductsWithProductId(productId),
                                    new String[]{
                                            KasebContract.DetailSaleProducts._ID},
                                    null, null, null).getCount();

                            if (numberUseOfThisProduct > 0)
                                Toast.makeText(getContext(), String.format("%s %d %s",
                                                getActivity().getResources().getString(R.string.confirm_delete_product_list_it_has_factor),
                                                numberUseOfThisProduct, getActivity().getResources().getString(R.string.confirm_delete_product_list_it_has)),
                                        Toast.LENGTH_LONG).show();
                            else {
                                new AlertDialog.Builder(getActivity())
                                        .setTitle(getActivity().getResources().getString(R.string.confirm_title))
                                        .setMessage(getActivity().getResources().getString(R.string.confirm_delete_product_list) +
                                                mCursor.getString(mCursor.getColumnIndex(KasebContract.Products.COLUMN_PRODUCT_NAME)))
                                        .setIcon(android.R.drawable.ic_dialog_alert)
                                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {

                                                mDb.beginTransaction();
                                                try {
                                                    getActivity().getContentResolver().delete(
                                                            KasebContract.ProductHistory.CONTENT_URI,
                                                            KasebContract.ProductHistory.COLUMN_PRODUCT_ID + " = ? ",
                                                            new String[]{mCursor.getString(mCursor.getColumnIndex(KasebContract.Products._ID))}
                                                    );

                                                    getActivity().getContentResolver().delete(
                                                            KasebContract.Products.CONTENT_URI,
                                                            KasebContract.Products._ID + " = ? ",
                                                            new String[]{mCursor.getString(mCursor.getColumnIndex(KasebContract.Products._ID))}
                                                    );

                                                    mDb.setTransactionSuccessful();
                                                    mDb.endTransaction();
                                                } catch (Exception e) {
                                                    mDb.endTransaction();
                                                }
                                                //just a message to show everything are under control
                                                Toast.makeText(getContext(),
                                                        getContext().getResources().getString(R.string.msg_delete_succeed), Toast.LENGTH_LONG).show();
                                            }
                                        })
                                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int whichButton) {
                                            }
                                        }).show();
                            }

                        }
                        //endregion Product
                        break;
                    }
                    default:
                        break;
                }

                return true;
            }
        });
        //endregion mListView setOnItemLongClickListener

        return rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        switch (getArguments().getString("witchActivity")) {
            case "cost": {
                inflater.inflate(R.menu.menu_costs, menu);
                break;
            }
            case "sale": {
                inflater.inflate(R.menu.menu_sales, menu);
                break;
            }
            case "product": {
                inflater.inflate(R.menu.menu_products, menu);
                break;
            }
            default:
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, "onStart");

        costValues = new ContentValues();
        saleValues = new ContentValues();

        super.onStart();
        updateList();
    }

    @Override
    public void onResume() {
        super.onResume();
        //define animation for scaling fab
        Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.scale_up);
        fab.startAnimation(hyperspaceJumpAnimation);
        fab.show();

        //region tryCatch tour
        try {
            if (kasebSharedPreferences.getBoolean("getStarted", false)) {

                String mMessage = "";
                switch (getArguments().getString("witchActivity")) {
                    case "cost": {
                        //region cost
                        break;
                        //endregion cost
                    }
                    case "sale": {
                        //region sale
                        mMessage = getResources().getString(R.string.tour_text_sale_list);
                        break;
                        //endregion sale
                    }
                    case "product": {
                        //region product
                        mMessage = getResources().getString(R.string.tour_text_product_list);
                        break;
                        //endregion product
                    }
                    default:
                        break;
                }

                dialogViewTour.setMessage(mMessage);
                dialogViewTour.show();
                dialogViewTour.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);

                switch (getArguments().getString("witchActivity")) {
                    case "cost": {
                        //region cost
                        break;
                        //endregion cost
                    }
                    case "sale": {
                        //region sale

                        dialogViewTour.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ((Sales) getActivity()).fab_cost_sale_product(getView());

                                dialogViewTour.dismiss();
                            }
                        });

                        dialogViewTour.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //region back tour
                                getFragmentManager().popBackStackImmediate();

                                Intent intent = new Intent(getActivity(), Products.class);
                                startActivity(intent);
                                Utility.setActivityTransition(getActivity());

                                dialogViewTour.dismiss();
                                //endregion back tour
                            }
                        });

                        break;
                        //endregion sale
                    }
                    case "product": {
                        //region product

                        dialogViewTour.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ((Products) getActivity()).fab_cost_sale_product(getView());

                                dialogViewTour.dismiss();
                            }
                        });

                        dialogViewTour.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                //region back tour
                                getFragmentManager().popBackStackImmediate();

                                Intent intent = new Intent(getActivity(), Customers.class);
                                startActivity(intent);
                                Utility.setActivityTransition(getActivity());

                                dialogViewTour.dismiss();
                                //endregion back tour
                            }
                        });

                        break;
                        //endregion product
                    }
                    default:
                        break;
                }

                dialogViewTour.getButton(AlertDialog.BUTTON_NEUTRAL).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //region end tour
                        editor.putBoolean("getStarted", false);
                        editor.apply();

                        dialogViewTour.dismiss();
                        //endregion end tour

                    }
                });

                dialogViewTour.setCancelable(false);
                dialogViewTour.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //region end tour
                        editor.putBoolean("getStarted", false);
                        editor.apply();
                        //endregion end tour
                    }
                });
            }
        } catch (Exception e) {
        }
        //endregion tryCatch tour
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FRAGMENT_COST_SALE_PRODUCT_LOADER, null, this);
    }

    public void getSearchQuery(String query) {
        searchQuery = query;
        updateList();
    }

    public void getSortOrder(int id) {
        sortId = id;
        updateList();
    }

    private void updateList() {
        getLoaderManager().restartLoader(FRAGMENT_COST_SALE_PRODUCT_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        Uri cursorUri = null;
        String _idColumn = null;
        String whereClause = null;
        String[] selectArg = null;
        switch (getArguments().getString("witchActivity")) {
            case "cost": {
                cursorUri = KasebContract.Costs.CONTENT_URI;
                _idColumn = KasebContract.Costs._ID;
                if (searchQuery != null) {
                    whereClause = mProjection[1] + " LIKE ? " + " OR " + mProjection[3] + " LIKE ? ";
                    selectArg = new String[2];
                    selectArg[0] = "%" + searchQuery + "%";
                    selectArg[1] = "%" + searchQuery + "%";
                }
                switch (sortId) {
                    case R.id.menu_sort_code:
                        sortOrder = KasebContract.Costs.COLUMN_COST_CODE + " ASC," + KasebContract.Costs.COLUMN_COST_NAME + " ASC";
                        break;
                    case R.id.menu_sort_name:
                        sortOrder = KasebContract.Costs.COLUMN_DATE + " ASC," + KasebContract.Costs.COLUMN_COST_NAME + " ASC";
                        break;
                }
                break;
            }
            case "sale": {
                cursorUri = KasebContract.Sales.CONTENT_URI;
                _idColumn = KasebContract.Sales._ID;
                whereClause = KasebContract.Sales.COLUMN_IS_DELETED + " = ? ";
                selectArg = new String[]{"0"};
                if (searchQuery != null) {
                    whereClause = KasebContract.Sales.COLUMN_IS_DELETED + " = ? and " + mProjection[2] + " LIKE ? ";
                    selectArg = new String[2];
                    selectArg[0] = "0";
                    selectArg[1] = "%" + searchQuery + "%";
                }
                switch (sortId) {
                    case R.id.menu_sort_code:
                        sortOrder = KasebContract.Sales.COLUMN_SALE_CODE + " ASC";
                        break;
                }
                break;
            }
            case "product": {
                cursorUri = KasebContract.Products.CONTENT_URI;
                _idColumn = KasebContract.Products._ID;
                if (searchQuery != null) {
                    whereClause = mProjection[1] + " LIKE ? " + " OR " + mProjection[2] + " LIKE ? ";
                    selectArg = new String[2];
                    selectArg[0] = "%" + searchQuery + "%";
                    selectArg[1] = "%" + searchQuery + "%";
                }
                switch (sortId) {
                    case R.id.menu_sort_code:
                        sortOrder = KasebContract.Products.COLUMN_PRODUCT_CODE + " ASC";
                        break;
                    case R.id.menu_sort_name:
                        sortOrder = KasebContract.Products.COLUMN_PRODUCT_NAME + " ASC";
                        break;
                }
                break;
            }
            default:
                new UnsupportedOperationException("Cost/Sale/Product Code Not Match..!");
        }

        mProjection[0] = _idColumn;
        return new CursorLoader(getActivity(), cursorUri, mProjection, whereClause, selectArg, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(LOG_TAG, "onLoadFinished");
        mAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "onLoadReset");
        mAdapter.swapCursor(null);
    }

    private void getHelperText() {

        costNameTextInputLayout.setError(null);
        costCodeTextInputLayout.setError(null);
        costAmountTextInputLayout.setError(null);
        costDateTextInputLayout.setError(null);

    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private boolean checkValidityWithChangeColorOfHelperText() {

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), costName)) {
            costNameTextInputLayout.setError(getResources().getString(R.string.example_cost_name));
            costName.setSelectAllOnFocus(true);
            costName.selectAll();
            costName.requestFocus();
            return false;
        } else
            costNameTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), costCode)) {
            costCodeTextInputLayout.setError(getResources().getString(R.string.example_cost_code));
            costCode.setSelectAllOnFocus(true);
            costCode.selectAll();
            costCode.requestFocus();
            return false;
        } else
            costCodeTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getActivity(), costAmount)) {
            costAmountTextInputLayout.setError(getResources().getString(R.string.example_price));
            costAmount.setSelectAllOnFocus(true);
            costAmount.selectAll();
            costAmount.requestFocus();
            return false;
        } else
            costAmountTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextDate(getActivity(), costDate)) {
            costDateTextInputLayout.setError(getResources().getString(R.string.example_date));
            costDate.setSelectAllOnFocus(true);
            costDate.selectAll();
            costDate.requestFocus();
            return false;
        } else
            costDateTextInputLayout.setError(null);

        return true;
    }
}
