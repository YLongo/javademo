package github.io.ylongo.spark;

import lombok.Data;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.ForeachFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.MapFunction;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SparkSqlGuide {


	public static void main(String[] args) throws AnalysisException {

		final SparkConf sparkConf = new SparkConf().setAppName("SparkSqlGuide").setMaster("local");
		final SparkSession spark = SparkSession.builder().appName("SparkSqlGuide")
				.config(sparkConf).getOrCreate();

		System.err.println("-----------------[Create DataFrame]-----------------");
		
		final String path = "/Users/YLongo/local/spark-3.2.0-bin-hadoop2.7/examples/src/main/resources/people.json";
		
		final Dataset<Row> df = spark.read().json(path);

		df.show();

		System.err.println("-----------------[DataFrame Operation]-----------------");
		
		df.printSchema();
		
		df.select("name").show();
		
		df.select(functions.col("name"), functions.col("age").plus(1)).show();
		
		df.filter(functions.col("age").gt(21)).show();
		
		df.groupBy("age").count().show();
		
		System.err.println("-----------------[Temp View]-----------------");
		df.createOrReplaceTempView("people");

		final Dataset<Row> sqlDF = spark.sql("select * from people");
		sqlDF.show();

		System.err.println("-----------------[Global Temp View]-----------------");
		df.createGlobalTempView("people");
		
		spark.sql("select * from global_temp.people").show();
		spark.newSession().sql("select * from global_temp.people").show();

		System.err.println("-----------------[Create Dataset]-----------------");

		final Person person = new Person();
		person.setName("Andy");
		person.setAge(32);

		final Encoder<Person> personEncoder = Encoders.bean(Person.class);
		final Dataset<Person> personDataset = spark.createDataset(Collections.singletonList(person), personEncoder);
		personDataset.show();

		System.err.println("-----------------[Long Encoder]");

		final Encoder<Long> longEncoder = Encoders.LONG();
		final Dataset<Long> primitiveDS = spark.createDataset(Arrays.asList(1L, 2L, 3L), longEncoder);
		final Dataset<Long> transformedDS = primitiveDS.map((MapFunction<Long, Long>) value -> value + 1L, longEncoder);
//		transformedDS.collect();
		transformedDS.show();

		System.err.println("-----------------[DataFrame => Dataset]");
		final Dataset<Person> peopleDS = spark.read().json(path).as(personEncoder);
		peopleDS.show();

		System.err.println("-----------------[Infer the Schema Use Reflection]-----------------");
		
		String textFile = "/Users/YLongo/local/spark-3.2.0-bin-hadoop2.7/examples/src/main/resources/people.txt";

		System.err.println("-----------------[获取一个指定对象的RDD]");
		final JavaRDD<Person> peopleRDD = spark.read().textFile(textFile)
				.javaRDD().map(line -> {
					final String[] parts = line.split(",");
					final Person p = new Person();
					p.setName(parts[0]);
					p.setAge(Integer.parseInt(parts[1].trim()));
					return p;
				});

		final Dataset<Row> peopleDF = spark.createDataFrame(peopleRDD, Person.class);
		peopleDF.show();
		
		peopleDF.foreach((ForeachFunction<Row>) row -> {
			System.out.println(row.getLong(0) + ":" + row.getString(1));
		});

		peopleDF.createOrReplaceTempView("people");
		
		final Dataset<Row> teenagersDF = spark.sql("select name from people where age between 13 and 19");

		final Encoder<String> stringEncoder = Encoders.STRING();
		
		final Dataset<String> teenagerNamesByIndexDF = teenagersDF
				.map((MapFunction<Row, String>) row -> "Name: " + row.getString(0), stringEncoder);
		teenagerNamesByIndexDF.show();

		final Dataset<String> teenagerNamesByFieldDF = teenagersDF
				.map((MapFunction<Row, String>) row -> "Name: " + row.getAs("name"), stringEncoder);
		
		teenagerNamesByFieldDF.show();

		final JavaRDD<String> peopleStringRDD = spark.sparkContext().textFile(textFile, 1).toJavaRDD();
		String schemaString = "name age";
		
		List<StructField> fields = new ArrayList<>();

		for (String fieldName : schemaString.split(" ")) {
			final StructField field = DataTypes.createStructField(fieldName, DataTypes.StringType, true);
			fields.add(field);
		}

		final StructType schema = DataTypes.createStructType(fields);

		final JavaRDD<Row> rowRDD = peopleStringRDD.map((Function<String, Row>) record -> {
			String[] attributes = record.split(",");
			return RowFactory.create(attributes[0], attributes[1].trim());
		});

		final Dataset<Row> peopleDataFrame = spark.createDataFrame(rowRDD, schema);
		peopleDataFrame.createOrReplaceTempView("people");

		final Dataset<Row> results = spark.sql("select name from people");

		final Dataset<String> namesDS = results.map((MapFunction<Row, String>) row -> "Name: " + row.getString(0), Encoders.STRING());
		namesDS.show();


	}
	
	@Data
	public static class Person implements Serializable {
		private String name;
		
		private long age;
		
		
	}
}
