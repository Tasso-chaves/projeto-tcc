

// --- Bibliotecas Auxiliares ---
#include <OneWire.h>
#include <DallasTemperature.h>

//pinos Sensores
#define pinTemp 10
#define pinGas 3
#define buzzer 5


// --- Declaração de Objetos ---
OneWire oneWire(pinTemp);
DallasTemperature sensor(&oneWire);

int gas, gasAux, temperatura;

String separador = "#";

void setup()
{
 
  Serial.begin(9600);

  pinMode(pinGas, INPUT);
  pinMode(buzzer, OUTPUT);
  sensor.begin();      //inicializa sensores
  
}

void loop(void)
{ 
  
  noTone(buzzer); 
  sensor.requestTemperatures();
  temperatura = sensor.getTempCByIndex(0);
  gas = digitalRead(pinGas);
  
  //Leituras Monitor Serial
  Serial.print(temperatura);
  Serial.print(separador);
  if(gas == LOW){
    gasAux = 1;
    tone(buzzer, 392);
    
  }else{
    gasAux = 0;
  }
  
  Serial.println(gasAux);
  
  delay(1000);
  
}
