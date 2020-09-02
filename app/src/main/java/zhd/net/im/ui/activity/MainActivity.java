package zhd.net.im.ui.activity;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.entity.Register;

import butterknife.BindView;
import butterknife.ButterKnife;
import rjw.net.baselibrary.mvp.BaseMvpActivity;
import zhd.net.im.R;
import zhd.net.im.service.ImService;
import zhd.net.im.ui.iface.MainActivityIFace;
import zhd.net.im.ui.presenter.MainActivityPresenter;

public class MainActivity extends BaseMvpActivity<MainActivityPresenter> implements MainActivityIFace, View.OnClickListener {

    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.et_login_name)
    EditText mEtLoginName;
    @BindView(R.id.et_login_pwd)
    EditText mEtLoginPwd;

    int registerID;

    private MyServiceConnection mMyServiceConnection;

    private ImService mService;

    @Override
    protected MainActivityPresenter getPresenter() {
        return new MainActivityPresenter();
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

    @Override
    public void initData() {

    }

    @Override
    public void setListener() {
        mBtnLogin.setOnClickListener(this);
    }

    @Override
    public void getData() {
        startService();
    }

    public void startService() {

        mMyServiceConnection = new MyServiceConnection();

        Intent intent = new Intent(this, ImService.class);

        bindService(intent, mMyServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                Register register = new Register();
//                registerID = Integer.parseInt(mEtLoginName.getText().toString().trim());
                registerID = 1;
                register.setUserId(registerID);
                mService.register(register);
                break;
        }
    }

    public class MyServiceConnection implements ServiceConnection {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = ((ImService.MyBinder) service).getService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    }
}