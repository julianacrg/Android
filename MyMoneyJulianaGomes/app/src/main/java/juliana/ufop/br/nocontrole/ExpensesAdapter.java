package juliana.ufop.br.nocontrole;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ExpensesAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Expense> expenses;


    public ExpensesAdapter(Context context, ArrayList<Expense> expenses) {
        this.context = context;
        this.expenses = expenses;
    }

    @Override
    public int getCount() {
        return expenses.size();
    }

    @Override
    public Object getItem(int position) {
        return expenses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Expense expense = expenses.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_expenses_adapter,null);

        TextView tv = v.findViewById(R.id.value);
        tv.setText (String.valueOf(expense.getValue()));

        TextView tv2 = v.findViewById(R.id.date);
        tv2.setText(expense.getDate());

        TextView tv3 = v.findViewById(R.id.origin);
        tv3.setText(expense.getOrigin());

        TextView tv4 = v.findViewById(R.id.pay);
        tv4.setText(expense.getPay());

        TextView tv5 = v.findViewById(R.id.description);
        tv5.setText(expense.getDescription());

        return v;
    }
}
