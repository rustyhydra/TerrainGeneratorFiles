/*
Jonas Moore
3/8/2023
this class will store triangles for use in an STL file. this is to be used with the rest of the
point-cloud classes to create a randomly generated STL planet.
*/
public class Triangle {
   private CordinatePoint a;
   private CordinatePoint b;
   private CordinatePoint c;
   private NormalVector n;
   
   public Triangle(CordinatePoint a, CordinatePoint b, CordinatePoint c) {
      this.a = a;
      this.b = b;
      this.c = c;
      n = new NormalVector(a, b, c);
   }

   public Triangle() {
      CordinatePoint blank = new CordinatePoint(0,0,0,0);
      a = blank;
      b = blank;
      c = blank;
      n = new NormalVector(a, b, c);
   }
   
   public CordinatePoint getA() {
      return a;
   }

   public CordinatePoint getB() {
      return b;
   }
   
   public CordinatePoint getC() {
      return c;
   }

   public NormalVector getNormal() {
      return n;
   }
   
   public void newA(CordinatePoint newA) {
      a = newA;
      n = new NormalVector(a, b, c);
   }
   
   public void newB(CordinatePoint newB) {
      b = newB;
      n = new NormalVector(a, b, c);
   }

   public void newC(CordinatePoint newC) {
      c = newC;
      n = new NormalVector(a, b, c);
   }

   public void round(int places) {
      a.round(places);
      b.round(places);
      c.round(places);
   }

   public void print() {
      //TODO: make this into a toString method
      System.out.print("a:  ");
      System.out.print(a.toString());
      System.out.print("   b:  ");
      System.out.print(b.toString());
      System.out.print("   c:  ");
      System.out.print(c.toString());
   }
}