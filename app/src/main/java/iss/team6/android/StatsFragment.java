package iss.team6.android;

import android.content.Intent;
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
    int count_glass = 100;
    int count_metal = 20;
    int count_paper= 50;
    int count_plastic = 20;
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

        progressBar_glass = view.findViewById(R.id.rewards_progress_bar_glass);
        btn_redeem_glass = view.findViewById(R.id.rewards_btn_redeem_glass);
        rewards_textview_count_glass = view.findViewById(R.id.rewards_textview_count_glass);
        progressBar_glass.setMax(maxCount);
        rewards_textview_count_glass.setText(String.valueOf(count_glass));
        progressBar_glass.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        if (btn_redeem_glass != null) {
            if (count_glass >= 0 && count_glass < maxCount) {
                progressBar_glass.setProgress(count_glass);
                btn_redeem_glass.setBackgroundColor(Color.GRAY);
            } else if (count_glass == maxCount) {
                progressBar_glass.setProgress(count_glass);
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
        rewards_textview_count_metal.setText(String.valueOf(count_metal));
        progressBar_metal.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        if (btn_redeem_metal != null) {
            if (count_metal >= 0 && count_metal < maxCount) {
                progressBar_metal.setProgress(count_metal);
                btn_redeem_metal.setBackgroundColor(Color.GRAY);
            } else if (count_metal == maxCount) {
                progressBar_metal.setProgress(count_metal);
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
        rewards_textview_count_paper.setText(String.valueOf(count_paper));
        progressBar_paper.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        if (btn_redeem_paper != null) {
            if (count_paper >= 0 && count_paper < maxCount) {
                progressBar_paper.setProgress(count_paper);
                btn_redeem_paper.setBackgroundColor(Color.GRAY);
            } else if (count_paper == maxCount) {
                progressBar_paper.setProgress(count_paper);
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
        rewards_textview_count_plastic.setText(String.valueOf(count_plastic));
        progressBar_plastic.setProgressTintList(ColorStateList.valueOf(Color.GREEN));
        if (btn_redeem_plastic != null) {
            if (count_plastic >= 0 && count_plastic < maxCount) {
                progressBar_plastic.setProgress(count_plastic);
                btn_redeem_plastic.setBackgroundColor(Color.GRAY);
            } else if (count_plastic == maxCount) {
                progressBar_plastic.setProgress(count_plastic);
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
