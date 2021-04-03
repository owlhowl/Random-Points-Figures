int pointsNumber = 7;           // Number of starting points
int iterationsPerFrame = 5000;  // Something like an animation speed
int gridCellSize = 50;          // Grid cell size in pixels
int pointsOpacity = 64;         // Drawn points opacity 0-255
boolean snapToGrid = true;      // Corners snap to grid when dragging

PVector[] cornerPoints;
PVector drawnPoint;
PVector randomPoint;
PVector selectedPoint;

void setup() {
  size(800, 800);
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

void draw() {
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

void drawGrid(){
  stroke(#DFDFDF);
  noFill();
  for (int i = 0; i < width; i += gridCellSize) {
    for (int j = 0; j < height; j += gridCellSize) {
      rect(i, j, gridCellSize, gridCellSize);
    }
  }
}

void drawCorners(){
  noStroke();
  fill(200, 0, 0);
  for (int i = 0; i < cornerPoints.length; i++) {
    ellipse(cornerPoints[i].x, cornerPoints[i].y, 5, 5);
  }
}

boolean isMouseOnPoint(PVector point){
  if (mouseX <= point.x + 10 && mouseX >= point.x - 10) {
    if (mouseY <= point.y + 10 && mouseY >= point.y - 10) {
      return true;
    }
  }
  return false;
}

void mousePressed() {
  for (PVector point : cornerPoints) {
    if(isMouseOnPoint(point)) {
      selectedPoint = point;
    }
  }
}

void mouseDragged() {
  if(selectedPoint != null){
    if(snapToGrid){
      selectedPoint.x = mouseX - (mouseX % gridCellSize);
      selectedPoint.y = mouseY - (mouseY % gridCellSize);
    }
    else {
      selectedPoint.x = mouseX;
      selectedPoint.y = mouseY;
    }
    background(255);
    drawGrid();
    drawCorners();
  }
}