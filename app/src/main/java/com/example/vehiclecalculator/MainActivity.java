package com.example.vehiclecalculator;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    // Declare UI component
    EditText editTextVehiclePrice, editTextDownPayment, editTextLoanPeriod, editTextInterestRate;
    Button buttonCalculate, buttonReset;
    TextView textViewLoanAmount, textViewTotalInterest, textViewTotalPayment, textViewMonthlyPayment;

    View[] inputViews;
    View[] outputViews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        //toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.bringToFront();
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Vehicle Loan Calculator");
        }
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.colorOnPrimary));
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimary));

        editTextVehiclePrice = findViewById(R.id.editTextVehiclePrice);
        editTextDownPayment = findViewById(R.id.editTextDownPayment);
        editTextLoanPeriod = findViewById(R.id.editTextLoanPeriod);
        editTextInterestRate = findViewById(R.id.editTextInterestRate);
        buttonCalculate = findViewById(R.id.buttonCalculate);
        buttonReset = findViewById(R.id.buttonReset);
        textViewLoanAmount = findViewById(R.id.textViewLoanAmount);
        textViewTotalInterest = findViewById(R.id.textViewTotalInterest);
        textViewTotalPayment = findViewById(R.id.textViewTotalPayment);
        textViewMonthlyPayment = findViewById(R.id.textViewMonthlyPayment);

        // hiding/showing
        inputViews = new View[]{editTextVehiclePrice, editTextDownPayment, editTextLoanPeriod, editTextInterestRate, buttonCalculate};
        outputViews = new View[]{textViewLoanAmount, textViewTotalInterest, textViewTotalPayment, textViewMonthlyPayment, buttonReset};


        //button click event
        buttonCalculate.setOnClickListener(v -> {
            // calculation
            try {
                String priceStr = editTextVehiclePrice.getText().toString();
                String downPaymentStr = editTextDownPayment.getText().toString();
                String periodStr = editTextLoanPeriod.getText().toString();
                String interestRateStr = editTextInterestRate.getText().toString();

                if (priceStr.isEmpty() || downPaymentStr.isEmpty() || periodStr.isEmpty() || interestRateStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                double vehiclePrice = Double.parseDouble(priceStr);
                double downPayment = Double.parseDouble(downPaymentStr);
                double loanPeriodYears = Double.parseDouble(periodStr);
                double interestRate = Double.parseDouble(interestRateStr);

                double loanAmount = vehiclePrice - downPayment;
                double totalInterest = loanAmount * (interestRate / 100) * loanPeriodYears;
                double totalPayment = loanAmount + totalInterest;
                double monthlyPayment = totalPayment / (loanPeriodYears * 12);

                textViewLoanAmount.setText(String.format("Loan Amount: RM%.2f", loanAmount));
                textViewTotalInterest.setText(String.format("Total Interest: RM%.2f", totalInterest));
                textViewTotalPayment.setText(String.format("Total Payment: RM%.2f", totalPayment));
                textViewMonthlyPayment.setText(String.format("Monthly Payment: RM%.2f", monthlyPayment));


                toggleViews(false);

            } catch (NumberFormatException e) {
                Toast.makeText(MainActivity.this, "Please enter valid numbers.", Toast.LENGTH_SHORT).show();
            }
        });

        buttonReset.setOnClickListener(v -> {
            toggleViews(true);

            editTextVehiclePrice.setText("");
            editTextDownPayment.setText("");
            editTextLoanPeriod.setText("");
            editTextInterestRate.setText("");
        });

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    // show/hide each view one by one
    private void toggleViews(boolean showInputs) {
        int inputVisibility = showInputs ? View.VISIBLE : View.GONE;
        int outputVisibility = showInputs ? View.GONE : View.VISIBLE;

        // input visibility
        for (View view : inputViews) {
            view.setVisibility(inputVisibility);
        }
        // output visibility
        for (View view : outputViews) {
            view.setVisibility(outputVisibility);
        }
    }


    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.menu_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}