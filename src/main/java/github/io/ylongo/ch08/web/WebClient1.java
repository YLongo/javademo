package github.io.ylongo.ch08.web;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * A WebClient implementation that retrieves the content from a URL.
 */
public class WebClient1 {
    /**
     * A method to retrieve the content from the given URL.
     *
     * @param url
     * @return
     */
    public String getContent(URL url) {
        StringBuffer content = new StringBuffer();

        try {
            // 之所以要把这个方法抽出来，是为了方便测试，
            // 因为URL是final修饰的，所以没办法模拟URL的行为url.openConnection()
            // 但是可以模拟HttpURLConnection的行为
            // 所以抽取一个protected方法，然后在测试类中继承WebClient1，重写这个方法
            // 然后在测试类中，就可以模拟HttpURLConnection的行为了
            HttpURLConnection connection = createHttpURLConnection(url);
            InputStream is = connection.getInputStream();

            int count;
            while (-1 != (count = is.read())) {
                content.append(new String(Character.toChars(count)));
            }
        } catch (IOException e) {
            return null;
        }

        return content.toString();
    }

    /**
     * Creates an HTTP connection.
     *
     * @param url
     * @return
     * @throws IOException
     */
    protected HttpURLConnection createHttpURLConnection(URL url) throws IOException {
        return (HttpURLConnection) url.openConnection();
    }
}