package jzkt3.umkc.edu.testtestetestsetestsetes;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.jjoe64.graphview.DefaultLabelFormatter;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.List;


public class GraphActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);

        Toolbar toolbar= (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Button barGraphButton = (Button) findViewById(R.id.button1);
        Button lineGraphButton = (Button) findViewById(R.id.button2);
        Button pointGraphButton = (Button) findViewById(R.id.button3);
        final TextView selectText = (TextView) findViewById(R.id.selectText);

        barGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectText.setText(null);
                setupGraph(1);
            }
        });
        lineGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectText.setText(null);
                setupGraph(2);
            }
        });
        pointGraphButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectText.setText(null);
                setupGraph(3);
            }
        });

    }

    private void setupGraph(int type) {

        GraphView graph = (GraphView) findViewById(R.id.graph);
        final List<Rate> graphList = MainActivity.getList();

        DataPoint[] d = new DataPoint[graphList.size()];
        for (int i = 0; i < graphList.size(); i++) {
            d[i] = new DataPoint(i,graphList.get(i).getExchangeRate());

        }

        if(type == 1) {
            graph.removeAllSeries();
            BarGraphSeries<DataPoint> bg = new BarGraphSeries<>(d);
            graph.addSeries(bg);
        }
        if(type == 2) {
            graph.removeAllSeries();
            LineGraphSeries<DataPoint> bg = new LineGraphSeries<>(d);
            graph.addSeries(bg);
        }
        if(type == 3) {
            graph.removeAllSeries();
            PointsGraphSeries<DataPoint> bg = new PointsGraphSeries<>(d);
            graph.addSeries(bg);
        }



        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setMaxX(170);
        graph.getViewport().setMaxY(5000);
        graph.getViewport().setScalable(true);
        graph.getViewport().setScrollable(true);

        final String[] horizontalLabels;
        horizontalLabels = new String[graphList.size() + 1];

        for (int i = 0; i < graphList.size();i++){
            horizontalLabels[i] = graphList.get(i).getName();
        }

        graph.getGridLabelRenderer().setLabelFormatter(new DefaultLabelFormatter() {
            @Override
            public String formatLabel(double value, boolean isValueX) {
                if (isValueX) {
                    if(value < graphList.size()) {
                        return horizontalLabels[(int) value];
                    }
                    else{
                        return " ";
                    }
                }
                return String.format("%.2f", value);
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_graph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == android.R.id.home){
            //NavUtils.navigateUpFromSameTask(this);
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
