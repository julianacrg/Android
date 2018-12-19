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

public class RevenuesEditActivity extends AppCompatActivity {

    private Revenue revenue;
    private int position;
    private EditText etValue;
    private EditText etDate;
    private Spinner etOrigin;
    private EditText etPay;
    private EditText etdescription;
    private static final String[] ORIGINS = {"Salário", "Aluguel", "Rendimentos",
            "Vendas", "Aposentaria", "Juros", "Outros"};
    private String selectOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_revenues_edit);

        setTitle("Editar Receita");

        Intent it = getIntent();
        position = it.getIntExtra("position", 0);
        revenue = SharedResourcesRevenue.getInstance().getRevenue().get(position);

        etValue = findViewById(R.id.editValue);
        etDate = findViewById(R.id.editDate);
        etDate.addTextChangedListener(Mask.insert("##/##/##", etDate));
        etPay = findViewById(R.id.editPay);
        etdescription = findViewById(R.id.editDescription);

        etOrigin = findViewById(R.id.editOrigin);
        etOrigin.setSelection(0);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, ORIGINS);
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

        if (revenue != null) {
            //Fill text fields
            etValue.setText(String.valueOf(revenue.getValue()));
            etDate.setText(revenue.getDate());

            String origin = revenue.getOrigin();
            for (int i = 0; i < ORIGINS.length; i++) {
                if (origin.equals(ORIGINS[i])) {
                    etOrigin.setSelection(i);
                    break;
                }
            }

            etPay.setText(String.valueOf(revenue.getPay()));
            etdescription.setText(String.valueOf(revenue.getDescription()));
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    public void confirmEdit(View view) {

        revenue.setValue(Double.parseDouble(etValue.getText().toString()));
        revenue.setDate(etDate.getText().toString());
        revenue.setOrigin(selectOrigin);
        revenue.setPay(etPay.getText().toString());
        revenue.setDescription(etdescription.getText().toString());

        SharedResourcesRevenue.getInstance().getRevenue().set(position, revenue);
        SharedResourcesRevenue.getInstance().saveRevenue(this);
        Toast.makeText(this, "Receita editada com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void deleteEdit(View view) {

        SharedResourcesRevenue.getInstance().getRevenue().remove(position);
        SharedResourcesRevenue.getInstance().saveRevenue(this);
        Toast.makeText(this, "Receita excluída com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }
}
