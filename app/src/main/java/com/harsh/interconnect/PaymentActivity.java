package com.harsh.interconnect;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Random;

import dev.shreyaspatil.easyupipayment.EasyUpiPayment;
import dev.shreyaspatil.easyupipayment.listener.PaymentStatusListener;
import dev.shreyaspatil.easyupipayment.model.TransactionDetails;

public class PaymentActivity extends AppCompatActivity {

    Button btnPay;
    EditText etAmount;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_payment);

        etAmount = findViewById(R.id.et_amount);
        btnPay = findViewById(R.id.btn_pay);

        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!etAmount.getText().toString().isEmpty()){
                    Toast.makeText(PaymentActivity.this, "Starting payment", Toast.LENGTH_SHORT).show();
                    EasyUpiPayment.Builder builder = new EasyUpiPayment.Builder(PaymentActivity.this)
                            .setPayeeVpa("harshrana2004-2@okaxis")
                            .setPayeeName("Harsh Rana")
                            .setPayeeMerchantCode("5812")
                            .setTransactionId("T"+System.currentTimeMillis()+ "_" + new Random().nextInt(1000))
                            .setTransactionRefId("T037033025024")
                            .setDescription("Payment Example")
                            .setAmount(Double.parseDouble(etAmount.getText().toString())+"");
                    try {
                        EasyUpiPayment easyUpiPayment = builder.build();
                        easyUpiPayment.startPayment();
//                        easyUpiPayment.setPaymentStatusListener(new PaymentStatusListener() {
//                            @Override
//                            public void onTransactionCompleted(@NonNull TransactionDetails transactionDetails) {
//                                Toast.makeText(PaymentActivity.this, "Payment Completed!", Toast.LENGTH_SHORT).show();
//                            }
//
//                            @Override
//                            public void onTransactionCancelled() {
//                                Toast.makeText(PaymentActivity.this, "Payment Cancelled!", Toast.LENGTH_SHORT).show();
//                            }
//                        });
                    } catch (Exception e) {
                        Toast.makeText(PaymentActivity.this, e+"", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}