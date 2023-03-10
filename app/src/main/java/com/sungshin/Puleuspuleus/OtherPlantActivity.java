package com.sungshin.Puleuspuleus;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class OtherPlantActivity extends AppCompatActivity {

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
        setContentView(R.layout.other_plant);

        ImageView my = (ImageView) findViewById(R.id.imageMenu2);

        my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MyActivity.class);
                startActivity(intent);
            }
        });

        setTitle("다른 식물 둘러보기");

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

        Button otherplant = (Button) findViewById(R.id.button_otherplant);
        Button search = (Button) findViewById(R.id.button_search);

        otherplant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),View_otherActivity.class);
                startActivity(intent);
            }
        });

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent url_intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://hgarden.net/"));
                startActivity(url_intent);
            }
        });





    }

}