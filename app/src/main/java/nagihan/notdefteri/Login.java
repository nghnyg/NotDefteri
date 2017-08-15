package nagihan.notdefteri;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;


public class Login extends AppCompatActivity {

    EditText edit;
    Button btnSave;
    ListView lst;
    ArrayList arrayList;
    List<Notes> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        edit=(EditText) findViewById(R.id.edit);
        EditText editClear=(EditText) findViewById(R.id.editT);
        btnSave=(Button)findViewById(R.id.btnSave);
        lst=(ListView)findViewById(R.id.lst);
        arrayList = new ArrayList<String>();

        editClear.requestFocus();
        Database db=new Database(Login.this);
        data =db.Listele();

        NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(),data);
        lst.setAdapter(noteAdapter);
        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/elyazisi.otf");
        edit.setTypeface(typeFace);


        lst.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {

                {
                    Intent intent = new Intent(Login.this, Lists.class);
                    intent.putExtra("POSITION", pos);
                    intent.putExtra("DATA",data.get(pos).getNotes());
                    intent.putExtra("ID",data.get(pos).getId());
                    startActivity(intent);
                }

            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Database db = new Database(Login.this);
                db.NotesAdd(edit.getText().toString());

                data =db.Listele();
                edit.setText("");
                NoteAdapter noteAdapter = new NoteAdapter(getApplicationContext(),data);
                lst.setAdapter(noteAdapter);



            }
        });

    }
}