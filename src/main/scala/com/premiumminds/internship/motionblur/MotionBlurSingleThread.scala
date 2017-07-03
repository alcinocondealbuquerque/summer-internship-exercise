package com.premiumminds.internship.motionblur

import scala.concurrent._
import java.util.concurrent.Executors

object MotionBlurSingleThread extends MotionBlurFactory {
  /**
    * Method to start processing the data, this one uses only the current thread
    * @param data matrix of integers
    * @param numberOfWorkers this parameter should be ignored
    * @return matrix of integers
    */
  override def run(data: Seq[Seq[Int]], numberOfWorkers: Int) = {
    implicit val ec = ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(1))
    val task: Seq[Seq[Future[Int]]] = for (row <- 0 until data.length) 
      yield for (col <- 0 until data(row).length) 
        yield Future(motionBlurOperation(data, row, col))   
    Future.sequence(task.map(seq => Future.sequence(seq)))     
  } 
}
