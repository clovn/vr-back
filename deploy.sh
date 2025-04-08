#!/bin/bash

REBUILD_DB=false

for arg in "$@"; do
  if [[ $arg == "--rebuild-db" ]]; then
    REBUILD_DB=true
  fi
done

PROJECT_NAME=$(basename $(pwd) | tr '[:upper:]' '[:lower:]')

APP_SERVICE_NAME="app"
DB_SERVICE_NAME="db"

echo "Очищаю контейнер и образ для сервиса '$APP_SERVICE_NAME'..."

APP_CONTAINER_NAME="${PROJECT_NAME}-${APP_SERVICE_NAME}-1"
if docker ps -a --filter "name=$APP_CONTAINER_NAME" | grep -q $APP_CONTAINER_NAME; then
  echo "Останавливаю и удаляю контейнер '$APP_CONTAINER_NAME'..."
  docker stop $APP_CONTAINER_NAME > /dev/null 2>&1
  docker rm $APP_CONTAINER_NAME > /dev/null 2>&1
else
  echo "Контейнер для сервиса '$APP_SERVICE_NAME' не найден."
fi

APP_IMAGE_ID=$(docker images --filter=reference="${PROJECT_NAME}-${APP_SERVICE_NAME}" --format "{{.ID}}")
if [ -n "$APP_IMAGE_ID" ]; then
  echo "Удаляю старый образ приложения '$APP_IMAGE_ID'..."
  docker rmi $APP_IMAGE_ID > /dev/null 2>&1
else
  echo "Образ для сервиса '$APP_SERVICE_NAME' не найден."
fi

if $REBUILD_DB; then
  echo "Очищаю контейнер и том для сервиса '$DB_SERVICE_NAME'..."

  DB_CONTAINER_NAME="${PROJECT_NAME}-${DB_SERVICE_NAME}-1"
  if docker ps -a --filter "name=$DB_CONTAINER_NAME" | grep -q $DB_CONTAINER_NAME; then
    echo "Останавливаю и удаляю контейнер '$DB_CONTAINER_NAME'..."
    docker stop $DB_CONTAINER_NAME > /dev/null 2>&1
    docker rm $DB_CONTAINER_NAME > /dev/null 2>&1
  else
    echo "Контейнер для сервиса '$DB_SERVICE_NAME' не найден."
  fi

  VOLUME_NAME="${PROJECT_NAME}_postgres_data"
  if docker volume ls --filter "name=$VOLUME_NAME" | grep -q $VOLUME_NAME; then
    echo "Удаляю том '$VOLUME_NAME'..."
    docker volume rm $VOLUME_NAME > /dev/null 2>&1
  else
    echo "Том для сервиса '$DB_SERVICE_NAME' не найден."
  fi
fi

echo "Собираю новый .jar файл..."
mvn clean package

echo "Пересобираю Docker-образы и запускаю контейнеры..."
docker-compose up -d --build

echo "Проверяю статус контейнеров..."
docker ps