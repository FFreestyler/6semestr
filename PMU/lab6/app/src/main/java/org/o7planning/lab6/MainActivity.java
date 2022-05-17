package org.o7planning.lab6;

import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.LinkedBlockingQueue;

public class MainActivity extends AppCompatActivity {

    static String url;
    static TextView tv;


    public static MainActivity activity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).hide(); //hide the title bar
        setContentView(R.layout.activity_main);
        url = "https://www.cbr.ru/hd_base/metall/metall_base_new/";
        tv = findViewById(R.id.textView);
        thread();
        activity = this;
    }


    public Map<String, String> thread() {
        LinkedBlockingQueue<Map<String, String>> lQueue = new LinkedBlockingQueue<>();

        Thread gfgThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tableParsing(lQueue);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        gfgThread.start();
        try {
            return lQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
            return null;
        }
    }

    protected void tableParsing(LinkedBlockingQueue<Map<String, String>> queue) throws IOException {
        Map<String, String> metals = new HashMap<>();
        Document doc = Jsoup.connect(url).get();
        Element table = doc.select("table").first();

        assert table != null;
        Elements rows = table.select("tr");

        Element row = rows.get(1);
        Elements cols = row.select("td");

        metals.put("Дата", cols.get(0).text());
        metals.put("Золото", cols.get(1).text());
        metals.put("Серебро", cols.get(2).text());
        metals.put("Платина", cols.get(3).text());
        metals.put("Палладий", cols.get(4).text());

        for (Map.Entry<String, String> entry : metals.entrySet()) {
            tv.append(entry.getKey() + ":" + entry.getValue().toString() + "\n");
        }

        queue.add(metals);
    }
}