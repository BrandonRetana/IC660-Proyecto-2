#!/bin/bash

# Instalar Java 17 y otras herramientas necesarias
sudo dnf install -y java-17-openjdk java-17-openjdk-devel maven git

# Instalar Node.js y npm
sudo dnf install -y nodejs npm

# Verificar versiones de Java y Node.js
java -version
node -v
npm -v

# Cambiar al directorio backend y construir el backend
cd Backend
chmod 777 mvnw
./mvnw clean install

# Ejecutar el backend en segundo plano
./mvnw spring-boot:run &

# Cambiar al directorio del frontend y configurar las dependencias
cd ../Frontend
npm install
npm install react-drag-drop-files axios @types/axios --save-dev

# Construir y ejecutar el frontend
npm run dev &

echo "El proyecto está en ejecución. Puedes acceder a él desde tu navegador."

