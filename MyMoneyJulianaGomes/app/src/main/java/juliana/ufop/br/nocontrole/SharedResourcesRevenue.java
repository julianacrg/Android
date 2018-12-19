package juliana.ufop.br.nocontrole;

import android.content.Context;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class SharedResourcesRevenue {

    private static SharedResourcesRevenue shared = null;

    //Singleton elements
    private static ArrayList<Revenue> revenues;

    public static SharedResourcesRevenue getInstance() {
        if(shared == null) {
            shared = new SharedResourcesRevenue();
        }
        return shared;
    }

    private SharedResourcesRevenue() {
        revenues = new ArrayList<Revenue>();
    }

    public ArrayList<Revenue> getRevenue() {
        return revenues;
    }

    public void saveRevenue(Context context){

        try {
            FileOutputStream fos = context.openFileOutput("revenue.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(revenues);
            oos.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadRevenue(Context context){
        try {
            FileInputStream fis = context.openFileInput("revenue.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            revenues = (ArrayList<Revenue>) ois.readObject();
            ois.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Double sumRevenue(){
        Double sumE = 0.0;
        for(Revenue e : revenues) {
            sumE +=  e.getValue();
        }
        return sumE;
    }

}
