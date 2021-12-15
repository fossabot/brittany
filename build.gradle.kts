tasks.register<Exec>("authDevUp"){
    commandLine("docker-compose", "-f", "auth-service-dev-compose.yml", "-p", "auth-service-dev", "up", "-d")
}

tasks.register<Exec>("authDevLog"){
    commandLine("docker-compose", "-f", "auth-service-dev-compose.yml", "-p", "auth-service-dev", "logs", "-f", "auth")
}

tasks.register<Exec>("authDevDown"){
    commandLine("docker-compose", "-f", "auth-service-dev-compose.yml", "-p", "auth-service-dev", "down")
}

tasks.register<Exec>("persistenceDevUp"){
    commandLine("docker-compose", "-f", "persistence-service-dev-compose.yml", "-p", "persistence-service-dev", "up", "-d")
}

tasks.register<Exec>("persistenceDevLog"){
    commandLine("docker-compose", "-f", "persistence-service-dev-compose.yml", "-p", "persistence-service-dev", "logs", "-f", "persistence")
}

tasks.register<Exec>("persistenceDevDown"){
    commandLine("docker-compose", "-f", "persistence-service-dev-compose.yml", "-p", "persistence-service-dev", "down")
}

tasks.register<Exec>("coreDevUp"){
    commandLine("docker", "run", "-d", "--rm", "--name", "greenhouse-core-dev", "alessandrotalmi/brittany-greenhouse-core-dev:latest")
}

tasks.register<Exec>("coreDevLog"){
    commandLine("docker", "logs", "-f", "greenhouse-core-dev")
}

tasks.register<Exec>("coreDevDown"){
    commandLine("docker", "stop", "greenhouse-core-dev")
}

