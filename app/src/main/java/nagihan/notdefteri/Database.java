package nagihan.notdefteri;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import static nagihan.notdefteri.R.id.image;


public class Database extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="veritabani_2";
    private static final int DATABASE_VERSION=1010;
    private static final String TABLE_NAME="nagihan";

    private static final String ID="_id";
    private static final String TEXT="notes";
    private static final String IMAGE="image";


    public Database(Context context) {
        super(context, DATABASE_NAME, null,DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String table_create="CREATE TABLE "+TABLE_NAME+
                " ("+ID+" INTEGER PRIMARY KEY AUTOINCREMENT, "+
                TEXT+" TEXT ," + IMAGE+ " BLOB  );";

        db.execSQL(table_create);


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);

    }

    public void NotesAdd(String notes) {

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put(TEXT, notes.trim());
        db.insert(TABLE_NAME, null, cv);
        db.close();

}

    public void NotesAddWithImage(String notes,byte[] image) {

        SQLiteDatabase db=this.getWritableDatabase();

        ContentValues cv=new ContentValues();

        cv.put(TEXT, notes.trim());
        cv.put(IMAGE, image);
        db.insert(TABLE_NAME, null, cv);
        db.close();

    }

    public List<Notes> Listele()
    {

        List<Notes> data = new ArrayList<Notes>();
        SQLiteDatabase db =this.getWritableDatabase();
        String []line = {ID, TEXT, IMAGE};
        Cursor cursor = db.query(TABLE_NAME , line, null, null, null, null, null);

        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Notes note = new Notes();
            note.setId(Integer.parseInt(cursor.getString(0)));
            note.setNotes(cursor.getString(1));
            if(cursor.isNull(2)){
                note.setImage(null);
                Log.i("database","IMAGE NULL");
            }else{
                byte[] byteArray = cursor.getBlob(2);
                note.setImage(byteArray);
                Log.w("database",",image ok");
            }

            data.add(note);
            cursor.moveToNext();
        }
        cursor.close();
        return data;
    }

    public Notes getById(int id){
        Notes note= new Notes();
        SQLiteDatabase db =this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE "+ ID  + "=?";
        Cursor cursor = db.rawQuery(query, new String[] {String.valueOf(id)});
        if (cursor.moveToFirst()){
            note.setId(Integer.parseInt(cursor.getString(0)));
            note.setNotes(cursor.getString(1));
            byte[] imgArr = cursor.getBlob(2);
            note.setImage(imgArr);
        }
        cursor.close();
        return note;
    }

    public void Update(String notes, int id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TEXT, notes);
        int i = db.update(TABLE_NAME, cv, ID+"=?", new String[]{String.valueOf(id)});
        Log.d("Database Update", String.valueOf(i));
        db.close();

    }

    public void UpdateWithImage(String notes,byte[] image, int id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(TEXT, notes);
        cv.put(IMAGE, image);
        int i = db.update(TABLE_NAME, cv, ID+"=?", new String[]{String.valueOf(id)});
        Log.d("Database Update", String.valueOf(i));
        db.close();

    }

    public void Delete( int id) {

        SQLiteDatabase db=this.getWritableDatabase();

         int result = db.delete(TABLE_NAME, ID+"="+String.valueOf(id),null);

        db.close();
    }


}