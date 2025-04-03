# Script to run only the Puntos de Interés microservice

Write-Host "Starting Puntos de Interés microservice with Docker Compose..." -ForegroundColor Green

# Make sure we're in the right directory
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptPath

# Check if Gis_create.sql exists in the current directory
if (-Not (Test-Path ".\Gis_create.sql")) {
    Write-Host "Copying Gis_create.sql to the current directory..." -ForegroundColor Yellow
    Copy-Item ".\Downloads\Gis_create.sql" ".\Gis_create.sql" -ErrorAction SilentlyContinue
}

# Run docker-compose for Puntos de Interés service only
docker-compose down 
docker-compose build --no-cache 
docker-compose up -d 

Write-Host "Puntos de Interés Service is starting up..." -ForegroundColor Green
Write-Host "- Puntos de Interés Service: http://localhost:8082/swagger-ui.html" -ForegroundColor Cyan
