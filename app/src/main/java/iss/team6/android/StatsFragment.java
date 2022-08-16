package iss.team6.android;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

public class StatsFragment extends Fragment {

    Button btn_redeem_glass, btn_redeem_metal, btn_redeem_paper, btn_redeem_plastic;
    ProgressBar progressBar_glass, progressBar_metal, progressBar_paper, progressBar_plastic;
    int glassCount;
    int metalCount;
    int paperCount;
    int plasticCount;
    private final static int maxCount = 100;
    Uri uri;
    Intent intent = null;
    String url = "https://www.healthhub.sg/programmes/182/healthhub-rewards/faq";
    TextView rewards_textview_count_glass, rewards_textview_count_metal, rewards_textview_count_paper,rewards_textview_count_plastic;


    public StatsFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        SharedPreferences pref = getActivity().getSharedPreferences("trashTypeCount", Context.MODE_PRIVATE);
        glassCount = pref.getInt("glassCount", glassCount);
        metalCount = pref.getInt("metalCount", metalCount);
        paperCount = pref.getInt("paperCount", paperCount);
        plasticCount = pref.getInt("plasticCount", plasticCount);

        progressBar_glass = view.findViewById(R.id.rewards_progress_bar_glass);
        btn_redeem_glass = view.findViewById(R.id.rewards_btn_redeem_glass);
        rewards_textview_count_glass = view.findViewById(R.id.rewards_textview_count_glass);
        progressBar_glass.setMax(maxCount);
        rewards_textview_count_glass.setText(String.valueOf(glassCount));
        progressBar_glass.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        if (btn_redeem_glass != null) {
            if (glassCount >= 0 && glassCount < maxCount) {
                progressBar_glass.setProgress(glassCount);
                btn_redeem_glass.setBackgroundColor(Color.GRAY);
            } else if (glassCount == maxCount) {
                progressBar_glass.setProgress(glassCount);
                btn_redeem_glass.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        uri = Uri.parse("https://www.healthhub.sg/programmes/182/healthhub-rewards/faq");
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        }

        progressBar_metal = view.findViewById(R.id.rewards_progress_bar_metal);
        btn_redeem_metal = view.findViewById(R.id.rewards_btn_redeem_metal);
        rewards_textview_count_metal = view.findViewById(R.id.rewards_textview_count_metal);
        progressBar_metal.setMax(maxCount);
        rewards_textview_count_metal.setText(String.valueOf(metalCount));
        progressBar_metal.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        if (btn_redeem_metal != null) {
            if (metalCount >= 0 && metalCount < maxCount) {
                progressBar_metal.setProgress(metalCount);
                btn_redeem_metal.setBackgroundColor(Color.GRAY);
            } else if (metalCount == maxCount) {
                progressBar_metal.setProgress(metalCount);
                btn_redeem_metal.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        uri = Uri.parse("https://www.healthhub.sg/programmes/182/healthhub-rewards/faq");
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        }
        progressBar_paper = view.findViewById(R.id.rewards_progress_bar_paper);
        btn_redeem_paper = view.findViewById(R.id.rewards_btn_redeem_paper);
        rewards_textview_count_paper = view.findViewById(R.id.rewards_textview_count_paper);
        progressBar_paper.setMax(maxCount);
        rewards_textview_count_paper.setText(String.valueOf(paperCount));
        progressBar_paper.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        if (btn_redeem_paper != null) {
            if (paperCount >= 0 && paperCount < maxCount) {
                progressBar_paper.setProgress(paperCount);
                btn_redeem_paper.setBackgroundColor(Color.GRAY);
            } else if (paperCount == maxCount) {
                progressBar_paper.setProgress(paperCount);
                btn_redeem_paper.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        uri = Uri.parse("https://www.healthhub.sg/programmes/182/healthhub-rewards/faq");
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        }

        progressBar_plastic = view.findViewById(R.id.rewards_progress_bar_plastic);
        btn_redeem_plastic = view.findViewById(R.id.rewards_btn_redeem_plastic);
        rewards_textview_count_plastic = view.findViewById(R.id.rewards_textview_count_plastic);
        progressBar_plastic.setMax(maxCount);
        rewards_textview_count_plastic.setText(String.valueOf(plasticCount));
        progressBar_plastic.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        if (btn_redeem_plastic != null) {
            if (plasticCount >= 0 && plasticCount < maxCount) {
                progressBar_plastic.setProgress(plasticCount);
                btn_redeem_plastic.setBackgroundColor(Color.GRAY);
            } else if (plasticCount == maxCount) {
                progressBar_plastic.setProgress(plasticCount);
                btn_redeem_plastic.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.M)
                    @Override
                    public void onClick(View view) {
                        uri = Uri.parse("https://www.healthhub.sg/programmes/182/healthhub-rewards/faq");
                        intent = new Intent(Intent.ACTION_VIEW, uri);
                        startActivity(intent);
                    }
                });
            }
        }
        return view;
    }
}
