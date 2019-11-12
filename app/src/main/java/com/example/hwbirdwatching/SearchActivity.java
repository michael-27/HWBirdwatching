package com.example.hwbirdwatching;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonSearch, buttonGoToReport;
    EditText editTextZipCodeSearch;
    TextView textViewBirdName, textViewPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        buttonSearch = findViewById(R.id.buttonSearch);
        buttonGoToReport = findViewById(R.id.buttonGoToReport);
        editTextZipCodeSearch = findViewById(R.id.editTextZipCodeSearch);
        textViewBirdName = findViewById(R.id.textViewBirdName);
        textViewPersonName = findViewById(R.id.textViewPersonName);

        buttonSearch.setOnClickListener(this);
        buttonGoToReport.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mainmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.itemReport){
            Intent secondIntent = new Intent(this,MainActivity.class);
            startActivity(secondIntent);
        } else if(item.getItemId() == R.id.itemSearch) {
            Toast.makeText(this,"You are already in the search section!", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Birds");

        if(buttonSearch == view) {
            String findZipCode = editTextZipCodeSearch.getText().toString();

            myRef.orderByChild("zipcode").equalTo(findZipCode).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    String findKey = dataSnapshot.getKey();
                    Bird foundBird = dataSnapshot.getValue(Bird.class);
                    String findBirdname = foundBird.birdname;
                    String findPersonname = foundBird.personname;

                    textViewBirdName.setText(findBirdname);
                    textViewPersonName.setText(findPersonname);
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }else if(buttonGoToReport == view) {
            Intent secondIntent = new Intent(this,MainActivity.class);
            startActivity(secondIntent);
        }

        }
}
