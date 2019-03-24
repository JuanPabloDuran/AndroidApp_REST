package com.example.juanpablo.rest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.StrictMode;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
private ListView lv_Clients_list;
private ArrayAdapter adapter;
private String getAllContactsURL = "http://192.168.49.35:8080/api_Clientes?user_hash=12345&action=get";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder().permitNetwork().build());
        lv_Clients_list = (ListView)findViewById(R.id.lv_Clients_list);
        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1);
        lv_Clients_list.setAdapter(adapter);
        webService(getAllContactsURL);
    }

    private  void webService(String requestURL){
        try{
            URL url = new URL(requestURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = "";
            String webServiceResult = "";
            while ((line = bufferedReader.readLine()) != null){
                webServiceResult += line;
            }
            bufferedReader.close();
            parseInformation(webServiceResult);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void  parseInformation(String jsonResult){
        JSONArray jsonArray = null;
        String id_cliente;
        String Nombre;
        String Ape_Pat;
        String Ape_Mat;
        String Telefon;
        String email;
        try{
            jsonArray = new JSONArray(jsonResult);
        } catch (JSONException p){
            p.printStackTrace();
        }
        for(int i = 0;i<jsonArray.length();i++){
            try{
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                id_cliente = jsonObject.getString("id_cliente");
                Nombre = jsonObject.getString("Nombre");
                Ape_Pat = jsonObject.getString("Ape_Pat");
                Ape_Mat = jsonObject.getString("Ape_Mat");
                Telefon = jsonObject.getString("Telefono");
                email = jsonObject.getString("email");
                adapter.add(id_cliente + ":" + Nombre);
            }catch (JSONException p){
                p.printStackTrace();
            }
        }
    }
}
