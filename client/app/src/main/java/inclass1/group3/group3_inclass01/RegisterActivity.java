package inclass1.group3.group3_inclass01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

    EditText name,id,pwd,age,weight,address;
    public static  apiCalls caller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        caller.activity=RegisterActivity.this;

        caller=new apiCalls();
        name = findViewById(R.id.txtname);
        id = findViewById(R.id.txtuserid);
        pwd = findViewById(R.id.txtpwd);
        age = findViewById(R.id.txtage);
        weight = findViewById(R.id.txtWeight);
        address = findViewById(R.id.txtaddress);

       // Toast.makeText(getApplicationContext(),"Registration form",Toast.LENGTH_SHORT).show();

        findViewById(R.id.btnCreateUser).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username=name.getText().toString();
                String email=id.getText().toString();
                String password=pwd.getText().toString();
                String userage=age.getText().toString();
                String w=weight.getText().toString();
                String add = address.getText().toString();

                MainActivity.caller.SignUpApi(email,password,username,userage,w,add);

            }
        });

    }



}
