package juliana.ufop.br.nocontrole;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;

public class ChartActivity extends AppCompatActivity {

    private BarChart barChart;
    private Spinner spinMonths, spinType;
    private String[] MONTHS = {"Janeiro", "Fevereiro", "Março", "Abril",
            "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private String[] EXPENSES = {"Combustível", "Vestuário", "Alimentação",
            "Internet", "Água", "Energia", "Outros"};
    private String[] TYPE = {"Despesas", "Receitas"};
    private static final String[] REVENUES = {"Salário", "Aluguel", "Rendimentos",
            "Vendas", "Aposentaria", "Juros", "Outros"};

    private ArrayList<Expense> expenses = new ArrayList<>();
    private ArrayList<Revenue> revenues = new ArrayList<>();
    ;
    int type = 0;
    int month = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);

        setTitle("Relatório");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        barChart = findViewById(R.id.barChart);
        spinMonths = findViewById(R.id.months);
        spinType = findViewById(R.id.type);

        SharedResourcesExpense.getInstance().loadExpenses(this);
        SharedResourcesRevenue.getInstance().loadRevenue(this);

        expenses = SharedResourcesExpense.getInstance().getExpenses();
        revenues = SharedResourcesRevenue.getInstance().getRevenue();

        setData(0, 0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, MONTHS);
        spinMonths.setAdapter(adapter);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, TYPE);
        spinType.setAdapter(adapter2);
        spinMonths.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                month = position;
                setData(type, month);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type = position;
                setData(type, month);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public class MyAxisValueFormater implements IAxisValueFormatter {

        private String[] mValues;

        public MyAxisValueFormater(String[] values) {
            this.mValues = values;
        }

        @Override
        public String getFormattedValue(float value, AxisBase axis) {
            return mValues[(int) value];
        }
    }

    public void setData(int type, int month) {
        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);
        if (type == 0) {
            barChart.setMaxVisibleValueCount(getMaxValueEx());

        } else {
            barChart.setMaxVisibleValueCount(getMaxValueRe());

        }
        barChart.setPinchZoom(false);
        barChart.setDrawGridBackground(true);

        ArrayList<BarEntry> barEntries = new ArrayList<>();
        XAxis xAxis;
        BarDataSet barDataSet;

        if (type == 0) {
            barEntries.clear();
            barEntries.add(new BarEntry(0, getSumValueEv(month, 0)));
            barEntries.add(new BarEntry(1, getSumValueEv(month, 1)));
            barEntries.add(new BarEntry(2, getSumValueEv(month, 2)));
            barEntries.add(new BarEntry(3, getSumValueEv(month, 3)));
            barEntries.add(new BarEntry(4, getSumValueEv(month, 4)));
            barEntries.add(new BarEntry(5, getSumValueEv(month, 5)));
            barEntries.add(new BarEntry(6, getSumValueEv(month, 6)));

            barDataSet = new BarDataSet(barEntries, "Data Set1");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

            BarData data = new BarData(barDataSet);
            data.setBarWidth(0.9f);

            barChart.setData(data);

            xAxis = barChart.getXAxis();

            xAxis.setValueFormatter(new MyAxisValueFormater(EXPENSES));
        } else {
            barEntries.clear();
            barEntries.add(new BarEntry(0, getSumValueRe(month, 0)));
            barEntries.add(new BarEntry(1, getSumValueRe(month, 1)));
            barEntries.add(new BarEntry(2, getSumValueRe(month, 2)));
            barEntries.add(new BarEntry(3, getSumValueRe(month, 3)));
            barEntries.add(new BarEntry(4, getSumValueRe(month, 4)));
            barEntries.add(new BarEntry(5, getSumValueRe(month, 5)));
            barEntries.add(new BarEntry(6, getSumValueRe(month, 6)));

            barDataSet = new BarDataSet(barEntries, "Data Set1");
            barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);

            BarData data = new BarData(barDataSet);
            data.setBarWidth(0.9f);

            barChart.setData(data);

            xAxis = barChart.getXAxis();

            xAxis.setValueFormatter(new MyAxisValueFormater(REVENUES));
        }
        barChart.getData().notifyDataChanged();
        barChart.notifyDataSetChanged();
    }

    private int getMaxValueEx() {
        int max = (int) expenses.get(0).getValue() + 1;

        for (Expense e : expenses) {
            if (max < e.getValue()) {
                max = (int) e.getValue() + 1;
            }
        }

        return max;
    }

    private int getMaxValueRe() {
        int max = (int) revenues.get(0).getValue() + 1;

        for (Revenue r : revenues) {
            if (max < r.getValue()) {
                max = (int) r.getValue() + 1;
            }
        }

        return max;
    }

    private float getSumValueEv(int month, int index) {
        float sum = 0;
        String date;
        month++;
        if (month < 10)
            date = "0" + month;
        else
            date = Integer.toString(month);

        for (Expense e : expenses) {
            if ((e.getDate().charAt(3) == date.charAt(0)) &&
                    (e.getDate().charAt(4) == date.charAt(1))) {
                if (e.getOrigin().equals(EXPENSES[index])) {
                    sum += e.getValue();
                }
            }
        }
        return sum;
    }

    private float getSumValueRe(int month, int index) {
        float sum = 0;
        String date;
        month++;
        if (month < 10)
            date = "0" + month;
        else
            date = Integer.toString(month);

        for (Revenue r : revenues) {
            if ((r.getDate().charAt(3) == date.charAt(0)) &&
                    (r.getDate().charAt(4) == date.charAt(1))) {
                if (r.getOrigin().equals(REVENUES[index])) {
                    sum += r.getValue();
                }
            }
        }
        return sum;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}


