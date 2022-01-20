package github.io.ylongo.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;

public class SparkSqlDatasource {

	public static void main(String[] args) {

		final SparkConf sparkConf = new SparkConf().setAppName("SparkSqlDatasource").setMaster("local");
		final SparkSession spark = SparkSession.builder().appName("SparkSqlDatasource")
				.config(sparkConf).getOrCreate();
		
		String parquetFile = "/Users/YLongo/local/spark-3.2.0-bin-hadoop2.7/examples/src/main/resources/users.parquet";

		System.err.println("-----------------[Generic Save Function]-----------------");
		final Dataset<Row> usersDF = spark.read().load(parquetFile);
		final Dataset<Row> favoriteColorDS = usersDF.select("name", "favorite_color");
		favoriteColorDS.show();
		favoriteColorDS.write().mode(SaveMode.Overwrite).save("namesAndFavColors.parquet");

		System.err.println("-----------------[JSON Save As Parquet]-----------------");
		String peopleJsonFile = "/Users/YLongo/local/spark-3.2.0-bin-hadoop2.7/examples/src/main/resources/people.json";
		final Dataset<Row> peopleDF = spark.read().format("json").load(peopleJsonFile);
		peopleDF.select("name", "age").write().mode(SaveMode.Overwrite).format("parquet").save("namesAndAges.parquet");

		final Dataset<Row> sqlDF = spark.sql("select * from parquet.`" + parquetFile + "`");
		sqlDF.foreach((ForeachFunction<Row>) row -> {
			System.out.println("row:" + row.toString());
		});
		
	}
}
