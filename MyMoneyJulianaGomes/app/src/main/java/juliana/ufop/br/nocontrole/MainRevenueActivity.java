package juliana.ufop.br.nocontrole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainRevenueActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ListView lvRevenue;
    private TextView tvEmptyList;
    private Spinner spinMonths;
    private String[] MONTHS = {"Todos", "Janeiro", "Fevereiro", "Março", "Abril",
            "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private ArrayList<Revenue> revenues = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_revenue);

        SharedResourcesRevenue.getInstance().loadRevenue(this);
        revenues = SharedResourcesRevenue.getInstance().getRevenue();

        setTitle("Receitas");
        lvRevenue = findViewById(R.id.lvRevenue);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        spinMonths = findViewById(R.id.filterRevenue);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, MONTHS);
        spinMonths.setAdapter(adapter);
        spinMonths.setOnItemSelectedListener(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadList(revenues);

        spinMonths.setSelection(0);

        if (SharedResourcesRevenue.getInstance().getRevenue().isEmpty())
            tvEmptyList.setVisibility(View.VISIBLE);
        else
            tvEmptyList.setVisibility(View.INVISIBLE);
    }

    private void loadList(ArrayList<Revenue> list) {
        lvRevenue.setAdapter(new RevenueAdapter(this, list));

        lvRevenue.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent it = new Intent(getApplicationContext(), RevenuesEditActivity.class);
                it.putExtra("position", position);

                startActivity(it);
            }
        });
    }

    public void addRevenues(View view) {
        Intent it = new Intent(this, RevenuesAddActivity.class);
        startActivity(it);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        filterMonth(position);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void filterMonth(int month) {

        if (month == 0) {
            loadList(revenues);
        } else {

            String date;
            ArrayList<Revenue> listAux = new ArrayList<>();
            if (month < 10)
                date = "0" + month;
            else
                date = Integer.toString(month);

            for (Revenue r : revenues) {
                if ((r.getDate().charAt(3) == date.charAt(0)) &&
                        (r.getDate().charAt(4) == date.charAt(1))) {
                    listAux.add(r);
                }
            }
            loadList(listAux);
        }
    }
}
