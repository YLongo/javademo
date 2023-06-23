package github.io.ylongo.ch08.web;

import java.io.InputStream;

public class WebClient2 {

    /**
     * 数据的来源可以是HTTP，ftp等，所以抽象出来一个接口获取数据，具体怎么获取数据由实现类决定
     * 这样处理之后，更加方便测试
     */
    public String getContent(ConnectionFactory connectionFactory) {
        StringBuffer content = new StringBuffer();
        try {
            InputStream is = connectionFactory.getData();
            int count;
            while (-1 != (count = is.read())) {
                content.append(new String(Character.toChars(count)));
            }
        } catch (Exception e) {
            return null;
        }
        return content.toString();
    }
}
