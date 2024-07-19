# Simulación de Algoritmos de Reemplazo de Páginas

### Proyecto del curso de Sistemas Operativos  
**Instituto Tecnológico de Costa Rica**

---
## Descripción

Este proyecto implementa varios algoritmos de reemplazo de páginas en un entorno de memoria virtual, simulando su comportamiento bajo diferentes cargas de trabajo. El objetivo es analizar y comparar la eficiencia de cada uno de estos algoritmos, en términos de fallos de página y tiempos de ejecución, en un sistema de paginación.


## Características

- **Implementación de Algoritmos de Reemplazo de Páginas**:
    - **FIFO**: Reemplaza la página que ha estado más tiempo en memoria.
    - **Second Chance**: Extiende FIFO dando a las páginas una "segunda oportunidad".
    - **MRU**: Reemplaza la página utilizada más recientemente.
    - **Random**: Selecciona una página al azar para reemplazo.
    - **Optimo**: Algoritmo teórico que reemplaza la página cuyo uso en el futuro será el más lejano.
  
- **Comparación de Rendimiento**: Se analizan los tiempos de ejecución y la cantidad de fallos de página bajo diferentes patrones de acceso a la memoria para cada algoritmo.

---

## Pasos de Compilación y Ejecución

## Automatica

```bash
chmod 777 install.sh
```
```bash
./install.sh
```

## Manual

Sigue los siguientes pasos para compilar y ejecutar el proyecto:

### 1. Instalar Java 17, Maven y Git

El proyecto requiere Java 17 para el backend, Maven para gestionar las dependencias y Git para clonar el repositorio si es necesario. Ejecuta el siguiente comando para instalar las herramientas necesarias en tu sistema (Fedora o distribuciones basadas en `dnf`):

```bash
sudo dnf install -y java-17-openjdk java-17-openjdk-devel maven git
```

### 2. Instalar Node.js y npm

El frontend del proyecto requiere Node.js y npm para gestionar los paquetes de JavaScript. Ejecuta el siguiente comando para instalarlos:


```bash
sudo dnf install -y nodejs npm
```


### 3. Verificar las versiones de las herramientas instaladas

Asegúrate de que las versiones correctas de Java, Node.js y npm están instaladas ejecutando los siguientes comandos:

```bash
java -version
node -v
npm -v
```

### 4. Compilar y ejecutar el Backend

Cambia al directorio del backend:

```bash
cd Backend
```

Otorga permisos de ejecución al script mvnw:

```bash
chmod 777 mvnw
```

Construye el backend con Maven:
```bash
./mvnw clean install
```
Ejecuta el backend en segundo plano:

```bash
./mvnw spring-boot:run &
```

### 5. Configurar y ejecutar el Frontend

Cambia al directorio del frontend:

```bash
cd ../Frontend
```

Instala las dependencias del proyecto frontend:
```bash
npm install
```

Instala las dependencias adicionales necesarias para el desarrollo:

```bash
npm install react-drag-drop-files axios @types/axios --save-dev
```
Construye y ejecuta el frontend en segundo plano:

```bash
npm run dev &
```

### 6. Finalización
El backend y el frontend estarán ejecutándose en segundo plano. Para detener ambos procesos, puedes utilizar el siguiente comando para listar los procesos activos y detenerlos, reemplanzo en puerto por el 8080 y el 5173:

```bash
sudo lsof -i :<puerto> # Muestra los procesos escuando ese puerto
kill -9 <pid>  # Detiene el proceso del backend
kill -9 <pid>  # Detiene el proceso del frontend
```

Autores
* Brandon Retana Chacón
* Kevin Cubillo Chacón
* Ervin Rodríguez