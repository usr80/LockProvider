package kreedx.org.provider.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import kreedx.org.provider.R;
import kreedx.org.provider.model.Info;
import kreedx.org.provider.sql.InfoData;

/**
 * @author kreedx
 * @since 2017年12月1日 11:07:00
 * Created by Administrator on 2017/12/1.
 */

public class EditActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText edit_title ;
    private ImageView edit_view;
    private Button edit_save;
    private EditText edit_url;

    private int requestCode = 1;
    private boolean edit_flag = false;
    private Info info;

    //private String picpath = "https://b-ssl.duitang.com/uploads/item/201711/11/20171111070410_kcGEA.thumb.700_0.jpeg";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_edit);
        edit_save = (Button)findViewById(R.id.button_save);
        edit_title =(EditText)findViewById(R.id.edit_title);
        edit_view = (ImageView)findViewById(R.id.edit_view);
        edit_url = (EditText)findViewById(R.id.edit_url);
        Bundle bundle = getIntent().getExtras();
        if(bundle!=null){
            info = bundle.getParcelable("info");
            edit_url.setText(info.getPic());
            edit_title.setText(info.getTitle());
            edit_flag = true;
        }


        edit_save.setOnClickListener(this);
        edit_view.setOnClickListener(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==this.requestCode){
            Uri uri = data.getData();
            if(uri!=null){
                edit_view.setImageURI(uri);

                String[] proj = {MediaStore.Images.Media.DATA};
                //好像是android多媒体数据库的封装接口，具体的看Android文档
                Cursor cursor = getContentResolver().query(uri, proj, null, null, null);
                //按我个人理解 这个是获得用户选择的图片的索引值
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                //将光标移至开头 ，这个很重要，不小心很容易引起越界
                cursor.moveToFirst();
                //最后根据索引值获取图片路径
                String path = cursor.getString(column_index);
                edit_url.setText(path);
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button_save:
                saveInfo(view);
                break;
            case R.id.edit_view:
                chooseImage();
                break;
            default:
                break;
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, requestCode);
    }

    private void saveInfo(View view) {
        // 设置URI
        Uri uri_user = Uri.parse("content://kreedx.org.provider.provider/info");

        // 获取ContentResolver
        ContentResolver resolver =  getContentResolver();


        if(edit_flag){
            // 通过ContentResolver 根据URI 向ContentProvider中插入数据
            ContentValues values = new ContentValues();
            values.put(InfoData.INFO_PIC,edit_url.getText().toString());
            values.put(InfoData.INFO_TITLE,edit_title.getText().toString());
            values.put(InfoData.INFO_VERSION,0);
            int num = resolver.update(uri_user,values,InfoData.INFO_IDETIFY+"=?",new String[]{info.getId()+""});
            Snackbar.make(view,"update "+num+"lists",Snackbar.LENGTH_SHORT).show();
        }else{
            // 通过ContentResolver 根据URI 向ContentProvider中插入数据
            ContentValues values = new ContentValues();
            values.put(InfoData.INFO_PIC,edit_url.getText().toString());
            values.put(InfoData.INFO_TITLE,edit_title.getText().toString());
            values.put(InfoData.INFO_VERSION,0);
            resolver.insert(uri_user,values);
            Snackbar.make(view,"insert into lists",Snackbar.LENGTH_SHORT).show();
        }
    }
}
