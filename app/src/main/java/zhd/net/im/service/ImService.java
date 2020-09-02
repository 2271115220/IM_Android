package zhd.net.im.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import com.example.entity.Model;
import com.example.entity.Register;

import zhd.net.im.client.AndroidClient;

public class ImService extends Service {
    AndroidClient mAndroidClient;

    @Override
    public void onCreate() {
        super.onCreate();
        mAndroidClient = new AndroidClient(this);
        mAndroidClient.start();
    }

    public ImService() {

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }


    @Override
    public void onDestroy() {

    }

    public class MyBinder extends Binder {
        public ImService getService() {
            return ImService.this;
        }
    }

    //注册
    public void register(Register register) {
        mAndroidClient.register(register);
    }

    //发送消息
    public void sendMsg(Model model) {
        if (mAndroidClient != null) {
            mAndroidClient.sendMessage(model);
        }
    }
}
