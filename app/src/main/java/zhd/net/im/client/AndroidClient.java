package zhd.net.im.client;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.client.Client;
import com.example.client.base.BaseAbstractClient;
import com.example.entity.Model;
import com.example.entity.ServerProp;
import com.example.listener.MsgListener;

import zhd.net.im.iface.Constants;

/**
 * @author zhd: 好好写
 * @date 2020/9/2 10:33
 * @desc
 */
public class AndroidClient extends BaseAbstractClient implements MsgListener {

    private Context mContext;

    public AndroidClient(Context context) {
        this.mContext = context;
    }

    @Override
    public MsgListener getListener() {
        return new MsgListener() {
            @Override
            public void receiveMsg(Model model) {
                Log.d("zhd", "receiveMsg: ");
            }
        };
    }

    @Override
    public ServerProp getHostAndPort() {
        ServerProp serverProp = new ServerProp();
        serverProp.setHost(Constants.address);
        serverProp.setPort(Constants.port);
        return serverProp;
    }

    @Override
    public void receiveMsg(Model model) {
        Toast.makeText(mContext, "" + model.getBody(), Toast.LENGTH_SHORT).show();
    }
}
