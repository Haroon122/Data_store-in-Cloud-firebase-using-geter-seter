package com.example.cloudfirestoredatabaseimplementation;

import android.app.ProgressDialog;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    EditText name,age;
    Button save;
    ProgressDialog progressDialog;
    FirebaseFirestore db;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.editName);
        age=findViewById(R.id.editage);
        progressDialog=new ProgressDialog(this);
        save=findViewById(R.id.saveBtn);
        db=FirebaseFirestore.getInstance();
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String sname=name.getText().toString();
                String sage=age.getText().toString();

                if (sname.isEmpty()||sage.isEmpty()){
                    Toast.makeText(MainActivity.this, "Required All Fields", Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    progressDialog.setTitle("Loading");
                    progressDialog.setMessage("Please wait while Loading...");
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.show();

                    db.collection("Data").add(new Adapter(sname,sage)).addOnCompleteListener(new OnCompleteListener<DocumentReference>() {
                        @Override
                        public void onComplete(@NonNull Task<DocumentReference> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this, "Data save Successfully", Toast.LENGTH_SHORT).show();
                                //clear fields
                                name.setText("");
                                age.setText("");

                            }else {
                                Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();

                        }
                    });


                }            }



        });



    }
}