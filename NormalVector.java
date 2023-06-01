/*
Jonas Moore
3/27/2023
this class will store a 3 double values for the normal vector of a triangle, I writing 
a fresh class rather than using the old cordinate point class for readability. also 
might do vector calculations in the constructor
*/

public class NormalVector {
   private double normalX;
   private double normalY;
   private double normalZ;
   
   public NormalVector (CordinatePoint a, CordinatePoint b, CordinatePoint c) {
      /*this constructor takes the a b and c corinates of a triangle and turns them into the 
      normal vectos for x y and z, to do this it first calculates AB and AC by subtracting
      x of b from x of a and x of c from x of a respectivly. then I take 2 of the vectors
      for AB and AC of multiple them then subtract 1 from the other, then I normalize the
      vector by taking the sqrt of (normal X component^2 + normal Y component^2 + normal Z component^2)
      then diving the indivdual value by the abs value of the double this spits out.*/
      
      //calculating AB and BC
      double ABX = (a.getX() - b.getX());
      double ABY = (a.getY() - b.getY());
      double ABZ = (a.getZ() - b.getZ());
      
      double ACX = (a.getX() - c.getX());
      double ACY = (a.getY() - c.getY());
      double ACZ = (a.getZ() - c.getZ());
      
      
      //calculating the normals, unnormalized
      normalX = (ABY * ACZ) - (ACY * ABZ);
      normalY = (ABZ * ACX) - (ACZ * ABX);
      normalZ = (ABX * ACY) - (ACX* ABY);
      
      //normalize the normal.
      double length = Math.sqrt(((normalX * normalX) + (normalY * normalY) + (normalZ * normalZ)));
      
      normalX /= length;
      normalY /= length;
      normalZ /= length;
   }
   
   //getters
   public double getNormalX() {
      return normalX;
   }
   public double getNormalY() {
      return normalY;
   }
   public double getNormalZ() {
      return normalZ;
   }
}