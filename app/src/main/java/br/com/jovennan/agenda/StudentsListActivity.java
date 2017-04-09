package br.com.jovennan.agenda;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.ContextMenu;
import android.view.View;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import br.com.jovennan.agenda.adapter.StudentAdapter;
import br.com.jovennan.agenda.dao.StudentDAO;
import br.com.jovennan.agenda.model.Student;

public class StudentsListActivity extends AppCompatActivity {

    ListView studentListView;
    final StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);

    @Override
    protected void onResume() {
        super.onResume();
        buildStudentsList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        studentListView = (ListView) findViewById(R.id.studentsList_listViewStudents);

        studentListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View item, int position, long id) {
                Student student = (Student) studentListView.getItemAtPosition(position);
                Intent intentStudentInsert = new Intent(StudentsListActivity.this, StudentsInsertActivity.class);
                intentStudentInsert.putExtra("student", student);
                startActivity(intentStudentInsert);
            }
        });

        Button newButton = (Button) findViewById(R.id.studentsInsert_buttonNew);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStudentInsert = new Intent(StudentsListActivity.this, StudentsInsertActivity.class);
                startActivity(intentStudentInsert);
            }
        });

        registerForContextMenu(studentListView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, final ContextMenu.ContextMenuInfo menuInfo) {
        MenuItem goToSite = menu.add("Visitar site");
        MenuItem deleteMenuItem = menu.add("Delete");
        MenuItem sendSmsMenuItem = menu.add("Enviar SMS");
        MenuItem locationMenuItem = menu.add("Ver Endereço");
        MenuItem callMenuItem = menu.add("Ligar");

        buildCall((AdapterView.AdapterContextMenuInfo) menuInfo, callMenuItem);


        buildShowLocation((AdapterView.AdapterContextMenuInfo) menuInfo, locationMenuItem);

        buildSendSMS((AdapterView.AdapterContextMenuInfo) menuInfo, sendSmsMenuItem);

        buildGoToSite((AdapterView.AdapterContextMenuInfo) menuInfo, goToSite);

        buildRemove((AdapterView.AdapterContextMenuInfo) menuInfo, deleteMenuItem);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 123){
            System.out.println("Requisitando Chamada");
        } else{
            System.out.println("Requisitando o/");
        }
    }

    private void buildCall(AdapterView.AdapterContextMenuInfo menuInfo, MenuItem callMenuItem) {
        //Intent callIntent = new Intent(Intent.ACTION_CALL); //ACTION_DIAL

        AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
        final Student student = (Student) studentListView.getItemAtPosition(adapterMenuInfo.position);
//        callIntent.setData(Uri.parse("tel:"+student.getNumber()));
//        callMenuItem.setIntent(callIntent);

        callMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if(ActivityCompat.checkSelfPermission(StudentsListActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(StudentsListActivity.this, new String[]{Manifest.permission.CALL_PHONE}, 123);
                } else {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.parse("tel:" + student.getNumber()));
                    startActivity(callIntent);
                }

                return false;
            }
        });
    }

    private void buildShowLocation(AdapterView.AdapterContextMenuInfo menuInfo, MenuItem locationMenuItem) {
        Intent showAddressIntent = new Intent(Intent.ACTION_VIEW);

        AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
        Student student = (Student) studentListView.getItemAtPosition(adapterMenuInfo.position);
        showAddressIntent.setData(Uri.parse("geo:0,0?q="+student.getAddress()));
        locationMenuItem.setIntent(showAddressIntent);
    }

    private void buildSendSMS(AdapterView.AdapterContextMenuInfo menuInfo, MenuItem sendSmsMenuItem) {
        Intent sendSmsIntent = new Intent(Intent.ACTION_VIEW);
        AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
        Student student = (Student) studentListView.getItemAtPosition(adapterMenuInfo.position);
        sendSmsIntent.setData(Uri.parse("sms:" +student.getNumber()));
        sendSmsMenuItem.setIntent(sendSmsIntent);
    }

    private void buildRemove(final AdapterView.AdapterContextMenuInfo menuInfo, MenuItem deleteMenuItem) {
        deleteMenuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
                Student student = (Student) studentListView.getItemAtPosition(adapterMenuInfo.position);

                //StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);
                studentDAO.delete(student.getId());
                studentDAO.close();

                buildStudentsList();

                Toast.makeText(StudentsListActivity.this, "Aluno "  + student.getName() + " removido!", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void buildGoToSite(AdapterView.AdapterContextMenuInfo menuInfo, MenuItem goToSite) {
        final Intent gottoSiteIntent = new Intent(Intent.ACTION_VIEW);

        AdapterView.AdapterContextMenuInfo adapterMenuInfo = menuInfo;
        Student student = (Student) studentListView.getItemAtPosition(adapterMenuInfo.position);
        Long id = student.getId();

        String site = student.getSite();//studentDAO.read().get((int) (long) id).getSite();
        //studentDAO.close();
        if(!site.startsWith("http://")){
            gottoSiteIntent.setData(Uri.parse("http://"+site));
        }else {
            gottoSiteIntent.setData(Uri.parse(site));
        }
        goToSite.setIntent(gottoSiteIntent);

        goToSite.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Toast.makeText(StudentsListActivity.this, "Visitando o site " , Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }

    private void buildStudentsList() {

        StudentDAO studentDAO = new StudentDAO(StudentsListActivity.this);
        List<Student> studentList = studentDAO.read();
        studentDAO.close();

        StudentAdapter studentsListViewAdapter = new StudentAdapter(this, studentList);
        studentListView.setAdapter(studentsListViewAdapter);
    }

}
