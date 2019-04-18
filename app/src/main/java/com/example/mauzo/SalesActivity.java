package com.example.mauzo;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class SalesActivity extends AppCompatActivity {

    private TextView date;
    private EditText salesData, expensesData;
    private Button btnSubmit;
    private ProgressDialog dialog;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales);
        //initatialize firestore
        //Create current time

        Calendar calendar =Calendar.getInstance();
        final String currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
        date = (TextView) findViewById(R.id.tvDate);
        date.setText(currentDate);

        salesData = (EditText) findViewById(R.id.etSales);
        expensesData = (EditText) findViewById(R.id.etExpenses);
        btnSubmit = (Button) findViewById(R. id. btnSubmit);

        dialog = new ProgressDialog(this);
        dialog.setTitle("Saving");
        dialog.setMessage("Please wait...");

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sales = salesData.getText().toString().trim();
                String expenses = expensesData.getText().toString().trim();
                Double margin = Double.parseDouble(sales)-Double.parseDouble(expenses);
                long time = System.currentTimeMillis();
                if (sales.isEmpty()){
                    messageDisplay("SALES ERROR","Please input the sales");
                }else if (expenses.isEmpty()){
                    messageDisplay("EXPENSES ERROR","Please input the expenses");
                }else {
                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("DailyTransactions/"+time);
                    Item x = new Item(currentDate,sales,expenses,String.valueOf(margin),String.valueOf(time));
                    dialog.show();
                    ref.setValue(x).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            dialog.dismiss();
                            if (task.isSuccessful()){
                                messageDisplay("SUCCESS","Saved successfully");
                                salesData.setText("");
                                expensesData.setText("");
                            }else {
                                messageDisplay("FAILED","Saving failed");
                            }
                        }
                    });
                }
            }
        });
    }

    public void messageDisplay(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).create().show();
    }

}
