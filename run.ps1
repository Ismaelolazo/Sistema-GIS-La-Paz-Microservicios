# Script to run the GIS microservices system - Only Micros service and DB

Write-Host "Starting Micro Transport service and PostgreSQL DB with Docker Compose..." -ForegroundColor Green

# Make sure we're in the right directory
$scriptPath = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $scriptPath

# Check if Gis_create.sql exists in the current directory
if (-Not (Test-Path ".\Gis_create.sql")) {
    Write-Host "Copying Gis_create.sql to the current directory..." -ForegroundColor Yellow
    Copy-Item ".\Downloads\Gis_create.sql" ".\Gis_create.sql" -ErrorAction SilentlyContinue
}

# Run docker-compose with only postgres and transport_micros services
docker-compose down
Write-Host "Building and starting only postgres and transport_micros services..." -ForegroundColor Yellow
docker-compose build --no-cache postgres transport_micros_service
docker-compose up -d postgres transport_micros_service

Write-Host "Services are starting up..." -ForegroundColor Green
Write-Host "- PostgreSQL DB: localhost:5433" -ForegroundColor Cyan
Write-Host "- Transport Micros Service: http://localhost:8081/swagger-ui.html" -ForegroundColor Cyan
