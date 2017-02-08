package es.cice.androidstackexchange.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

import es.cice.androidstackexchange.model.Item;

/**
 * Created by cice on 7/2/17.
 */

public class QuestionOpenHelper extends SQLiteOpenHelper{
    public static final String OWNER_AVATAR_COLUM="PROFILE_IMAGE";
    public static final String QUESTION_TITLE_COLUM="TITLE";
    public static final String QUESTION_ID_COLUM="QUESTION_ID";
    public static final String QUESTION_LINK_COLUM="LINK";
    public static final String OWNER_ID_COLUM="USER_ID";
    public static final String QUESTION_TABLE= "QUESTIONS";


    public static final String QUESTIO_DB="questionsDB";
    public static final int VERSION=1;
    private static QuestionOpenHelper qoh;
    private static Context ctx;

    private QuestionOpenHelper(Context context) {
        super(context, QUESTIO_DB, null, VERSION);
    }

    public static QuestionOpenHelper getInstance(Context ctx){
        //solo se admite un ejemplar en qoh, es un singleton
        if(qoh==null){

            qoh = new QuestionOpenHelper(ctx);
            QuestionOpenHelper.ctx=ctx;
        }
        return qoh;
    }

    public Cursor insert(List<Item> questions){
        String sql="insert into " + QUESTION_TABLE + "(" + QUESTION_ID_COLUM + "," +
                QUESTION_TITLE_COLUM + "," + QUESTION_LINK_COLUM + "," +
                OWNER_ID_COLUM + "," + OWNER_AVATAR_COLUM + ") VALUES(?,?,?,?,?)";
        SQLiteDatabase db= qoh.getWritableDatabase();
        db.beginTransaction();


        for(Item item:questions){

            db.execSQL(sql,new Object[]{item.questionId, item.title, item.link, item.owner.userId, item.owner.profileImage});

        }
        //marcar la transacción como exitosa
        db.setTransactionSuccessful();
        db.endTransaction();
        return db.query(QUESTION_TABLE,new String[] {"_id",QUESTION_TITLE_COLUM, OWNER_AVATAR_COLUM}, null, null, null, null, null);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="CREATE TABLE QUESTIONS(_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                QUESTION_ID_COLUM + " INTEGER, " + QUESTION_TITLE_COLUM + " TEXT," +
                QUESTION_LINK_COLUM + " TEXT," + OWNER_ID_COLUM + " INTEGER," +
                OWNER_AVATAR_COLUM + " INTEGER)";
        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        throw new RuntimeException("No se admiten actualizaciones de esta B.D.");

    }
}



