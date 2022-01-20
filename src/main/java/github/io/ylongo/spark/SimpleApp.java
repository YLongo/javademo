package github.io.ylongo.spark;

import org.apache.spark.api.java.function.FilterFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.SparkSession;

public class SimpleApp {

	public static void main(String[] args) {
		
		String logFile = "/Users/YLongo/local/spark-3.2.0-bin-hadoop2.7/README.md";

		final SparkSession spark = SparkSession.builder().appName("SimpleApp").getOrCreate();

		final Dataset<String> logData = spark.read().textFile(logFile).cache();

		long numAs = logData.filter((FilterFunction<String>) s -> s.contains("a")).count();
		final long numBs = logData.filter((FilterFunction<String>) s -> s.contains("b")).count();

		System.out.println("numAs:" + numAs + " = numBs:" + numBs);
		
		spark.stop();

	}
}
