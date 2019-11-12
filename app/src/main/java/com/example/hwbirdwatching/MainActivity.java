package com.example.hwbirdwatching;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


//Michael Linke
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button buttonSubmit, buttonGoToSearch;
    EditText editTextBirdName, editTextZipCode, editTextPersonName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonSubmit = findViewById(R.id.buttonSubmit);
        buttonGoToSearch = findViewById(R.id.buttonGoToSearch);
        editTextBirdName = findViewById(R.id.editTextBirdname);
        editTextZipCode = findViewById(R.id.editTextZipCode);
        editTextPersonName = findViewById(R.id.editTextPersonName);

        buttonSubmit.setOnClickListener(this);
        buttonGoToSearch.setOnClickListener(this);
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
            Toast.makeText(this,"You are already in the report section!", Toast.LENGTH_SHORT).show();
        } else if(item.getItemId() == R.id.itemSearch) {
            Intent mainIntent = new Intent(this,SearchActivity.class);
            startActivity(mainIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("Birds");

        if(buttonSubmit == view) {
            String createBirdName = editTextBirdName.getText().toString();
            String createZipCode = editTextZipCode.getText().toString();
            String createPersonName = editTextPersonName.getText().toString();

            Bird myBird = new Bird(createBirdName, createZipCode, createPersonName);

            myRef.push().setValue(myBird);
            //maybe add a successmessage

        } else if(buttonGoToSearch == view) {
            Intent mainIntent = new Intent(this,SearchActivity.class);
            startActivity(mainIntent);
            }
        }
    }

