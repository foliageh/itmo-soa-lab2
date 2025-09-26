# Примеры curl запросов для Windows

## Способы запуска в Windows:

### 1. PowerShell (рекомендуется)
```powershell
# Запуск полного теста
.\test-api.ps1

# Или отдельные команды:
Invoke-RestMethod -Uri "http://localhost:8080/itmo-soa-lab2-1.0/api/flats" -Method GET
```

### 2. Command Prompt (cmd)
```cmd
# Запуск полного теста
test-api.bat

# Или отдельные команды:
curl -X GET "http://localhost:8080/itmo-soa-lab2-1.0/api/flats"
```

## Отдельные curl команды для Windows:

### Создание квартиры
```cmd
curl -X POST "http://localhost:8080/itmo-soa-lab2-1.0/api/flats" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"Современная квартира\", \"coordinates\": {\"x\": 100, \"y\": 200.5}, \"area\": 85, \"number_of_rooms\": 3, \"living_space\": 65.5, \"furnish\": \"FINE\", \"transport\": \"ENOUGH\", \"house\": {\"name\": \"ЖК Солнечный\", \"year\": 360, \"number_of_floors\": 15, \"number_of_lifts\": 2}}"
```

### Получение квартиры
```cmd
curl -X GET "http://localhost:8080/itmo-soa-lab2-1.0/api/flats/1"
```

### Обновление квартиры
```cmd
curl -X PUT "http://localhost:8080/itmo-soa-lab2-1.0/api/flats/1" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"Обновленная квартира\", \"coordinates\": {\"x\": 120, \"y\": 250.0}, \"area\": 90, \"number_of_rooms\": 3, \"living_space\": 70.0, \"furnish\": \"DESIGNER\", \"transport\": \"ENOUGH\", \"house\": {\"name\": \"ЖК Плюс\", \"year\": 361, \"number_of_floors\": 18, \"number_of_lifts\": 3}}"
```

### Фильтрация квартир
```cmd
curl -X POST "http://localhost:8080/itmo-soa-lab2-1.0/api/flats/filter?pageNumber=0&pageSize=10&sortBy=area&sortDirection=asc" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": null, \"min_area\": 50, \"max_area\": 100, \"min_rooms\": null, \"max_rooms\": null, \"furnish\": null, \"transport\": null}"
```

### Удаление квартиры
```cmd
curl -X DELETE "http://localhost:8080/itmo-soa-lab2-1.0/api/flats/1"
```

## PowerShell команды (более читаемые):

### Создание квартиры через PowerShell
```powershell
$body = @{
    name = "Современная квартира"
    coordinates = @{
        x = 100
        y = 200.5
    }
    area = 85
    number_of_rooms = 3
    living_space = 65.5
    furnish = "FINE"
    transport = "ENOUGH"
    house = @{
        name = "ЖК Солнечный"
        year = 2020
        number_of_floors = 15
        number_of_lifts = 2
    }
} | ConvertTo-Json -Depth 3

Invoke-RestMethod -Uri "http://localhost:8080/itmo-soa-lab2-1.0/api/flats" -Method POST -Body $body -ContentType "application/json"
```

### Получение квартиры через PowerShell
```powershell
Invoke-RestMethod -Uri "http://localhost:8080/itmo-soa-lab2-1.0/api/flats/1" -Method GET
```

### Фильтрация через PowerShell
```powershell
$filter = @{
    name = $null
    min_area = 50
    max_area = 100
    min_rooms = $null
    max_rooms = $null
    furnish = $null
    transport = $null
} | ConvertTo-Json -Depth 2

Invoke-RestMethod -Uri "http://localhost:8080/itmo-soa-lab2-1.0/api/flats/filter?pageNumber=0&pageSize=10" -Method POST -Body $filter -ContentType "application/json"
```

## Запуск тестов:

### Вариант 1: PowerShell (рекомендуется)
```powershell
# Откройте PowerShell от имени администратора
Set-ExecutionPolicy -ExecutionPolicy RemoteSigned -Scope CurrentUser
.\test-api.ps1
```

### Вариант 2: Command Prompt
```cmd
# Откройте cmd
test-api.bat
```
