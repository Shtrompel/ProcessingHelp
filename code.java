
// Distance between every squiggly line
public static final float CONST_DISTANCE = 30.0f; 
// Maxmimum size of a squiggly line
public static final float CONST_AMPLITUTE = 30.0f; 
// The "speed" of a squiggly line
public static final float CONST_FREQUENCY = 0.04f; 
// Stroke weight, how wide each squiggly lines is
public static final float CONST_WEIGHT = 3.0f; 
// How blured the result is
public static final float CONST_BLUR = 2.0f;
// How much black or white there is (0 - completely white, 1 - completely black)
public static final float CONST_THRESHOLD = 0.55f;
// Set to false to see the result without any effects
public static final boolean CONST_ENABLE_EFFECTS = true;


public PImage img;
// Render the sketch in a seperate bigger PGraphics object and rescale it
public PGraphics render;

// For drawing an array of squiggly lines
public void drawLines(int ix, int iy, float d, float a, float f, boolean offset) {
  render.pushMatrix();
  render.translate(render.width/2, render.height/2);
  render.rotate(PI / 4.0f);
  
  if (offset)
    render.translate(0, d/2.0f);
  
  for (int i = -ix/2; i < ix/2; i += d) {
    render.noFill();
    render.beginShape();
    for (int j = -iy/2; j < iy/2; j++) {
      float x = j;
      float y = a * sin(j * f) + i;
      render.vertex(x, y);
    }
    render.endShape();
  }
  
  render.popMatrix();
}

public void settings() {
  size(640, 640, P2D);
}

public void setup() {
  
  render = createGraphics(2*width, 2*height, P2D);
  render.smooth(2);
  
  img = loadImage("img.jpg");
  if (img == null) {
    println("No image was loaded!");
    System.exit(1);
  }
  
}

public void draw() {
  
  render.beginDraw();
  render.background(255);
  render.strokeWeight(CONST_WEIGHT);
  
  // Draw the image
  render.image(img, 0, 0, render.width, render.height);
 
  // Draw black lines
  render.stroke(0);
  drawLines(
      2*render.width,
      2*render.height,
      CONST_DISTANCE,
      CONST_AMPLITUTE,
      CONST_FREQUENCY,
      false
      );
  
  // Draw white lines
  render.stroke(255);
  drawLines(
      2*render.width,
      2*render.height,
      CONST_DISTANCE,
      CONST_AMPLITUTE,
      CONST_FREQUENCY,
      true
      );
  
  if (CONST_ENABLE_EFFECTS) {
    render.filter(BLUR, CONST_WEIGHT);
    render.filter(THRESHOLD, CONST_THRESHOLD);
  }
  
  render.endDraw();
  
  image(render, 0, 0, width, height);
}