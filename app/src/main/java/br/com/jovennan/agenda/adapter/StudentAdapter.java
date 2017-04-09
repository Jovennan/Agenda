package br.com.jovennan.agenda.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.com.jovennan.agenda.R;
import br.com.jovennan.agenda.model.Student;

/**
 * Created by jovennan on 08/04/17.
 */

public class StudentAdapter extends BaseAdapter {

    private final Context context;
    private final List<Student> students;

    public StudentAdapter(Context context, List<Student> students) {
        this.context = context;
        this.students = students;
    }



    @Override
    public int getCount() {
        return students.size();
    }

    @Override
    public Object getItem(int position) {
        return students.get(position);
    }

    @Override
    public long getItemId(int position) {
        return students.get(position).getId();
    }

    @Override
    public View getView(int position, View contextView, ViewGroup parent) {
        Student student = this.students.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = contextView;
        if(view == null){
            view = inflater.inflate(R.layout.list_student_item, parent, false);
        }

        TextView studentName = (TextView) view.findViewById(R.id.studentItemTextViewNome);
        TextView studentPhone = (TextView)view.findViewById(R.id.studentItemTextViewPhone);

        studentName.setText(student.getName());
        studentPhone.setText(student.getNumber());

        ImageView studentPhoto = (ImageView) view.findViewById((R.id.studentItemImageViewPhoto));
        if(student.getPathPhoto() != null) {

            Bitmap bitmap = BitmapFactory.decodeFile(student.getPathPhoto());
            Bitmap bitmapReduce = Bitmap.createScaledBitmap(bitmap, 300, 300, true);
            studentPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            studentPhoto.setImageBitmap(bitmapReduce);
        }

        //textView.setText("Item da posiçao "+ position);
        return view;
    }
}
