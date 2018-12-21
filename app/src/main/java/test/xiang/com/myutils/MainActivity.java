package test.xiang.com.myutils;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xiang.myutils.file.FileUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FileUtil.T(this,"hello");
    }
}
