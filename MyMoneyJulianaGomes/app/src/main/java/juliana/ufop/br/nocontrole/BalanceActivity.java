package juliana.ufop.br.nocontrole;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class BalanceActivity extends AppCompatActivity {

    private TextView sumExpense;
    private TextView sumRevenue;
    private TextView balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        setTitle("Balanço");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        SharedResourcesExpense.getInstance().loadExpenses(this);
        SharedResourcesRevenue.getInstance().loadRevenue(this);

        sumExpense = findViewById(R.id.sumExpense);

        sumExpense.setText("R$" + SharedResourcesExpense.getInstance().sumExpense());

        sumRevenue = findViewById(R.id.sumRevenue);

        sumRevenue.setText("R$" + SharedResourcesRevenue.getInstance().sumRevenue());

        balance = findViewById(R.id.balance);

        Double total = SharedResourcesRevenue.getInstance().sumRevenue()
                - SharedResourcesExpense.getInstance().sumExpense();

        balance.setText("R$" + total);

        if (total >= 0) {
            balance.setTextColor(Color.GREEN);
        } else {
            balance.setTextColor(Color.RED);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
