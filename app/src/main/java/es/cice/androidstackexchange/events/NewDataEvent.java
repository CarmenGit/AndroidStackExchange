package es.cice.androidstackexchange.events;

import android.database.Cursor;

/**
 * Created by cice on 7/2/17.
 */

//clase que origina los eventos
    //cuando estén los datos preparados genera evento

public class NewDataEvent {
    private Cursor c;

    public NewDataEvent(Cursor c) {
        this.c = c;
    }

    public Cursor getC() {
        return c;
    }

    public void setC(Cursor c) {

        this.c = c;
    }
}
