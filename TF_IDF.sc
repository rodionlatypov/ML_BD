import org.apache.spark.sql._
import org.apache.spark.sql.functions._
import scala.math.{log => mathLog}

val spark = SparkSession.builder()
  .master("local[*]")
  .appName("HA4_TF_IDF")
  .getOrCreate()

val df = spark.read
  .option("header", "true")
  .option("inferSchema", "true")
  .option("sep", ",")
  .csv("C:\\Users\\rlaty\\MADE\\ML_BD\\HA4\\tripadvisor_hotel_reviews.csv")

val docCount = df.count()

val normalizeString = functions.udf { (s: String) => {
  s.replaceAll("[^0-9a-zA-Z\\s$]", "")
}
}

def calculateIdf  (x : Int) : Double = {
  mathLog((docCount + 1) / (x + 1))
}

val calculateIdfUdf = functions.udf{
  (d: Int) => calculateIdf(d)
}

val dfClean = df.select(normalizeString(col("Review")).as("Review_clean"))

val dfCleanToLower = dfClean
  .select(lower(col("Review_clean")).as("Review_lower"))
  .select(split(col("Review_lower"), " ").as("Word_list"))
  .withColumn("doc_id", monotonically_increasing_id())

val dfCleanedExploded = dfCleanToLower.withColumn("Word_list", explode(dfCleanToLower.col("Word_list")))

val dfWordCount = dfCleanedExploded.groupBy("doc_id" , "Word_list")
  .agg(count("doc_id") as "num_word")

val wordsInDoc = dfCleanedExploded.groupBy("doc_id")
  .agg(count("doc_id") as "words_in_doc")

val dfTf = dfWordCount
  .join(wordsInDoc, Seq("doc_id"), "inner")
  .withColumn("tf", col("num_word") / col("words_in_doc"))

val dfDocFreq = dfCleanedExploded.groupBy("Word_list")
  .agg(countDistinct("doc_id") as "df")
  .orderBy(desc("df"))
  .limit(100)
  .withColumn("idf", calculateIdfUdf(col("df")))

val dfResultTfIdf = dfTf
  .join(dfDocFreq, Seq("Word_list"), "inner")
  .withColumn("tf_idf", col("tf") * col("idf"))
  .select(col("doc_id"), col("Word_list") as "word", col("tf_idf"))

dfResultTfIdf.show

val dfPivotTable = dfResultTfIdf.groupBy("doc_id")
  .pivot(col("word"))
  .agg(first(col("tf_idf"), ignoreNulls = true))

dfPivotTable.show