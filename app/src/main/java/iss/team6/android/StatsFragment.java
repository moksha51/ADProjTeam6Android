package iss.team6.android;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class StatsFragment extends Fragment {

    Button btn_redeem_glass, btn_redeem_metal, btn_redeem_paper, btn_redeem_plastic;
    ProgressBar progressBar_glass, progressBar_metal, progressBar_paper, progressBar_plastic;
    int count_glass, count_metal, count_paper, count_plastic = 0;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public StatsFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment StatsFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static StatsFragment newInstance(String param1, String param2) {
        StatsFragment fragment = new StatsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_stats, container, false);

        return view;
    }

    /*protected void setupProgressBar(){
        int [] progressBars = {
                R.id.rewards_progress_bar_glass,
                R.id.rewards_progress_bar_metal,
                R.id.rewards_progress_bar_paper,
                R.id.rewards_progress_bar_plastic,
        };

        for (int i = 0; i<progressBars.length; i++){
            //ProgressBar progressBar = (View) findViewById(progressBars[i]);
        }
        progressBar_glass = (progressBar)findViewById(R.id.rewards_progress_bar_glass);
        progressBar_glass.setMax(100);
        if (btn_redeem_glass != null){
            if (count_glass > 0 && count_glass < 100){
                progressBar_glass.setProgress(count_glass);
                btn_redeem_glass.setBackgroundColor(Color.GRAY);
            } else if (count_glass == 100){
                btn_redeem_glass.setOnClickListener((View.OnClickListener) this);
            }
        }

        progressBar_metal.setMax(100);
        progressBar_paper.setMax(100);
        progressBar_plastic.setMax(100);
    }*/

}