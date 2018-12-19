package juliana.ufop.br.nocontrole;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class ExpensesEditActivity extends AppCompatActivity {

    private Expense expense;
    private int position;
    private EditText etValue;
    private EditText etDate;
    private Spinner etOrigin;
    private EditText etPay;
    private EditText etdescription;
    private static final String[] ORIGINS = {"Combustível","Vestuário","Alimentação",
            "Internet","Água","Energia","Outros"};
    private String selectOrigin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expenses_edit);

        setTitle("Editar Despesa");

        Intent it = getIntent();
        position = it.getIntExtra("position", 0);
        expense = SharedResourcesExpense.getInstance().getExpenses().get(position);

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

        if (expense != null) {
            //Fill text fields
            etValue.setText(String.valueOf(expense.getValue()));
            etDate.setText(expense.getDate());

            String origin = expense.getOrigin();
            for (int i = 0; i < ORIGINS.length; i++) {
                if (origin.equals(ORIGINS[i])) {
                    etOrigin.setSelection(i);
                    break;
                }
            }

            etPay.setText(String.valueOf(expense.getPay()));
            etdescription.setText(String.valueOf(expense.getDescription()));
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

        expense.setValue(Double.parseDouble(etValue.getText().toString()));
        expense.setDate(etDate.getText().toString());
        expense.setOrigin(selectOrigin);
        expense.setPay(etPay.getText().toString());
        expense.setDescription(etdescription.getText().toString());

        SharedResourcesExpense.getInstance().getExpenses().set(position, expense);
        SharedResourcesExpense.getInstance().saveExpenses(this);
        Toast.makeText(this, "Despesa editada com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }

    public void deleteEdit(View view) {

        SharedResourcesExpense.getInstance().getExpenses().remove(position);
        SharedResourcesExpense.getInstance().saveExpenses(this);
        Toast.makeText(this, "Despesa excluída com sucesso!", Toast.LENGTH_SHORT).show();
        finish();
    }


}
