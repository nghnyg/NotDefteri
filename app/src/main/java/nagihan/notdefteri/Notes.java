package nagihan.notdefteri;


import android.media.Image;

import java.sql.Blob;

public class Notes  {
    private int Id;
    private String Notes;
    private byte[] Image;

    public Notes(){}
    public Notes(String notes) {
        setNotes(notes);
    }

    public void setId(int id) {
        Id = id;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }

    public String getNotes() {

        return Notes;
    }

    public int getId() {
        return Id;
    }

    public byte[] getImage() {
        return Image;
    }

    public void setImage(byte[] image) {
        Image = image;
    }
}
