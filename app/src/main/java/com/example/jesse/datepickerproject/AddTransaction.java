package com.example.jesse.datepickerproject;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddTransaction extends AppCompatActivity {
    DatabaseHelper dbh;
    int GalleryImage = 0;
    int year1, month1, day1;
    static final int DATEPICKER_ID = 0 ;
    TextView expenseDate;




    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_transaction);
        dbh = new DatabaseHelper(this);

        /*
        long date = System.currentTimeMillis();
        TextView dateText = (TextView)findViewById(R.id.dateText);
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM - DD - YY");
        String dateString = dateFormat.format(date);
        */
        Calendar cal =  Calendar.getInstance();
        year1 = cal.get(Calendar.YEAR);
        month1 = cal.get(Calendar.MONTH)   ;
        day1 = cal.get(Calendar.DAY_OF_MONTH);

        final EditText expenseAmount = (EditText)findViewById(R.id.amountText);

        final EditText expenseNote = (EditText)findViewById(R.id.noteText);
        final ImageButton expensePhoto = (ImageButton)findViewById(R.id.addPhotoButton);
        final Spinner expenseCategory = (Spinner)findViewById(R.id.categorySpinner);
        final Button addExpenseBtn = (Button)findViewById(R.id.addExpense);
         expenseDate = (TextView)findViewById(R.id.dateText);



        if(month1 < 10 && day1 < 10) {

            expenseDate.setText( "0" + month1+1 + " - " + "0" + day1 + " - " + year1);
        }
        if(month1 >= 10 && day1 >= 10)
        {
            expenseDate.setText(  month1+1 + " - "  + day1 + " - " + year1);
        }
        if(month1 > 10 && day1 < 10)
        {
            expenseDate.setText( month1+1 + " - " + "0" + day1 + " - " + year1);
        }
        if(month1 < 10 && day1 > 10)
        {
            expenseDate.setText( "0" + month1+1 + " - " +  day1 + " - " + year1);
        }
        if(month1 < 10 && day1==10)
        {
            expenseDate.setText(  "0" + month1+1 + " - " +  day1 + " - " + year1);
        }
        if(month1 == 10 && day1 < 10)
        {
            expenseDate.setText(   month1+1 + " - " + "0" + day1 + " - " + year1);
        }
        showDatePicker();





        addExpenseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String date = expenseDate.getText().toString();
                String note = expenseNote.getText().toString();
                String category = expenseCategory.getSelectedItem().toString();
                String amount = expenseAmount.getText().toString();

                if (!amount.matches("") && !note.matches("")) { //TODO: Trim the variables
                    dbh.createTable("Jesse");
                    dbh.addRec(date, category, amount, note);

                    expenseAmount.setText("");
                    expenseNote.setText("");
                    Toast.makeText(AddTransaction.this, "Values Added", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(AddTransaction.this, MainActivity.class));

                } else {
                    Toast.makeText(AddTransaction.this, "The Values Amount and Note Can Not Be Empty ", Toast.LENGTH_LONG).show();
                }
            }
        });

        expensePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(galleryIntent, GalleryImage);

                }
                catch (Exception e) {}

            }
        });


    }

    public void showDatePicker()
    {
        expenseDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showDialog(DATEPICKER_ID);
            }
        });
    }

    protected Dialog onCreateDialog(int datePickerID)
    {
        if(datePickerID == DATEPICKER_ID)
        {
            return new DatePickerDialog(this, datePickerListener, year1, month1, day1);
        }
        return null;

    }

    private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
             year1 = year;
             month1 = monthOfYear + 1;
             day1 = dayOfMonth;

            if(month1 < 10 && day1 < 10) {

                expenseDate.setText( "0" + month1 + " - " + "0" + day1 + " - " + year1);
            }
            if(month1 >= 10 && day1 >= 10)
            {
                expenseDate.setText(  month1 + " - "  + day1 + " - " + year1);
            }
            if(month1 > 10 && day1 < 10)
            {
                expenseDate.setText(  month1 + " - " + "0" + day1 + " - " + year1);
            }
            if(month1 < 10 && day1 > 10)
            {
                expenseDate.setText(  "0" + month1 + " - " +  day1 + " - " + year1);
            }
            if(month1 < 10 && day1==10)
            {
                expenseDate.setText(  "0" + month1 + " - " +  day1 + " - " + year1);
            }
            if(month1 == 10 && day1 < 10)
            {
                expenseDate.setText(   month1 + " - " + "0" + day1 + " - " + year1);
            }

        }
    };

}
