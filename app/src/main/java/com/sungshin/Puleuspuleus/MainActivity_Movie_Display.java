package com.sungshin.Puleuspuleus;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;



import java.util.Calendar;

public class MainActivity_Movie_Display extends AppCompatActivity implements View.OnClickListener {

    Button button2_movie, button1_movie, button3_movie;
    ImageButton dialogbutton1, dialogbutton2;
    TextView dialogtextview1, dialogtextview2;
    TextView plantwaterday, waterday, lastwaterday;
    ImageView plantimage;
    View dialogplanttype;
    EditText editTextName_movie;


    private DBHelper_movie mydb_movie;
    TextView name_movie;
    TextView kind_movie;
    TextView watercycle_movie;
    TextView lastwater_movie;

    int id = 0;

    //파일 이름을 지정할 문자열 변수 1개 선언
    String fileName;

    //DB 연결을 위한 변수 2개 선언
    DBHelper_movie dbHelper_movie;
    SQLiteDatabase sqlDB;
    TextView viewResult;
    //dialogtextview, waterday, PNickname, lastwaterday

    DatePickerDialog dataPickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_movie_display);

        name_movie = (TextView) findViewById(R.id.editTextName_movie);//식물애칭
        kind_movie = (TextView) findViewById(R.id.dialogtextview1);//식물종류
        watercycle_movie = (TextView) findViewById(R.id.waterday);//식물물주기
        lastwater_movie = (TextView) findViewById(R.id.dialogtextview2);//마지막물준날

        button2_movie = (Button) findViewById(R.id.button2_movie);
        button3_movie = (Button) findViewById(R.id.button3_movie);


        //대화상자(imagebutton)
        dialogbutton1 = (ImageButton) findViewById(R.id.dialogbutton1);
        dialogbutton2 = (ImageButton) findViewById(R.id.dialogbutton2);

        //대화상자(textview)
        //dialogtextview1 = (TextView) findViewById(R.id.dialogtextview1);//식물종류
        //dialogtextview2 = (TextView) findViewById(R.id.dialogtextview2);//마지막으로물준날

        //visible적용
        plantwaterday = (TextView) findViewById(R.id.plantwaterday);
        //waterday = (TextView) findViewById(R.id.waterday);//식물물주기
        lastwaterday = (TextView) findViewById(R.id.lastwaterday);
        //plantimage = (ImageView) findViewById(R.id.imageView1_movie);

        editTextName_movie = (EditText) findViewById(R.id.editTextName_movie);//식물애칭


        mydb_movie = new DBHelper_movie(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                Cursor rs = mydb_movie.getData(Value);
                id = Value;
                rs.moveToFirst();
                @SuppressLint("Range") String n = rs.getString(rs.getColumnIndex(DBHelper_movie.MOVIES_COLUMN_NAME));
                @SuppressLint("Range") String k = rs.getString(rs.getColumnIndex(DBHelper_movie.MOVIES_COLUMN_KIND));
                @SuppressLint("Range") String w = rs.getString(rs.getColumnIndex(DBHelper_movie.MOVIES_COLUMN_WATERCYCLE));
                @SuppressLint("Range") String l = rs.getString(rs.getColumnIndex(DBHelper_movie.MOVIES_COLUMN_LASTWATER));
                //@SuppressLint("Range") String p = rs.getString(rs.getColumnIndex(DBHelper_movie.MOVIES_COLUMN_PLANTIMAGE));


                if (!rs.isClosed()) {
                    rs.close();
                }
                Button b = (Button) findViewById(R.id.button1_movie);
                b.setVisibility(View.VISIBLE);

                name_movie.setText((CharSequence) n);
                kind_movie.setText((CharSequence) k);
                watercycle_movie.setText((CharSequence) w);
                lastwater_movie.setText((CharSequence) l);
                //plantimage = Integer.parseInt((CharSequence) l);
                //imageView.setImageResource(intent.getIntExtra("imgRes",0));
                //titleRes.setText(intent.getStringExtra("titleRes"));


            }
        }



        //버튼 클릭 이벤트 정의
        //DB저장
        //button2_movie.setOnClickListener(this);
        //대화상자(imagebutton)
        dialogbutton1.setOnClickListener(this);//식물종류
        dialogbutton2.setOnClickListener(this);//마지막으로물준날
        //대화상자(textview)
        kind_movie.setOnClickListener(this);//식물종류
        lastwater_movie.setOnClickListener(this);//마지막으로물준날


        //DB
        //dbHelper = new DBHelper(MainActivity_Movie_Display.this, 1);
        //DB testResult
        viewResult = (TextView) findViewById(R.id.txtResult);


        //날짜다이어로그
        Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        dataPickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                lastwater_movie.setText(year + "/" + (month + 1) + "/" + dayOfMonth);
            }
        }, mYear, mMonth, mDay);

    }

    //DB저장에서 가져오는 텍스트뷰 이름들 순서대로
    //식물종류, 물주기, 애칭, 마지막준날
    //dialogtextview, waterday, PNickname, lastwaterday
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //마지막 확인 버튼 -> DB에 저장됨


            //이미지 버튼 -> 식물 종류 대화상자
            case R.id.dialogbutton1:
                dialogbutton1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity_Movie_Display.this);
                        dlg.setTitle("식물의 종류를 선택해주세요");
                        //dlg.setView(dialogplanttype);

                        //물주기
                        final String[] memberArray = new String[]{"스투키", "산세베리아", "몬스테라", "고무나무", "테이블야자", "애플민트", "바질트리", "로즈마리", "페퍼민트", "레몬밤", "카랑코에", "피쉬본", "만세선인장", "국화", "안개꽃", "제라늄", "아스파라거스", "블루베리", "아보카도", "방울토마토"};
                        final String[] memberArray1 = new String[]{"30DAY", "30DAY", "4DAY", "7DAY", "7DAY", "7DAY", "5DAY", "5DAY", "7DAY", "5DAY", "10DAY", "15DAY", "30DAY", "10DAY", "5DAY", "10DAY", "3DAY", "7DAY", "2DAY", "5DAY"};
                        //이미지
                        int[] images = {R.drawable.stuckyi, R.drawable.sansevieria, R.drawable.monstera, R.drawable.elastica, R.drawable.parlourpalm, R.drawable.applemint,
                                R.drawable.basiltree, R.drawable.rosemary, R.drawable.peppermint, R.drawable.lemonbalm, R.drawable.kalanchoe, R.drawable.fishbone,
                                R.drawable.roadkillcactus, R.drawable.chrysanthemum, R.drawable.gypsophila, R.drawable.geranium, R.drawable.asparagus, R.drawable.blueberry, R.drawable.avocado, R.drawable.tomato}; // 이미지 배열. 이미지는 return 값이 integer 인것 까먹지 않기!

                        dlg.setSingleChoiceItems(memberArray, 0,
                                new DialogInterface.OnClickListener() {
                                    @Override

                                    public void onClick(DialogInterface dialog, int which) {
                                        //이미지 나타내기
                                        //plantimage.setImageResource(images[which]); // 선택한 값 저장.

                                        //물주기 나타내기
                                        kind_movie.setText(memberArray[which]);//식물종류
                                        watercycle_movie.setText(memberArray1[which]);//물주기

                                    }
                                });

                        dlg.setNegativeButton("취소", null);
                        dlg.setPositiveButton("확인",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        dlg.show();
                    }
                });
                break;

            //이미지 버튼 -> 날짜선택 대화상자
            case R.id.dialogbutton2:
                dialogbutton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (dialogbutton2.isClickable()) {
                            dataPickerDialog.show();
                        }
                    }
                });
                break;
            //날짜 선택 텍스트뷰 -> 날짜선택 대화상자
            case R.id.dialogtextview2:
                lastwater_movie.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (lastwater_movie.isClickable()) {
                            dataPickerDialog.show();
                        }
                    }
                });
                break;
        }


    }









    public void insert(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                if (mydb_movie.updateMovie(id, name_movie.getText().toString(),kind_movie.getText().toString(),watercycle_movie.getText().toString(),lastwater_movie.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainActivity_Movie.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            } else {
                if (mydb_movie.insertMovie(name_movie.getText().toString(),kind_movie.getText().toString(),watercycle_movie.getText().toString(),lastwater_movie.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "추가되었음", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(getApplicationContext(), "추가되지 않았음", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        }
    }

    public void delete(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int Value = extras.getInt("id");
            if (Value > 0) {
                mydb_movie.deleteMovie(id);
                Toast.makeText(getApplicationContext(), "삭제되었음", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "삭제되지 않았음", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void edit(View view) {
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            int value = extras.getInt("id");
            if (value > 0) {
                if (mydb_movie.updateMovie(id, name_movie.getText().toString(),kind_movie.getText().toString(),watercycle_movie.getText().toString(),lastwater_movie.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "수정되었음", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(getApplicationContext(), "수정되지 않았음", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}

