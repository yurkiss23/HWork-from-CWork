package com.example.cwork;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ListViewActivity extends AppCompatActivity {

    // имена атрибутов для Map
    final String ATTRIBUTE_NAME_TEXT = "text";
    final String ATTRIBUTE_NAME_CHECKED = "checked";
    final String ATTRIBUTE_NAME_IMAGE = "image";

    ListView lvSimple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        // массивы данных
        String[] texts = { "sometext 1", "sometext 2", "sometext 3",
                "sometext 4", "sometext 5" };
        boolean[] checked = { true, false, false, true, false };
        int img = R.drawable.ic_launcher_foreground;

        // упаковываем данные в понятную для адаптера структуру
        ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>(
                texts.length);
        Map<String, Object> m;
        for (int i = 0; i < texts.length; i++) {
            m = new HashMap<>();
            m.put(ATTRIBUTE_NAME_TEXT, texts[i]);
            m.put(ATTRIBUTE_NAME_CHECKED, checked[i]);
            m.put(ATTRIBUTE_NAME_IMAGE, img);
            data.add(m);
        }

        // массив имен атрибутов, из которых будут читаться данные
        String[] from = { ATTRIBUTE_NAME_TEXT, ATTRIBUTE_NAME_CHECKED,
                ATTRIBUTE_NAME_IMAGE };
        // массив ID View-компонентов, в которые будут вставлять данные
        int[] to = { R.id.tvText, R.id.cbChecked, R.id.ivImg };

        // создаем адаптер
        SimpleAdapter sAdapter = new SimpleAdapter(this, data, R.layout.item,
                from, to);

        // определяем список и присваиваем ему адаптер
        lvSimple = (ListView) findViewById(R.id.lvSimple);
        lvSimple.setAdapter(sAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.table:
                Toast.makeText(this, "зробити фото", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(this, TableActivity.class);
                startActivity(intent);
                return true;
            case R.id.home:
                Toast.makeText(this, "Пошук", Toast.LENGTH_LONG).show();
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.listviewitem:
                Toast.makeText(this, "Пошук", Toast.LENGTH_LONG).show();
                intent = new Intent(this, ListViewActivity.class);
                startActivity(intent);
                return true;
            case R.id.listviewsimpleitem:
                Toast.makeText(this, "Пошук", Toast.LENGTH_LONG).show();
                intent = new Intent(this, ListViewSimpleActivity.class);
                startActivity(intent);
                return true;
            case R.id.edit:
                Toast.makeText(this, "Редагування", Toast.LENGTH_LONG).show();
                return true;
            case R.id.edit_copy:
                Toast.makeText(this, "Копіювати", Toast.LENGTH_LONG).show();
                return true;
            case R.id.edit_past:
                Toast.makeText(this, "Вставити", Toast.LENGTH_LONG).show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}