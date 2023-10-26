package com.politecnicomalaga.polisensors.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.politecnicomalaga.polisensors.R;
import com.politecnicomalaga.polisensors.databinding.FragmentMainBinding;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlaceholderFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";

    private int indexUsed;
    private PageViewModel pageViewModel;
    private FragmentMainBinding binding;

    public static PlaceholderFragment newInstance(int index) {
        PlaceholderFragment fragment = new PlaceholderFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        fragment.setIndex(index);
        return fragment;

    }

    public void setIndex(int index) {
        indexUsed = index;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {

        binding = FragmentMainBinding.inflate(inflater, container, false);
        View root = binding.getRoot();



        TextView textView = binding.sectionLabel;
        GraphView gvSensor = binding.gvGrafico;

        ArrayList<DataPoint> miArray = new ArrayList<>();
        ArrayList<DataPoint> miArray2 = new ArrayList<>();

        Double x = 0.0;
        Double y = 0.0;

        Double x2 = 0.0;
        Double y2 = 0.0;
        for (int i=0;i<240;i++) {
            x = x + 0.1;
            if (indexUsed == 1) {
                y = Math.cos(x)*3+Math.atan(x)*50;
            } else {
                y = Math.atan(x)*30;
            }
            DataPoint newDP = new DataPoint(x, y);
            miArray.add(newDP);


            x2 = x2 + 0.1;
            if (indexUsed == 1) {
                y2 = Math.cos(x2)*8+Math.atan(x2)*90 + 2;
            } else {
                y2 = Math.atan(x2)*70;
            }
            DataPoint newDP2 = new DataPoint(x2, y2);
            miArray2.add(newDP2);

        }


        // on below line we are adding data to our graph view.
        LineGraphSeries<DataPoint> series = new LineGraphSeries<DataPoint>(miArray.toArray(new DataPoint[0]));
        LineGraphSeries<DataPoint> series2 = new LineGraphSeries<DataPoint>(miArray2.toArray(new DataPoint[0]));

        series.setThickness(5);
        series.setTitle("Humedad %");
        series.setDrawBackground(true);
        series.setColor(Color.argb(255, 255, 60, 60));
        series.setBackgroundColor(Color.argb(100, 204, 119, 119));
        series.setDrawDataPoints(true);

        series2.setThickness(5);
        series2.setTitle("CO2 (ppm)");
        series2.setDrawBackground(true);
        series2.setColor(Color.argb(255, 60, 60, 255));
        series2.setBackgroundColor(Color.argb(100, 109, 109, 204));
        series2.setDrawDataPoints(true);


        // legend
        gvSensor.getLegendRenderer().setVisible(true);
        gvSensor.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        gvSensor.getLegendRenderer().setBackgroundColor(Color.rgb(220,220,220));

        // after adding data to our line graph series.
        // on below line we are setting
        // title for our graph view.
        gvSensor.setTitle("Sensor: " + indexUsed);

        // on below line we are setting
        // text color to our graph view.
        gvSensor.setTitleColor(Color.BLACK);

        // on below line we are setting
        // our title text size.
        gvSensor.setTitleTextSize(40);

        // enable scaling and scrolling
        //miGrafico.getViewport().setScalable(true);
        //miGrafico.getViewport().setScalableY(true);

        // set manual X bounds
        gvSensor.getViewport().setYAxisBoundsManual(true);
        gvSensor.getViewport().setMinY(0);
        gvSensor.getViewport().setMaxY(200);

        gvSensor.getViewport().setXAxisBoundsManual(true);
        gvSensor.getViewport().setMinX(0);
        gvSensor.getViewport().setMaxX(24);

        gvSensor.getViewport().setBorderColor(Color.BLUE);

        // on below line we are adding
        // data series to our graph view.
        gvSensor.removeAllSeries();
        gvSensor.addSeries(series);
        gvSensor.addSeries(series2);

        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}