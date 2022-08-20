package iss.team6.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeDashboardFragment extends Fragment {

    int monCountPl;
    int tueCountPl;
    int wedCountPl;
    int thuCountPl;
    int friCountPl;
    int satCountPl;
    int sunCountPl;
    int monCountGl;
    int tueCountGl;
    int wedCountGl;
    int thuCountGl;
    int friCountGl;
    int satCountGl;
    int sunCountGl;
    int monCountMe;
    int tueCountMe;
    int wedCountMe;
    int thuCountMe;
    int friCountMe;
    int satCountMe;
    int sunCountMe;
    int monCountPa;
    int tueCountPa;
    int wedCountPa;
    int thuCountPa;
    int friCountPa;
    int satCountPa;
    int sunCountPa;

    BarChart stackedChart;
    int[] colorClassArray = new int[]{Color.rgb(167, 239, 255), Color.rgb(138, 221, 146), Color.rgb(248, 229, 52), Color.rgb(255, 133, 125)};

    public HomeDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_home_dashboard_fragment, container, false);

        stackedChart = view.findViewById(R.id.idBarChart);

        BarDataSet barDataSet = new BarDataSet(dataValues1(), null);
        barDataSet.setColors(colorClassArray);
        barDataSet.setStackLabels(new String[]{"Paper", "Glass", "Metal","Plastic"});

        BarData barData = new BarData(barDataSet);
        stackedChart.setData(barData);

        //to hide right Y and top X border
        YAxis rightYAxis = stackedChart.getAxisRight();
        rightYAxis.setEnabled(false);
        YAxis leftYAxis = stackedChart.getAxisLeft();
        leftYAxis.setEnabled(false);
        XAxis topXAxis = stackedChart.getXAxis();
        topXAxis.setEnabled(false);

        //animation
        stackedChart.animateX(1800);
        stackedChart.animateY(1800);

        stackedChart.getDescription().setEnabled(false);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        return view;
    }

        private ArrayList<BarEntry> dataValues1 () {
            ArrayList<BarEntry> dataVals = new ArrayList<BarEntry>();

            SharedPreferences pref = getActivity().getSharedPreferences("dayCount", Context.MODE_PRIVATE);
            monCountPl = pref.getInt("monCountPl", monCountPl);
            tueCountPl = pref.getInt("tueCountPl", tueCountPl);
            wedCountPl = pref.getInt("wedCountPl", wedCountPl);
            thuCountPl = pref.getInt("thuCountPl", thuCountPl);
            friCountPl = pref.getInt("friCountPl", friCountPl);
            satCountPl = pref.getInt("satCountPl", satCountPl);
            sunCountPl = pref.getInt("sunCountPl", sunCountPl);

            monCountGl = pref.getInt("monCountGl", monCountGl);
            tueCountGl = pref.getInt("tueCountGl", tueCountGl);
            wedCountGl = pref.getInt("wedCountGl", wedCountGl);
            thuCountGl = pref.getInt("thuCountGl", thuCountGl);
            friCountGl = pref.getInt("friCountGl", friCountGl);
            satCountGl = pref.getInt("satCountGl", satCountGl);
            sunCountGl = pref.getInt("sunCountGl", sunCountGl);

            monCountMe = pref.getInt("monCountMe", monCountMe);
            tueCountMe = pref.getInt("tueCountMe", tueCountMe);
            wedCountMe = pref.getInt("wedCountMe", wedCountMe);
            thuCountMe = pref.getInt("thuCountMe", thuCountMe);
            friCountMe = pref.getInt("friCountMe", friCountMe);
            satCountMe = pref.getInt("satCountMe", satCountMe);
            sunCountMe = pref.getInt("sunCountMe", sunCountMe);

            monCountPa = pref.getInt("monCountPa", monCountPa);
            tueCountPa = pref.getInt("tueCountPa", tueCountPa);
            wedCountPa = pref.getInt("wedCountPa", wedCountPa);
            thuCountPa = pref.getInt("thuCountPa", thuCountPa);
            friCountPa = pref.getInt("friCountPa", friCountPa);
            satCountPa = pref.getInt("satCountPa", satCountPa);
            sunCountPa = pref.getInt("sunCountPa", sunCountPa);

//            These values albeit an integer, could work when placed in new float[])
//            dataVals.add(new BarEntry(0, new float[]{2, 3f, 4.5f, 3}));
//            dataVals.add(new BarEntry(1, new float[]{2, 9f,0, 6.3f}));
//            dataVals.add(new BarEntry(3, new float[]{2, 6f, 3, 7}));
            dataVals.add(new BarEntry(1f, new float[]{monCountPa, monCountGl, monCountMe, monCountPl}));
            dataVals.add(new BarEntry(2f, new float[]{tueCountPa, tueCountGl, tueCountMe, tueCountPl}));
            dataVals.add(new BarEntry(3f, new float[]{wedCountPa, wedCountGl, wedCountMe, wedCountPl}));
            dataVals.add(new BarEntry(4f, new float[]{thuCountPa, thuCountGl, thuCountMe, thuCountPl}));
            dataVals.add(new BarEntry(5f, new float[]{friCountPa, friCountGl, friCountMe, friCountPl}));
            dataVals.add(new BarEntry(6f, new float[]{satCountPa, satCountGl, satCountMe, satCountPl}));
            dataVals.add(new BarEntry(7f, new float[]{sunCountPa, sunCountGl, sunCountMe, sunCountPl}));

            return dataVals;
        }

}
