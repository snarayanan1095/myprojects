package controllers

import org.apache.spark.mllib.recommendation.{ALS, MatrixFactorizationModel, Rating}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

object mainmovie {

  val spark = SparkSession
    .builder()
    .appName("MovieRec")
    .master("local[*]")
    .getOrCreate()


  val ratigsFile = "u.data"

  val df1 = spark.read.format("com.databricks.spark.csv").option("header", "false").option("inferSchema", "true").option("delimiter", "\t").load(ratigsFile)




  val ratingsDF = df1.select(df1.col("_c0"), df1.col("_c1"), df1.col("_c2"), df1.col("_c3"))

  val moviesFile = "u.item"

  val df2 = spark.read.format("com.databricks.spark.csv").option("header", "false").option("inferSchema", "true").option("delimiter", "|").load(moviesFile)


  val moviesDF = df2.select(df2.col("_c0"), df2.col("_c1"))


  ratingsDF.createOrReplaceTempView("ratings")
  moviesDF.createOrReplaceTempView("movies")

  //println(spark.sqlContext.sql("select `_c0` from movies limit 10"))

  val splits = ratingsDF.randomSplit(Array(0.75, 0.25), seed = 12345L)

  val (trainingData, testData) = (splits(0), splits(1))

  val numTraining = trainingData.count()

  val numTest = testData.count()

  println("Training: " + numTraining + " test: " + numTest)

  val ratingsRDD = trainingData.rdd.map(row => {

    val userId = row.getInt(0)

    val movieId = row.getInt(1)

    val ratings = row.getInt(2)

    Rating(userId, movieId, ratings)

  })

  val testRDD = testData.rdd.map(row => {

    val userId = row.getInt(0)

    val movieId = row.getInt(1)

    val ratings = row.getInt(2)

    Rating(userId, movieId, ratings)

  })

  //val movieRDD =


  val rank = 20

  val numIterations = 15

  val lambda = 0.10

  val alpha = 1.00

  val block = -1

  val seed = 12345L

  val implicitPrefs = false

  val model = new ALS().setIterations(numIterations) .setBlocks(block).setAlpha(alpha)

    .setLambda(lambda)

    .setRank(rank) .setSeed(seed)

    .setImplicitPrefs(implicitPrefs)

    .run(ratingsRDD)





  println("Rating:(UserID, MovieID, Rating)")

  println("----------------------------------")

  val topRecsForUser = model.recommendProducts(668, 6)



  for (rating <- topRecsForUser) {
    var temp = rating.product
    var temp2= moviesDF.filter("_c0===temp").show()
   // val temp1 = moviesDF.filter()
    println(temp2)
    println(rating.toString())
  }
  println("----------------------------------")

  spark.sqlContext.sql("select * movies limit 20 ")


  def computeRmse(model: MatrixFactorizationModel, data: RDD[Rating], implicitPrefs: Boolean): Double = {
    val predictions: RDD[Rating] = model.predict(data.map(x => (x.user, x.product)))
    val predictionsAndRatings = predictions.map { x => ((x.user, x.product), x.rating) } .join(data.map(x => ((x.user, x.product), x.rating))).values
    if (implicitPrefs) {
      println("(Prediction, Rating)")
      println(predictionsAndRatings.take(5).mkString("n"))
    }
    math.sqrt(predictionsAndRatings.map(x => (x._1 - x._2) * (x._1 - x._2)).mean())
  }

  val rmseTest = computeRmse(model, testRDD, true)

  println("Test RMSE: = " + rmseTest)

}
