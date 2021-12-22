#ifndef BRITTANY_THING_DESCRIPTOR_BUILDER_H
#define BRITTANY_THING_DESCRIPTOR_BUILDER_H

#include "td_util.h"
#include "json/json.h"
#include "modules/Module.h"
#include <string>

using namespace TD;

class ThingDescriptorBuilder {

public:

    static Json::Value build(std::string ip, int port, std::list<Module*> modules) {
        Json::Value td;
        add_context(td);
        add_id(td, ip, port);
        add_title(td);
        add_security(td);
        return td;
    };

private:

    static void add_context(Json::Value &td) { 
        td[key(Key::CONTEXT)] = value(Key::CONTEXT);
    }

    static void add_title(Json::Value &td) { 
        td["title"] = "Mock Title";
    }
        
    static void add_id(Json::Value &td, std::string ip, int port) { 
        td["id"] = "http://" + ip + ":" + std::to_string(port);
    }

    static void add_security(Json::Value &td) { 
        Json::Value no_sec_obj;
        no_sec_obj[key(Key::SECURITY_DEFINITIONS_SCHEME)] = value(Key::SECURITY_DEFINITIONS_SCHEME);
        no_sec_obj[key(Key::SECURITY_DEFINITIONS_IN)] = value(Key::SECURITY_DEFINITIONS_IN);
        Json::Value security_def_obj;
        security_def_obj[value(Key::SECURITY)] = no_sec_obj;
        td[key(Key::SECURITY_DEFINITIONS)] = security_def_obj;
        td[key(Key::SECURITY)][0] = value(Key::SECURITY);
    }

};


#endif //BRITTANY_THING_DESCRIPTOR_BUILDER_H