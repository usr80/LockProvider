package kreedx.org.provider.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import kreedx.org.provider.model.Info;
import kreedx.org.provider.sql.DBOpenHelper;
import kreedx.org.provider.sql.InfoData;

/**
 * @author kreedx
 * @since 2017年12月1日 11:26:38
 * Created by Administrator on 2017/12/1.
 */

public class InfoProvider extends ContentProvider {

    private static int CONTACT = 1;
    private DBOpenHelper dbOpenHelper;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
    static {
        uriMatcher.addURI("kreedx.org.provider.provider","info",CONTACT);
    }
    @Override
    public boolean onCreate() {
        dbOpenHelper = new DBOpenHelper(getContext(),InfoData.INFO_DATENAME,null,4);
        //dbOpenHelper.getWritableDatabase().execSQL("insert into "+InfoData.INFO_TABELNAME+" values(null,'du','https://b-ssl.duitang.com/uploads/item/201711/26/20171126001809_f82Yc.thumb.700_0.jpeg','01','01')");
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Cursor cursor = null;
        if(uriMatcher.match(uri)==CONTACT){
            SQLiteDatabase database = dbOpenHelper.getReadableDatabase();
            cursor = database.query(InfoData.INFO_TABELNAME, projection, selection, selectionArgs, null, null, sortOrder);
            cursor.setNotificationUri(getContext().getContentResolver(),uri);
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues contentValues) {
        Uri uri1 = null;
        if(uriMatcher.match(uri)==CONTACT){

            SQLiteDatabase sqLiteDatabase = dbOpenHelper.getReadableDatabase();
            long id = sqLiteDatabase.insert(InfoData.INFO_TABELNAME,"_id",contentValues);
            uri1 = ContentUris.withAppendedId(uri,id);
            getContext().getContentResolver().notifyChange(uri1,null);
        }

        return uri1;
    }

    /**
     *
     * @param uri
     * @param s 表达 name=?
     * @param strings ?的内容
     * @return
     */
    @Override
    public int delete(@NonNull Uri uri, @Nullable String s, @Nullable String[] strings) {
        int num =0 ;
        if(uriMatcher.match(uri)==CONTACT){
            SQLiteDatabase sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            num = sqLiteDatabase.delete(InfoData.INFO_TABELNAME,s,strings);

        }
        return num;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues contentValues, @Nullable String s, @Nullable String[] strings) {
        int num =0 ;
        if(uriMatcher.match(uri)==CONTACT){
            SQLiteDatabase sqLiteDatabase = dbOpenHelper.getWritableDatabase();
            num = sqLiteDatabase.update(InfoData.INFO_TABELNAME,contentValues,s,strings);

        }
        return num;
    }
}
