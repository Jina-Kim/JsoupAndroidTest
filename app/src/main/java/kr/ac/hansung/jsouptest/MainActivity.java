package kr.ac.hansung.jsouptest;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private Button getBtn;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.text);
        getBtn = (Button) findViewById(R.id.button);
        getBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getWebsite();
            }
        });
    }

    private void getWebsite() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final StringBuilder builder = new StringBuilder();

                try {
                    Document doc = Jsoup.connect("http://www.hansung.ac.kr/web/www/life_03_01_t2").get();
                    String title = doc.title();
                    //ArrayList<String> foodnames = new ArrayList<String>();
                    Element tbody = doc.select("table").get(0);
                    Elements rows = tbody.select("tr");

                    builder.append(title).append("\n");

                    for(int i=1;i<rows.size();i++){
                        Element row = rows.get(i);
                        Elements cols = row.select("td");

                        builder.append(cols.get(0).text()).append("\n");
                    }
                } catch (IOException e) {
                    builder.append("Error : ").append(e.getMessage()).append("\n");
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        result.setText(builder.toString());
                    }
                });
            }
        }).start();
    }
}
