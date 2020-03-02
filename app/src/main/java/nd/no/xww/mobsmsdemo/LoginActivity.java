package nd.no.xww.mobsmsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.smssdk.EventHandler;
import cn.smssdk.OnSendMessageHandler;
import cn.smssdk.SMSSDK;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, OnSendMessageHandler {

    private EditText edt_phone;
    private EditText edt_code;
    private Button btn_get_code;
    private Button btn_verify;

    private EventHandler eventHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();

        eventHandler = new EventHandler() {
            public void afterEvent(int event, int result, Object data) {
                if (result == SMSSDK.RESULT_COMPLETE) {
                    // TODO 处理验证成功的结果
                    if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                        //提交验证码成功
                        Log.i("EventHandler", "提交验证码成功");
                        startActivity(new Intent(LoginActivity.this,MainActivity.class));
                    } else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE) {
                        //获取验证码成功
                        Log.i("EventHandler", "获取验证码成功");
                    } else if (event == SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES) {
                        //返回支持发送验证码的国家列表
                        Log.i("EventHandler", "返回支持发送验证码的国家列表");
                    }
                } else {
                    // TODO 处理错误的结果
                    Log.i("EventHandler", "提交验证码失败");
                    ((Throwable) data).printStackTrace();
                }
            }
        };

        //注册 eventHandler
        SMSSDK.registerEventHandler(eventHandler);
    }

    private void initView() {
        edt_phone = (EditText) findViewById(R.id.edt_phone);
        edt_code = (EditText) findViewById(R.id.edt_code);
        btn_get_code = (Button) findViewById(R.id.btn_get_code);
        btn_verify = (Button) findViewById(R.id.btn_verify);

        btn_get_code.setOnClickListener(this);
        btn_verify.setOnClickListener(this);

        Toast.makeText(this, "new branch", Toast.LENGTH_SHORT).show();
        Toast.makeText(this, "new branch", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onClick(View v) {
        String phone = edt_phone.getText().toString().trim();
        switch (v.getId()) {
            case R.id.btn_get_code:
                if (TextUtils.isEmpty(phone)) {
                    return;
                }
                //中国大陆区域 +86
                SMSSDK.getVerificationCode("86", phone, this);// 获取短信验证码
                break;
            case R.id.btn_verify:
                String code = edt_code.getText().toString().trim();
                if (TextUtils.isEmpty(code)) {
                    return;
                }
                SMSSDK.submitVerificationCode("86", phone, code); // 校验短信验证码
                break;
        }
    }


    // 使用完EventHandler需注销，否则可能出现内存泄漏
    @Override
    protected void onDestroy() {
        super.onDestroy();
        SMSSDK.unregisterEventHandler(eventHandler);
    }

    @Override
    public boolean onSendMessage(String country, String phone) {
        /**
         * 此方法在发送验证短信前被调用，传入参数为接收者号码
         * 返回true表示此号码无须实际接收短信
         */
        return false;
    }
}
