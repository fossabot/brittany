#ifndef BRITTANY_MOCK_TURN_OFF_DIGITAL_LIGHT_HANDLER_INTERFACE_H
#define BRITTANY_MOCK_TURN_OFF_DIGITAL_LIGHT_HANDLER_INTERFACE_H

#include <list>
#include <string>
#include <json/json.h>
#include "operation-handler/interfaces/RetrieveValueFromComponentInterface.h"
#include "mock-digital-light/hw/MockDigitalLightHw.h"
#include "operation-handler/OperationHandlerResult.h"
#include "util.h"

class MockTurnOffDigitalLightHandler
: public RetrieveValueFromComponentInterface<MockDigitalLightHw, std::string> {

public:

    MockTurnOffDigitalLightHandler(
        std::string name,
        std::string path,
        std::list<MockDigitalLightHw*> components
    ): RetrieveValueFromComponentInterface(
        name,
        path,
        OperationType::ACTION,
        Type::STRING,
        components
    ) {

    };

private:

    std::optional<std::string> sub_operation(MockDigitalLightHw* hw, Json::Value args) {
        hw -> off();
        return std::optional(phrase(ContentResult::Ok));
    };
    
};

#endif //BRITTANY_MOCK_TURN_OFF_DIGITAL_LIGHT_HANDLER_INTERFACE_H
