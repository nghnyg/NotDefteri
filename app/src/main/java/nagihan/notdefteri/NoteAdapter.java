package nagihan.notdefteri;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class NoteAdapter extends ArrayAdapter<Notes> {
    private  Context context;
    private  List<Notes> notes;
    private String image;

    public NoteAdapter(Context context,List<Notes> notes){

       super(context,0,notes);
        this.context = context;
        this.notes = notes;
        this.image=image;
    }

    public  int getCount(){return notes.size();
    }

    @Override
    public View getView(int position , View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.listview_note_item,parent,false);

        TextView txt = (TextView) rowView.findViewById(R.id.txtNote);
        txt.setText(notes.get(position).getNotes());

        if(notes.get(position).getImage()!=null && (notes.get(position).getImage().length>0)) {
            Bitmap bmp = BitmapFactory.decodeByteArray(notes.get(position).getImage(), 0, notes.get(position).getImage().length);
            ImageView imgNoteImage = (ImageView) rowView.findViewById(R.id.imgNoteImage);

            imgNoteImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 300,
                    300, false));
        }
        Typeface typeFace = Typeface.createFromAsset(getContext().getAssets(), "fonts/elyazisi.otf");
        txt.setTypeface(typeFace);

        return rowView;

    }

}
