# 🎮 Tic Tac Toe 2

<div align="center">

![Android](https://img.shields.io/badge/Android-3DDC84?style=for-the-badge&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/kotlin-%237F52FF.svg?style=for-the-badge&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/Jetpack%20Compose-4285F4?style=for-the-badge&logo=jetpackcompose&logoColor=white)

**Una versión alternativa del clásico TicTacToe, también conocido como "Gato" o "Tres en Raya"**

</div>

## 🎯 Descripción del Juego

TicTacToe2 es una variante del original, con una nueva **regla de límite de 3 símbolos**, que añade profundidad estratégica y evita los empates. Los jugadores deben pensar con anticipación ya que sus símbolos más antiguos desaparecen al querer colocar un cuarto símbolo.

## 🎨 Capturas de Pantalla

<img width="425" height="441" alt="ss" src="https://github.com/user-attachments/assets/55ed75db-8a14-476e-bdb6-ef23109cb3e1" />

### 🎲 Mecánicas Únicas del Juego

- **Símbolos Limitados**: Cada jugador solo puede tener 3 símbolos en el tablero simultáneamente
- **Símbolos Débiles**: Al colocar un 4º símbolo, el más antiguo se vuelve "débil" y puede ser sobrescrito
- **Profundidad Estratégica**: No más empates - los juegos siempre llegan a una conclusión
- **Mejor de 3**: El primer jugador en ganar 3 rondas logra la victoria definitiva

## 🏗️ Arquitectura Técnica

### 🛠️ Construido Con
- **Kotlin** 2.0.21
- **Jetpack Compose** - Kit de herramientas UI moderno
- **Arquitectura MVVM** - Separación limpia de responsabilidades
- **Corrutinas** - Programación asíncrona
- **StateFlow** - Gestión de estado reactiva

### 📱 Requisitos de Android
- **SDK Objetivo**: 35 (Android 14)
- **SDK Mínimo**: 26 (Android 8.0)
- **SDK de Compilación**: 35


### 🎨 Componentes UI
```
├── GameScreen          # Interfaz principal del juego
├── SplashScreen        # Pantalla de inicio de la app
├── GameBoard           # Componente de cuadrícula 3x3
├── GameCell            # Lógica de celda individual
├── ScoreContainer      # Seguimiento de victorias
└── TurnIndicator       # Visualización del jugador actual
```

## 🎮 Cómo Jugar

1. **Inicio**: Los jugadores se ubican frente a frente, se turnan colocando símbolos X y O. El símbolo activo señala de quien es el turno.
2. **Límite**: Cada jugador puede tener máximo 3 símbolos en el tablero
3. **Símbolos Débiles**: La 4ª colocación hace que el símbolo más antiguo sea "débil" (atenuado)
4. **Captura**: Los símbolos débiles pueden ser sobrescritos por el oponente
5. **Ganar Ronda**: Conseguir 3 símbolos fuertes en línea
6. **Ganar Juego**: El primero en ganar 3 rondas logra la victoria definitiva

### 🎯 Consejos Estratégicos
- Planifica la colocación de símbolos cuidadosamente
- Anticipa cuándo los símbolos se vuelven débiles
- Controla el centro para máximas opciones

## 🚀 Instalación

### Prerrequisitos
- Android Studio Arctic Fox o más reciente
- JDK 11 o superior
- Android SDK 35

### Instrucciones de Compilación
```bash
# Clonar el repositorio
git clone https://github.com/tuusuario/TicTacToe2.git

# Navegar al directorio del proyecto
cd TicTacToe2

# Compilar APK de debug
./gradlew assembleDebug

# Instalar en dispositivo
./gradlew installDebug
```



## 🔧 Desarrollo

### Estructura del Proyecto
```
app/
├── src/main/java/dev/javfuentes/tictactoe2/
│   ├── domain/
│   │   ├── manager/        # Gestión de sonidos
│   │   ├── model/          # Modelos de estado del juego
│   │   └── usecase/        # Lógica de negocio
│   ├── ui/
│   │   ├── components/     # Componentes UI reutilizables
│   │   ├── screens/        # Pantallas de la app
│   │   ├── theme/          # Sistema de diseño
│   │   └── viewmodel/      # Gestión de estado
│   └── utils/              # Clases utilitarias
└── src/main/res/
    ├── drawable/           # Símbolos X/O (fuertes/débiles)
    ├── font/              # Tipografía Romanus
    ├── raw/               # Archivos de audio
    └── values-*/          # Strings localizados
```

## 🌟 Contribuir

¡Las contribuciones son bienvenidas! Por favor, siéntete libre de enviar un Pull Request.

### Configuración de Desarrollo
1. Hacer fork del repositorio
2. Crear una rama de característica (`git checkout -b feature/CaracteristicaIncreible`)
3. Confirmar cambios (`git commit -m 'Agregar CaracteristicaIncreible'`)
4. Push a la rama (`git push origin feature/CaracteristicaIncreible`)
5. Abrir un Pull Request

## 📄 Licencia

```
MIT License

Copyright (c) 2023 Javier Fuentes

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

For more information about the author and his projects, visit http://javierfuentes.dev
```

## 👨‍💻 Author

**Javier Fuentes**
- GitHub: [@javfuentes](https://github.com/javfuentes)
- Email: contacto@javierfuentes.dev


---

<div align="center">

**¿Te gustó el proyecto? ¡Dale una ⭐!**

<a href='https://ko-fi.com/I2I2OPHE0' target='_blank'>
  <img height='36' src='https://storage.ko-fi.com/cdn/kofi6.png?v=6' alt='Cómprame un café en ko-fi.com' />
</a>

</div>