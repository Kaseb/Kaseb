package mjkarbasian.moshtarimadar.Customers;

import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import mjkarbasian.moshtarimadar.Adapters.CustomerAdapter;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.R;


/**
 * A placeholder fragment containing a simple view.
 */
public class CustomersLists extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    //region declare values
    final static int FRAGMENT_CUSTOMER_LOADER = 2;
    private final String LOG_TAG = CustomersLists.class.getSimpleName();
    String searchQuery;
    FloatingActionButton fab;
    CustomerAdapter mCustomerAdapter = null;
    ListView mListView;
    String[] mProjection;
    String kasebPREFERENCES = "kasebProfile";

    SharedPreferences kasebSharedPreferences;
    SharedPreferences.Editor editor;

    AlertDialog.Builder builder;
    AlertDialog dialogView;
    AlertDialog.Builder builderTour;
    AlertDialog dialogViewTour;

    private String sortOrder = null;
    //endregion declare values

    public CustomersLists() {
        super();
    }

    public void onCreate(Bundle savedInstanceState) {
        mProjection = new String[]{
                KasebContract.Customers._ID,
                KasebContract.Customers.COLUMN_FIRST_NAME,
                KasebContract.Customers.COLUMN_LAST_NAME,
                KasebContract.Customers.COLUMN_STATE_ID,
                KasebContract.Customers.COLUMN_CUSTOMER_PICTURE};

        //region handle sharepreference
        kasebSharedPreferences = getActivity().getSharedPreferences(kasebPREFERENCES, getActivity().MODE_PRIVATE);
        editor = kasebSharedPreferences.edit();
        //endregion handle sharepreference

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (getArguments() != null) searchQuery = getArguments().getString("query");
        mCustomerAdapter = new CustomerAdapter(
                getActivity(),
                null,
                0);

        View rootView = inflater.inflate(R.layout.fragment_customers, container, false);

        mListView = (ListView) rootView.findViewById(R.id.list_view_customers);
        mListView.setAdapter(mCustomerAdapter);

        TextView emptyText = (TextView) rootView.findViewById(R.id.empty_text_view);
        emptyText.setText(getActivity().getResources().getString(R.string.empty_list_text));
        emptyText.setVisibility(View.VISIBLE);
        mListView.setEmptyView(emptyText);

        //hide fab to show it as animation
        fab = (FloatingActionButton) rootView.findViewById(R.id.fab_customers);

        try {
            if (kasebSharedPreferences.getBoolean("getStarted", false)) {

                getActivity().getWindow().setSoftInputMode(
                        WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
                );

                //region create alertdialog tour
                builderTour = new AlertDialog.Builder(getActivity())
                        .setNegativeButton(R.string.cancel_tour, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).setPositiveButton(R.string.next_tour, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).setTitle(R.string.title_customer);

                dialogViewTour = builderTour.create();
                //endregion create alertdialog tour

                //region create tour dialog
                builder = new AlertDialog.Builder(getActivity())
                        .setView(getActivity().getLayoutInflater().inflate(R.layout.dialog_choose_tour, null))
                        .setNegativeButton(R.string.discard_button, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).setPositiveButton(R.string.start_tour, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                            }
                        }).setTitle(R.string.title_take_tour);

                dialogView = builder.create();
                dialogView.show();
                dialogView.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                dialogView.setCancelable(false);
                dialogView.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialog) {
                        //region end tour
                        editor.putBoolean("getStarted", false);
                        editor.apply();
                        //endregion end tour
                    }
                });

                dialogView.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //region end tour
                        editor.putBoolean("getStarted", false);
                        editor.apply();
                        //endregion end tour

                        dialogView.dismiss();
                    }
                });

                dialogView.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        //region start tour
                        editor.putBoolean("getStarted", true);
                        editor.apply();
                        //region

                        dialogViewTour.setMessage(getResources().getString(R.string.tour_text_customer_list));
                        dialogViewTour.show();

                        dialogViewTour.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                        dialogViewTour.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                ((Customers) getActivity()).fab_customers(getView());

                                dialogViewTour.dismiss();
                            }
                        });

                        dialogViewTour.getButton(AlertDialog.BUTTON_NEGATIVE).setOnClickListener(new View.OnClickListener() {
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

                        dialogView.dismiss();
                        //endregion

                        //endregion start tour
                    }
                });
                //endregion create tour dialog
            }
        } catch (Exception e) {
        }

        fab.hide();
        final Animation hyperspaceJumpAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fab_on_click);
        fab.setAnimation(hyperspaceJumpAnimation);
        fab.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (hyperspaceJumpAnimation.getRepeatCount() < 2)
                    v.startAnimation(hyperspaceJumpAnimation);
                return false;
            }
        });

        //region mListView setOnItemClickListener
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //animation bundle
                View sharedView = view.findViewById(R.id.item_list_customer_avatar);
                Bundle bundle = ActivityOptions.makeSceneTransitionAnimation(getActivity(), sharedView, sharedView.getTransitionName()).toBundle();
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    Intent intent = new Intent(getActivity(), DetailCustomer.class)
                            .setData(KasebContract.Customers.buildCustomerUri(
                                    Long.parseLong(cursor.getString(cursor.getColumnIndex(KasebContract.Customers._ID)))
                            ));
                    startActivity(intent, bundle);
                }
            }
        });
        //endregion mListView setOnItemClickListener

        //region mListView setOnItemLongClickListener
        mListView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                final Cursor cursor = (Cursor) parent.getItemAtPosition(position);
                if (cursor != null) {
                    final long customerId = cursor.getLong(cursor.getColumnIndex(KasebContract.Customers._ID));

                    int numberUseOfThisCustomer = getContext().getContentResolver().query(
                            KasebContract.Sales.customerSales(customerId),
                            new String[]{
                                    KasebContract.Sales._ID},
                            null, null, null).getCount();

                    if (numberUseOfThisCustomer > 0)
                        Toast.makeText(getContext(),
                                getActivity().getResources().getString(R.string.confirm_delete_customer_list_it_has)
                                        + numberUseOfThisCustomer
                                        + getActivity().getResources().getString(R.string.confirm_delete_customer_list_factors), Toast.LENGTH_LONG).show();
                    else {
                        new AlertDialog.Builder(getActivity())
                                .setTitle(getActivity().getResources().getString(R.string.confirm_title))
                                .setMessage(getActivity().getResources().getString(R.string.confirm_want_delete_customer_info) +
                                        cursor.getString(cursor.getColumnIndex(KasebContract.Customers.COLUMN_FIRST_NAME)) + "  " +
                                        cursor.getString(cursor.getColumnIndex(KasebContract.Customers.COLUMN_LAST_NAME)))
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        getActivity().getContentResolver().delete(
                                                KasebContract.Customers.CONTENT_URI,
                                                KasebContract.Customers._ID + " = ? ",
                                                new String[]{String.valueOf(customerId)}
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
        inflater.inflate(R.menu.menu_customers, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    public void getSearchQuery(String query) {
        searchQuery = query;
        updateList();
    }

    public void getSortOrder(int id) {
        switch (id) {
            case R.id.menu_sort_newest:
                sortOrder = KasebContract.Customers.COLUMN_FIRST_NAME + " ASC," + KasebContract.Customers.COLUMN_LAST_NAME + " ASC";
                break;
            case R.id.menu_sort_rating:
                sortOrder = KasebContract.Customers.COLUMN_STATE_ID + " ASC," + KasebContract.Customers.COLUMN_FIRST_NAME + " ASC";
                break;
        }
        updateList();
    }

    @Override
    public void onStart() {
        Log.d(LOG_TAG, "onStart");
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
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        Log.d(LOG_TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(FRAGMENT_CUSTOMER_LOADER, null, this);
    }

    private void updateList() {
        getLoaderManager().restartLoader(FRAGMENT_CUSTOMER_LOADER, null, this);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Log.d(LOG_TAG, "onCreateLoader");
        String whereClause;
        String[] selectArg = null;
        if (searchQuery != null) {
            whereClause = mProjection[1] + " LIKE ? " + " OR " + mProjection[2] + " LIKE ? ";
            selectArg = new String[2];
            selectArg[0] = "%" + searchQuery + "%";
            selectArg[1] = "%" + searchQuery + "%";
        } else {
            whereClause = null;
        }
        return new CursorLoader(getActivity(), KasebContract.Customers.CONTENT_URI, mProjection, whereClause, selectArg, sortOrder);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        Log.d(LOG_TAG, "onLoadFinished");
        mCustomerAdapter.swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        Log.d(LOG_TAG, "onLoadReset");
        mCustomerAdapter.swapCursor(null);
    }


}
