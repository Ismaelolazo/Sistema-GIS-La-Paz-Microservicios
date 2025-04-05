# Script to run the GIS microservices system

Write-Host "Starting GIS microservices with Docker Compose..." -ForegroundColor Green

# Make sure we're in the right directory
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptPath

# Check if Gis_create.sql exists in the current directory
if (-Not (Test-Path ".\Gis_create.sql")) {
    Write-Host "Copying Gis_create.sql to the current directory..." -ForegroundColor Yellow
    Copy-Item ".\Downloads\Gis_create.sql" ".\Gis_create.sql" -ErrorAction SilentlyContinue
}

# Run docker-compose
docker-compose down
docker-compose build --no-cache
docker-compose up -d

Write-Host "Services are starting up..." -ForegroundColor Green
Write-Host "- PostgreSQL DB: localhost:5433" -ForegroundColor Cyan
Write-Host "- Usuario Service: http://localhost:8080/swagger-ui.html" -ForegroundColor Cyan
Write-Host "- Transport Service: http://localhost:8081/swagger-ui.html" -ForegroundColor Cyan
Write-Host "- Puntos de Interes Service: http://localhost:8082/swagger-ui.html" -ForegroundColor Cyan
Write-Host "Ensure that PuntosInt_service is running correctly and connected to the database." -ForegroundColor Green
