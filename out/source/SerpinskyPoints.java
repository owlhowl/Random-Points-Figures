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

int pointsNumber = 6;           // Number of starting points
int iterationsPerFrame = 5000;  // Something like an animation speed
int gridCellSize = 50;          // Grid cell size in pixels, 0 to disable grid
int pointsOpacity = 64;         // Drawn points opacity 0-255
boolean snapToGrid = true;      // Corners snap to grid when dragging

PVector[] cornerPoints;
PVector drawnPoint;
PVector randomPoint;
PVector selectedPoint;

public void setup() {
  
  background(255);
  drawGrid();
  cornerPoints = new PVector[pointsNumber];
  for (int i = 0; i < cornerPoints.length; i++) {
    cornerPoints[i] = new PVector();
    cornerPoints[i].x = random(0, width);
    cornerPoints[i].y = random(0, height);
  }
  PVector startPoint = cornerPoints[0];
  drawnPoint = new PVector();
  drawnPoint.x = startPoint.x;
  drawnPoint.y = startPoint.y;
}

public void draw() {
  drawCorners();
  stroke(0, pointsOpacity);
  strokeWeight(1);
  for (int i = 0; i < iterationsPerFrame; i++) { 
    randomPoint = cornerPoints[(int)random(pointsNumber)];
    drawnPoint.x = (drawnPoint.x + randomPoint.x)/2;
    drawnPoint.y = (drawnPoint.y + randomPoint.y)/2;
    point(drawnPoint.x, drawnPoint.y);
  }
}

public void drawGrid(){
  if (gridCellSize == 0){
    return;
  }
  stroke(0xffDFDFDF);
  noFill();
  for (int i = 0; i < width; i += gridCellSize) {
    for (int j = 0; j < height; j += gridCellSize) {
      rect(i, j, gridCellSize, gridCellSize);
    }
  }
}

public void drawCorners(){
  noStroke();
  fill(200, 0, 0);
  for (int i = 0; i < cornerPoints.length; i++) {
    ellipse(cornerPoints[i].x, cornerPoints[i].y, 5, 5);
  }
}

public boolean isMouseOnPoint(PVector point){
  if (mouseX <= point.x + gridCellSize/2 && mouseX >= point.x - gridCellSize/2) {
    if (mouseY <= point.y + gridCellSize/2 && mouseY >= point.y - gridCellSize/2) {
      return true;
    }
  }
  return false;
}

public void mousePressed() {
  for (int i = 0; i < cornerPoints.length; i++) {
    selectedPoint = cornerPoints[i];
    if(isMouseOnPoint(cornerPoints[i])) {
      return;
    }
    else{
      selectedPoint = null;
    }
  }
}

float prevX, prevY;

public void mouseDragged() {
  if(selectedPoint == null){
    return;
  }
  if(snapToGrid){
    prevX = selectedPoint.x;
    prevY = selectedPoint.y;
    selectedPoint.x = (mouseX + gridCellSize/2) - ((mouseX + gridCellSize/2) % gridCellSize);
    selectedPoint.y = (mouseY + gridCellSize/2) - ((mouseY + gridCellSize/2) % gridCellSize);
    if(selectedPoint.x != prevX || selectedPoint.y != prevY){
      background(255);
      drawGrid();
      drawCorners();
    }
  }
  else {
    selectedPoint.x = mouseX;
    selectedPoint.y = mouseY;    
    background(255);
    drawGrid();
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
