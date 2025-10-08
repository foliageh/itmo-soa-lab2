# PowerShell скрипт для тестирования REST API квартир
# Запуск: .\test-api.ps1
chcp 65001

$baseUrl = "http://localhost:8080/itmo-soa-lab2-1.0/api/flats"

Write-Host "=== Тестирование REST API для квартир ===" -ForegroundColor Green

# 1. Создание новой квартиры
Write-Host "`n1. Создание новой квартиры:" -ForegroundColor Yellow
$body1 = @{
    name = "Современная квартира в центре"
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
        year = 360
        number_of_floors = 15
        number_of_lifts = 2
    }
} | ConvertTo-Json -Depth 3

Invoke-RestMethod -Uri $baseUrl -Method POST -Body $body1 -ContentType "application/json"

Write-Host "`n2. Создание еще одной квартиры:" -ForegroundColor Yellow
$body2 = @{
    name = "Уютная студия"
    coordinates = @{
        x = 150
        y = -300.2
    }
    area = 35
    number_of_rooms = 1
    living_space = 25.0
    furnish = "DESIGNER"
    transport = "FEW"
    house = @{
        name = "Дом на окраине"
        year = 355
        number_of_floors = 5
        number_of_lifts = 1
    }
} | ConvertTo-Json -Depth 3

Invoke-RestMethod -Uri $baseUrl -Method POST -Body $body2 -ContentType "application/json"

Write-Host "`n3. Создание третьей квартиры:" -ForegroundColor Yellow
$body3 = @{
    name = "Просторная семейная квартира"
    coordinates = @{
        x = -10
        y = 500.8
    }
    area = 120
    number_of_rooms = 4
    living_space = 95.2
    furnish = "NONE"
    transport = "LITTLE"
    house = @{
        name = "Элитный комплекс"
        year = 363
        number_of_floors = 25
        number_of_lifts = 4
    }
} | ConvertTo-Json -Depth 3

Invoke-RestMethod -Uri $baseUrl -Method POST -Body $body3 -ContentType "application/json"

# 2. Получение квартиры по ID
Write-Host "`n4. Получение квартиры по ID (1):" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/1" -Method GET
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n5. Получение квартиры по ID (2):" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/2" -Method GET
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

# 3. Обновление квартиры
Write-Host "`n6. Обновление квартиры с ID 1:" -ForegroundColor Yellow
$updateBody = @{
    name = "Обновленная современная квартира"
    coordinates = @{
        x = 120
        y = 250.0
    }
    area = 90
    number_of_rooms = 3
    living_space = 70.0
    furnish = "DESIGNER"
    transport = "ENOUGH"
    house = @{
        name = "ЖК Солнечный Плюс"
        year = 361
        number_of_floors = 18
        number_of_lifts = 3
    }
} | ConvertTo-Json -Depth 3

try {
    Invoke-RestMethod -Uri "$baseUrl/1" -Method PUT -Body $updateBody -ContentType "application/json"
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

# 4. Фильтрация квартир с пагинацией
Write-Host "`n7. Фильтрация квартир (все квартиры, первая страница):" -ForegroundColor Yellow
$filterBody1 = @{
    name = $null
    min_area = $null
    max_area = $null
    min_rooms = $null
    max_rooms = $null
    furnish = $null
    transport = $null
} | ConvertTo-Json -Depth 2

try {
    Invoke-RestMethod -Uri "$baseUrl/filter?pageNumber=0&pageSize=2&sortBy=area&sortDirection=asc" -Method POST -Body $filterBody1 -ContentType "application/json"
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n8. Фильтрация квартир по площади (от 50 до 100):" -ForegroundColor Yellow
$filterBody2 = @{
    name = $null
    min_area = 50
    max_area = 100
    min_rooms = $null
    max_rooms = $null
    furnish = $null
    transport = $null
} | ConvertTo-Json -Depth 2

try {
    Invoke-RestMethod -Uri "$baseUrl/filter?pageNumber=0&pageSize=10&sortBy=number_of_rooms&sortDirection=desc" -Method POST -Body $filterBody2 -ContentType "application/json"
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n9. Фильтрация квартир по количеству комнат (от 2 до 4):" -ForegroundColor Yellow
$filterBody3 = @{
    name = $null
    min_area = $null
    max_area = $null
    min_rooms = 2
    max_rooms = 4
    furnish = $null
    transport = $null
} | ConvertTo-Json -Depth 2

try {
    Invoke-RestMethod -Uri "$baseUrl/filter?pageNumber=0&pageSize=5&sortBy=name&sortDirection=asc" -Method POST -Body $filterBody3 -ContentType "application/json"
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n10. Фильтрация квартир по отделке:" -ForegroundColor Yellow
$filterBody4 = @{
    name = $null
    min_area = $null
    max_area = $null
    min_rooms = $null
    max_rooms = $null
    furnish = "DESIGNER"
    transport = $null
} | ConvertTo-Json -Depth 2

try {
    Invoke-RestMethod -Uri "$baseUrl/filter?pageNumber=0&pageSize=10" -Method POST -Body $filterBody4 -ContentType "application/json"
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

# 5. Получение квартир с количеством комнат больше указанного
Write-Host "`n11. Квартиры с количеством комнат больше 2:" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/rooms-greater-than/2" -Method GET
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n12. Квартиры с количеством комнат больше 1:" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/rooms-greater-than/1" -Method GET
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

# 6. Удаление квартир по типу отделки
Write-Host "`n13. Удаление квартир с отделкой NONE:" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/by-furnish/NONE" -Method DELETE
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

# 7. Уникальные значения жилой площади (заглушки)
Write-Host "`n14. Получение уникальных значений жилой площади:" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/unique-living-spaces" -Method GET
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n15. Запуск задачи поиска уникальных значений:" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/unique-living-spaces" -Method POST -ContentType "application/json"
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n16. Отмена задачи поиска уникальных значений:" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/unique-living-spaces" -Method DELETE
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

# 8. Удаление квартиры по ID
Write-Host "`n17. Удаление квартиры с ID 1:" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/1" -Method DELETE
} catch {
    Write-Host "Ошибка: $($_.Exception.Message)" -ForegroundColor Red
}

# 9. Тестирование ошибок
Write-Host "`n18. Попытка получить несуществующую квартиру (должна вернуть 404):" -ForegroundColor Yellow
try {
    Invoke-RestMethod -Uri "$baseUrl/999" -Method GET
} catch {
    Write-Host "Ожидаемая ошибка 404: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n19. Попытка обновить несуществующую квартиру (должна вернуть 404):" -ForegroundColor Yellow
$invalidBody = @{
    name = "Несуществующая квартира"
    coordinates = @{
        x = 0
        y = 0.0
    }
    area = 50
    number_of_rooms = 2
    living_space = 40.0
    furnish = "FINE"
    transport = "ENOUGH"
    house = @{
        name = "Тестовый дом"
        year = 360
        number_of_floors = 10
        number_of_lifts = 1
    }
} | ConvertTo-Json -Depth 3

try {
    Invoke-RestMethod -Uri "$baseUrl/999" -Method PUT -Body $invalidBody -ContentType "application/json"
} catch {
    Write-Host "Ожидаемая ошибка 404: $($_.Exception.Message)" -ForegroundColor Red
}

Write-Host "`n=== Тестирование завершено ===" -ForegroundColor Green
