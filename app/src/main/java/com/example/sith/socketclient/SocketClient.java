package com.example.sith.socketclient;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.UnknownHostException;

import com.example.sith.socketclient.R;
//import com.example.sith.socketclient.SocketClient.outputerlistenr;
//import com.example.sith.socketclient.SocketClient.receiverlistenr;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.os.Build;
/***
 * 注意，以下采用两种方法实现添加线程
 * @author Administrator
 *
 */
public class SocketClient extends Activity implements OnClickListener, Runnable {// //implements Runnable
    private Button btnRev = null;
    private Button btnOut = null;
    private Button links = null;

    private TextView revtext = null;
    private EditText outText = null;
    private EditText displayText = null;
    private TextView con = null;

    private String str;
    private DataInputStream inRec = null;
    private DataOutputStream output = null;
    private InetAddress serverAddr;
    private Thread th1, th2, th;
    private Thread link2s;
    private Socket mSocket;
    private MulticastSocket ms;
    private int port = 5000;
    private DatagramSocket socket = null;


    //	public final boolean sendMessage (Message msg);
//	public void handleMessage (Message msg);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket_client);

        btnRev = (Button) findViewById(R.id.rev);
        btnOut = (Button) findViewById(R.id.out);
        links = (Button) findViewById(R.id.link);
        outText = (EditText) findViewById(R.id.show);
        displayText = (EditText) findViewById(R.id.display);
        revtext = (TextView) findViewById(R.id.receiver);
        con = (TextView) findViewById(R.id.outputer);

        /*
         * 注意两种按键监听的写法，一种需要执行OnClickListener接口，
         * btnOut.setOnClickListener(this);
         *并且重写 @Override
		 *public void onClick(View v)
		 *一种：rev.setOnClickListener(new receiverlistenr());需要写内部类：
		 * class receiverlistenr implements OnClickListener{
	     *	public void onClick(View v) {   ;}} 
		 *
         */
        //rev.setOnClickListener(new receiverlistenr());
        //out.setOnClickListener(new outputerlistenr());

        btnRev.setOnClickListener(this);
        btnOut.setOnClickListener(this);
        links.setOnClickListener(this);
		/*if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar   
        getMenuInflater().inflate(R.menu.menu_socket_client, menu);
        return true;
    }

    /*
        class receiverlistenr implements OnClickListener{
            public void onClick(View v) {       // TODO Auto-generated method stub
                Log.v(str, "接收操作：OnClickListener");
                th2 = new Thread(new SocketInRun());
                th2.start();


            }

        }

        class outputerlistenr implements OnClickListener{
            public void onClick(View v) {       // TODO Auto-generated method stub
                Log.v(str, "进入发送操作：OnClickListener");
                th1 = new Thread(new SocketOutRun());
                th1.start();
            }


        }
        class SocketInRun implements Runnable {
            @Override
            public void run(){
                try {
                    //实例化Socket
                    Socket socket2 = null;

                    serverAddr = InetAddress.getByName("192.168.1.103");
                    socket2=new Socket("192.168.1.103",12345);
                    output = new DataOutputStream(socket2.getOutputStream());
                    output.writeUTF("发送");
                    output.flush();
                    //获得输入流
                    inRec=new DataInputStream(socket2.getInputStream());
                    String msg="";
                    str=msg = inRec.readUTF();
                    Log.v(str,"接收到的圆面积是：msg"+ str);
                    //设置文本框的字符串
                    //displayText.setText(msg);
                    //Main.revtext.setText(msg); //子线程内不能设置控件，否则程序崩溃

                    Log.v(msg,"接收到的圆面积是："+msg);
                    inRec.close();
                    socket2.close();
                }
                catch (UnknownHostException e) {     // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e) {     // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            }
        }
        class SocketOutRun implements Runnable {
            @Override
            public void run(){
                try {
                    //实例化Socket

                    Socket socket1 = null;
                    serverAddr = InetAddress.getByName("192.168.1.103");
                    Log.v(str, "进入发送操作1");
                    socket1=new Socket("192.168.1.103",12345);
                    Log.v(str, "进入发送操作2");224.0  .0  .1
                    String msg = outText.getText().toString();
                    output = new DataOutputStream(socket1.getOutputStream());
                    output.writeUTF("接受");
                    output.flush();
                    output.writeUTF(msg);
                    output.flush();
                    output.close();
                    socket1.close();
                }
                catch (UnknownHostException e) {     // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                catch (IOException e) {     // TODO Auto-generated catch block
                    e.printStackTrace();
                }

            } //catch(Exception e){}

        }
    */
    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

        if (v == links) {
            Log.v(str, "连接服务器：onClick");
            link2s = new Thread(this);
            link2s.start();
        }
        if (v == btnRev) {
            Log.v(str, "接收操作：onClick");
            //th2 = new Thread(new SocketInRun());
            th2 = new Thread(this);//线程实例化
            th2.start();//线程启动

            //displayText.setText(str);
            //revtext.setText(str);
        }
        if (v == btnOut) {
            Log.v(str, "进入发送操作：onClick");
            //th1 = new Thread(new SocketOutRun());
            th1 = new Thread(this);//线程实例化
            th1.start();//线程启动
        }

    }

    @Override
    public void run() {
        // TODO Auto-generated method stubthis
        th = new Thread(this);
        if (th.currentThread() == th1) {//判断当前执行的是哪个线程，如果是线程1，操作发送程序
/*            Log.v(str, "1");
            try {
                //实例化Socket

                Socket socket1 = null;
                serverAddr = InetAddress.getByName("192.168.1.port");
                Log.v(str, "进入发送操作1：run");
                socket1 = new Socket("192.168.1.103", port);
                Log.v(str, "进入发送操作2：run ");
                String msg = outText.getText().toString();
                output = new DataOutputStream(socket1.getOutputStream());//实例化输出流
                output.writeUTF("接受");//写数据
                output.flush();//
                output.writeUTF(msg);
                output.flush();
                output.close();
                socket1.close();

            } catch (UnknownHostException e) {     // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {     // TODO Auto-generated catch block
                e.printStackTrace();
            }
            */
            if (ConnectServer() == true)
                runsend();

        }
        if (th.currentThread() == th2) {//如果是进程2则操作下面接收到数据
            Log.v(str, "2");
            try {
                //实例化Socket
                Socket socket2 = null;
                Message msgg = new Message();
                msgg.what = 100;
                this.mHandler.sendMessage(msgg);
                String msg = "";

                serverAddr = InetAddress.getByName("192.168.1.103");
                socket2 = new Socket("192.168.1.103", port);//设置机器所用的网络IP，要与PC机保持一致，端口自定义
                output = new DataOutputStream(socket2.getOutputStream());
                output.writeUTF("发送");
                output.flush();
                //获得输入流
                inRec = new DataInputStream(socket2.getInputStream());

                str = msg = inRec.readUTF();//读数据
                // msg = inRec.readUTF();
                //               Message msgg = new Message();
                msgg.what = 100;
                this.mHandler.sendMessage(msgg);

                Log.v(msg, "接收到的圆面积是：" + msg);
                inRec.close();
                socket2.close();
            } catch (UnknownHostException e) {     // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IOException e) {     // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
        if (th.currentThread() == link2s) {


            try {
            /*创建socket实例,准备发送广播包*/
                ms = new MulticastSocket(40000);
                //ms = new MulticastSocket(50000);
                Log.v(str, "试图注册多播服务：success");
            } catch (Exception e) {
                e.printStackTrace();
                Log.v(str, "试图注册多播服务：error");
            }


            DatagramPacket dataPacket = null;           //发送接口
            byte buf[] = new byte[1024];
            DatagramPacket dp = new DatagramPacket(buf, 1024);  //接收接口
            int m = 0;
            String ACK = new String("hello");        //设置ACK的内容

            while (m < 3) {
                //发送的数据包，局网内的所有地址都可以收到该数据包
                try {
                    ms.setTimeToLive(2);
                    //将本机的IP（这里可以写动态获取的IP）地址放到数据包里，其实server端接收到数据包后也能获取到发包方的IP的
                    byte[] data = "192.168.1.101".getBytes();
                    //224.0.0.1为广播地址
                    InetAddress address = InetAddress.getByName("224.0.0.1");
                    //这个地方可以输出判断该地址是不是广播类型的地址

                    dataPacket = new DatagramPacket(data, data.length, address, port);
                    ms.send(dataPacket);
                    Log.v(str, "成功发送封包：" + ms.getLocalPort());

                } catch (Exception e) {
                    e.printStackTrace();
                }


                try {
                    ms.setSoTimeout(3000);          //设置超时3000ms
                    ms.receive(dp);
                    //Toast.makeText(this, new String(buf, 0, dp.getLength()), Toast.LENGTH_LONG);
                    Log.v(str, "成功收到封包：UDP MUL");
                    String getback = new String(buf, 0,
 dp.getLength());
                    Log.v(getback, "client ip : " + getback);
                    if (ACK.equals(getback)) {                   //比较是否获得想要的参数
                        Log.v(str, "匹配成功：UDP MUL");
                        str = "link to server!";
                        sendmesg(200);

                        serverAddr = dp.getAddress();

                        ms.close();
                        break;
                    }
                    m++;
                } catch (java.net.SocketTimeoutException ie) {
                    Log.v(str, "没有收到封包：UDP MUL");
                    m++;
                } catch (Exception e) {
                    Log.v(str, "收到封包error：");
                    //                 e.printStackTrace();
                }


            }
            if (m >= 3) {
                Log.v(str, "连接服务器失败：UDP MUL");
                str = "failed at link to server!";
                sendmesg(200);
            }
            ms.close();


        }

    }

    private void sendmesg(int n) {


        Message news = new Message();
        news.what = n;
        this.mHandler.sendMessage(news);//(news);
    }

    /*
     * 注意控件的设置不能在子线程中操作，需要在主线程中。123456
     * 需要子线程中参数来改变控件的请注意以下方法
     *
     */
    private Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 100:
                    con.setText(str);

                    break;
                case 200:
                    revtext.setText(str);
                    break;
            }
        }
    };


    boolean ConnectServer() {
        try {
            socket = new DatagramSocket(50000);
           // socket = new DatagramSocket();

        } catch (Exception e) {
            Log.v(str, "UDP socket：fail...");
            //                 e.printStackTrace();
            str = "没有UDP socket：fail...";
            sendmesg(100);
            return false;


        }
        Log.v(str, "UDP socket：success...");
        //          th = new Thread(this);
        //          th.start();
        return true;
    }

    public void runsend() {
        byte[] buf, buf2;
        int ACK = 0x123456;
        if (!outText.getText().toString().isEmpty()) {
            buf = outText.getText().toString().getBytes();

        } else {
            buf = ("Default message").getBytes();
        }

        Log.v(str, "UDP packet：success..." + serverAddr + port);
        DatagramPacket packet=null;

        try{
            // DatagramPacket packet = new DatagramPacket(buf, buf.length,serverAddr, port);
            InetAddress address = InetAddress.getByName("192.168.1.200");
            packet = new DatagramPacket(buf, buf.length,address, port);
            Log.v(str, "UDP packet：success..." + serverAddr + port);
        }catch (Exception e)
        {

        }

        try {
            socket.send(packet);
        } catch (Exception e) {
            Log.v(str, "UDP send：fail...");
            //                 e.printStackTrace();
            str = "UDP send：fail...";
            sendmesg(100);
        }
        Log.v(str, "UDP packet：send..." + serverAddr + port);
        try {
            socket.setSoTimeout(5000);
            socket.receive(packet);
            Log.v(str, "收到UDP封包：UDP ");

        } catch (java.net.SocketTimeoutException ie) {
            Log.v(str, "没有收到封包：UDP MUL");
        } catch (Exception e) {
            Log.v(str, "收到UDP封包：UDP err");

        }
        Log.v(str, "成功收到封包：UDP ");
        //           int[] getback = new int[packet.getLength()];
        buf2 = packet.getData();

        str = buf2.toString();
       Log.v(str, "client ip : "+buf2);
        if (buf2.equals(ACK)) {                   //比较是否获得想要的参数
            Log.v(str, "匹配成功：UDP MUL");
            str = "get the ACK!";
            sendmesg(100);


 //           ms.close();


            socket.close();


        }


    }
}

	
	    