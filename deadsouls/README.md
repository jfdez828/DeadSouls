¡Bienvenido a **Dead Souls**! Este es un videojuego de rol y aventura de texto desarrollado en Java para la consola. El proyecto ha sido diseñado y programado desde cero como parte de la entrega de la **Primera Evaluación** para el módulo de Programación de **1.º de DAW (Curso 2026/2027)**.

## 📝 Descripción y Objetivo del Juego
En *Dead Souls*, asumes el papel de un valiente aventurero que debe explorar un reino hostil plagado de monstruos. El objetivo principal es **derrotar a 10 enemigos comunes** para limpiar las tierras y hacer aparecer al jefe definitivo: **El Señor de las Sombras**. La partida termina con una victoria absoluta si logras vencer al Boss, o con una derrota si tu salud llega a cero en cualquier combate.

## ⚔️ Reglas Básicas del Juego
*   **Creación de Personaje:** Al iniciar, dispones de 10 puntos de estadística iniciales para repartir libremente entre Vida (+10 PV), Ataque (+2 ATQ) y Defensa (+1 DEF).
*   **Combate por Turnos:** Durante la exploración, te enfrentarás de forma aleatoria a Duendes, Lobos, Esqueletos u Orcos. Cada turno podrás elegir entre:
    *   *Atacar:* Inflige daño al enemigo basado en tu estadística de ataque (con una ligera variación aleatoria).
    *   *Defenderse:* Duplica tu estadística de defensa durante ese turno para mitigar el daño recibido.
    *   *Usar Objeto:* Permite abrir un inventario rápido para consumir pociones de salud en pleno combate.
*   **Progresión y Recompensas:** Al derrotar enemigos, conseguirás monedas y puntos de experiencia. Al acumular suficiente experiencia, subirás de nivel, incrementando tus estadísticas máximas de forma permanente.
*   **La Tienda del Reino:** Podrás gastar tus monedas acumuladas en adquirir pociones de vida o equipamiento pasivo (Dagas afiladas o Escudo reforzado) que mejoran tus atributos de combate de forma permanente.
*   **Penalización por Derrota:** Si mueres contra un enemigo común, unos aldeanos te rescatarán a cambio de perder la mitad de tus monedas actuales. Si mueres contra el Boss Final, el contador de enemigos derrotados se reiniciará a 5.

## 🛠️ Requisitos Previos
Para poder compilar y ejecutar este videojuego, necesitas tener instalado en tu sistema:
*   **Java Development Kit (JDK):** Versión 17 o superior.
*   Una terminal de comandos (CMD, PowerShell, Bash) o un Entorno de Desarrollo Integrado (IDE) como VS Code, IntelliJ IDEA o NetBeans.

## 🚀 Instrucciones para Compilar y Ejecutar

### Opción 1: Desde la Consola de Comandos
1. Abre tu terminal y navega hasta la carpeta del proyecto donde se encuentra el archivo fuente:
   ```bash
   cd ruta/al/proyecto/src
   ```
2. Compila el archivo fuente utilizando el compilador de Java (`javac`):
   ```bash
   javac Deadsouls.java
   ```
3. Ejecuta el programa compilado con la máquina virtual de Java (`java`):
   ```bash
   java Deadsouls
   ```

### Opción 2: Desde un IDE (VS Code / IntelliJ / NetBeans)
1. Importa o abre la carpeta raíz del proyecto en tu IDE favorito.
2. Asegúrate de que el entorno detecta correctamente el SDK de Java configurado.
3. Busca el archivo `Deadsouls.java` en el explorador de archivos.
4. Haz clic derecho sobre el archivo y selecciona la opción **Run** (o pulsa el botón de *Play* en la esquina superior derecha).
