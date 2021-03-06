package es.cice.androidstackexchange;


import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

import es.cice.androidstackexchange.database.QuestionOpenHelper;
import es.cice.androidstackexchange.events.NewDataEvent;
import es.cice.androidstackexchange.model.QuestionGroup;
import es.cice.androidstackexchange.retrofitresources.QuestionCall;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */

//Fragmento encargado de obtener los datos
    //En un hilo de java
    //QuestionCall es el servicio que obtienen datos de tipo qeestiongroup
    //QuestionGroup esta modelado para tener los datos que necesito
    //
public class ModelFragment extends Fragment {


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //el fragmento se activa si hay alguna actividad que decide usarlo
        super.onActivityCreated(savedInstanceState);
        //lanzamos hilo
        new ModelLoadThread().start();
    }

    public ModelFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

    public class ModelLoadThread extends Thread{
        public void run(){
            Retrofit retrofit=new Retrofit.Builder().
                    baseUrl("https://api.stackexchange.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            //
            QuestionCall service=retrofit.create(QuestionCall.class);
            try {
                //recuperamos ls items
                QuestionGroup qg=service.getQuestionsCall("android").execute().body();
                //los guardo en BD
                QuestionOpenHelper qoh= QuestionOpenHelper.getInstance(getActivity());
                //inertamos los datos en la bd
                Cursor c=qoh.insert(qg.items);
                //notificar cursos mediante un evento
                EventBus.getDefault().postSticky(new NewDataEvent(c));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

}
