package kreedx.org.provider;

import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import kreedx.org.provider.activity.EditActivity;
import kreedx.org.provider.adapter.InfoAdapter;
import kreedx.org.provider.model.Info;
import kreedx.org.provider.sql.InfoData;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private InfoAdapter infoAdapter;
    private List<Info> infoList;

    private FloatingActionButton floatingActionButton;
    private int requestCode = 100;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        infoList = new ArrayList<>();

        loadDate();
        initRecycleView();

        initButton();
        
        toastSnack(getWindow().getDecorView(),"hello");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(this.requestCode==requestCode){
            loadDate();
            infoAdapter.notifyDataSetChanged();
        }
    }

    private void initButton() {
        floatingActionButton = (FloatingActionButton)findViewById(R.id.floatingActionButton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                startActivityForResult(intent,requestCode);
            }
        });
    }

    private void initRecycleView() {

        recyclerView = (RecyclerView)findViewById(R.id.info_recycler);
        infoAdapter = new InfoAdapter(infoList,getApplicationContext());
        infoAdapter.setOnClickListener(new InfoAdapter.OnClickListener() {
            @Override
            public void OnClick(Info info) {
                //修改内容
                Intent intent = new Intent(MainActivity.this, EditActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("info",info);
                intent.putExtras(bundle);
                startActivityForResult(intent,requestCode);
            }

            @Override
            public void OnLongClick(Info info) {
                // 设置URI
                Uri uri_user = Uri.parse("content://kreedx.org.provider.provider/info");
                // 获取ContentResolver
                ContentResolver resolver =  getContentResolver();
                int num = resolver.delete(uri_user,InfoData.INFO_IDETIFY+"=?",new String[]{info.getId()+""});
                toastSnack(getWindow().getDecorView(),"delete "+ num +"lists");

                loadDate();
                infoAdapter.notifyDataSetChanged();
            }
        });

        recyclerView.setAdapter(infoAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));



    }

    private void loadDate() {

        infoList.clear();
        // 设置URI
        Uri uri_user = Uri.parse("content://kreedx.org.provider.provider/info");

        // 通过ContentResolver 向ContentProvider中查询数据
        Cursor cursor = getContentResolver().query(uri_user, new String[]{InfoData.INFO_IDETIFY,InfoData.INFO_TITLE,InfoData.INFO_PIC}, null, null, null);
        while (cursor.moveToNext()){
            System.out.println("query book:" + cursor.getInt(0) + cursor.getString(1) +" "+ cursor.getString(2));
            // 将表中数据全部输出
            Info info = new Info();
            info.setPic(cursor.getString(2));
            info.setTitle(cursor.getString(1));
            info.setId(cursor.getInt(0));
            infoList.add(info);
        }
        cursor.close();

    }

    void toastSnack(View view,CharSequence text){

        Snackbar.make(view,text,Snackbar.LENGTH_LONG).show();
    }
}
