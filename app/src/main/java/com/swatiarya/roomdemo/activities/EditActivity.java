package com.swatiarya.roomdemo.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import com.swatiarya.roomdemo.R;
import com.swatiarya.roomdemo.constants.Constants;
import com.swatiarya.roomdemo.database.AppDatabase;
import com.swatiarya.roomdemo.database.AppExecutors;
import com.swatiarya.roomdemo.model.User;

public class EditActivity extends AppCompatActivity {
    EditText name, email, pincode, city, phoneNumber;
    Button button;
    int mPersonId;
    Intent intent;
    private AppDatabase mDb;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        initViews();
        mDb = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        if (intent != null && intent.hasExtra(Constants.UPDATE_Person_Id)) {
            button.setText("Update");

            mPersonId = intent.getIntExtra(Constants.UPDATE_Person_Id, -1);

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    User user = mDb.userDao().loadUserById(mPersonId);
                    populateUI(user);
                }
            });
        }

    }

    private void populateUI(User person) {

        if (person == null) {
            return;
        }

        name.setText(person.getName());
        email.setText(person.getEmail());
        phoneNumber.setText(person.getNumber());
        pincode.setText(person.getPincode());
        city.setText(person.getCity());
    }

    private void initViews() {
        name = findViewById(R.id.edit_name);
        email = findViewById(R.id.edit_email);
        pincode = findViewById(R.id.edit_pincode);
        city = findViewById(R.id.edit_city);
        phoneNumber = findViewById(R.id.edit_number);
        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClicked();
            }
        });
    }

    public void onSaveButtonClicked() {
//        final Person person = new Person(
//                name.getText().toString(),
//                email.getText().toString(),
//                phoneNumber.getText().toString(),
//                pincode.getText().toString(),
//                city.getText().toString());
       final User person=new User();
        person.setName(name.getText().toString());
        person.setEmail(email.getText().toString());
        person.setNumber(phoneNumber.getText().toString());
        person.setPincode(pincode.getText().toString());
        person.setCity(city.getText().toString());
        person.setAge(18);
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                if (!intent.hasExtra(Constants.UPDATE_Person_Id)) {
                    mDb.userDao().insertUser(person);
                } else {
                    person.setId(mPersonId);
                    mDb.userDao().updateUser(person);
                }
                finish();
            }
        });
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
