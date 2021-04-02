import processing.core.*; 
import processing.data.*; 
import processing.event.*; 
import processing.opengl.*; 

import java.util.HashMap; 
import java.util.ArrayList; 
import java.io.File; 
import java.io.BufferedReader; 
import java.io.PrintWriter; 
import java.io.InputStream; 
import java.io.OutputStream; 
import java.io.IOException; 

public class SerpinskyPoints extends PApplet {

int pointsNumber = 3;           // Number of starting points
int iterationsPerFrame = 1000;  // Something like an animation speed

PVector drawnPoint;
PVector[] cornerPoints;
PVector randomPoint;
PVector selectedPoint;

public void setup() {
  
  background(255);
  fill(200, 0, 0);
  cornerPoints = new PVector[pointsNumber];

  for (int i = 0; i < cornerPoints.length; i++) {
    cornerPoints[i] = new PVector();
    cornerPoints[i].x = (int)random(0, width);
    cornerPoints[i].y = (int)random(0, height);
  }

  PVector startPoint = cornerPoints[0];
  drawnPoint = new PVector();
  drawnPoint.x = startPoint.x;
  drawnPoint.y = startPoint.y;
}

public void draw() {
  drawCorners();
  
  stroke(0, 0, 0, 127);
  strokeWeight(1);

  for (int i = 0; i < iterationsPerFrame; i++) { 
    randomPoint = cornerPoints[(int)random(pointsNumber)];
    drawnPoint.x = (int)(drawnPoint.x + randomPoint.x)/2;
    drawnPoint.y = (int)(drawnPoint.y + randomPoint.y)/2;
    point(drawnPoint.x, drawnPoint.y);
  }
}

public void drawCorners(){
  noStroke();
  for (int i = 0; i < cornerPoints.length; i++) {
    ellipse(cornerPoints[i].x, cornerPoints[i].y, 5, 5);
  }
}

public boolean isMouseOnPoint(PVector point){
  if (mouseX <= point.x + 10 && mouseX >= point.x - 10) {
    if (mouseY <= point.y + 10 && mouseY >= point.y - 10) {
      return true;
    }
  }
  return false;
}

public void mousePressed() {
  for (PVector point : cornerPoints) {
    if(isMouseOnPoint(point)) {
      selectedPoint = point;
    }
  }
}

public void mouseDragged() {
  if(selectedPoint != null){
    selectedPoint.x = mouseX;
    selectedPoint.y = mouseY;
    background(255);
    drawCorners();
  }
}
  public void settings() {  size(800, 800); }
  static public void main(String[] passedArgs) {
    String[] appletArgs = new String[] { "SerpinskyPoints" };
    if (passedArgs != null) {
      PApplet.main(concat(appletArgs, passedArgs));
    } else {
      PApplet.main(appletArgs);
    }
  }
}
