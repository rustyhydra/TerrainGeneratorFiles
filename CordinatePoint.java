/*
Jonas Moore
3/1/2023
this will be a class that creates point obgects. each obgect will have an X,Y, and Z cordinate.
this is a nothing fancy no frills class.
*/
//package PointCloudClasses;
import java.util.ArrayList;

public class CordinatePoint {
   private double x;
   private double y;
   private double z;
   private double dist;
   private ArrayList<String> atributes;
   
   public CordinatePoint(double x, double y, double z, double dist) {
      this.x = x;
      this.y = y;
      this.z = z;
      this.dist = dist;
      atributes = new ArrayList<String>();//stores a list of atributes for stuff like weather a point is in a crater or not.
   }
   
   public void newX(double newX) {
      x = newX;
   }
   
   public void newY(double newY) {
      y = newY;
   }
   
   public void newZ(double newZ) {
      z = newZ;
   }
   
   public void newDist(double newDist) {
      dist = newDist;
   }
   
   public double getX() {
      return x;
   }
   
   public double getY() {
      return y;
   }
   
   public double getZ() {
      return z;
   }
   
   public double getDist() {
      return dist;
   }
   
   public void newAtribute(String atribute) {
      atributes.add(atribute);
   }
   
   public boolean hasAtribute(String atribute) {
      for (int i = 0; i < atributes.size(); i++) {
         if (atributes.get(i).indexOf(atribute) != -1) {
            return true;
         }
      }
      return false;
   }
   
   public double extractAtributeData(String atribute) {
      for (int i = 0; i < atributes.size(); i++) {
         if (atributes.get(i).indexOf(atribute) != -1) {
            String noText = atributes.get(i).substring(atributes.get(i).indexOf(":") + 1);//cutting out the text portion
            String firstHalf = noText.substring(0, noText.indexOf("."));
            /*String secondHalf = noText.substring(noText.indexOf(".") + 1);//spliting it around the decimal point.*/
            
            double outPut = Integer.parseInt(firstHalf);
            
            /*if (outPut < 0) {
               outPut -= (Integer.parseInt(secondHalf) / Math.pow(10, secondHalf.length()));//this logic is taking the second half wich must be a decimal and divding it by it its length as it is currently a whole counting number
            }
            else {
               outPut += (Integer.parseInt(secondHalf) / Math.pow(10, secondHalf.length()));
            }//the if else lets it handle negatives*/
            
            //all of this is to extract the double split it around the decimal place and recombine it in a way the computer understands.
            
            return outPut;
         }
      }
      throw new IllegalArgumentException(" Atribute Input Does Not Exist In Targets Artibutes");
   }
   
   public String toString() {
      return "x:" + x + " y:" + y + " z:" + z + " dist:" + dist;
   }
   
   public void print() {
      System.out.println("x:" + x + " y:" + y + " z:" + z + " dist:" + dist);
   }
   
   public double getDistanceTo(CordinatePoint navPoint) {
      double finalDist;
      double nX = navPoint.getX();
      double nY = navPoint.getY();
      double nZ = navPoint.getZ();
      
      double xDist = x - nX;
      double yDist = y - nY;
      double zDist = z - nZ;
      
      xDist = xDist * xDist;
      yDist = yDist * yDist;
      zDist = zDist * zDist;
      
      finalDist = Math.sqrt(xDist + yDist + zDist);
      
      return finalDist;//Math.sqrt((Math.sqrt(((x - navPoint.getX()) * (x - navPoint.getX())) + ((y - navPoint.getY()) * (y - navPoint.getY()))) *  Math.sqrt(((x - navPoint.getX()) * (x - navPoint.getX())) + ((y - navPoint.getY()) * (y - navPoint.getY()))))+ ((z - navPoint.getZ()) * (z - navPoint.getZ())));
   }
}