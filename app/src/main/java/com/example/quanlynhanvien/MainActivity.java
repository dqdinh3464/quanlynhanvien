package com.example.quanlynhanvien;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.strictmode.SqliteObjectLeakedViolation;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private SQLiteDatabase db;
    EditText edtName, edtBirth, edtPhongBan;
    RadioGroup rdGroupGender;
    CheckBox cb1To5Year, cb5To10Year, cbGreater10Year;
    Button btnAdd, btnShow;
    ListView lvNhanVien;

    ArrayAdapter<NhanVien> adapter;
    ArrayList<NhanVien> list_nv = new ArrayList<>();

    String gender = "";
    String worked_time = "1 - 5 năm";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initDataBase();

        edtName = findViewById(R.id.edtName);
        edtBirth = findViewById(R.id.edtBirth);
        edtPhongBan = findViewById(R.id.edtPhongBan);
        rdGroupGender = findViewById(R.id.rdGroupGender);
        btnAdd = findViewById(R.id.btnAdd);
        btnShow = findViewById(R.id.btnShow);
        lvNhanVien = findViewById(R.id.lvNhanVien);

        cb1To5Year = findViewById(R.id.cb1To5Year);
        cb5To10Year = findViewById(R.id.cb5To10Year);
        cbGreater10Year = findViewById(R.id.cbGreater10Year);

        cb1To5Year.setOnCheckedChangeListener(listenerCheckBox);
        cb5To10Year.setOnCheckedChangeListener(listenerCheckBox);
        cbGreater10Year.setOnCheckedChangeListener(listenerCheckBox);

        rdGroupGender.check(R.id.rdBtnMale);
        rdGroupGender.setOnCheckedChangeListener(listenerRadioGroup);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                create();
            }
        });

        btnShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfo();
            }
        });

        adapter = new ArrayAdapter<NhanVien>(this, 0,list_nv){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.nhan_vien,null);

                TextView txtTen = convertView.findViewById(R.id.txtName);
                TextView txtBirthDay = convertView.findViewById(R.id.txtDateOfBirth);
                TextView txtPhongBan = convertView.findViewById(R.id.txtPhongBan);
                TextView txtGender = convertView.findViewById(R.id.txtGender);
                TextView txtWorkedTime = convertView.findViewById(R.id.txtWorkedTime);

                NhanVien nv = list_nv.get(position);
                txtTen.setText(nv.getName());
                txtBirthDay.setText(nv.getBirthDay());
                txtPhongBan.setText(nv.getPhongBan());
                txtGender.setText(nv.isGender());
                txtWorkedTime.setText(nv.getWorkedTime());

                return convertView;
            }
        };
        lvNhanVien.setAdapter(adapter);
    }

    CompoundButton.OnCheckedChangeListener listenerCheckBox = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            if (isChecked){
                worked_time = buttonView.getText().toString();
            }
            else{
                worked_time = "";
            }
        }
    };

    RadioGroup.OnCheckedChangeListener listenerRadioGroup = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup group, int checkedId) {
            switch (checkedId){
                case R.id.rdBtnMale:
                    gender = "Nam";
                    break;
                case R.id.rdBtnFemale:
                    gender = "Nữ";
                    break;
            }
        }
    };

    private void initDataBase(){
        db = openOrCreateDatabase("ql_nhan_vien.db", MODE_PRIVATE, null);
        String sql = "CREATE TABLE IF NOT EXISTS nhan_vien (id integer primary key autoincrement, name text, birth_day text, phong_ban text, gender text, worked_time text)";
        db.execSQL(sql);
    }

    private void create(){
        String name = edtName.getText().toString();
        String birth_day = edtBirth.getText().toString();
        String phong_ban = edtPhongBan.getText().toString();
        String gender = this.gender;
        String worked_time = this.worked_time;
        String sql = "INSERT INTO nhan_vien (name, birth_day, phong_ban, gender, worked_time) VALUES ('"+ name +"','" + birth_day + "', '" + phong_ban + "', '" + gender + "', '" + worked_time + "')";
        db.execSQL(sql);
    }

    private void showInfo(){
        list_nv.clear();
        String sql = "SELECT * FROM nhan_vien";
        Cursor cs  = db.rawQuery(sql,null);
        cs.moveToFirst();

        while (cs.isAfterLast() == false){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            String birth_day  = cs.getString(2);
            String phong_ban  = cs.getString(3);
            String gender  = cs.getString(4);
            String worked_time  = cs.getString(5);

            NhanVien nv = new NhanVien();
            nv.setId(id);
            nv.setName(name);
            nv.setBirthDay(birth_day);
            nv.setPhongBan(phong_ban);
            nv.setGender(gender);
            nv.setWorkedTime(worked_time);

            list_nv.add(nv);
            cs.moveToNext();
        }
        adapter.notifyDataSetChanged();
    }
}