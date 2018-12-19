package juliana.ufop.br.nocontrole;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class RevenueAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Revenue> revenues;


    public RevenueAdapter(Context context, ArrayList<Revenue> revenues) {
        this.context = context;
        this.revenues = revenues;
    }

    @Override
    public int getCount() {
        return revenues.size();
    }

    @Override
    public Object getItem(int position) {
        return revenues.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Revenue revenue = revenues.get(position);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_revenue_adapter,null);

        TextView tv = v.findViewById(R.id.value);
        tv.setText (String.valueOf(revenue.getValue()));

        TextView tv2 = v.findViewById(R.id.date);
        tv2.setText(revenue.getDate());

        TextView tv3 = v.findViewById(R.id.origin);
        tv3.setText(revenue.getOrigin());

        TextView tv4 = v.findViewById(R.id.pay);
        tv4.setText(revenue.getPay());

        TextView tv5 = v.findViewById(R.id.description);
        tv5.setText(revenue.getDescription());

        return v;
    }
}
