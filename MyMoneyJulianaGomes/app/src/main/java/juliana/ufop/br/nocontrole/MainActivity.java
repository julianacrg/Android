package juliana.ufop.br.nocontrole;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button_expenses = (Button) findViewById(R.id.button_expenses);

        Button button_recipes = (Button) findViewById(R.id.button_recipes);

        Button button_balance = (Button) findViewById(R.id.button_balance);

        Button button_price = (Button) findViewById(R.id.button_price);

        Button button_chart = (Button) findViewById(R.id.button_chart);
    }

    public void expensesView(View view){
        Intent it = new Intent(this,MainExpensesActivity.class);
        startActivity(it);
    }

    public void revenuesView(View view){
        Intent it = new Intent(this,MainRevenueActivity.class);
        startActivity(it);
    }

    public void balanceView(View view){
        Intent it = new Intent(this,BalanceActivity.class);
        startActivity(it);
    }

    public void priceView(View view) {
        Intent it = new Intent(this,PriceActivity.class);
        startActivity(it);
    }

    public void chartView(View view) {
        Intent it = new Intent(this,ChartActivity.class);
        startActivity(it);
        //Toast.makeText(this, "Teste", Toast.LENGTH_SHORT).show();
    }
}
