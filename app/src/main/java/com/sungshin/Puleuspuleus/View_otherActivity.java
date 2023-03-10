package com.sungshin.Puleuspuleus;

import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

public class View_otherActivity extends AppCompatActivity {

    private CustomAdapter adapter1;
    private ListView listView1;







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_otherplant);

        //Navigation Drawer
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

        adapter1 = new CustomAdapter();
        ListView listView1 = (ListView) findViewById(R.id.MyListView);

        setData();

        listView1.setAdapter(adapter1);




        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long l) {
                Intent intent = new Intent(getApplicationContext(),Other_plant_info.class);


                int imgRes = ((CustomDTO)adapter1.getItem(position)).getResId();
                String titleRes = ((CustomDTO)adapter1.getItem(position)).getTitle();
                String contentRes = ((CustomDTO)adapter1.getItem(position)).getContent();
                String contentRes2 = ((CustomDTO)adapter1.getItem(position)).getContent2();
                String contentRes3 = ((CustomDTO)adapter1.getItem(position)).getContent3();
                String contentRes4 = ((CustomDTO)adapter1.getItem(position)).getContent4();

                intent.putExtra("imgRes",imgRes);
                intent.putExtra("titleRes",titleRes);
                intent.putExtra("contentRes",contentRes);
                intent.putExtra("contentRes2",contentRes2);
                intent.putExtra("contentRes3",contentRes3);
                intent.putExtra("contentRes4",contentRes4);
                startActivity(intent);







            }
        });
    }







    // 보통 ListView는 통신을 통해 가져온 데이터를 보여줍니다.
    // arrResId, titles, contents를 서버에서 가져온 데이터라고 생각하시면 됩니다.
    private void setData() {
        TypedArray arrResId = getResources().obtainTypedArray(R.array.resId);
        String[] titles = getResources().getStringArray(R.array.title);
        String[] contents = getResources().getStringArray(R.array.content);
        String[] contents2 = getResources().getStringArray(R.array.content2);
        String[] contents3 = getResources().getStringArray(R.array.content3);
        String[] contents4 = getResources().getStringArray(R.array.content4);



        for (int i = 0; i < arrResId.length(); i++) {
            CustomDTO dto = new CustomDTO();
            dto.setResId(arrResId.getResourceId(i, 0));
            dto.setTitle(titles[i]);
            dto.setContent(contents[i]);
            dto.setContent2(contents2[i]);
            dto.setContent3(contents3[i]);
            dto.setContent4(contents4[i]);



            adapter1.addItem(dto);
        }




    }



};






