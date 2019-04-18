package controllers

import controllers.mainmovie.{ratigsFile, spark}

object MovieRatings {



    val ratings = "u.data"

    val dfr = spark.read.format("com.databricks.spark.csv").option("header", "false").option("inferSchema", "true").option("delimiter", "\t").load(ratings)

  



}
