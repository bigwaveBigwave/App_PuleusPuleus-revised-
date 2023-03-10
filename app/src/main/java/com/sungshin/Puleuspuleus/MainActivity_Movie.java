package com.sungshin.Puleuspuleus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.os.Bundle;
import android.content.Intent;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;


import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

public class MainActivity_Movie extends AppCompatActivity  {
    private ListView myListView_movie;
    DBHelper_movie mydb_movie;
    ArrayAdapter mAdapter;

    long time = 0;

    @Override
    public void onBackPressed() {
        if(System.currentTimeMillis()-time>=1000) {
            time=System.currentTimeMillis();
            Toast.makeText(getApplicationContext(),"뒤로 가기 버튼을 한 번 더 누르면 종료됩니다.", Toast.LENGTH_SHORT).show();
        }
        else if(System.currentTimeMillis()-time<1000){ // 뒤로 가기 한번 더 눌렀을때의 시간간격 텀이 1초
            finishAffinity();
            System.runFinalization();
            System.exit(0);
        }
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie);

        ImageView my = (ImageView) findViewById(R.id.imageMenu2);

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                startActivity(intent);
            }
        });



        final DrawerLayout drawerLayout = findViewById(R.id.drawerLayout);

        findViewById(R.id.imageMenu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // start에 지정된 Drawer 열기
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();

                int id = menuItem.getItemId();
                String title = menuItem.getTitle().toString();

                if(id == R.id.myP){
                    Intent intent = new Intent(getApplicationContext(),MainActivity_Movie.class);
                    startActivity(intent);
                }
                else if(id == R.id.otherP){
                    Intent intent = new Intent(getApplicationContext(),OtherPlantActivity.class);
                    startActivity(intent);
                }
                else if(id == R.id.myInfo){
                    Intent intent = new Intent(getApplicationContext(),MyActivity.class);
                    startActivity(intent);
                }

                return true;
            }
        });




        mydb_movie = new DBHelper_movie(this);
        ArrayList array_list_movie = mydb_movie.getAllMovies();

        mAdapter =
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, array_list_movie);

        myListView_movie = (ListView) findViewById(R.id.listView1_movie);
        myListView_movie.setAdapter(mAdapter);

        myListView_movie.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long arg4) {
                String item = (String) ((ListView) parent).getItemAtPosition(position);
                String[] strArray = item.split(" ");
                int id = Integer.parseInt(strArray[0]);
                Bundle dataBundle = new Bundle();
                dataBundle.putInt("id", id);
                Intent intent = new Intent(getApplicationContext(), MainActivity_Movie_Display.class);
                intent.putExtras(dataBundle);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mAdapter.clear();
        mAdapter.addAll(mydb_movie.getAllMovies());
        mAdapter.notifyDataSetChanged();
    }





    public void onClick(View target) {

        Bundle bundle = new Bundle();
        bundle.putInt("id", 0);
        Intent intent = new Intent(getApplicationContext(), MainActivity_Movie_Display.class);
        intent.putExtras(bundle);
        startActivity(intent);


    }
}

