package org.o7planning.lab6;

import android.os.Bundle;
import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.sql.SQLOutput;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        url = "https://www.cbr.ru/hd_base/metall/metall_base_new/";

        Thread gfgThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tableParsing();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        gfgThread.start();
    }

    protected void tableParsing() throws IOException {
        Document doc = Jsoup.connect(url).get();
        Element table = doc.select("table").first();

        Map<String, String> metals = new HashMap<>();

        Elements rows = table.select("tr");

        Element row = rows.get(1);
        Elements cols = row.select("td");

        metals.put("Дата", cols.get(0).text());
        metals.put("Золото", cols.get(1).text());
        metals.put("Серебро", cols.get(2).text());
        metals.put("Платина", cols.get(3).text());
        metals.put("Палладий", cols.get(4).text());

        for(Map.Entry<String, String> out : metals.entrySet()) {
            System.out.println(out.getKey() + ": " + out.getValue());
        }

        System.out.println();

    }
}