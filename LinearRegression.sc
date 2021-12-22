import breeze.linalg._
import breeze.stats.distributions._
import java.io.PrintWriter

// generate X's from standard uniform distribution
def generateX(N: Int,
              d: Int): DenseMatrix[Double] = {
  DenseMatrix.rand(N, d)
}

// from X's, predetermined weights and random errors, initialise y's
// errors are drawn from standard normal distribution
def initY(w: DenseVector[Double],
          X: DenseMatrix[Double],
          N: Int): DenseVector[Double] = {
  val gau = Gaussian(0.0, 1.0)
  var vecEps = DenseVector(gau.sample(N).toArray)
  var y = X * w + vecEps
  return y
}

// compute w_hat by the means of gradient descent with tolerance
def fitGradientDescent(y: DenseVector[Double],
                       X: DenseMatrix[Double],
                       N: Int,
                       d: Int,
                       lambda: Double,
                       steps: Int,
                       tolerance: Double): DenseVector[Double] = {
  var epsHat = DenseVector.zeros[Double](N)
  var w = DenseVector.ones[Double](d)
  var wNext = DenseVector.ones[Double](d)

  for (i <- (0 until steps-1).by(1)) {
    epsHat = X * w - y
    wNext = w - lambda / N * (epsHat.t * X).t
    if (norm(w - wNext) < tolerance && i > 0){
      println(f"Computation converged at step: ${i}")
      return w
    }
    w = wNext
  }
  return w
}

def main() = {
  var N = 1000000
  var d = 3
  // initialise weights
  val w = DenseVector(1.5, 0.3, -0.7)
  var X = generateX(N, d)
  // from X's, predetermined weights and random errors, initialise y's
  var y = initY(w, X, N)

  var wHat = fitGradientDescent(y, X, N, d, 0.1, 100000, 0.0001)

  new PrintWriter("C:\\Users\\rlaty\\MADE\\ML_BD\\HA5\\RegressionWeights.txt") { write(f"Regression weights are, ${wHat}"); close }
  println(f"Estimated weights are: ${wHat}")
  println(f"True weights are: ${w}")
}

main()
