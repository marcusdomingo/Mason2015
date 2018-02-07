#include <Servo.h>

//Servo initialization
Servo myServo;

//Initialization of Pin Numebrs on Arduino
int ledPin = 12;
int buttonPin = 13;
int servoPin = 11;

//Initialization of button pressed and angle of servo
int buttonValue;
int angle;

void setup()
{
  //serial port at 9600 bits/sec
  Serial.begin(9600);
  
  //attach the servo to the pin
  myServo.attach(servoPin);
  
  //set the servo angle to 0
  myServo.write(0);
}

void loop()
{
  //read the current angle of the arduino
  angle = myServo.read();
  
  //read the if the button is pushed
  buttonValue = digitalRead(buttonPin);
  
  //if button is pushed
  if (buttonValue == HIGH)
  {
    //light the LED
    digitalWrite(ledPin, HIGH);
    int temp = angle;   //set temporary value to angle
    angle = temp + 10;  //add 10 to the current angle read
    
    //if the angle is greater than 180 set the angle and the servo back to 0
    if (angle > 180)
    {
      myServo.write(0);
      angle = 0;
    }
    
    //write the angle to the servo
    myServo.write(angle);
  }
  else
  {
    //button not pushed so don't light LED
    digitalWrite(ledPin, LOW);
  }
  
  //print the current angle to the serial monitor
  Serial.println(angle);
  
  //delay 1 second
  delay(1000);
}
