#!/bin/bash
# Script to run the GIS microservices system

echo -e "\e[32mStarting GIS microservices with Docker Compose...\e[0m"

# Make sure we're in the right directory
cd "$(dirname "$0")"

# Check if Gis_create.sql exists in the current directory
if [ ! -f "./Gis_create.sql" ]; then
    echo -e "\e[33mCopying Gis_create.sql to the current directory...\e[0m"
    cp ./Downloads/Gis_create.sql ./Gis_create.sql 2>/dev/null || true
fi

# Run docker-compose
docker-compose down
docker-compose build --no-cache
docker-compose up -d

echo -e "\e[32mServices are starting up...\e[0m"
echo -e "\e[36m- PostgreSQL DB: localhost:5433\e[0m"
echo -e "\e[36m- Usuario Service: http://localhost:8080/swagger-ui.html\e[0m"
echo -e "\e[36m- Transport Service: http://localhost:8081/swagger-ui.html\e[0m"
echo -e "\e[36m- Puntiint Service: http://localhost:8082/swagger-ui.html\e[0m"
