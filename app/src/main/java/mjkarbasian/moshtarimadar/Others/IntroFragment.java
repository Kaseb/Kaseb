package mjkarbasian.moshtarimadar.Others;

import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.itextpdf.text.DocumentException;

import java.io.IOException;

import mjkarbasian.moshtarimadar.Dashboard;
import mjkarbasian.moshtarimadar.Helpers.Utility;
import mjkarbasian.moshtarimadar.R;


public class IntroFragment extends Fragment {

    Animation animationFadeIn;
    private ImageView imageView;
    private TextView careView;
    private AnimatedVectorDrawable avd;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_intro_animation, container, false);

        imageView = (ImageView) rootView.findViewById(R.id.image_view);
        careView = (TextView) rootView.findViewById(R.id.care_image);
        avd = (AnimatedVectorDrawable) getActivity().getResources().getDrawable(R.drawable.avd_kaseb_design);

        try {
            Utility.createTypeFaceWithSize(careView);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }

        imageView.setImageDrawable(avd);
        animationFadeIn = AnimationUtils.loadAnimation(getActivity(), R.anim.care_anim);
        avd.start();

        careView.startAnimation(animationFadeIn);
        animationFadeIn.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Intent intent = new Intent(getActivity(), Dashboard.class);
                getActivity().finish();
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        return rootView;
    }
}
