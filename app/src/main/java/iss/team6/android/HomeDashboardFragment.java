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
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class HomeDashboardFragment extends Fragment {
    //https://learntodroid.com/how-to-display-a-bar-chart-in-your-android-app/
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntriesArrayList;
    int monCount;
    int tueCount;
    int wedCount;
    int thuCount;
    int friCount;
    int satCount;
    int sunCount;

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

        // Initialising variable for bar chart
        barChart = view.findViewById(R.id.idBarChart);

        // get bar data
        getData();

        //creating new bar data set
        barDataSet = new BarDataSet(barEntriesArrayList, null);

        //creating new bar data
        // and passing bar data set
        barData = new BarData(barDataSet);

        //setting data to bar chart
        barChart.setData(barData);

        //to hide right Y and top X border
        YAxis rightYAxis = barChart.getAxisRight();
        rightYAxis.setEnabled(false);
        YAxis leftYAxis = barChart.getAxisLeft();
        leftYAxis.setEnabled(false);
        XAxis topXAxis = barChart.getXAxis();
        topXAxis.setEnabled(false);

        //animation
        barChart.animateX(1800);
        barChart.animateY(1800);

        //adding color to bar data set
        barDataSet.setColors(ColorTemplate.LIBERTY_COLORS);

        //setting text color
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        //test
        return view;
    }

    private void getData(){

        SharedPreferences pref = getActivity().getSharedPreferences("dayCount", Context.MODE_PRIVATE);
        monCount = pref.getInt("monCount", monCount);
        tueCount = pref.getInt("tueCount", tueCount);
        wedCount = pref.getInt("wedCount", wedCount);
        thuCount = pref.getInt("thuCount", thuCount);
        friCount = pref.getInt("friCount", friCount);
        satCount = pref.getInt("satCount", satCount);
        sunCount = pref.getInt("sunCount", sunCount);

        //creating new array list
        //ArrayList<BarEntry>
        barEntriesArrayList = new ArrayList<>();

        barEntriesArrayList.add(new BarEntry(1f,monCount));
        barEntriesArrayList.add(new BarEntry(2f,tueCount));
        barEntriesArrayList.add(new BarEntry(3f,wedCount));
        barEntriesArrayList.add(new BarEntry(4f,thuCount));
        barEntriesArrayList.add(new BarEntry(5f,friCount));
        barEntriesArrayList.add(new BarEntry(6f,satCount));
        barEntriesArrayList.add(new BarEntry(7f,sunCount));
    }

}