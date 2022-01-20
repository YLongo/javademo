package github.io.ylongo.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.util.LongAccumulator;
import scala.Tuple2;

import java.net.URL;
import java.util.Arrays;
import java.util.List;

public class RDDProgramGuide {

	public static void main(String[] args) {

		final SparkConf sparkConf = new SparkConf().setAppName("RDDProgramGuide").setMaster("spark://9a71ce802953:7077");
		final JavaSparkContext sc = new JavaSparkContext(sparkConf);

		final List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
		final JavaRDD<Integer> distData = sc.parallelize(data);

		// 读取文件
		final URL resource = RDDProgramGuide.class.getClassLoader().getResource("data.txt");
		final String filePath = resource.getPath();
		System.out.println("resource path:" + filePath);
		JavaRDD<String> lines = sc.textFile(filePath);
		final JavaRDD<Integer> lineLengths = lines.map(String::length);
		// 缓存
		lineLengths.persist(StorageLevel.MEMORY_ONLY());
		
		final long l = System.currentTimeMillis();
		Integer totalLength = lineLengths.reduce(Integer::sum);
		final long l1 = System.currentTimeMillis();
		System.out.println(totalLength + " = " + (l1 -l) + "ms");

		final long l2 = System.currentTimeMillis();
		totalLength = lineLengths.reduce(Integer::sum); // 真正去执行这些操作
		final long l3 = System.currentTimeMillis();
		System.out.println(totalLength + " = " + (l3 -l2) + "ms");

		System.out.println("=============================");

		final LongAccumulator counter = sc.sc().longAccumulator("counter");
		JavaRDD<Integer> rdd = sc.parallelize(data);
		rdd.foreach(r -> {
			final Long value = counter.value();
			System.out.println("counter value:" + value);
			counter.add(r);
		});
		System.out.println((counter));

		System.out.println("=============================");
		
//		lines = sc.textFile(filePath);
		final JavaPairRDD<String, Integer> pairs = lines.mapToPair(s -> new Tuple2<>(s, 1));
		final JavaPairRDD<String, Integer> counts = pairs.reduceByKey(Integer::sum);
		final JavaPairRDD<String, Integer> sorts = counts.sortByKey();
//		sorts.foreach(pair -> System.out.println("key:value: " + pair._1 + ":" + pair._2));
		sorts.take(10).forEach(pair -> System.out.println("key:value: " + pair._1 + ":" + pair._2));
		
		System.out.println("=============================");

		final Broadcast<int[]> broadcastVar = sc.broadcast(new int[]{1, 2, 3});
		final int[] value = broadcastVar.value();

		final LongAccumulator accum = sc.sc().longAccumulator();
		data.stream().map(x -> {
			accum.add(x);
			return x;
		});

		System.out.println(accum.value());

	}
}
