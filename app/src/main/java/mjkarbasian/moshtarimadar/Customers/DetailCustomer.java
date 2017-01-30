package mjkarbasian.moshtarimadar.Customers;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import mjkarbasian.moshtarimadar.Adapters.DetailCustomerAdapter;
import mjkarbasian.moshtarimadar.Data.KasebContract;
import mjkarbasian.moshtarimadar.Helpers.RoundImageView;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;


public class DetailCustomer extends AppCompatActivity {

    //region declare values
    Toolbar mToolbar;
    RoundImageView mCustomerAvatar;
    byte[] imagegBytes;
    ContentValues customerValues = new ContentValues();

    MenuItem saveItem;
    MenuItem editItem;
    MenuItem membershipItem;
    MenuItem goldItem;
    MenuItem silverItem;
    MenuItem bronzeItem;
    MenuItem ordinaryItem;

    String[] mProjection;
    String nameCustomer;
    String familyCustomer;

    int mCustomerId;
    int mStateId;

    EditText customerFirstName;
    EditText customerLastName;
    EditText customerBirthDay;
    EditText customerPhoneMobile;
    EditText customerDescription;
    EditText customerEmail;
    EditText customerPhoneWork;
    EditText customerPhoneHome;
    EditText customerPhoneOther;
    EditText customerPhoneFax;
    EditText customerAddressCountry;
    EditText customerAddressCity;
    EditText customerAddressStreet;
    EditText customerAddressPostalCode;

    TextInputLayout firstNameTextInputLayout;
    TextInputLayout lastNameTextInputLayout;
    TextInputLayout phoneMobileTextInputLayout;
    TextInputLayout birthDayTextInputLayout;
    TextInputLayout customerDescriptionTextInputLayout;
    TextInputLayout emailTextInputLayout;
    TextInputLayout phoneWorkTextInputLayout;
    TextInputLayout phoneHomeTextInputLayout;
    TextInputLayout phoneFaxTextInputLayout;
    TextInputLayout phoneOtherTextInputLayout;
    TextInputLayout addressCountryTextInputLayout;
    TextInputLayout addressCityTextInputLayout;
    TextInputLayout addressStreetTextInputLayout;
    TextInputLayout addressPostalCodeTextInputLayout;
    //endregion declare values

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_customer);
        mToolbar = (Toolbar) findViewById(R.id.customer_detail_toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.detail_customer_toolbar_title));

        if (!(Utility.getLocale(this).equals("IR"))) {
            mToolbar.setNavigationIcon(R.drawable.arrow_left);
        } else
            mToolbar.setNavigationIcon(R.drawable.arrow_right);

        //region define and cast views
        customerFirstName = (EditText) findViewById(R.id.customer_first_name);
        customerLastName = (EditText) findViewById(R.id.customer_last_name);
        customerBirthDay = (EditText) findViewById(R.id.customer_birth_day);
        customerPhoneMobile = (EditText) findViewById(R.id.customer_phone_mobile);
        customerDescription = (EditText) findViewById(R.id.customer_description);
        customerEmail = (EditText) findViewById(R.id.customer_email);
        customerPhoneWork = (EditText) findViewById(R.id.customer_phone_work);
        customerPhoneHome = (EditText) findViewById(R.id.customer_phone_home);
        customerPhoneOther = (EditText) findViewById(R.id.customer_phone_other);
        customerPhoneFax = (EditText) findViewById(R.id.customer_phone_fax);
        customerAddressCountry = (EditText) findViewById(R.id.customer_address_country);
        customerAddressCity = (EditText) findViewById(R.id.customer_address_city);
        customerAddressStreet = (EditText) findViewById(R.id.customer_address_street);
        customerAddressPostalCode = (EditText) findViewById(R.id.customer_address_postal_code);
        mCustomerAvatar = (RoundImageView) findViewById(R.id.image_toolbar);
        //endregion

        Intent intent = getIntent();
        Uri uri = intent.getData();
        mProjection = new String[]{
                KasebContract.Customers._ID,
                KasebContract.Customers.COLUMN_FIRST_NAME,
                KasebContract.Customers.COLUMN_LAST_NAME,
                KasebContract.Customers.COLUMN_STATE_ID,
                KasebContract.Customers.COLUMN_CUSTOMER_PICTURE};

        Cursor customerCursor = getBaseContext().getContentResolver().query(
                uri,
                mProjection,
                null,
                null,
                null
        );

        if (customerCursor != null) {
            if (customerCursor.moveToFirst()) {
                mCustomerId = customerCursor.getInt(customerCursor.getColumnIndex(KasebContract.Customers._ID));
                nameCustomer = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_FIRST_NAME));
                familyCustomer = customerCursor.getString(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_LAST_NAME));
                mStateId = customerCursor.getInt(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_STATE_ID));
                imagegBytes = customerCursor.getBlob(customerCursor.getColumnIndex(KasebContract.Customers.COLUMN_CUSTOMER_PICTURE));
            }
        }

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.customer_tab_info)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.customer_tab_dash)));
        tabLayout.addTab(tabLayout.newTab().setText(getResources().getString(R.string.customer_tab_bill)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final DetailCustomerAdapter adapter = new DetailCustomerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount(), this, uri);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    saveItem.setVisible(false);
                    editItem.setVisible(true);
                } else {
                    saveItem.setVisible(false);
                    editItem.setVisible(false);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        customerCursor.close();

        customerBirthDay = (EditText) findViewById(R.id.customer_birth_day);
        customerPhoneMobile = (EditText) findViewById(R.id.customer_phone_mobile);
        customerDescription = (EditText) findViewById(R.id.customer_description);
        customerEmail = (EditText) findViewById(R.id.customer_email);
        customerPhoneWork = (EditText) findViewById(R.id.customer_phone_work);
        customerPhoneHome = (EditText) findViewById(R.id.customer_phone_home);
        customerPhoneOther = (EditText) findViewById(R.id.customer_phone_other);
        customerPhoneFax = (EditText) findViewById(R.id.customer_phone_fax);
        customerAddressCountry = (EditText) findViewById(R.id.customer_address_country);
        customerAddressCity = (EditText) findViewById(R.id.customer_address_city);
        customerAddressStreet = (EditText) findViewById(R.id.customer_address_street);
        customerAddressPostalCode = (EditText) findViewById(R.id.customer_address_postal_code);

        mCustomerAvatar = (RoundImageView) findViewById(R.id.image_toolbar);

        customerFirstName.setText(nameCustomer);
        customerLastName.setText(familyCustomer);

        customerFirstName.setEnabled(false);
        customerLastName.setEnabled(false);

        try {
            if (imagegBytes.length == 0)
                mCustomerAvatar.setImageDrawable(getBaseContext().getResources().getDrawable(
                        getBaseContext().getResources().getIdentifier("@drawable/kaseb_pic", null, getPackageName())));
            else {
                mCustomerAvatar.setImageBitmap(BitmapFactory.decodeByteArray(imagegBytes, 0, imagegBytes.length));
            }
        } catch (Exception e) {
        }
        //endregion

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_detail_customer, menu);

        saveItem = (MenuItem) menu.findItem(R.id.item_save_customer);
        editItem = (MenuItem) menu.findItem(R.id.item_edit_customer);
        membershipItem = (MenuItem) menu.findItem(R.id.customer_detail_membership_icon);

        switch (mStateId) {
            case 1:
                membershipItem.setIcon(getResources().getDrawable(R.drawable.star_gold));
                break;
            case 2:
                membershipItem.setIcon(getResources().getDrawable(R.drawable.star_silver));
                break;
            case 3:
                membershipItem.setIcon(getResources().getDrawable(R.drawable.star_bronze));
                break;
            case 4:
                membershipItem.setIcon(getResources().getDrawable(R.drawable.star_ordinary));
                break;
            default:
                break;
        }

        saveItem.setVisible(false);

        customerFirstName = (EditText) findViewById(R.id.customer_first_name);
        customerLastName = (EditText) findViewById(R.id.customer_last_name);
        customerBirthDay = (EditText) findViewById(R.id.customer_birth_day);
        customerPhoneMobile = (EditText) findViewById(R.id.customer_phone_mobile);
        customerDescription = (EditText) findViewById(R.id.customer_description);
        customerEmail = (EditText) findViewById(R.id.customer_email);
        customerPhoneWork = (EditText) findViewById(R.id.customer_phone_work);
        customerPhoneHome = (EditText) findViewById(R.id.customer_phone_home);
        customerPhoneOther = (EditText) findViewById(R.id.customer_phone_other);
        customerPhoneFax = (EditText) findViewById(R.id.customer_phone_fax);
        customerAddressCountry = (EditText) findViewById(R.id.customer_address_country);
        customerAddressCity = (EditText) findViewById(R.id.customer_address_city);
        customerAddressStreet = (EditText) findViewById(R.id.customer_address_street);
        customerAddressPostalCode = (EditText) findViewById(R.id.customer_address_postal_code);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // make update for membership selection
        Uri uri = KasebContract.Customers.CONTENT_URI;
        ContentValues contentValues = new ContentValues();
        String key = KasebContract.Customers.COLUMN_STATE_ID;
        int value = 0;
        String selection = KasebContract.State.COLUMN_STATE_COLOR + " = ? ";
        String[] selectArg;
        Cursor cursor;
        String updateSelect = KasebContract.Customers._ID + " = ?";
        String[] updSelArg = new String[]{String.valueOf(mCustomerId)};

        //region Switch Case for state type
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                break;
            case R.id.item_save_customer:
                if (checkValidityWithChangeColorOfHelperText()) {

                    saveItem.setVisible(false);
                    editItem.setVisible(true);

                    customerValues.put(KasebContract.Customers.COLUMN_FIRST_NAME, customerFirstName.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_LAST_NAME, customerLastName.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_BIRTHDAY, customerBirthDay.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_MOBILE, customerPhoneMobile.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_WORK, customerPhoneWork.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_HOME, customerPhoneHome.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_FAX, customerPhoneFax.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_PHONE_OTHER, customerPhoneOther.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_DESCRIPTION, customerDescription.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_EMAIL, customerEmail.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_COUNTRY, customerAddressCountry.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_CITY, customerAddressCity.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_STREET, customerAddressStreet.getText().toString());
                    customerValues.put(KasebContract.Customers.COLUMN_ADDRESS_POSTAL_CODE, customerAddressPostalCode.getText().toString());

                    getBaseContext().getContentResolver().update(
                            KasebContract.Customers.CONTENT_URI,
                            customerValues,
                            KasebContract.Customers._ID + " = ? ",
                            new String[]{String.valueOf(mCustomerId)}
                    );

                    //just a message to show everything are under control
                    Toast.makeText(getBaseContext(), getBaseContext().getResources().getString(R.string.msg_update_succeed),
                            Toast.LENGTH_LONG).show();

                    customerFirstName.setEnabled(false);
                    customerLastName.setEnabled(false);
                    customerBirthDay.setEnabled(false);
                    customerPhoneMobile.setEnabled(false);
                    customerDescription.setEnabled(false);
                    customerEmail.setEnabled(false);
                    customerPhoneWork.setEnabled(false);
                    customerPhoneHome.setEnabled(false);
                    customerPhoneOther.setEnabled(false);
                    customerPhoneFax.setEnabled(false);
                    customerAddressCountry.setEnabled(false);
                    customerAddressCity.setEnabled(false);
                    customerAddressStreet.setEnabled(false);
                    customerAddressPostalCode.setEnabled(false);

                    getHelperText();
                }
                break;
            case R.id.item_edit_customer:

                saveItem.setVisible(true);
                editItem.setVisible(false);

                customerFirstName = (EditText) findViewById(R.id.customer_first_name);
                customerLastName = (EditText) findViewById(R.id.customer_last_name);
                customerBirthDay = (EditText) findViewById(R.id.customer_birth_day);
                customerPhoneMobile = (EditText) findViewById(R.id.customer_phone_mobile);
                customerDescription = (EditText) findViewById(R.id.customer_description);
                customerEmail = (EditText) findViewById(R.id.customer_email);
                customerPhoneWork = (EditText) findViewById(R.id.customer_phone_work);
                customerPhoneHome = (EditText) findViewById(R.id.customer_phone_home);
                customerPhoneOther = (EditText) findViewById(R.id.customer_phone_other);
                customerPhoneFax = (EditText) findViewById(R.id.customer_phone_fax);
                customerAddressCountry = (EditText) findViewById(R.id.customer_address_country);
                customerAddressCity = (EditText) findViewById(R.id.customer_address_city);
                customerAddressStreet = (EditText) findViewById(R.id.customer_address_street);
                customerAddressPostalCode = (EditText) findViewById(R.id.customer_address_postal_code);

                firstNameTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_first_name);
                lastNameTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_last_name);
                phoneMobileTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_phone_mobile);
                birthDayTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_birth_day);
                customerDescriptionTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_description);
                emailTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_email);
                phoneWorkTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_phone_work);
                phoneHomeTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_phone_home);
                phoneOtherTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_phone_other);
                phoneFaxTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_phone_fax);
                addressCountryTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_address_country);
                addressCityTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_address_city);
                addressStreetTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_address_street);
                addressPostalCodeTextInputLayout = (TextInputLayout) findViewById(R.id.text_input_layout_customer_address_postal_code);

                //region handle asterisk for necessary fields

                //region first name
                Utility.setAsteriskToTextInputLayout(firstNameTextInputLayout, getResources().getString(R.string.hint_first_name), true);
                //endregion first name

                customerFirstName.requestFocus();

                //region last name
                Utility.setAsteriskToTextInputLayout(lastNameTextInputLayout, getResources().getString(R.string.hint_last_name), true);
                //endregion last name

                //region mobile number
                Utility.setAsteriskToTextInputLayout(phoneMobileTextInputLayout, getResources().getString(R.string.hint_mobile_number), true);
                //endregion mobile number

                //endregion handle asterisk for necessary fields

                //endregion handle asterisk for necessary fields

                customerFirstName.setEnabled(true);
                customerLastName.setEnabled(true);
                customerBirthDay.setEnabled(true);
                customerPhoneMobile.setEnabled(true);
                customerDescription.setEnabled(true);
                customerEmail.setEnabled(true);
                customerPhoneWork.setEnabled(true);
                customerPhoneHome.setEnabled(true);
                customerPhoneOther.setEnabled(true);
                customerPhoneFax.setEnabled(true);
                customerAddressCountry.setEnabled(true);
                customerAddressCity.setEnabled(true);
                customerAddressStreet.setEnabled(true);
                customerAddressPostalCode.setEnabled(true);

                break;
            case R.id.gold_member:
                selectArg = new String[]{String.valueOf(Color.rgb(255, 215, 0))};
                cursor = getContentResolver().query(KasebContract.State.CONTENT_URI,
                        new String[]{KasebContract.State._ID, KasebContract.State.COLUMN_STATE_COLOR}, selection,
                        selectArg, null);
                if (cursor.moveToFirst())
                    value = cursor.getInt(cursor.getColumnIndex(KasebContract.State._ID));
                contentValues.put(key, value);
                getContentResolver().update(uri, contentValues, updateSelect, updSelArg);
                membershipItem.setIcon(getResources().getDrawable(R.drawable.star_gold));
                break;
            case R.id.silver_member:
                selectArg = new String[]{String.valueOf(Color.rgb(192, 192, 192))};
                cursor = getContentResolver().query(KasebContract.State.CONTENT_URI,
                        new String[]{KasebContract.State._ID, KasebContract.State.COLUMN_STATE_COLOR}, selection,
                        selectArg, null);
                if (cursor.moveToFirst())
                    value = cursor.getInt(cursor.getColumnIndex(KasebContract.State._ID));
                contentValues.put(key, value);
                getContentResolver().update(uri, contentValues, updateSelect, updSelArg);
                membershipItem.setIcon(getResources().getDrawable(R.drawable.star_silver));
                break;
            case R.id.bronze_member:
                selectArg = new String[]{String.valueOf(Color.rgb(218, 165, 32))};
                cursor = getContentResolver().query(KasebContract.State.CONTENT_URI,
                        new String[]{KasebContract.State._ID, KasebContract.State.COLUMN_STATE_COLOR}, selection,
                        selectArg, null);
                if (cursor.moveToFirst())
                    value = cursor.getInt(cursor.getColumnIndex(KasebContract.State._ID));
                contentValues.put(key, value);
                getContentResolver().update(uri, contentValues, updateSelect, updSelArg);
                membershipItem.setIcon(getResources().getDrawable(R.drawable.star_bronze));
                break;
            case R.id.non_member:
                selectArg = new String[]{String.valueOf(Color.rgb(176, 224, 230))};
                cursor = getContentResolver().query(KasebContract.State.CONTENT_URI,
                        new String[]{KasebContract.State._ID, KasebContract.State.COLUMN_STATE_COLOR}, selection,
                        selectArg, null);
                if (cursor.moveToFirst())
                    value = cursor.getInt(cursor.getColumnIndex(KasebContract.State._ID));
                contentValues.put(key, value);
                this.getContentResolver().update(uri, contentValues, updateSelect, updSelArg);
                membershipItem.setIcon(getResources().getDrawable(R.drawable.star_ordinary));
                break;
            default:
                return true;
        }
        //endregion Switch Case for state type

        return super.onOptionsItemSelected(item);
    }

    private void getHelperText() {

        firstNameTextInputLayout.setError(null);
        lastNameTextInputLayout.setError(null);
        phoneMobileTextInputLayout.setError(null);
        birthDayTextInputLayout.setError(null);
        emailTextInputLayout.setError(null);
    }

    // this method check the validation and correct entries. its check fill first and then check the validation rules.
    private boolean checkValidityWithChangeColorOfHelperText() {

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getBaseContext(), customerFirstName)) {
            firstNameTextInputLayout.setError(getResources().getString(R.string.example_first_name));
            customerFirstName.setSelectAllOnFocus(true);
            customerFirstName.selectAll();
            customerFirstName.requestFocus();
            return false;
        } else
            firstNameTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmpty(getBaseContext(), customerLastName)) {
            lastNameTextInputLayout.setError(getResources().getString(R.string.example_last_name));
            customerLastName.setSelectAllOnFocus(true);
            customerLastName.selectAll();
            customerLastName.requestFocus();
            return false;
        } else
            lastNameTextInputLayout.setError(null);

        if (!Utility.checkForValidityForEditTextNullOrEmptyAndItterative(
                getBaseContext(), customerPhoneMobile, phoneMobileTextInputLayout, KasebContract.Customers.CONTENT_URI,
                KasebContract.Customers.COLUMN_PHONE_MOBILE + " = ? and " + KasebContract.Customers._ID + " != ? ",
                KasebContract.Customers._ID,
                new String[]{customerPhoneMobile.getText().toString(), String.valueOf(mCustomerId)})) {

            phoneMobileTextInputLayout.setError(String.format("%s %s",
                    getResources().getString(R.string.example_mobile_number),
                    getResources().getString(R.string.non_repetitive)));
            return false;
        }

        if (!customerEmail.getText().toString().equals("") && !customerEmail.getText().toString().equals(null) &&
                !Utility.validateEmail(customerEmail.getText().toString())) {

            emailTextInputLayout.setError(getResources().getString(R.string.example_email));
            customerEmail.setSelectAllOnFocus(true);
            customerEmail.selectAll();
            customerEmail.requestFocus();
            return false;
        } else
            emailTextInputLayout.setError(null);

        if (!customerBirthDay.getText().toString().equals("") && !customerBirthDay.getText().toString().equals(null) &&
                !Utility.checkForValidityForEditTextDate(getBaseContext(), customerBirthDay)) {

            birthDayTextInputLayout.setError(getResources().getString(R.string.example_date));

            customerBirthDay.setSelectAllOnFocus(true);
            customerBirthDay.selectAll();
            customerBirthDay.requestFocus();
            return false;
        } else
            birthDayTextInputLayout.setError(null);

        return true;
    }
}