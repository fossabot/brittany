#include <Arduino.h>
#include <json_util.h>
#include <list>
#include "web-server/Esp8266WebServer.h"
#include "wifi_secret.h"
#include "modules/Module.h"

#include <ESP8266WiFi.h>

#include "modules/concrete/concrete-dht22.h"
#include "modules/mock/mock-digital-light.h"
#include "modules/mock/mock-temperature-humidity.h"
#include "modules/mock/mock-temperature-humidity-sensor.h"
#include "modules/mock/mock-temperature-humidity-actuator.h"

Esp8266WebServer* server;

void setup_variables() {
    server = new Esp8266WebServer(edge());
}

void connect_to_wifi() {
    WiFi.begin(WIFI_SSID, WIFI_PSWD);
    Serial.print("Connecting to ");
    Serial.print(WIFI_SSID);
    while (WiFi.status() != WL_CONNECTED) {
        delay(1000);
        Serial.print('.');
    }
    Serial.println('\n');
    Serial.println("Connection established!");  
    Serial.print("IP address:\t");
    Serial.println(WiFi.localIP());  
}

void setup() {
    Serial.begin(115200);
    connect_to_wifi();
    setup_variables();
    server -> begin();
}

void loop() {
    server -> handleClient();
}
