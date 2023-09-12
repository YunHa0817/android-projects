package com.example.systemdrone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class FarmImage extends AppCompatActivity {
    ListView list;
    String[] titles = {
            "농경지1",
            "농경지2",
            "농경지3",
            "농경지4",
            "농경지5",
            "농경지6"
    };

    String[] names = {
            "감자",
            "배추",
            "무",
            "대파",
            "양배추",
            "녹차"
    };

    int[] farmImages = {
            R.drawable.agricultural_land1,
            R.drawable.agricultural_land2,
            R.drawable.agricultural_land3,
            R.drawable.agricultural_land4,
            R.drawable.agricultural_land5,
            R.drawable.agricultural_land6
    };

    private int temp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.farmimage);

        CustomList adapter = new CustomList(FarmImage.this);
        list = (ListView) findViewById(R.id.farmList);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getBaseContext(), "농경지 종류 : "+titles[+position] + "\n작물정보 : "+names[+position], Toast.LENGTH_SHORT).show();
                temp = farmImages[+position];
            }
        });
    }

    public class CustomList extends ArrayAdapter<String> {
        private final Activity context;

        public CustomList(Activity context) {
            super(context, R.layout.listitem, titles);
            this.context = context;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = context.getLayoutInflater();
            View rowView = inflater.inflate(R.layout.listitem, null, true);
            ImageView imageView = (ImageView) rowView.findViewById(R.id.farmImage);

            TextView title = (TextView) rowView.findViewById(R.id.farmTitle);
            TextView name = (TextView) rowView.findViewById(R.id.cropName);

            title.setText(titles[position]);
            name.setText(names[position]);
            imageView.setImageResource(farmImages[position]);

            return rowView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.farmimagemenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.fm_OK)
        {
            Toast.makeText(getApplicationContext(), "농경지 선택 완료", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent();
            intent.putExtra("INPUT_TEXT", temp);
            setResult(RESULT_OK, intent);
            finish();
            return true;
        }
        else
            return super.onOptionsItemSelected(item);
    }
}
