package juliana.ufop.br.nocontrole;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class RevenuesAddActivity extends AppCompatActivity {

    private EditText etValue;
    private EditText etDate;
    private Spinner etOrigin;
    private EditText etPay;
    private EditText etDescription;
    private static final String[] ORIGINS = {"Salário", "Aluguel", "Rendimentos",
            "Vendas", "Aposentaria", "Juros", "Outros"};
    private String selectOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenue_add);

        setTitle("Adicionar Receitas");

        etValue = findViewById(R.id.addValue);
        etDate = findViewById(R.id.addDate);
        etDate.addTextChangedListener(Mask.insert("##/##/##", etDate));
        etPay = findViewById(R.id.addPay);
        etDescription = findViewById(R.id.addDescription);

        etOrigin = findViewById(R.id.addOrigin);
        etOrigin.setSelection(0);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line,ORIGINS);
        etOrigin.setAdapter(adapter);
        etOrigin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectOrigin = ORIGINS[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void confirmAdd(View view){

        Double value = Double.parseDouble(etValue.getText().toString());
        String date = etDate.getText().toString();
        String origin = selectOrigin;
        String pay = etPay.getText().toString();
        String description = etDescription.getText().toString();

        Revenue e = new Revenue(value, date, origin, pay, description);

        SharedResourcesRevenue.getInstance().getRevenue().add(e);
        SharedResourcesRevenue.getInstance().saveRevenue(this);

        Toast.makeText(this,"Receita adicionada com sucesso.",Toast.LENGTH_SHORT).show();

        finish();

    }
}
