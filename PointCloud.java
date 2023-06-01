/*
Jonas Moore
2/28/2023
this program will generate an array of points that can be passed to a script to become an STL.

functioning details, this program creats an array of cordinate point obgects that are then filled in by
creating 180 points in a half cirlce each point is on a different ring, then it does this 360 times changing
what the degree that it makes the point on is by 1 each time to make a sphere. to avoid making a perfect
sphere (boring) when it is filling in the points the program will randomly adjust the distance from the 
center of the sphere then use 3d trig to convert that distance into cartisian cordinates.

*/
import java.util.Random;

public class PointCloud {
   private CordinatePoint[][] pointCloud;
   private int lRing;
   private int lDegree;//stores the last ring, last degree and last radius used by the program.
   private double lRad;
   
   private boolean cratersStarted;//used to trigger the initial crater script only once.
   
   public PointCloud(double amplification, int numCraters, String seed) {//seed is used for seed based math and is structured as is follows
                                                         /*the 1st and 2nd numbers are used for general purpose randomization
                                                         the 3rd through 8th numbers are for determining positions of features
                                                         with the 3rd through 5th for ring and the 6th through 8th for degree*/   
      if (seed.length() < 8) {
        throw new IllegalArgumentException(" seed length incorect");
      }
      
      cratersStarted = false;
      Random rand = new Random(Integer.parseInt(seed));
      
      pointCloud = new CordinatePoint[720][720]; //first value is the degree around the center of the planet, second value is the ring.
      double distance;
      for (int i = 0; i < pointCloud.length; i++) {
         for (int i2 = 0; i2 < pointCloud[i].length; i2++) {
            
            distance = 100;
            double x;
            double y;
            double z;
            double c;//stores a hypotinuse length for trig magic.
            
            //to get z we need to do dist * sin(i2) so dist * sin(the angle from 0) if the angle is 0 or 180 then 
            //we can just call z dist and x and y 0 and 0;
            
            z = (distance * Math.sin(Math.toRadians((i2 * .25) + 90)));//this should take the distance from the center
            //then multiplie that by the sin of the angle from the center of the sphere to get the z cord
            c = (distance * Math.cos(Math.toRadians((i2 * .25) + 90)));//the plus 90 is to make it so that we start at a 90 degree angle and end at 270.
            x = (c * Math.cos(Math.toRadians(i * .5)));
            y = (c * Math.sin(Math.toRadians(i * .5)));
            /*to find the x and y we first must get the b value of the right traingle we used form getting z
            then we need to use that as the hypotinuse of another triangle which we then solve for a and b to 
            get x and y. basicly we took a traingle then used the bottom as the top for another triangle*/
            
            pointCloud[i][i2] = new CordinatePoint(x,y,z,distance);//i2 is ring i is degree.
            
         }
      }
      while (numCraters != 0) {
         if (!cratersStarted) {
            int ringSeed = Integer.parseInt(seed.substring(2, 5));
            int degreeSeed = Integer.parseInt(seed.substring(5));
            int radSeed = Integer.parseInt(seed.substring(0, 2));//spliting the seed into its component parts
            
            rand.setSeed(ringSeed);
            lRing = rand.nextInt(711) + 5;//calculating all the random values from the split seed.
            
            rand.setSeed(degreeSeed);
            lDegree = rand.nextInt(711) + 5;
            
            rand.setSeed(radSeed);
            lRad = rand.nextInt(151) + 50;//lRing and lDegree stand for last ring and last degree, the same pattern aplies to lRad (last radius).
            
            lRad *= -0.03;
            lRad += 6;
            lRad = Math.pow(2, lRad);
            lRad = 50 / lRad;// the above 3 equations represent an exponential decay function, then yoy divide by the exponential decay function to get a value from about 2 to 50
            
            addCrater(lRing, lDegree, lRad, amplification, seed);//using tha add crater method to add a crater
            
            numCraters--;//iterating the loop.
            cratersStarted = true;
         }
         else {
            int ringSeed = Integer.parseInt(seed.substring(2, 5)) * ((int) lRad * lRing);
            int degreeSeed = Integer.parseInt(seed.substring(5)) * (lRing * lDegree);
            int radSeed = Integer.parseInt(seed.substring(0, 2)) * (lDegree * (int) lRad);//spliting the seed into its component parts and adding the previous ring and degree to make it differnet from the origional
            
            
            rand.setSeed(ringSeed);
            lRing = rand.nextInt(711) + 5;//calculating all the random values from the split seed.
            
            rand.setSeed(degreeSeed);
            lDegree = rand.nextInt(711) + 5;
            
            rand.setSeed(radSeed);
            lRad = rand.nextInt(151) + 50;//lRing and lDegree stand for last ring and last degree, the same pattern aplies to lRad (last radius).
            
            lRad *= -0.03;
            lRad += 6;
            lRad = Math.pow(2, lRad);
            lRad = 50 / lRad;
            
            
            addCrater(lRing, lDegree, lRad, amplification, seed);//using tha add crater method to add a crater
            numCraters--;
         }
      }
   }
   
   public void print() {
      for (int i = 0; i < pointCloud.length; i++) {
         for(int i2 = 0; i2 < pointCloud[i].length; i2++) {
            System.out.print(pointCloud[i][i2].toString());//i2 is ring i is degree.
         }
         System.out.println();
      }
   }
   
   public CordinatePoint get(int ring, int degree) {
      return pointCloud[degree][ring];
   }
   
   public void recalculateCartisian(double dist, int ring, int degree) {
      pointCloud[degree][ring].newDist(dist);
      
      double x;
      double y;
      double z;
      double c;//stores a hypotinuse length for trig magic.
            
       //to get z we need to do dist * sin(i2) so dist * sin(the angle from 0) if the angle is 0 or 180 then 
      //we can just call z dist and x and y 0 and 0;
           
      z = (dist * Math.sin(Math.toRadians((ring * .25) + 90)));//this should take the distance from the center
      //then multiplie that by the sin of the angle from the center of the sphere to get the z cord
      c = (dist * Math.cos(Math.toRadians((ring * .25) + 90)));//the plus 90 is to make it so that we start at a 90 degree angle and end at 270.
      x = (c * Math.cos(Math.toRadians(degree * .5)));
      y = (c * Math.sin(Math.toRadians(degree * .5)));//i2 is ring i is degree.
      /*to find the x and y we first must get the b value of the right traingle we used form getting z
      then we need to use that as the hypotinuse of another triangle which we then solve for a and b to 
      get x and y. basicly we took a traingle then used the bottom as the top for another triangle*/
      
      pointCloud[degree][ring].newX(x);
      pointCloud[degree][ring].newY(y);
      pointCloud[degree][ring].newZ(z);
   }
   
   public void addCrater(int ring, int degree, double radius, double amplification,  String seed) {    
      Random rand = new Random(Integer.parseInt(seed));
      for(int i = 0; i < pointCloud.length; i++) {
         for (int i2 = 0; i2 < pointCloud[i].length; i2++) {
            if (pointCloud[i][i2].hasAtribute("craterCenter")) {
               if (!(pointCloud[degree][ring].getDistanceTo(pointCloud[i][i2]) < pointCloud[i][i2].extractAtributeData("craterRadius") - radius - 2) && (pointCloud[degree][ring].getDistanceTo(pointCloud[i][i2]) < pointCloud[i][i2].extractAtributeData("craterRadius") + radius + 2)) {
                  
                  int ringSeed = Integer.parseInt(seed.substring(2, 5)) * ((int) lRad * lRing);
                  int degreeSeed = Integer.parseInt(seed.substring(5)) * (lRing * lDegree);
                  int radSeed = Integer.parseInt(seed.substring(0, 2)) * (lDegree * (int) lRad);//spliting the seed into its component parts and adding the previous ring and degree to make it differnet from the origional
            
               
                  rand.setSeed(ringSeed);
                  lRing = rand.nextInt(711) + 5;//calculating all the random values from the split seed.
            
                  rand.setSeed(degreeSeed);
                  lDegree = rand.nextInt(711) + 5;
            
                  rand.setSeed(radSeed);
                  lRad = rand.nextInt(151) + 50;//lRing and lDegree stand for last ring and last degree, the same pattern aplies to lRad (last radius).
            
                  lRad *= -0.03;
                  lRad += 6;
                  lRad = Math.pow(2, lRad);
                  lRad = 50 / lRad;
            
            
                  addCrater(lRing, lDegree, lRad, amplification, seed);//using tha add crater method to add a crater
                  return;
               }
            }
         }
      }//will call the method recursivly to avoid having crater edges overlap.
      
      pointCloud[degree][ring].newAtribute("craterCenter");
      pointCloud[degree][ring].newAtribute("craterRadius:" + radius);
      
      for (int i = 0; i < pointCloud.length; i++) {
         for (int i2 = 0; i2 < pointCloud[i].length; i2++) {//i2 is ring i is degree.
            if (pointCloud[degree][ring].getDistanceTo(pointCloud[i][i2]) < radius) {
                  
                  double distToCenter = radius - pointCloud[degree][ring].getDistanceTo(pointCloud[i][i2]);
                  double denominator = Math.pow(2.718, (.1 * distToCenter));
                  denominator = .81 - denominator;
                  double newDist = pointCloud[i][i2].getDist() - (((1 / denominator) + 5) * (radius / (100 / amplification))); //calculating the new dist using a logistics function so it levels off.
                  
                  recalculateCartisian(newDist, i2, i);//sets the dist of the target point equal to the current distance minus the log of the distance to the center of the crater
            }
         }
      }
   }//for dealing with overlapping craters we can average the distance between the craters that point is in and ue that for calculating the height change difference.
}
/*IMPORTANT NOTE
   the way the 2d array get method is set up the first parameter is the ring, the second paremter is the
   pos on the ring, the degree.
*/