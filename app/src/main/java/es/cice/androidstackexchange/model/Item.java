package es.cice.androidstackexchange.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cice on 7/2/17.
 */

public class Item {
    //como no es igual el nombre del atributo que el JSON hacemos lo de Serializedname
    @SerializedName("question_id") public int questionId;
    public String link;
    public String title;

    public Owner owner;

}
