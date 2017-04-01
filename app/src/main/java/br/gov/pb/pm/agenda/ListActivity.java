package br.gov.pb.pm.agenda;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.gov.pb.pm.agenda.model.Aluno;

public class ListActivity extends AppCompatActivity {

    List<Aluno> alunos;
    ListView studentListView;

    @Override
    protected void onResume() {
        super.onResume();
        buildAlunoList();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        studentListView = (ListView) findViewById(R.id.studentsList);

        Button newButton = (Button) findViewById(R.id.studentsInsert_buttonNew);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentStudentInsert = new Intent(ListActivity.this, InsertActivity.class);
                startActivity(intentStudentInsert);
            }
        });
    }

    private void buildAlunoList() {

        alunos = new ArrayList<Aluno>();

        for (int i = 0; i < 10; i++){
            alunos. add(new Aluno("Aluno"+i));
        }

        ArrayAdapter<Aluno> studentsListViewAdapter = new ArrayAdapter<Aluno>(this, android.R.layout.simple_list_item_1, alunos);
        studentListView.setAdapter(studentsListViewAdapter);
    }
}
