package cn.morningtec.yesdk.javademo;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import cn.morningtec.yesdk.YeSDK;
import cn.morningtec.yesdk.callback.YeSDKCallback;
import cn.morningtec.yesdk.callback.YeSDKCallbackStatus;
import cn.morningtec.yesdk.callback.YeSDKCallbackType;
import cn.morningtec.yesdk.entity.PayInfo;
import cn.morningtec.yesdk.utils.LogUtils;

public class MainActivity extends Activity
{
    private cn.morningtec.yesdk.callback.YeSDKCallback yeSDKCallback = new cn.morningtec.yesdk.callback.YeSDKCallback( )
    {
        @Override
        public void callback(int actionType, int errorCode, String jsonString)
        {
            LogUtils.OutputDebugString(String.format ("actionType=%d, errorCode=%d, jsonString=%s", actionType, errorCode, jsonString));
            
            if (actionType == YeSDKCallbackType.INIT)
            {
                
            }
            else if (actionType == YeSDKCallbackType.PAY)
            {
                PayInfo payInfo = PayInfo.createWithJsonString(jsonString);
                if (errorCode == YeSDKCallbackStatus.SUCCESS)
                {
                    // 支付成功 - 通知游戏
                }
                else if (errorCode == YeSDKCallbackStatus.CANCEL)
                {
                    // 支付取消 - 通知游戏
                }
                else if (errorCode == YeSDKCallbackStatus.FAIL)
                {
                    // 支付失败 - 通知游戏
                }
            }
            else if (actionType == YeSDKCallbackType.EXIT)
            {
                if (errorCode == YeSDKCallbackStatus.SUCCESS)
                {
                    // 确定退出
                }else
                {
                    // 取消退出
                }
            }
            else if (actionType == YeSDKCallbackType.LOGIN)
            {
                if (errorCode == YeSDKCallbackStatus.SUCCESS)
                {
                    // 登录成功 - 获得登录 token
                    String tokenString = YeSDK.getInstance( ).getUserInfo( ).getToken( );
                }
                else if (errorCode == YeSDKCallbackStatus.FAIL)
                {
                    // 登录失败
                }
            }
        }
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        YeSDK.getInstance().setIsDebug( true );
        YeSDK.getInstance().setActivity(this);
        YeSDK.getInstance( ).getAppInfo( ).setOffline(true);
        YeSDK.getInstance( ).setYeSDKCallback(yeSDKCallback);
        YeSDK.getInstance().init();
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    
    public void ButtonClickLogin(View view)
    {
        YeSDK.getInstance().login();
    }
    
    public void ButtonClickPay(View view)
    {
        // 新建一个支付信息
        // 支付信息必须要有名字(ProductName)
        // 此名称中不能有空格以及特殊字符
        // 可以为全数字或全英文以及英文加数字
        PayInfo payInfo = new PayInfo();
        payInfo.setProductName("001");
        payInfo.setUnitPrice(1); // 设置计费点，单位：分
        payInfo.setCount(1); // 购买的个数
        
        // ... 可以有更多的设置
        
        // 调用 YeSDK 开始支付
        YeSDK.getInstance().pay(payInfo);
    }
    
    public void ButtonClickExit(View view)
    {
        YeSDK.getInstance().exit();
    }
    
    public void ButtonClickMoreGame(View view)
    {
        YeSDK.getInstance().moreGame();
    }
}
