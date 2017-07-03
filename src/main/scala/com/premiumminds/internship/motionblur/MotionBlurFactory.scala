package com.premiumminds.internship.motionblur

import scala.concurrent._

trait MotionBlurFactory {
  /**
    * Method to start processing the data
    * @param data matrix of integers
    * @param numberOfWorkers number of threads that should work in parallel
    * @return matrix of integers
    */
  def run(data: Seq[Seq[Int]], numberOfWorkers: Int): Future[Seq[Seq[Int]]]
  
   /**
    * Method to calculate the motion blur of one point 
    * @param data matrix of integers
    * @param row index of row
    * @param col index of col
    * @return integer of a point with motion blur applied 
    */
  def motionBlurOperation(data: Seq[Seq[Int]], row: Int, col: Int): Int = {
    val Point: String = "point"
    val Up: String = "up"
    val Down: String  = "down"
    val Left: String = "left"
    
    def blurFunction(row :Int, col: Int,mod: String) = mod match {
      case Point => data(row)(col)
      case Up if data.isDefinedAt(row-1) => data(row-1)(col)
      case Down if data.isDefinedAt(row+1) => data(row+1)(col)
      case Left if data(row).isDefinedAt(col-1) => data(row)(col-1)
      case _ => "none"
    }
    def operation(row: Int, col: Int): Int = {
      val points = List(
        blurFunction(row, col, Point),
        blurFunction(row, col, Up),
        blurFunction(row, col, Down),
        blurFunction(row, col, Left)
      ).filter(_ != "none").map(_.toString.toDouble)
      math.round(points.sum/points.length).toInt
    }
    operation(row, col)
  }      
 }
