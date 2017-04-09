package br.com.jovennan.agenda;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import br.com.jovennan.agenda.dao.StudentDAO;
import br.com.jovennan.agenda.model.Student;
import br.com.jovennan.agenda.utils.StudentsInsertUtil;

public class StudentsInsertActivity extends AppCompatActivity {


    private static final int CODE_PHOTO = 567;
    private StudentsInsertUtil studentsInsertUtil;
    private String pathPhoto;
    private ImageView imageViewPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_insert);

        studentsInsertUtil = new StudentsInsertUtil(StudentsInsertActivity.this);

        Intent intent = getIntent();
        Student editStudent = (Student) intent.getSerializableExtra("student");


        if (editStudent != null){
            studentsInsertUtil.buildEditStudent(editStudent);
        }

        Button buttonPhoto = (Button) this.findViewById(R.id.studentsInsert_camButtonId);
        buttonPhoto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                Intent intentCaptureImage = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                pathPhoto = getExternalFilesDir(null)+"/"+ System.currentTimeMillis()+".jpg";
                File filePhoto = new File(pathPhoto);
                intentCaptureImage.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(filePhoto));
                startActivityForResult(intentCaptureImage, CODE_PHOTO);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == Activity.RESULT_OK && requestCode == CODE_PHOTO){
            imageViewPhoto = (ImageView) findViewById((R.id.studentInsert_imageViewPhoto));
            imageViewPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            Bitmap bitmap = BitmapFactory.decodeFile(pathPhoto);
            Bitmap bitmapReduce = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            imageViewPhoto.setImageBitmap(bitmapReduce);
            imageViewPhoto.setTag(pathPhoto);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.this_menu_insert, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.StudentsInsert_MenuOk:
                Student student;// = new Student();
                try {
                    StudentDAO studentDAO = new StudentDAO(StudentsInsertActivity.this);
                    student = studentsInsertUtil.buildStudentForInsert();

                    if (student.getId() != null){
                        studentDAO.update(student);
                    }else{
                        studentDAO.create(student);
                    }

                    studentDAO.close();
                    Toast.makeText(StudentsInsertActivity.this, "Novo aluno " + student.getName() + " salvo!", Toast.LENGTH_SHORT).show();
                    finish();
                }catch (Exception e){
                    Toast.makeText(StudentsInsertActivity.this, "Erro ao salvar novo aluno. \n" + e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

}