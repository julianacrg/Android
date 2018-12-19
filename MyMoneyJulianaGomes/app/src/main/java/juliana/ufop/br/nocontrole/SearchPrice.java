package juliana.ufop.br.nocontrole;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpRetryException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SearchPrice extends AsyncTask {

    private Context context;
    private ProgressDialog dialog;
    private TextView etUSD, etEUR, etGBP, etJPY;

    public SearchPrice(PriceActivity context) {
        this.context = context;

        etUSD = context.findViewById(R.id.priceUSD);
        etEUR = context.findViewById(R.id.priceEUR);
        etGBP = context.findViewById(R.id.priceGBP);
        etJPY = context.findViewById(R.id.priceJPY);
    }

    @Override
    protected void onPreExecute() {
        dialog = ProgressDialog.show(context, "Aguarde",
                "Buscando...", true, true);
    }

    @Override
    protected String doInBackground(Object[] objects) {

        try {
            URL url = new URL("https://api.exchangeratesapi.io/latest?base=BRL");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            InputStream stream = connection.getInputStream();

            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            StringBuffer buffer = new StringBuffer();

            String line = "";
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }


            String finalJson = buffer.toString();

            JSONObject parentObject = new JSONObject(finalJson);
            JSONObject ratesObject = parentObject.getJSONObject("rates");

            Double valueUSD = ratesObject.getDouble("USD");
            Double valueEUR = ratesObject.getDouble("EUR");
            Double valueGBP = ratesObject.getDouble("GBP");
            Double valueJPY = ratesObject.getDouble("JPY");

            valueUSD = 1 / valueUSD;
            valueEUR = 1 / valueEUR;
            valueGBP = 1 / valueGBP;
            valueJPY = 1 / valueJPY;

            etUSD.setText("R$" + String.format("%.2f", valueUSD));
            etEUR.setText("R$" + String.format("%.2f", valueEUR));
            etGBP.setText("R$" + String.format("%.2f", valueGBP));
            etJPY.setText("R$" + String.format("%.2f", valueJPY));

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {

            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        dialog.dismiss();

    }
}
