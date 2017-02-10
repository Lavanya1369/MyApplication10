package com.l9ench.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import shem.com.materiallogin.DefaultLoginView;
import shem.com.materiallogin.DefaultRegisterView;
import shem.com.materiallogin.MaterialLoginView;

public class CustomizeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customize);
       /* getSupportActionBar().hide();*/


        final MaterialLoginView login = (MaterialLoginView) findViewById(R.id.login);
        ((DefaultLoginView)login.getLoginView()).setListener(new DefaultLoginView.DefaultLoginViewListener() {

            @Override
            public void onLogin(TextInputLayout loginUser, TextInputLayout loginPass) {
                String user = loginUser.getEditText().getText().toString();
                if (user.isEmpty()) {
                    loginUser.setError("User name can't be empty");
                    return;
                }
              //  loginUser.setError("");

                String pass = loginPass.getEditText().getText().toString();
                if (pass.isEmpty()) {
                    loginPass.setError("password can't be empty");
                    return;
                }
                getallme(user,pass);
               // loginPass.setError("");
                Intent i1=new Intent(CustomizeActivity.this,MainActivity.class);
                startActivity(i1);

                //Snackbar.make(login, "Login success!", Snackbar.LENGTH_LONG).show();
            }
        });

        ((DefaultRegisterView)login.getRegisterView()).setListener(new DefaultRegisterView.DefaultRegisterViewListener() {
            @Override
            public void onRegister(TextInputLayout registerUser, TextInputLayout registerPass, TextInputLayout registerPassRep) {
                String user = registerUser.getEditText().getText().toString();
                String pass = registerPass.getEditText().getText().toString();
                String passRep = registerPassRep.getEditText().getText().toString();
                if (user.isEmpty()&&pass.isEmpty()&&passRep.isEmpty()) {
                    registerUser.setError("User name can't be empty");
                    return;
                }
                else{
                    
                }
                registerUser.setError("");
                registerPass.setError("");
                registerPassRep.setError("");

                Snackbar.make(login, "Register success!", Snackbar.LENGTH_LONG).show();
            }
        });
    }



    public void getallme(final String user, final String pass)
    {
        final StringRequest stringRequest = new StringRequest(Request.Method.POST, MyUrl_controller.loginform ,new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject responseObj = new JSONObject(response);
                    boolean success = responseObj.getBoolean("success");
                    //Toast.makeText(MainActivity.this,"jikx",Toast.LENGTH_LONG).show();
                    if (success)
                    {
                        String name = responseObj.getString("name");
                        String number = responseObj.getString("phoneno");
                        String email = responseObj.getString("email");
                        String type = responseObj.getString("type");
                        Intent intent=new Intent(CustomizeActivity.this,MainActivity.class);
                        intent.putExtra("name",name);
                        intent.putExtra("num",number);
                        intent.putExtra("email",email);
                        intent.putExtra("type",type);
                        startActivity(intent);

                    }
                    else {
                        Toast.makeText(CustomizeActivity.this,"something went wrong please try again!!!",Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String,String>();
                params.put("na1",user);
                params.put("na2",pass);
                return params;
            }
        };
        Secondclass.getInstance().addToRequestQueue(stringRequest);
    }

}


