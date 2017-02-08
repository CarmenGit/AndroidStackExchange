package es.cice.androidstackexchange.retrofitresources;

import es.cice.androidstackexchange.model.QuestionGroup;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by cice on 7/2/17.
 */

public interface QuestionCall {
    //para obtener el listado de preguntas

    //podríamos añadir filtro en la dir web con &tagged=android
    @GET ("/2.1/questions?order=desc&sort=creation&site=stackoverflow")
    public Call<QuestionGroup> getQuestionsCall(@Query("tagged") String filtro);
}
