package github.io.ylongo.spark.hackernews

import com.alibaba.druid.pool.{DruidDataSource, DruidPooledConnection}
import com.alibaba.fastjson.{JSON, JSONArray, JSONObject}
import scalaj.http.{Http, HttpRequest, HttpResponse}

import java.io.{BufferedWriter, File, FileWriter}
import java.sql.{ResultSet, Statement}
import java.util.Objects


object HackerNewsAPI {

  private val DEFAULT_STORY_ID = 29627105
  private val STORY_URL_TEMPLATE = "https://hacker-news.firebaseio.com/v0/item/%d.json"
  private val MYSQL_DRIVER = "com.mysql.jdbc.Driver"
  private val DB_URL = "jdbc:mysql://127.0.0.1:3306/test?useUnicode=true&characterEncoding=UTF-8"

  def main(args: Array[String]): Unit = {

    val bodyJson: JSONObject = getItem(DEFAULT_STORY_ID)

    writeFile("hacker-news.json", bodyJson.toString)

    val itemType: String = bodyJson.getString("type")
    val kids: JSONArray = getKids(bodyJson)

    if (kids != null && !kids.isEmpty) {
      next(kids, itemType)
    }

  }


  private def testDB(): Unit = {
    val source: DruidDataSource = initDruidDatasource()
    val connection: DruidPooledConnection = source.getConnection
    val statement: Statement = connection.createStatement()
    statement.execute("select * from user")
    val resultSet: ResultSet = statement.getResultSet

    while (resultSet.next()) {
      val id: Int = resultSet.getInt(1)
      val email: String = resultSet.getString(2)
      val nickName: String = resultSet.getString(3)
      println("id:" + id + ", email:" + email + ", nickName:" + nickName)
    }
  }

  /**
   * write a `String` to the `filename`.
   */
  def writeFile(filename: String, str: String): Unit = {
    val file = new File(filename)
    val bw = new BufferedWriter(new FileWriter(file))
    bw.write(str)
    bw.close()
  }


  private def initDruidDatasource(): DruidDataSource = {

    val datasource = new DruidDataSource
    datasource.setDriverClassName(MYSQL_DRIVER)
    datasource.setUrl(DB_URL)
    datasource.setUsername("root")
    datasource.setPassword("root")
    datasource.setMaxActive(500)
    datasource.setInitialSize(5)
    datasource.setMaxWait(60000)
    datasource.setMinIdle(5)
    datasource.setTimeBetweenEvictionRunsMillis(60000)
    datasource.setMinEvictableIdleTimeMillis(300000)
    datasource.setValidationQuery("select 'x'")
    datasource.setTestWhileIdle(true)
    datasource.setTestOnBorrow(false)
    datasource.setTestOnReturn(false)
    datasource.setPoolPreparedStatements(true)
    datasource.setMaxOpenPreparedStatements(20)
    datasource.setQueryTimeout(60)
    datasource.init()

    datasource
  }

  def loopItems(kids: JSONArray): Unit = {

    if (kids != null && !kids.isEmpty) {
      kids.forEach(kid => {
        val bodyJson: JSONObject = getItem(kid.toString.toInt)
        val nextKids: JSONArray = getKids(bodyJson)
        val itemType: String = bodyJson.getString("type")
        next(nextKids, itemType)
      })
    }
  }

  def next(kids: JSONArray, itemType: String): Unit = {
    itemType match {
      case "story" => loopItems(kids)
      case "comment" => loopItems(kids)
    }
  }


  def getKids(bodyJson: JSONObject): JSONArray = {

    val id: Int = bodyJson.getIntValue("id")
    val by: String = bodyJson.getString("by")
    val itemType: String = bodyJson.getString("type")

    if (Objects.equals("story", itemType)) {
      val title: String = bodyJson.getString("title")
      println("id" + id + ", title:" + title + ", by:" + by)
    } else if (Objects.equals("comment", itemType)) {
      val text: String = bodyJson.getString("text")
      println("id" + id + ", text:" + text + ", by:" + by)
    }

    val kids: JSONArray = bodyJson.getJSONArray("kids")
    kids
  }

  def getItem(storyId: Int): JSONObject = {

    val storyUrl: String = STORY_URL_TEMPLATE.format(storyId)
    val request: HttpRequest = Http(storyUrl).proxy("127.0.0.1", 1080)

    val response: HttpResponse[String] = request.asString

    val body: String = response.body

    println(body)

    JSON.parseObject(body)
  }

}
