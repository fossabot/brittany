#ifndef BRITTANY_MOCK_DIGITAL_LIGHT_MODULE_H
#define BRITTANY_MOCK_DIGITAL_LIGHT_MODULE_H

#include <list>
#include "operation-handler/OperationHandler.h"
#include "modules/ComponentModule.h"
#include "mock-digital-light/hw/MockDigitalLightHw.h"
#include "mock-digital-light/operation-handler/MockIsOnDigitalLightHandler.h"
#include "mock-digital-light/operation-handler/MockTurnOffDigitalLightHandler.h"
#include "mock-digital-light/operation-handler/MockTurnOnDigitalLightHandler.h"
#include "modules/ModuleNames.h"

class MockDigitalLightModule : public ComponentModule<MockDigitalLightHw> {

public:

    MockDigitalLightModule(std::list<MockDigitalLightHw*> components)
    : ComponentModule<MockDigitalLightHw>(module_as_string(ModuleNames::Light), components) {
        _handlers.push_back(new MockIsOnDigitalLightHandler(components));
        _handlers.push_back(new MockTurnOnDigitalLightHandler(components));  
        _handlers.push_back(new MockTurnOffDigitalLightHandler(components));
    };

};

#endif //BRITTANY_MOCK_DIGITAL_LIGHT_MODULE_H