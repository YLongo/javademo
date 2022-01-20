package github.io.ylongo.spark.streaming;

import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

public class NetworkWordCount {

    public static void main(String[] args) {

        SparkConf sparkConf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount");

        JavaStreamingContext context = new JavaStreamingContext(sparkConf, Durations.seconds(1));
        JavaReceiverInputDStream<String> lines = context.socketTextStream("localhost", 9999);

    }
}
