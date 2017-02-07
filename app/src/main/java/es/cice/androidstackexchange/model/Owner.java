package es.cice.androidstackexchange.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by cice on 7/2/17.
 */

public class Owner {
    @SerializedName("user_id") public int userId;
    @SerializedName("profile_image") public String profileImage;
}
