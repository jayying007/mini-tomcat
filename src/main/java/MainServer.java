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
import java.util.concurrent.TimeUnit;

public class MainServer {
    private int port = 8080;
    private final Logger logger = LoggerFactory.getLogger(MainServer.class);

    public MainServer() {
        ServerSocket serverSocket = null;

        try {
            serverSocket = new ServerSocket(this.port);
            while (true) {
                logger.info("准备提供服务...");
                Socket socket = serverSocket.accept();//调用该方法后进程会阻塞，直到有主机连接
                logger.info(String.format("接收新的服务： %s %d", socket.getInetAddress(), socket.getPort()));

                InputStream inputStream = socket.getInputStream();

                String requestData = "";
                byte[] requestBytes = new byte[1024];
                int length = 0;
                if((length = inputStream.read(requestBytes)) > 0) {
                    requestData = new String(requestBytes, 0, length);
                }
                System.out.println(requestData);


                StringBuilder responseData = new StringBuilder();
                responseData.append("HTTP/1.1 200 OK\n")
                        .append("Content-Type: text/html\n")
                        .append("\r\n")
                        .append("<html><body>")
                        .append("Hello world")
                        .append("</body></html>");
                try {
                    //every request cost
                    TimeUnit.MILLISECONDS.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                OutputStream outputStream = socket.getOutputStream();
                outputStream.write(responseData.toString().getBytes());
                outputStream.close();

                socket.close();
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
}
