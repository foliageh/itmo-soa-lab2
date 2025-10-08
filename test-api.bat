@echo off
chcp 65001
REM Batch скрипт для тестирования REST API квартир
REM Запуск: test-api.bat

set BASE_URL=http://localhost:8080/itmo-soa-lab2-1.0/api/flats

echo === Тестирование REST API для квартир ===

echo.
echo 1. Создание новой квартиры:
curl -X POST "%BASE_URL%" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"Современная квартира в центре\", \"coordinates\": {\"x\": 100, \"y\": 200.5}, \"area\": 85, \"number_of_rooms\": 3, \"living_space\": 65.5, \"furnish\": \"FINE\", \"transport\": \"ENOUGH\", \"house\": {\"name\": \"ЖК Солнечный\", \"year\": 360, \"number_of_floors\": 15, \"number_of_lifts\": 2}}"

echo.
echo 2. Создание еще одной квартиры:
curl -X POST "%BASE_URL%" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"Уютная студия\", \"coordinates\": {\"x\": 150, \"y\": -300.2}, \"area\": 35, \"number_of_rooms\": 1, \"living_space\": 25.0, \"furnish\": \"DESIGNER\", \"transport\": \"FEW\", \"house\": {\"name\": \"Дом на окраине\", \"year\": 355, \"number_of_floors\": 5, \"number_of_lifts\": 1}}"

echo.
echo 3. Создание третьей квартиры:
curl -X POST "%BASE_URL%" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"Просторная семейная квартира\", \"coordinates\": {\"x\": -10, \"y\": 500.8}, \"area\": 120, \"number_of_rooms\": 4, \"living_space\": 95.2, \"furnish\": \"NONE\", \"transport\": \"LITTLE\", \"house\": {\"name\": \"Элитный комплекс\", \"year\": 363, \"number_of_floors\": 25, \"number_of_lifts\": 4}}"

echo.
echo 4. Получение квартиры по ID (1):
curl -X GET "%BASE_URL%/1"

echo.
echo 5. Получение квартиры по ID (2):
curl -X GET "%BASE_URL%/2"

echo.
echo 6. Обновление квартиры с ID 1:
curl -X PUT "%BASE_URL%/1" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"Обновленная современная квартира\", \"coordinates\": {\"x\": 120, \"y\": 250.0}, \"area\": 90, \"number_of_rooms\": 3, \"living_space\": 70.0, \"furnish\": \"DESIGNER\", \"transport\": \"ENOUGH\", \"house\": {\"name\": \"ЖК Солнечный Плюс\", \"year\": 361, \"number_of_floors\": 18, \"number_of_lifts\": 3}}"

echo.
echo 7. Фильтрация квартир (все квартиры, первая страница):
curl -X POST "%BASE_URL%/filter?pageNumber=0&pageSize=2&sortBy=area&sortDirection=asc" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": null, \"min_area\": null, \"max_area\": null, \"min_rooms\": null, \"max_rooms\": null, \"furnish\": null, \"transport\": null}"

echo.
echo 8. Фильтрация квартир по площади (от 50 до 100):
curl -X POST "%BASE_URL%/filter?pageNumber=0&pageSize=10&sortBy=number_of_rooms&sortDirection=desc" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": null, \"min_area\": 50, \"max_area\": 100, \"min_rooms\": null, \"max_rooms\": null, \"furnish\": null, \"transport\": null}"

echo.
echo 9. Фильтрация квартир по количеству комнат (от 2 до 4):
curl -X POST "%BASE_URL%/filter?pageNumber=0&pageSize=5&sortBy=name&sortDirection=asc" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": null, \"min_area\": null, \"max_area\": null, \"min_rooms\": 2, \"max_rooms\": 4, \"furnish\": null, \"transport\": null}"

echo.
echo 10. Фильтрация квартир по отделке:
curl -X POST "%BASE_URL%/filter?pageNumber=0&pageSize=10" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": null, \"min_area\": null, \"max_area\": null, \"min_rooms\": null, \"max_rooms\": null, \"furnish\": \"DESIGNER\", \"transport\": null}"

echo.
echo 11. Фильтрация квартир по транспорту:
curl -X POST "%BASE_URL%/filter?pageNumber=0&pageSize=10" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": null, \"min_area\": null, \"max_area\": null, \"min_rooms\": null, \"max_rooms\": null, \"furnish\": null, \"transport\": \"ENOUGH\"}"

echo.
echo 12. Поиск квартир по названию:
curl -X POST "%BASE_URL%/filter?pageNumber=0&pageSize=10" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"квартира\", \"min_area\": null, \"max_area\": null, \"min_rooms\": null, \"max_rooms\": null, \"furnish\": null, \"transport\": null}"

echo.
echo 13. Квартиры с количеством комнат больше 2:
curl -X GET "%BASE_URL%/rooms-greater-than/2"

echo.
echo 14. Квартиры с количеством комнат больше 1:
curl -X GET "%BASE_URL%/rooms-greater-than/1"

echo.
echo 15. Удаление квартир с отделкой NONE:
curl -X DELETE "%BASE_URL%/by-furnish/NONE"

echo.
echo 16. Получение уникальных значений жилой площади:
curl -X GET "%BASE_URL%/unique-living-spaces"

echo.
echo 17. Запуск задачи поиска уникальных значений:
curl -X POST "%BASE_URL%/unique-living-spaces" -H "Content-Type: application/json"

echo.
echo 18. Отмена задачи поиска уникальных значений:
curl -X DELETE "%BASE_URL%/unique-living-spaces"

echo.
echo 19. Удаление квартиры с ID 1:
curl -X DELETE "%BASE_URL%/1"

echo.
echo 20. Попытка получить несуществующую квартиру (должна вернуть 404):
curl -X GET "%BASE_URL%/999"

echo.
echo 21. Попытка обновить несуществующую квартиру (должна вернуть 404):
curl -X PUT "%BASE_URL%/999" ^
  -H "Content-Type: application/json" ^
  -d "{\"name\": \"Несуществующая квартира\", \"coordinates\": {\"x\": 0, \"y\": 0.0}, \"area\": 50, \"number_of_rooms\": 2, \"living_space\": 40.0, \"furnish\": \"FINE\", \"transport\": \"ENOUGH\", \"house\": {\"name\": \"Тестовый дом\", \"year\": 360, \"number_of_floors\": 10, \"number_of_lifts\": 1}}"

echo.
echo === Тестирование завершено ===
pause
