package br.com.jovennan.agenda.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import br.com.jovennan.agenda.R;
import br.com.jovennan.agenda.model.Student;

/**
 * Created by Jovennan on 14/03/2017.
 */

public class StudentsInsertUtil{

    private EditText textName;
    private EditText textAddress;
    private EditText textEmail;
    private EditText textNumber;
    private EditText textSite;
    private RatingBar ratingNote;
    private Student student;
    //private Button buttonPhoto;
    private ImageView imagePhoto;

    public StudentsInsertUtil(AppCompatActivity appCompatActivity){

        textName = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextName);
        textAddress = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextAddres);
        textEmail = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextEmail);
        textNumber = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextNumber);
        textSite = (EditText) appCompatActivity.findViewById(R.id.studentsInsert_editTextSite);
        ratingNote = (RatingBar) appCompatActivity.findViewById(R.id.studentInsert_ratingBarNote);
        student = new Student();
        //buttonPhoto = (Button) appCompatActivity.findViewById(R.id.studentsInsert_camButtonId);
        imagePhoto = (ImageView) appCompatActivity.findViewById(R.id.studentInsert_imageViewPhoto);
    }

    public Student buildStudentForInsert() throws Exception {

        if (textName.getText().toString().equals("")) {
            throw new Exception("Campo 'nome' obrigatório!");
        }

        if (textAddress.getText().toString().equals("")) {
            throw new Exception("Campo 'endereço' obrigatório!");
        }

        if (textEmail.getText().toString().equals("")) {
            throw new Exception("Campo 'email' obrigatório!");
        }

        if (textNumber.getText().toString().equals("")) {
            throw new Exception("Camnpo 'número' obrigatório!");
        }

//        if (textSite.getText().toString() == "") {
//            throw new Exception();
//        }

//        if (ratingNote.getRating() == 0) {
//            throw new Exception();
//        }

        String name = textName.getText().toString();

        String address = textAddress.getText().toString();

        String email = textEmail.getText().toString();

        String number = textNumber.getText().toString();

        String site = textSite.getText().toString();

        Float note = ratingNote.getRating();

        String pathPhoto = (String) imagePhoto.getTag();

        this.student.setName(name);
        this.student.setAddress(address);
        this.student.setEmail(email);
        this.student.setNumber(number);
        this.student.setSite(site);
        this.student.setNote(Double.valueOf(note));
        this.student.setPathPhoto(pathPhoto);

        return this.student;
    }

    public void buildEditStudent(Student editStudent) {

        textName.setText(editStudent.getName());

        textAddress.setText(editStudent.getAddress());

        textEmail.setText(editStudent.getEmail());

        textNumber.setText(editStudent.getNumber());

        textSite.setText(editStudent.getSite());

        ratingNote.setRating(editStudent.getNote().floatValue());

        imagePhoto.setTag(editStudent.getPathPhoto());

        if (editStudent.getPathPhoto() != null) {
            Bitmap bitmap = BitmapFactory.decodeFile(editStudent.getPathPhoto());
            if (bitmap != null) {
                Bitmap bitmapReduce = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
                imagePhoto.setScaleType(ImageView.ScaleType.FIT_XY);
                imagePhoto.setImageBitmap(bitmapReduce);
            }
        }

        this.student = editStudent;

    }
}