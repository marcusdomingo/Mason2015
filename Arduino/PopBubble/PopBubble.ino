/***************************************************
  This is our touchscreen painting example for the Adafruit ILI9341
  captouch shield
  ----> http://www.adafruit.com/products/1947
  Check out the links above for our tutorials and wiring diagrams
  Adafruit invests time and resources providing this open source code,
  please support Adafruit and open-source hardware by purchasing
  products from Adafruit!
  Written by Limor Fried/Ladyada for Adafruit Industries.
  MIT license, all text above must be included in any redistribution
 ****************************************************/


#include <Adafruit_GFX.h>    // Core graphics library
#include <SPI.h>       // this is needed for display
#include <Adafruit_ILI9341.h>
#include <Wire.h>      // this is needed for FT6206
#include <Adafruit_FT6206.h>

/*
Screen size:
240x320
*/
int MAX_WIDTH = 240;
int MAX_HEIGHT = 320;

// The FT6206 uses hardware I2C (SCL/SDA)
Adafruit_FT6206 ctp = Adafruit_FT6206();

// The display also uses hardware SPI, plus #9 & #10
#define TFT_CS 10
#define TFT_DC 9
/*#define 111 ILI9341_PINK
#define 112 ILI9341_BLACK
#define 113 ILI9341_YELLOW
#define 114 ILI9341_GREEN
#define 115 ILI9341_NAVY
#define 116 ILI9341_MAROON*/

Adafruit_ILI9341 tft = Adafruit_ILI9341(TFT_CS, TFT_DC);

// Size of the color selection boxes and the paintbrush size
int oldcolor, currentcolor;

class Bubble
{
  private:
    int x;
    int y;
    int radius;
    int color;
  
  public:
    void drawBubble();
    void deltaX(int delta); //Changes x by delta
    void deltaY(int delta); //Changes y by delta

    Bubble(int initX, int initY, int initRadius, int initColor);
};

Bubble::Bubble(int initX, int initY, int initRadius, int initColor)
{
  x = initX;
  y = initY;
  radius = initRadius;
  color = initColor;
}

void Bubble::drawBubble()
{
   tft.drawCircle(x, y, radius, color);
   //Clear the inside of the circle to make it look like a bubble
   tft.drawCircle(x,y,radius,ILI9341_BLACK);
   
   if(x > MAX_WIDTH-radius)
   {
     x = 0;
   }
   else if(x < 0)
   {
     x = MAX_WIDTH- radius;
   }
}

void Bubble::deltaX(int delta)
{
  x+=delta;
}

void Bubble::deltaY(int delta)
{
  y+=delta;
}


//Bubbles
Bubble *bubble0;
Bubble *bubble1;
Bubble *bubble2;
Bubble *bubble3;
Bubble *bubble4;
Bubble *bubble5;
Bubble *bubble6;
Bubble *bubble7;
Bubble *bubble8;
Bubble *bubble9;

void setup(void) {
  while (!Serial);     // used for leonardo debugging
 
 //115200 was the old .begin value
  Serial.begin(9600);
  Serial.println(("Pop Bubbles"));
  
  tft.begin();

  if (!ctp.begin(40)) {  // pass in 'sensitivity' coefficient
    Serial.println("Couldn't start FT6206 touchscreen controller");
    while (1);
  }

  Serial.println("Capacitive touchscreen started");
  
  tft.fillScreen(ILI9341_BLACK);
  
  bubble0 = new Bubble(10,10,20,ILI9341_YELLOW);
  bubble1 = new Bubble(200,300,20,ILI9341_YELLOW);
  bubble2 = new Bubble(150,150,15,ILI9341_YELLOW);
  bubble3 = new Bubble(40,70,25,ILI9341_YELLOW);
  bubble4 = new Bubble(10,320,3,ILI9341_YELLOW);
  bubble5 = new Bubble(70,25,40,ILI9341_YELLOW);
  bubble6 = new Bubble(80,100,30,ILI9341_YELLOW);
  bubble7 = new Bubble(100,80,5,ILI9341_YELLOW);
  bubble8 = new Bubble(180,250,10,ILI9341_YELLOW);
  bubble9 = new Bubble(111,280,20,ILI9341_YELLOW);
}

//Speed for the bubbles
int speed = 2;

void loop() {
  
  int randomNum = random(); //std::rand;
  
  bubble0->drawBubble();
  bubble0->deltaX(speed);
  bubble1->drawBubble();
  bubble1->deltaX(speed);
  bubble2->drawBubble();
  bubble2->deltaX(speed);
  bubble3->drawBubble();
  bubble3->deltaX(speed);
  bubble4->drawBubble();
  bubble4->deltaX(speed);
  bubble5->drawBubble();
  bubble5->deltaX(speed);
  bubble6->drawBubble();
  bubble6->deltaX(speed);
  bubble7->drawBubble();
  bubble7->deltaX(speed);
  bubble8->drawBubble();
  bubble8->deltaX(speed);
  bubble9->drawBubble();
  bubble9->deltaX(speed);
}
