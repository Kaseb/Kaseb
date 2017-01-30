package mjkarbasian.moshtarimadar.Customers;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;

import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.GalleryUtil;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.Others.DrawerActivity;
import mjkarbasian.moshtarimadar.R;

public class Customers extends DrawerActivity {

    private static final int REQUEST_CROP_URI = 800;
    private static final int GALLERY_ACTIVITY_CODE = 200;
    private static final int RESULT_CROP = 400;
    Context mContext;
    String mQuery;
    ImageView mCustomerAvatar;
    Fragment customersFragment = new CustomersLists();
    Fragment customerInsert = new CustomerInsert();
    Bitmap photo;
    Long customerId;
    SharedPreferences kasebSharedPreferences;
    SharedPreferences.Editor editor;

    android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
    private String LOG_TAG = Customers.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setDefaultKeyMode(DEFAULT_KEYS_SEARCH_LOCAL);
        //this line initialize all references

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mContext = this;

        //region handle sharepreference
        kasebSharedPreferences = getSharedPreferences(getResources().getString(R.string.kasebPreference), MODE_PRIVATE);
        editor = kasebSharedPreferences.edit();
        //endregion handle sharepreference

        //region handle search query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            handleIntent(intent);
        } else
            fragmentManager.beginTransaction().replace(R.id.container, customersFragment, "customersList").commit();

        //endregion handle search query
    }

    @Override
    public void onBackPressed() {
        try {
            if (!fragmentManager.findFragmentByTag("customerInsertTag").isResumed())
                Utility.activityOnBackExit(this);
            else
                super.onBackPressed();
        } catch (Exception e) {
            Utility.activityOnBackExit(this);
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        CustomersLists queryFragment = (CustomersLists) fragmentManager.findFragmentByTag("customersList");
        queryFragment.getSearchQuery(query);
    }

    public void fab_customers(View v) {

        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.slide_in_up, R.anim.slide_out_down, R.anim.slide_in_down, R.anim.slide_out_up);
        fragmentTransaction.replace(R.id.container, customerInsert, "customerInsertTag");
        fragmentTransaction.addToBackStack(null);
        int callBackStack = fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_customers, menu);

        // Associate searchable configuration with the SearchView
        SearchManager searchManager =
                (SearchManager) getSystemService(this.SEARCH_SERVICE);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search_button).getActionView();
        searchView.setSearchableInfo(
                searchManager.getSearchableInfo(getComponentName()));
        SearchView.OnQueryTextListener queryTextListener = new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextChange(String newText) {
                doMySearch(newText);
                // this is your adapter that will be filtered
                return true;
            }

            @Override
            public boolean onQueryTextSubmit(String query) {
                mQuery = query;
                //Here u can get the value "query" which is entered in the search box.
                return (query != null);
            }
        };
        searchView.setOnQueryTextListener(queryTextListener);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        CustomersLists sortFragment = (CustomersLists) fragmentManager.findFragmentByTag("customersList");
        switch (item.getItemId()) {
            case R.id.menu_sort_newest:
                sortFragment.getSortOrder(R.id.menu_sort_newest);
                break;
            case R.id.menu_sort_rating:
                sortFragment.getSortOrder(R.id.menu_sort_rating);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    public void pic_selector(View view, Long _customerId) {
        mCustomerAvatar = (ImageView) view;
        customerId = _customerId;
        Intent gallery_Intent = new Intent(getApplicationContext(), GalleryUtil.class);
        startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String picturePath = data.getStringExtra("picturePath");
                //perform Crop on the Image Selected from Gallery
                performCrop(picturePath);
            }
        }
        if (requestCode == RESULT_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                if (data.getExtras() != null) {
                    photo = data.getExtras().getParcelable("data");
                    mCustomerAvatar.setImageBitmap(photo);

                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    photo.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                    byte[] imagegBytes = byteArrayOutputStream.toByteArray();

                    ContentValues customerValues = new ContentValues();
                    customerValues.put(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE, imagegBytes);

                    getBaseContext().getContentResolver().update(
                            KasebContract.Customers.CONTENT_URI,
                            customerValues,
                            KasebContract.Customers._ID + " = ? ",
                            new String[]{String.valueOf(customerId)});
                } else if (data.getData() != null) {
                    Uri picUri = data.getData();
                    BufferedInputStream bufferInputStream = null;
                    try {
                        URLConnection connection = new URL(picUri.toString()).openConnection();
                        connection.connect();
                        bufferInputStream = new BufferedInputStream(connection.getInputStream(), 8192);
                        photo = BitmapFactory.decodeStream(bufferInputStream);

                        mCustomerAvatar.setImageBitmap(photo);

                        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                        photo.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
                        byte[] imagegBytes = byteArrayOutputStream.toByteArray();

                        ContentValues customerValues = new ContentValues();
                        customerValues.put(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE, imagegBytes);

                        getBaseContext().getContentResolver().update(
                                KasebContract.Customers.CONTENT_URI,
                                customerValues,
                                KasebContract.Customers._ID + " = ? ",
                                new String[]{String.valueOf(customerId)});
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else
                    Toast.makeText(this, R.string.problem_in_crop_image, Toast.LENGTH_LONG).show();
            }
        }
    }

    private void performCrop(String picUri) {
        try {
            //Start Crop Activity

            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            File f = new File(picUri);
            Uri contentUri = Uri.fromFile(f);

            cropIntent.setDataAndType(contentUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280);

            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            Toast toast = Toast.makeText(this, R.string.does_not_support_crop_action, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    public void pic_deleter(final View view, final Long _customerId) {
        customerId = _customerId;

        Cursor mCursor = getContentResolver().query(
                KasebContract.Customers.CONTENT_URI,
                new String[]{
                        KasebContract.Customers.COLUMN_FIRST_NAME,
                        KasebContract.Customers.COLUMN_LAST_NAME},
                KasebContract.Customers._ID + " = ? ",
                new String[]{String.valueOf(customerId)},
                null);

        String infoCustomer = null;
        if (mCursor != null)
            if (mCursor.moveToFirst())
                infoCustomer = mCursor.getString(mCursor.getColumnIndex(KasebContract.Customers.COLUMN_FIRST_NAME)) + "   " +
                        mCursor.getString(mCursor.getColumnIndex(KasebContract.Customers.COLUMN_LAST_NAME));

        mCursor.close();

        new AlertDialog.Builder(this)
                .setTitle(R.string.confirm_title)
                .setMessage(getString(R.string.confirm_message_delete_customer_image) + infoCustomer)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        mCustomerAvatar = (ImageView) view;

                        ContentValues customerValues = new ContentValues();
                        customerValues.put(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE, new byte[]{});

                        getBaseContext().getContentResolver().update(
                                KasebContract.Customers.CONTENT_URI,
                                customerValues,
                                KasebContract.Customers._ID + " = ? ",
                                new String[]{String.valueOf(customerId)});
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                    }
                }).show();
    }
}
