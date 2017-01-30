package mjkarbasian.moshtarimadar.AboutUs;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import mjkarbasian.moshtarimadar.R;

/**
 * Created by Morteza on 10/19/2016.
 */
public class AboutUsFragment extends Fragment {
    private static final String LOG_TAG = AboutUsFragment.class.getSimpleName();

    EditText yourText;

    public AboutUsFragment() {
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_about_us, container, false);
        yourText = (EditText) view.findViewById(R.id.about_us_your_text);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_about_us, menu);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_about_us_send: {

                if (yourText.getText().toString().equals("") || yourText.getText().toString().equals(null))
                    Snackbar.make(yourText.getRootView(), R.string.write_your_mind, Snackbar.LENGTH_INDEFINITE)
                            .setAction(getResources().getString(R.string.ok_button), new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    yourText.requestFocus();
                                }
                            })
                            .setActionTextColor(getResources().getColor(R.color.colorAccent))
                            .show();
                else {
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setType("message/rfc822");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"kaseb.team@gmail.com"});
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, getResources().getString(R.string.opinion_email_title));
                    emailIntent.putExtra(Intent.EXTRA_TEXT, yourText.getText().toString());
                    emailIntent.putExtra(Intent.EXTRA_PHONE_NUMBER, "+989194930521");
                    try {
                        startActivity(Intent.createChooser(emailIntent, "Send mail..."));
                    } catch (android.content.ActivityNotFoundException ex) {
                        Snackbar.make(yourText.getRootView(), R.string.no_email_app, Snackbar.LENGTH_INDEFINITE)
                                .setAction(getResources().getString(R.string.ok_button), new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        yourText.requestFocus();
                                    }
                                })
                                .setActionTextColor(getResources().getColor(R.color.colorAccent))
                                .show();
                    }
                }
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
