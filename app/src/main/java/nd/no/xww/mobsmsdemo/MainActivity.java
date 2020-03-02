package nd.no.xww.mobsmsdemo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

/**
 * @author xww
 * @desciption :
 * @date 2019/11/29
 * @time 19:07
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toast.makeText(this, "new branch", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "new branch", Toast.LENGTH_SHORT).show();
    }
}
