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

import java.io.IOException;
import java.io.InputStream;

public class Request {
    private InputStream inputStream;

    public Request(InputStream inputStream) {
        this.inputStream = inputStream;

        readData();

    }
    public void readData() {
        String requestData = "";
        byte[] requestBytes = new byte[1024];
        int length = 0;
        try {
            if((length = inputStream.read(requestBytes)) > 0) {
                requestData = new String(requestBytes, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(requestData);
    }
}
