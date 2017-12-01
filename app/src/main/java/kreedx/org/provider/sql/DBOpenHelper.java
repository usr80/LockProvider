package kreedx.org.provider.sql;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * @author kreedx
 * @since 2017年12月1日 11:19:12
 * Created by Administrator on 2017/12/1.
 */

public class DBOpenHelper extends SQLiteOpenHelper {
    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version,null);
    }

    public DBOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sql = "create table "+InfoData.INFO_TABELNAME
                + "(" + InfoData.INFO_IDETIFY
                + " INTEGER PRIMARY KEY autoincrement,"
                + InfoData.INFO_TITLE + " varchar(20),"
                + InfoData.INFO_PIC + " varchar(20),"
                + InfoData.INFO_DATE + " varchar(20),"
                + InfoData.INFO_VERSION + " INTEGER)" + ";";
        sqLiteDatabase.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
