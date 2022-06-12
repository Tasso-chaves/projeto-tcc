
// --- Bibliotecas Auxiliares ---
#include <ESP8266WiFi.h>
#include <WebSocketsServer.h>
#include <neotimer.h>
#include <OneWire.h>
#include <DallasTemperature.h>
#include "ArduinoJson.h"

//pinos Sensores
#define pinTemp D2
#define pinGas D4
#define buzzer D0

// --- Declaração de Objetos ---
OneWire oneWire(pinTemp);
DallasTemperature sensor(&oneWire);


const size_t capacity = 1024;
DynamicJsonDocument doc(capacity);

WebSocketsServer webSocket = WebSocketsServer(80); // Recebe dados do cliente
Neotimer mytimer = Neotimer(1000); // Intervalo de 5 segundos para envio dos dados do sensor

// Autenticação wi-fi - Coloque aqui a sua configuração
const char* ssid     = "tchaves";
const char* password = "tchaves.9141";

String estadoGas;
int temp;
int ultimaTemp = 0;

// Tratamento de eventos dos dados que vêm do cliente 
void webSocketEvent(uint8_t num, WStype_t type, uint8_t * payload, size_t lenght) {

  switch (type) {
    case WStype_DISCONNECTED:
      break;

    case WStype_CONNECTED:
      { IPAddress ip = webSocket.remoteIP(num);
        Serial.println(ip);
      }
      break;
  }
}

void setup() {
  
  Serial.begin(9600);
  pinMode(pinGas, INPUT);
  pinMode(buzzer, OUTPUT);
  
  // Conexões wi-fi e websocket
  WiFi.begin(ssid, password);
  while (WiFi.status() != WL_CONNECTED) {
    Serial.print(". ");
    delay(100);
  }
  Serial.println(WiFi.localIP());
  webSocket.begin();
  webSocket.onEvent(webSocketEvent);

}

void loop() {
  noTone(buzzer);
  
  webSocket.loop();
 
  if(digitalRead(pinGas) == LOW){
    estadoGas = "Detectado";
    tone(buzzer, 392);
    delay(200);
    noTone(buzzer); 
  }else{
    estadoGas = "Normal";
  }

  // Envio periódico dos dados do sensor de temperatura para o cliente
  if (mytimer.repeat()) {
    //tempString += readTemperature();
    String buf;
    temp = sensor.getTempCByIndex(0);
    sensor.requestTemperatures();
    if(temp != ultimaTemp){
      ultimaTemp = temp;
      doc["temp"] = temp;
    }
    
    doc["gas"] = estadoGas;
    serializeJson(doc, buf);
     
    //Envia para todos os clientes
    webSocket.broadcastTXT(buf);
  }
}
