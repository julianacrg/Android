package juliana.ufop.br.nocontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class MainExpensesActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private ListView lvExpense;
    private TextView tvEmptyList;
    private Spinner spinMonths;
    private String[] MONTHS = {"Todos", "Janeiro", "Fevereiro", "Março", "Abril",
            "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private ArrayList<Expense> expenses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_expenses);

        SharedResourcesExpense.getInstance().loadExpenses(this);
        expenses = SharedResourcesExpense.getInstance().getExpenses();

        setTitle("Despesas");
        lvExpense = findViewById(R.id.lvExpense);
        tvEmptyList = findViewById(R.id.tvEmptyList);
        spinMonths = findViewById(R.id.filterExpense);

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
        loadList(expenses);

        spinMonths.setSelection(0);

        if (SharedResourcesExpense.getInstance().getExpenses().isEmpty())
            tvEmptyList.setVisibility(View.VISIBLE);
        else
            tvEmptyList.setVisibility(View.INVISIBLE);
    }

    private void loadList(ArrayList<Expense> list) {
        lvExpense.setAdapter(new ExpensesAdapter(this, list));

        lvExpense.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent it = new Intent(getApplicationContext(), ExpensesEditActivity.class);

                it.putExtra("position", position);

                startActivity(it);
            }
        });
    }

    public void addExpenses(View view) {
        Intent it = new Intent(this, ExpensesAddActivity.class);
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
            loadList(expenses);
        } else {

            String date;
            ArrayList<Expense> listAux = new ArrayList<>();
            if (month < 10)
                date = "0" + month;
            else
                date = Integer.toString(month);

            for (Expense e : expenses) {
                if ((e.getDate().charAt(3) == date.charAt(0)) &&
                        (e.getDate().charAt(4) == date.charAt(1))) {
                    listAux.add(e);
                }
            }
            loadList(listAux);
        }
    }
}
