/*
 * Copyright 2020-2030 com.github.Jayying007
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MainServer {
    private int port = 8080;
    private final Logger logger = LoggerFactory.getLogger(MainServer.class);
    private ServerSocket serverSocket;
    private Request request;
    private Response response;
    private ExecutorService fixedThreadPool;

    public MainServer() {
        try {
            serverSocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        fixedThreadPool = Executors.newFixedThreadPool(4);
    }
    public MainServer(int port) {
        this();
        this.port = port;
    }
    public void start() {
        try {
            while (true) {
                logger.info("启动成功，监听端口 " + this.port);
                final Socket socket = serverSocket.accept();//调用该方法后进程会阻塞，直到有主机连接

                fixedThreadPool.submit(new Runnable() {
                    public void run() {
                        try {
                            System.out.println(Thread.currentThread().getName());
                            handler(socket);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void handler(Socket socket) throws IOException {
        logger.info(String.format("接收新的服务： %s %d", socket.getInetAddress(), socket.getPort()));

        this.request = new Request(socket.getInputStream());
        this.response = new Response(socket.getOutputStream());

        try {
            //every request cost
            TimeUnit.MILLISECONDS.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        OutputStream outputStream = response.getOutputStream();

        String responseData = "HTTP/1.1 200 OK\n" +
                "Content-Type: text/html\n" +
                "\r\n" +
                "<html><body>" +
                "Hello world" +
                "</body></html>";
        outputStream.write(responseData.getBytes());

        socket.close();
    }
}
