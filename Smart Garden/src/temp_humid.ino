#include <OneWire.h>
#include <DallasTemperature.h>
#include <SoftwareSerial.h>
SoftwareSerial EEblue(10, 11);
 
// Data wire is plugged into pin 2 on the Arduino
#define ONE_WIRE_BUS 2

String incoming = "";
String command = "";
String temp = "temp";
String umid = "umid";
int rainPin = A1;
int redLED = 7;
int pump = 6;
// you can adjust the threshold value
int thresholdValue = 800;
 
// Setup a oneWire instance to communicate with any OneWire devices 
// (not just Maxim/Dallas temperature ICs)
OneWire oneWire(ONE_WIRE_BUS);
 
// Pass our oneWire reference to Dallas Temperature.
DallasTemperature sensors(&oneWire);
 
void setup(void)
{
  Serial.begin(9600);
  EEblue.begin(9600);
  
  Serial.println("Citire temperatura si umiditate sol.");
  // umiditate
  pinMode(rainPin, INPUT);
  pinMode(redLED, OUTPUT);
  pinMode(pump, OUTPUT);
  digitalWrite(redLED, LOW);
  digitalWrite(pump, HIGH);

  // Start up the library
  sensors.begin();
}
 
 
void loop(void)
{
  // verificam daca a venit vreun mesaj pe seriala
  if (EEblue.available()) {
    incoming = EEblue.readString();
    command = incoming.substring(0, 4);
  }
  //if (Serial.available())
    //EEblue.write(Serial.read());
  // temperatura
  if (command.compareTo(temp) == 0) {
    sensors.requestTemperatures(); // send command to get temperature
    Serial.print("Temperature is: ");
    Serial.println(sensors.getTempCByIndex(0)); // Why "byIndex"? 
      // You can have more than one IC on the same bus. 
      // 0 refers to the first IC on the wire
  }
  // umiditate
  if (command.compareTo(umid) == 0) {
    int sensorValue = analogRead(rainPin);
    Serial.print("Humidity is: ");
    Serial.print(sensorValue);
    if(sensorValue < thresholdValue){
      Serial.println(" - Doesn't need watering");
      digitalWrite(redLED, LOW);
      digitalWrite(pump, HIGH);
    }
    else {
      Serial.println(" - Time to water your plant");
      digitalWrite(redLED, HIGH);
      digitalWrite(pump,LOW);
      delay(10000);
      digitalWrite(pump,HIGH);
    }
  }
  incoming = "done";
  command = "done";
}
