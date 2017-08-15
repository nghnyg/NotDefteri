package nagihan.notdefteri;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;


public class Lists extends Activity {
    EditText editText;
    Button btnSil, btnKaydet, btnPhoto;
    private String db;
    public static int TAKE_PICTURE = 1002;
    public int id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list);
        editText = (EditText) findViewById(R.id.editText);
        btnSil = (Button) findViewById(R.id.btnSil);
        btnKaydet = (Button) findViewById(R.id.btnKaydet);
        btnPhoto = (Button) findViewById(R.id.btnPhoto);

        final int position = getIntent().getIntExtra("POSITION", 0);
        String data = getIntent().getStringExtra("DATA");
        id = getIntent().getIntExtra("ID", 0);
        editText.setText("" + data);

        Typeface typeFace = Typeface.createFromAsset(getAssets(), "fonts/elyazisi.otf");
        editText.setTypeface(typeFace);

        ImageView img= (ImageView)findViewById(R.id.img);
        Database d = new Database(getApplicationContext());
        Notes note = d.getById(id);

        if(note.getImage()!=null){

            Bitmap bmp = BitmapFactory.decodeByteArray(note.getImage(), 0, note.getImage().length);
            ImageView imgNoteImage = (ImageView) findViewById(R.id.img);

            imgNoteImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 500,
                    500, false));

        }


        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db = editText.getText().toString();
                Database database = new Database(getApplicationContext());
                database.Update(db, id);
                Intent i = new Intent(Lists.this, Login.class);
                startActivity(i);

            }
        });

        btnSil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editText.setText("");
                db = editText.getText().toString();
                Database d = new Database(getApplicationContext());
                d.Delete(id);
                Intent i = new Intent(Lists.this, Login.class);
                startActivity(i);

            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera,TAKE_PICTURE);

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent datas) {

        if(requestCode==TAKE_PICTURE){
            Bundle extras = datas.getExtras();
            Bitmap image=(Bitmap)datas.getExtras().get("data");//Çekilen resim id olarak bitmap şeklinde alındı ve imageview'e atandı
            ImageView img= (ImageView)findViewById(R.id.img);
            img.setImageBitmap(image);

            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            image.compress(Bitmap.CompressFormat.PNG, 0, stream);
            byte[] byteArray = stream.toByteArray();

            db = editText.getText().toString();
            Database database = new Database(getApplicationContext());
            database.UpdateWithImage(db, byteArray ,id);
            Intent i = new Intent(Lists.this, Login.class);
            startActivity(i);
        }

    }
}