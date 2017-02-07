package es.cice.androidstackexchange;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.SimpleCursorAdapter;

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
        CursorAdapter adapter=new SimpleCursorAdapter(getActivity(),R.layout.question_row,
                null, new String[]{QuestionOpenHelper.OWNER_AVATAR_COLUM,QuestionOpenHelper.QUESTION_TITLE_COLUM}, new int[]{R.id.avatarIV, R.id.questionTV}, 0);

        setListAdapter(adapter);

        return super.onCreateView(inflater, container, savedInstanceState);

    }

    //Método que se realiza cuando se recibe notificación del EvenBus
    //por donde quiero recibir la notificación
    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getDataNotificationEven(NewDataEvent event){
        Log.d(TAG, "getDataNotificationEvent().....");

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

}
