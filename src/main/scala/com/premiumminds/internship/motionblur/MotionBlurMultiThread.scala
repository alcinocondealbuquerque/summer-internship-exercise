package com.premiumminds.internship.motionblur

import scala.concurrent._
import java.util.concurrent.Executors

object MotionBlurMultiThread extends MotionBlurFactory {
  /**
    * Method to start processing the data, this one should work in parallel
    * @param data matrix of integers
    * @param numberOfWorkers number of threads that should work in parallel
    * @return matrix of integers
    */
  override def run(data: Seq[Seq[Int]], numberOfWorkers: Int) =  {
    implicit val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(numberOfWorkers))
    val tasks: Seq[Seq[Future[Int]]] = for (row <- 0 until data.length) 
      yield for (col <- 0 until data(row).length) 
        yield Future(motionBlurOperation(data, row, col))   
    Future.sequence(tasks.map(seq => Future.sequence(seq)))   
  }
}
