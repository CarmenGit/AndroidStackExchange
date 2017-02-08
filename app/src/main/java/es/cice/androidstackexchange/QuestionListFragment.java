package es.cice.androidstackexchange;


import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import es.cice.androidstackexchange.database.QuestionOpenHelper;
import es.cice.androidstackexchange.events.NewDataEvent;

/**
 * A simple {@link Fragment} subclass.
 */
public class QuestionListFragment extends ListFragment {
    private static final String TAG=QuestionListFragment.class.getCanonicalName();


    public QuestionListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //creamos adaptador
        //penúltimo parámetro los nombres de las columnas q vamos a usar
        //último los id de los widgets
        SimpleCursorAdapter adapter=new SimpleCursorAdapter(getActivity(),R.layout.question_row,
                null, new String[]{QuestionOpenHelper.OWNER_AVATAR_COLUM,QuestionOpenHelper.QUESTION_TITLE_COLUM}, new int[]{R.id.avatarIV, R.id.questionTV}, 0);
        //ViewBinder es un compnente que define cada uno de widget que debe tratar el adapter
        adapter.setViewBinder(new QuestionViewBinder());
        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    //Método que se realiza cuando se recibe notificación del EvenBus
    //por donde quiero recibir la notificación
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataNotificationEven(NewDataEvent event){
        Log.d(TAG, "getDataNotificationEvent().....");
        Cursor cursor =event.getC();
        ((CursorAdapter) getListView().getAdapter()).swapCursor(cursor);
        ((CursorAdapter) getListView().getAdapter()).notifyDataSetChanged();

    }

    @Override
    public void onStop() {
        super.onStop();
        //El fragmento se desapunta del EventBus
        EventBus.getDefault().unregister(this);


    }

    @Override
    public void onStart() {
        super.onStart();
        //el fragmento se subscribe al bus de eventos para recibir notificación
        //EventBus es una librería externa, la hemos añadido
        EventBus.getDefault().register(this);

    }

    private class QuestionViewBinder implements SimpleCursorAdapter.ViewBinder {
        @Override

        //este método es llamado cada vez que el adaptador debe construir una fila
        //columnIndex es la posición, la fila
        //a este se le llama dos veces uno para el imagen y otro para el textview, la pregunta
        //usamos Picasso q se encarga de cargar en el widget un imagen
        public boolean setViewValue(View view, Cursor cursor, int columnIndex) {
            //distinguimos si el view es la imagenview o el textview, pa usar Picasso o no
            switch (view.getId()){
                case R.id.avatarIV:
                    //with es un método estático, con load le pido iniciar la descarga de la imagen
                    Picasso
                            .with(getActivity())
                            .load(cursor.getString(columnIndex))
                            //pedimos q redimensione la imagen pq no sabemos su tamaño
                            .resize(54,54)
                            .centerCrop()
                    .into((ImageView)view);
                    return true;

                case R.id.questionTV:
                    ((TextView) view).setText(cursor.getString(cursor.getColumnIndex(QuestionOpenHelper.QUESTION_TITLE_COLUM)));
                    return true;
            }
            return false;
        }
    }
}
