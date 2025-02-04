FROM maven:3.9.6-eclipse-temurin-17-alpine

# Устанавливаем рабочую директорию
WORKDIR /app

# Копируем файлы проекта
COPY . .

# Выполняем тесты
CMD ["mvn", "test", "-Dtest=FreestyleProjectApiTest,MockUserTest"]