package juliana.ufop.br.nocontrole;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;

public class SharedResourcesExpense {

    private static SharedResourcesExpense shared = null;

    //Singleton elements
    private static ArrayList<Expense> expenses;

    public static SharedResourcesExpense getInstance() {
        if(shared == null) {
            shared = new SharedResourcesExpense();
        }
        return shared;
    }

    private SharedResourcesExpense() {
        expenses = new ArrayList<Expense>();
    }

    public ArrayList<Expense> getExpenses() {
        return expenses;
    }

    public void saveExpenses(Context context){

        try {
            FileOutputStream fos = context.openFileOutput("expense.dat", Context.MODE_PRIVATE);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(expenses);
            oos.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadExpenses(Context context){
        try {
            FileInputStream fis = context.openFileInput("expense.dat");
            ObjectInputStream ois = new ObjectInputStream(fis);
            expenses = (ArrayList<Expense>) ois.readObject();
            ois.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public Double sumExpense(){
        Double sumE = 0.0;
        for(Expense e : expenses) {
            sumE +=  e.getValue();
        }
        return sumE;
    }

}
