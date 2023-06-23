package github.io.ylongo.ch07;

/**
 * A sample test case that demonstrates how to stub an HTTP server using Jetty as an embedded server.
 *
 * @version $Id$
 */
//public class TestWebClient {
//
//    private WebClient client = new WebClient();
//
//    @BeforeAll
//    public static void setUp() throws Exception {
//        
//        // 模拟一个http server
//        Server server = new Server(8081);
//
//        // 模拟接口
//        Context contentOkContext = new Context(server, "/testGetContentOk");
//        // 模拟接口处理请求
//        contentOkContext.setHandler(new TestGetContentOkHandler());
//
//        Context contentErrorContext = new Context(server, "/testGetContentError");
//        contentErrorContext.setHandler(new TestGetContentServerErrorHandler());
//
//        Context contentNotFoundContext = new Context(server, "/testGetContentNotFound");
//        contentNotFoundContext.setHandler(new TestGetContentNotFoundHandler());
//
//        server.setStopAtShutdown(true);
//        server.start();
//    }
//
//    @AfterAll
//    public static void tearDown() {
//        // Empty
//    }
//
//    @Test
//    public void testGetContentOk() throws MalformedURLException {
//        String workingContent = client.getContent(new URL("http://localhost:8081/testGetContentOk"));
//        assertEquals("It works", workingContent);
//    }
//
//    /**
//     * Handler to handle the good requests to the server.
//     */
//    private static class TestGetContentOkHandler extends AbstractHandler {
//        public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException {
//
//            OutputStream out = response.getOutputStream();
//            ByteArrayISO8859Writer writer = new ByteArrayISO8859Writer();
//            writer.write("It works");
//            writer.flush();
//            response.setIntHeader(HttpHeaders.CONTENT_LENGTH, writer.size());
//            writer.writeTo(out);
//            out.flush();
//        }
//    }
//
//    /**
//     * Handler to handle bad requests to the server
//     */
//    private static class TestGetContentServerErrorHandler extends AbstractHandler {
//
//        public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException {
//            response.sendError(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
//        }
//    }
//
//    /**
//     * Handler to handle requests that request unavailable content.
//     */
//    private static class TestGetContentNotFoundHandler extends AbstractHandler {
//
//        public void handle(String target, HttpServletRequest request, HttpServletResponse response, int dispatch) throws IOException {
//            response.sendError(HttpServletResponse.SC_NOT_FOUND);
//        }
//    }
//}