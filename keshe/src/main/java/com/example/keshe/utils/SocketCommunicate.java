package com.example.keshe.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


@Component
public class SocketCommunicate {
    //Socket通信的端口
    private static int portNo = 28003;

    //Socket通信的ip地址
    //这个在云服务器的内网地址！！！不是外网地址，切记！
    private static String ipAddress = "172.25.201.218"; //"172.23.2.105";

    private static ServerSocket ss = null;
    private static Socket s = null;


    private static String msg;
    private static String p;
    private static String i;
    private static String d;
    private static String mp;
    private static String mi;
    private static String md;
    private static String inp;
    private static String ini;
    private static String ind;


    //用来控制子线程的关闭
    private static boolean flag = false;

    Lock lock = new ReentrantLock();

    @Autowired
    Helper helper;


    public void sendPID(String P, String I, String D) {
        SocketCommunicate.p = P;
        SocketCommunicate.i = I;
        SocketCommunicate.d = D;
    }

    public void sendMPID(String MP, String MI, String MD) {
        SocketCommunicate.mp = MP;
        SocketCommunicate.mi = MI;
        SocketCommunicate.md = MD;
    }

    public void sendINPID(String INP, String INI, String IND) {
        SocketCommunicate.inp = INP;
        SocketCommunicate.ini = INI;
        SocketCommunicate.ind = IND;
    }

    public void sendMsg(String msg) {
        SocketCommunicate.msg = msg;
    }

    public void sendFlag(){
        SocketCommunicate.flag = true;
    }

    public void sendFlagFalse(){
        SocketCommunicate.flag = false;
    }

    public void close(){
        try {
            if (ss != null) {
                ss.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            if (s != null) {
                s.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void communicate() {
        try {
            ss = new ServerSocket(portNo, 100, InetAddress.getByName(ipAddress));
            s = ss.accept();

            if(s != null){
                DataInputStream din = new DataInputStream(s.getInputStream());
                PrintWriter out = new PrintWriter(
                        new BufferedWriter(new OutputStreamWriter(s.getOutputStream())),true);

                SendThread it = new SendThread(out);
                PrintThread ot = new PrintThread(din);

                new Thread(ot).start();
                new Thread(it).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                if (ss != null) {
                    ss.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            try {
                if (s != null) {
                    s.close();
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }

    }

    class SendThread implements Runnable {

        private PrintWriter dout;

        public SendThread(PrintWriter dout) {
            super();
            this.dout = dout;
        }

        @Override
        public void run() {
            System.out.println("准备发送数据：");
            while (true) {
                if (flag){
                    //flag = false;
                    break;
                }
                lock.lock();
                try{
                    if (p != null && i != null && d != null) {
                        dout.println("P=" + p + "<");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dout.println("I=" + i + "<");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dout.println("D=" + d + "<");
                        System.out.println("发送PID到matlab成功！！！");
                        sendPID(null,null,null);
                    }

                    if (mp != null && mi != null && md != null) {
                        dout.println("MP=" + mp + "<");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dout.println("MI=" + mi + "<");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dout.println("MD=" + md + "<");
                        System.out.println("发送MPID到matlab成功！！！");
                        sendMPID(null,null,null);
                    }

                    if (inp != null && ini != null && ind != null) {
                        dout.println("IN_P=" + inp + "<");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dout.println("IN_I=" + ini + "<");
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        dout.println("IN_D=" + ind + "<");
                        System.out.println("发送INPID到matlab成功！！！");
                        sendINPID(null,null,null);
                    }

                    if (msg != null) {
                        dout.println(msg);
                        System.out.println("发送"+msg+"到matlab成功！！！");
                        Thread.sleep(100);
                        sendMsg(null);
                    }
                }catch (Exception e){
                    System.out.println("lock error!!!");
                }finally {
                    lock.unlock();
                }
            }
            System.out.println("线程结束。。。。。。。");
        }
    }


    class PrintThread implements Runnable {

        private DataInputStream din;

        public PrintThread(DataInputStream din) {
            super();
            this.din = din;
        }

        @Override
        public void run() {     // 打开 socket后才会运行！
            while (true) {
                String msg = null;
                try {
                    msg = din.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(msg);
                //如果收到byebye则退出循环
                if (msg.equals("query all")) {
                    break;
                }
                helper.help(msg);
            }
        }
    }
}
