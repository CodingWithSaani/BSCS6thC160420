package com.ekorydes.bscs6thc160420;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView objectRecyclerView;
    private AppAdapterClass objectAppAdapterClass;

    private FirebaseFirestore objectFirebaseFirestore;
    private EditText nameET,statusET;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        connectWithXML();
    }

    private void connectWithXML()
    {
        try
        {
            nameET=findViewById(R.id.nameET);
            statusET=findViewById(R.id.statusET);

            objectFirebaseFirestore=FirebaseFirestore.getInstance();
            objectRecyclerView=findViewById(R.id.RV);

            addValuesToRV();
        }
        catch (Throwable t)
        {
            Toast.makeText(this, "connectWithXML:"+t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addValuesToRV()
    {
        try
        {
            Query object=objectFirebaseFirestore.collection("Records");

            //Step 1: Create object of Recycler option class
            //step 2: initialize this object
            //step 3: pass that to adapter class's constructor

            //-->1
            FirestoreRecyclerOptions<AppModelClass> options;
            //-->2
            options=new FirestoreRecyclerOptions.Builder<AppModelClass>().setQuery(object,AppModelClass.class)
                    .build();

            //-->3
            objectAppAdapterClass=new AppAdapterClass(options);

            objectRecyclerView.setLayoutManager(new LinearLayoutManager(this));
            objectRecyclerView.setAdapter(objectAppAdapterClass);
        }
        catch (Throwable t)
        {
            Toast.makeText(this, "addValuesToRV:"+t.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        objectAppAdapterClass.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        objectAppAdapterClass.stopListening();
    }

    public void addStatus(View v)
    {
        try
        {
            if(!nameET.getText().toString().isEmpty() && !statusET.getText().toString().isEmpty())
            {
                Map<String,String> objectMap=new HashMap<>();
                objectMap.put("name",nameET.getText().toString());

                objectMap.put("status",statusET.getText().toString());
                objectFirebaseFirestore.collection("Records").document()
                        .set(objectMap)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Status published", Toast.LENGTH_SHORT).show();
                                nameET.setText("");

                                statusET.setText("");
                                nameET.requestFocus();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(MainActivity.this, "Fails to publish status:"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
            else
            {
                Toast.makeText(this, "Please fill the fields", Toast.LENGTH_SHORT).show();
            }
        }
        catch (Exception e)
        {
            Toast.makeText(this, "addStatus:"+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

}
