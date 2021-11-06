import scala.io.Source
import java.io.PrintWriter

val FILENAME_TRAIN = "C:\\Users\\rlaty\\MADE\\ML_BD\\HA3\\train.csv"
val FILENAME_TEST = "C:\\Users\\rlaty\\MADE\\ML_BD\\HA3\\test.csv"

def readCSV(filename: String): (Array[Double], Array[Array[Double]])  = {
  // reads data from csv and returns a one-dimensional array of y's and a matrix of X's
  val bufferedSource = Source.fromFile(filename)
  var y: Array[Double] = Array.empty
  var X: Array[Array[Double]] = Array.empty
  for (line <- bufferedSource.getLines) {
    val cols = line.split(",").map(_.trim.toDouble)
    val xLen = cols.length
    y = y :+ cols(0)
    X = X :+ cols.slice(1,xLen)
  }
  bufferedSource.close
  return (y, X)
}

def fit(y: Array[Double], X: Array[Array[Double]]): Array[Double] = {
  // returns array of coefficients (the first coefficient is an intercept)
  val X_T = X.transpose
  var X_avg: Array[Double] = Array.empty
  var y_avg: Double = 0

  y_avg = y.reduce(_+_) / y.length

  for (i <- (0 to X_T.length-1).by(1)) {
    X_avg = X_avg :+ X_T(i).reduce(_+_) / X_T(i).length
  }

  var betas: Array[Double] = Array.empty

  var s_xy: Double = 0
  var s_xx: Double = 0

  for (j <- (0 to X_T.length-1).by(1)) {
    for (i <- (0 to y.length-1).by(1)) {
      s_xy = s_xy + X(i)(j)*y(i) - X(i)(j)*y_avg+ X_avg(j)*y_avg - X_avg(j)*y(i)
      s_xx = s_xx + (X(i)(j) - X_avg(j))*(X(i)(j) - X_avg(j))
    }
    betas = betas :+ s_xy / s_xx

    s_xy  = 0
    s_xx = 0
  }

  // compute intercept
  var alpha: Double = y_avg
  for (j <- (0 to X_T.length-1).by(1)) {
    alpha = alpha - X_avg(j) * betas(j)
  }

  return alpha +: betas
}

def predict(betas: Array[Double], X: Array[Array[Double]]): Array[Double] = {
  // prediction function takes betas and X's, returns predictions for y's
  var y_predicted: Array[Double] = Array.empty
  var y_hat: Double = betas(0)
  for (i <- (0 to X.length-1).by(1)) {
    for (j <- (0 to X(i).length-1).by(1)) {
      y_hat = y_hat + betas(j+1) * X(i)(j)
    }
    y_predicted = y_predicted :+ y_hat
    y_hat = betas(0)
  }
  return y_predicted
}

def computeRSquared(y_predicted: Array[Double], y_true: Array[Double]): Double ={
  // compute R-squared from true and fitted test values
  var y_avg = y_true.reduce(_+_) / y_true.length

  var rss: Double = 0
  var tss: Double = 0

  for (i <- (0 to y_true.length-1).by(1)) {
    rss = rss + (y_true(i) - y_predicted(i))*(y_true(i) - y_predicted(i))
    tss = tss + (y_true(i) - y_avg)*(y_true(i) - y_avg)
  }

  var RSquared = 1 - rss / tss

  return RSquared
}

def main() = {
  // load train dataset

  val yX_train = readCSV(FILENAME_TRAIN)
  val y_train = yX_train._1
  val X_train = yX_train._2

  // estimate betas on a train test

  var betas = fit(y_train, X_train)

  // load test dataset

  val yX_test = readCSV(FILENAME_TEST)
  val y_test = yX_test._1
  val X_test = yX_test._2

  // predict y's from test X's
  val y_predicted = predict(betas, X_test)

  // compute R-squared as a measure of fit and see how it changes from train to test set
  println(f"R-squared on a train set, ${computeRSquared(predict(betas, X_train), y_train)}")
  println(f"R-squared on a test set, ${computeRSquared(y_predicted, y_test)}")

  new PrintWriter("C:\\Users\\rlaty\\MADE\\ML_BD\\HA3\\results.txt") { write(f"R-squared on a train set, ${computeRSquared(predict(betas, X_train), y_train)}\nR-squared on a test set, ${computeRSquared(y_predicted, y_test)}"); close }
}

main()
