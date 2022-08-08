package iss.team6.android;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class HomeDashboardFragment extends AppCompatActivity {


    //https://learntodroid.com/how-to-display-a-bar-chart-in-your-android-app/
    BarChart barChart;
    BarData barData;
    BarDataSet barDataSet;
    ArrayList barEntriesArrayList;

    public Dashboard_Fragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.activity_dashboard_fragment, container, false);

        // Initialising variable for bar chart
        barChart = view.findViewById(R.id.idBarChart);

        // get bar data
        getData();

        //creating new bar data set
        barDataSet = new BarDataSet(barEntriesArrayList, "Good Morning Singapore");

        //creating new bar data
        // and passing bar data set
        barData = new BarData(barDataSet);

        //setting data to bar chart
        barChart.setData(barData);

        //adding color to bar data set
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

        //setting text color
        barDataSet.setValueTextColor(Color.BLACK);

        // setting text size
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(false);

        return view;
    }

    private void getData(){
        //creating new array list
        barEntriesArrayList = new ArrayList();

        barEntriesArrayList.add(new BarEntry(1f,7));
        barEntriesArrayList.add(new BarEntry(2f,2));
        barEntriesArrayList.add(new BarEntry(3f,8));
        barEntriesArrayList.add(new BarEntry(4f,4));
        barEntriesArrayList.add(new BarEntry(5f,0));
        barEntriesArrayList.add(new BarEntry(6f,0));
        barEntriesArrayList.add(new BarEntry(7f,0));


    }
}
