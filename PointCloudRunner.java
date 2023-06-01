/*
Jonas Moore
3/7/2023
this program will run PointCloud.java and eventualy use it to creat an STL
*/
import java.io.File;//used for data writing and file creation with the output STL.
import java.io.FileWriter;//used for STL file creation and data writing
import java.io.IOException;//used for STL file data writing
import java.util.ArrayList;//used for the array list of triangles during STL file creation.
import java.nio.ByteBuffer;//used for the byte array conversion for converting to a STL file.

public class PointCloudRunner {
   public static final int  MAX_SEED = 99999999;
   public static final int MIN_SEED = 10000000;
   public static void main(String[] args) {
      ArrayList<Triangle> triangles = new ArrayList<Triangle>();
      
      int seed = (int) (Math.random() * (MAX_SEED-MIN_SEED)) + MIN_SEED;
      
      PointCloud cloud = new PointCloud(7, 300, String.valueOf(seed));
      //cloud.print();//outputs the pointcloud
      pointCloudToTriangleArray(cloud, triangles);
      /*for(int i = 0; i < triangles.size(); i++) {
         triangles.get(i).print();
         System.out.println();
      }*/// outputs the triangle arraylist.
      // Create a new STL file
        File stlFile = new File("plantet_generator_output.stl");

        // Create a new FileWriter object to write data to the STL file
        try {
            FileWriter writer = new FileWriter(stlFile);

            // Write data to the STL file
            // Define the start of the solid object
            writer.write("solid planet_generator_output\n");
            
            for (Triangle triangle : triangles) {
               // Write the normal vector
               writer.write("facet normal " + triangle.getNormal().getNormalX() + " " + triangle.getNormal().getNormalY() + " " + triangle.getNormal().getNormalZ() + "\n");

               // Write the triangle vertices
               writer.write("outer loop\n");
               writer.write("vertex " + triangle.getA().getX() + " " + triangle.getA().getY() + " " + triangle.getA().getZ() + "\n");
               writer.write("vertex " + triangle.getB().getX() + " " + triangle.getB().getY() + " " + triangle.getB().getZ() + "\n");
               writer.write("vertex " + triangle.getC().getX() + " " + triangle.getC().getY() + " " + triangle.getC().getZ() + "\n");
               writer.write("endloop\n");

               // Write the end of the triangle definition
               writer.write("endfacet\n");
            }
            
            writer.write("endsolid planet_generator_output\n");

            // Close the FileWriter object
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*Triangle tra = new Triangle(cloud.get(1,2),cloud.get(2,2),cloud.get(1,3));
        tra.print();*///test code for the triangle class
   }
   
   public static void pointCloudToTriangleArray(PointCloud cloud, ArrayList<Triangle> triangles) {
      double dist = 0;
      for (int degree = 0; degree < 720; degree++) {
         dist += cloud.get(0/*719*/, degree).getDist();
      }
      dist /= 720;
      CordinatePoint top = new CordinatePoint (0, 0, dist, dist);
      
      dist = 0;
      for (int degree = 0; degree < 720; degree++) {
         dist += cloud.get(719/*0*/, degree).getDist();
      }
      dist /= 720;
      CordinatePoint bottom = new CordinatePoint (0, 0, -dist, dist);
       
      
      for (int degree = 0; degree < 720; degree++) {
         for (int ring = 0; ring < 720; ring++) {
         
            if (ring != 0 && degree != 719) {
               Triangle t1 = new Triangle(cloud.get(ring, degree), cloud.get(ring, degree + 1), cloud.get(ring - 1, degree));
               triangles.add(t1);
            }
            else if (ring != 0 && degree == 719) {
               Triangle t1 = new Triangle(cloud.get(ring, degree), cloud.get(ring, 0), cloud.get(ring - 1, degree));
               triangles.add(t1);
            }
            //if its ring zero you do nothing because the the lower triangles of ring 2 will take care of it
            /*aditional note, when this converts to an STL if there is weird problems at one pole, have it start at ring one and
            use the aditional point solution (comments on lines 50-51) to seal it up.*/  

            if (ring != 719 && ring != 0 && degree != 719) {//cant have this on ring zero, it will generate triangles that are just lines ring 1 should seal it up.
               Triangle t2 = new Triangle(cloud.get(ring, degree), cloud.get(ring, degree + 1), cloud.get(ring + 1, degree + 1));
               triangles.add(t2);
            }
            else if (degree == 719 && ring != 0 && ring != 719) {
               Triangle t2 = new Triangle(cloud.get(ring, degree), cloud.get(ring, 0), cloud.get(ring + 1, 0));
               triangles.add(t2);
            }
            else if (degree != 719 && ring == 0) {
               Triangle t2 = new Triangle(cloud.get(ring, degree), cloud.get(ring, degree + 1), top);//top is the average of the dist
               triangles.add(t2);
            }
            else if (degree != 719 && ring == 719) {
               Triangle t2 = new Triangle(cloud.get(ring, degree), cloud.get(ring, degree + 1), bottom);
               triangles.add(t2);
            }
            /*minor issue, this is at the top of the planet and well... I cant figure out how to close it up, I think the best way is
               add a extra cordinate specificly for closing up the top of the sphere then connecting all the traingles to that for point c
               (point c is referenced in the triangle class) for now I will just finish writing this and then wait tell I have an STL to
               further debug.*/
         }
      }
   }
}
