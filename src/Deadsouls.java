import java.util.Random;
import java.util.Scanner;

public class Deadsouls {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random random = new Random();

        // Variables de identificación
        String nombreHeroe = "Aventurero";

        // --- SISTEMA DE CREACIÓN DE PERSONAJE (1ª Evaluación) ---
        // Estadísticas base fijas antes del reparto
        int vidaMaxima = 80;
        int ataqueHeroe = 10;
        int defensaHeroe = 3;
        int puntosDisponibles = 10; // Puntos extra para repartir

        System.out.println("========================================");
        System.out.println("     BIENVENIDO A DEAD SOULS            ");
        System.out.println("========================================");
        System.out.print("Introduce el nombre de tu heroe: ");
        nombreHeroe = scanner.nextLine().trim();

        // Validación del nombre del personaje (si está vacío, conserva "Aventurero")
        if (nombreHeroe.isEmpty()) {
            nombreHeroe = "Aventurero";
            System.out.println("No has introducido ningun nombre. Se usara el predeterminado: " + nombreHeroe);
        }

        System.out.println("\nTienes " + puntosDisponibles + " puntos para repartir en tus estadisticas.");
        System.out.println("Estadisticas base -> Vida: " + vidaMaxima + " | Ataque: " + ataqueHeroe + " | Defensa: " + defensaHeroe);

        // Bucle de reparto de puntos de estadísticas
        while (puntosDisponibles > 0) {
            System.out.println("\nTe quedan [" + puntosDisponibles + "] puntos disponibles.");
            System.out.println("1. Aumentar Vida (+10 PV por punto)");
            System.out.println("2. Aumentar Ataque (+2 ATQ por punto)");
            System.out.println("3. Aumentar Defensa (+1 DEF por punto)");
            System.out.print("Que estadistica deseas mejorar? (1-3): ");

            int eleccionStat = leerEntero(scanner);

            if (eleccionStat >= 1 && eleccionStat <= 3) {
                System.out.print("Cuantos puntos quieres asignarle?: ");
                int puntosAAsignar = leerEntero(scanner);

                if (puntosAAsignar > 0 && puntosAAsignar <= puntosDisponibles) {
                    switch (eleccionStat) {
                        case 1:
                            vidaMaxima += (puntosAAsignar * 10);
                            System.out.println("Vida Maxima aumentada a " + vidaMaxima + ".");
                            break;
                        case 2:
                            ataqueHeroe += (puntosAAsignar * 2);
                            System.out.println("Ataque aumentado a " + ataqueHeroe + ".");
                            break;
                        case 3:
                            defensaHeroe += puntosAAsignar;
                            System.out.println("Defensa aumentada a " + defensaHeroe + ".");
                            break;
                    }
                    puntosDisponibles -= puntosAAsignar;
                } else {
                    System.out.println("Cantidad de puntos no valida. Debes elegir entre 1 y " + puntosDisponibles + ".");
                }
            } else {
                System.out.println("Opcion no valida. Selecciona 1, 2 o 3.");
            }
        }

        // Estadísticas secundarias e Inventario inicial tras el reparto
        int vidaActual = vidaMaxima;
        int nivel = 1;
        int experiencia = 0;
        int expSiguienteNivel = 100;
        int monedas = 50;
        String[] inventario = { "Pocion de vida", "Espada oxidada", "Escudo de madera", "Vacio", "Vacio" };

        // Arrays Paralelos de datos de Enemigos
        String[] enemigosNombres = { "Duende", "Lobo Salvaje", "Esqueleto", "Orco" };
        int[] enemigosVidaMax = { 30, 45, 60, 80 };
        int[] enemigosAtaque = { 8, 12, 15, 20 };

        // Variables de progresión del Boss Final (Declarado como constante final)
        int enemigosDerrotados = 0;
        final int ENEMIGOS_PARA_BOSS = 10;

        System.out.println("\nPersonaje creado con exito.");
        System.out.println("Estadisticas finales -> Vida: " + vidaMaxima + " | Ataque: " + ataqueHeroe + " | Defensa: " + defensaHeroe);
        System.out.println("=========================================================================================");

        boolean jugando = true;

        // Bucle principal del juego
        while (jugando) {
            System.out.println("\n--- MENU PRINCIPAL ---");
            System.out.println("1. Explorar el reino (" + enemigosDerrotados + "/" + ENEMIGOS_PARA_BOSS + " derrotados)");
            System.out.println("2. Consultar Estadisticas");
            System.out.println("3. Ver Inventario / Usar Objeto");
            System.out.println("4. Visitar la Tienda");
            System.out.println("5. Salir del juego");
            System.out.print("Selecciona una opcion: ");

            int opcion = leerEntero(scanner);

            switch (opcion) {
                case 1:
                    // --- SISTEMA DE EXPLORACIÓN Y COMBATE ---
                    if (vidaActual <= 0) {
                        System.out.println("No puedes explorar sin vida. Debes descansar o curarte.");
                        break;
                    }

                    String enemigo;
                    int enemigoVida;
                    int enemigoAtaque;
                    boolean esBoss = false;

                    // Comprobar si se ha alcanzado el límite para invocar al Boss Final
                    if (enemigosDerrotados >= ENEMIGOS_PARA_BOSS) {
                        esBoss = true;
                        enemigo = "EL SEÑOR DE LAS SOMBRAS (BOSS FINAL)";
                        enemigoVida = 180;
                        enemigoAtaque = 28;
                        System.out.println("\n¡¡ EL CIELO SE VUELVE NEGRO !!");
                        System.out.println("Has completado tu camino. Te enfrentas al enemigo definitivo: " + enemigo);
                    } else {
                        // Seleccionar enemigo común aleatorio
                        int indiceEnemigo = random.nextInt(enemigosNombres.length);
                        enemigo = enemigosNombres[indiceEnemigo];
                        enemigoVida = enemigosVidaMax[indiceEnemigo];
                        enemigoAtaque = enemigosAtaque[indiceEnemigo];

                        System.out.println("\nExplorando el reino...");
                        System.out.println("Te has encontrado con un " + enemigo + " (Vida: " + enemigoVida + ", Ataque: " + enemigoAtaque + ").");
                    }

                    boolean enCombate = true;

                    // Bucle de combate por turnos
                    while (enCombate) {
                        System.out.println("\n----------------------------------------");
                        System.out.println("Tu Vida: " + vidaActual + "/" + vidaMaxima + " | Vida del " + enemigo + ": " + enemigoVida);
                        System.out.println("1. Atacar");
                        System.out.println("2. Defenderse");
                        System.out.println("3. Usar Objeto (Inventario en Combate)");
                        System.out.print("Que deseas hacer?: ");

                        int accion = leerEntero(scanner);
                        boolean seDefendio = false;
                        boolean turnoValido = true;

                        if (accion == 1) {
                            // Turno del Jugador: Atacar (Modificador aleatorio entre -2 y 2)
                            int dañoAlEnemigo = Math.max(1, ataqueHeroe + random.nextInt(5) - 2);
                            enemigoVida -= dañoAlEnemigo;
                            System.out.println("Atacas al " + enemigo + " y le infringes " + dañoAlEnemigo + " de daño.");
                        } else if (accion == 2) {
                            // Turno del Jugador: Defenderse
                            seDefendio = true;
                            System.out.println("Te pones en guardia. Tu defensa aumentara este turno.");
                        } else if (accion == 3) {
                            // Turno del Jugador: Abrir el inventario rápido en pleno combate
                            System.out.println("\n --- INVENTARIO EN COMBATE ---");
                            for (int i = 0; i < inventario.length; i++) {
                                System.out.println((i + 1) + ". " + inventario[i]);
                            }
                            int opcionCancelar = inventario.length + 1;
                            System.out.println(opcionCancelar + ". Cancelar y volver");
                            System.out.print("Selecciona un objeto para usar: ");
                            int slot = leerEntero(scanner);

                            if (slot >= 1 && slot <= inventario.length) {
                                String objetoSeleccionado = inventario[slot - 1];
                                if (objetoSeleccionado.equals("Pocion de vida")) {
                                    if (vidaActual == vidaMaxima) {
                                        System.out.println("Tu vida ya esta al maximo. No gastas el objeto.");
                                        turnoValido = false;
                                    } else {
                                        vidaActual = Math.min(vidaMaxima, vidaActual + 40);
                                        inventario[slot - 1] = "Vacio";
                                        System.out.println("Te has tomado la Pocion de vida en mitad del combate. Recuperas hasta 40 PV. Vida actual: " + vidaActual);
                                    }
                                } else if (objetoSeleccionado.equals("Vacio")) {
                                    System.out.println("No hay ningun objeto en ese hueco.");
                                    turnoValido = false;
                                } else {
                                    System.out.println("Ese objeto es pasivo, no se puede activar de forma manual. Elige otra opcion.");
                                    turnoValido = false;
                                }
                            } else if (slot == opcionCancelar) {
                                // Distinguir claramente la cancelación de una opción inválida
                                System.out.println("Cancelando uso de objeto. Volviendo a las opciones de combate...");
                                turnoValido = false;
                            } else {
                                System.out.println("Opcion invalida. Introduce un numero del 1 al " + opcionCancelar + ".");
                                turnoValido = false;
                            }
                        } else {
                            System.out.println("Opcion invalida. Pierdes el turno por dudar.");
                        }

                        // Reiniciar el turno del bucle si el jugador canceló el inventario o cometió un error
                        if (!turnoValido) {
                            continue;
                        }

                        // Comprobar si el enemigo murió
                        if (enemigoVida <= 0) {
                            System.out.println("\nHas derrotado al " + enemigo + ".");
                            // Si se derrota al Boss Final se acaba la partida con victoria
                            if (esBoss) {
                                System.out.println("\n========================================================");
                                System.out.println(" ¡¡ ENHORABUENA, " + nombreHeroe.toUpperCase() + " !!");
                                System.out.println(" Has derrotado al Señor de las Sombras y salvado el reino.");
                                System.out.println(" ¡HAS GANADO EL JUEGO!");
                                System.out.println("========================================================");
                                jugando = false;
                                enCombate = false;
                                break;
                            }
                            // Recompensas por enemigos comunes
                            enemigosDerrotados++;
                            int monedasGanadas = random.nextInt(15) + 10;
                            int expGanada = random.nextInt(20) + 20;
                            monedas += monedasGanadas;
                            experiencia += expGanada;
                            System.out.println("Encontraste " + monedasGanadas + " monedas.");
                            System.out.println("Ganaste " + expGanada + " puntos de experiencia.");
                            
                            // Sistema de subida de nivel
                            if (experiencia >= expSiguienteNivel) {
                                nivel++;
                                experiencia -= expSiguienteNivel;
                                expSiguienteNivel += 50;
                                vidaMaxima += 20;
                                vidaActual = vidaMaxima; // Rellenar vida al subir de nivel o dejar max
                                ataqueHeroe += 5;
                                defensaHeroe += 2;
                                System.out.println("\n SUBISTE DE NIVEL. Ahora eres Nivel " + nivel);
                                System.out.println("Estadisticas aumentadas de forma permanente.");
                            }
                            enCombate = false;
                            break;
                        }

                        // Turno del Enemigo
                        System.out.println("\nEl " + enemigo + " se prepara para atacar...");
                        int defensaTotal = seDefendio ? (defensaHeroe * 2) : defensaHeroe;
                        int dañoAlHeroe = Math.max(1, enemigoAtaque - defensaTotal + random.nextInt(3) - 1);
                        vidaActual -= dañoAlHeroe;
                        System.out.println("El " + enemigo + " te inflige " + dañoAlHeroe + " de daño.");

                        // Comprobar si el jugador murió
                        if (vidaActual <= 0) {
                            System.out.println("\nHas sido derrotado por el " + enemigo + "...");
                            if (esBoss) {
                                System.out.println("El Boss te ha destrozado. El contador de enemigos derrotados se resetea a 5.");
                                enemigosDerrotados = 5;
                            }
                            System.out.println("Unos aldeanos te rescatan, pero pierdes la mitad de tus monedas.");
                            monedas /= 2;
                            vidaActual = vidaMaxima / 2;
                            enCombate = false;
                        }
                    }
                    break;
                case 2:
                    // --- CONSULTAR ESTADÍSTICAS ---
                    System.out.println("\n --- ESTADISTICAS DE " + nombreHeroe.toUpperCase() + " ---");
                    System.out.println("Nivel: " + nivel);
                    System.out.println("Experiencia: " + experiencia + "/" + expSiguienteNivel);
                    System.out.println("Vida: " + vidaActual + "/" + vidaMaxima);
                    System.out.println("Ataque: " + ataqueHeroe);
                    System.out.println("Defensa: " + defensaHeroe);
                    System.out.println("Monedas: " + monedas);
                    break;
                case 3:
                    // --- VER E INTERACTUAR CON EL INVENTARIO ---
                    System.out.println("\n --- INVENTARIO ---");
                    for (int i = 0; i < inventario.length; i++) {
                        System.out.println((i + 1) + ". " + inventario[i]);
                    }
                    int slotMenu = inventario.length + 1;
                    System.out.println(slotMenu + ". Volver al menu");
                    System.out.print("Selecciona un objeto para usar: ");
                    int slot = leerEntero(scanner);
                    if (slot >= 1 && slot <= inventario.length) {
                        String objetoSeleccionado = inventario[slot - 1];
                        if (objetoSeleccionado.equals("Pocion de vida")) {
                            if (vidaActual == vidaMaxima) {
                                System.out.println("Tu vida ya esta al maximo.");
                            } else {
                                vidaActual = Math.min(vidaMaxima, vidaActual + 40);
                                inventario[slot - 1] = "Vacio";
                                System.out.println("Te has tomado la Pocion de vida. Recuperas hasta 40 PV. Vida actual: " + vidaActual);
                            }
                        } else if (objetoSeleccionado.equals("Vacio")) {
                            System.out.println("No hay ningun objeto en ese hueco.");
                        } else {
                            System.out.println("El objeto '" + objetoSeleccionado + "' se equipa automaticamente (efecto pasivo ya aplicado).");
                        }
                    }
                    break;
                case 4:
                    // --- SISTEMA DE TIENDA CORREGIDO ---
                    boolean enTienda = true;
                    while (enTienda) {
                        System.out.println("\n --- TIENDA DEL REINO ---");
                        System.out.println("Tus Monedas: " + monedas);
                        System.out.println("1. Pocion de vida (20 monedas) [+40 PV]");
                        System.out.println("2. Dagas afiladas (40 monedas) [+4 ATQ] (Reemplaza arma)");
                        System.out.println("3. Escudo reforzado (45 monedas) [+2 DEF] (Reemplaza escudo)");
                        System.out.println("4. Salir de la tienda");
                        System.out.print("Que deseas comprar?: ");
                        int opcionTienda = leerEntero(scanner);

                        switch (opcionTienda) {
                            case 1:
                                // Compra de Consumible: Verificar dinero y hueco libre ("Vacio")
                                if (monedas >= 20) {
                                    int huecoVacio = -1;
                                    for (int i = 0; i < inventario.length; i++) {
                                        if (inventario[i].equals("Vacio")) {
                                            huecoVacio = i;
                                            break;
                                        }
                                    }
                                    if (huecoVacio != -1) {
                                        monedas -= 20;
                                        inventario[huecoVacio] = "Pocion de vida";
                                        System.out.println("¡Compraste una Pocion de vida!");
                                    } else {
                                        System.out.println("No tienes espacio en tu inventario.");
                                    }
                                } else {
                                    System.out.println("No tienes suficientes monedas.");
                                }
                                break;
                            case 2:
                                // Comprobar si ya tiene las dagas y verificar que tiene la espada oxidada para reemplazar
                                boolean tieneDagas = false;
                                for (int i = 0; i < inventario.length; i++) {
                                    if (inventario[i].equals("Dagas afiladas")) {
                                        tieneDagas = true;
                                        break;
                                    }
                                }

                                if (tieneDagas) {
                                    System.out.println("Ya posees las Dagas afiladas. No puedes comprarlas de nuevo.");
                                } else if (monedas >= 40) {
                                    int posEspada = -1;
                                    for (int i = 0; i < inventario.length; i++) {
                                        if (inventario[i].equals("Espada oxidada")) {
                                            posEspada = i;
                                            break;
                                        }
                                    }
                                    if (posEspada != -1) {
                                        monedas -= 40;
                                        ataqueHeroe += 4;
                                        inventario[posEspada] = "Dagas afiladas";
                                        System.out.println("¡Compraste las Dagas afiladas! Tu ataque aumenta permanentemente.");
                                    } else {
                                        System.out.println("No tienes la Espada oxidada en el inventario para realizar el reemplazo.");
                                    }
                                } else {
                                    System.out.println("No tienes suficientes monedas.");
                                }
                                break;
                            case 3:
                                // Corrección: Comprobar si ya posee el escudo reforzado y verificar que tiene el escudo de madera
                                boolean tieneEscudoReforzado = false;
                                for (int i = 0; i < inventario.length; i++) {
                                    if (inventario[i].equals("Escudo reforzado")) {
                                        tieneEscudoReforzado = true;
                                        break;
                                    }
                                }

                                if (tieneEscudoReforzado) {
                                    System.out.println("Ya posees el Escudo reforzado. No puedes comprarlo de nuevo.");
                                } else if (monedas >= 45) {
                                    int posEscudo = -1;
                                    for (int i = 0; i < inventario.length; i++) {
                                        if (inventario[i].equals("Escudo de madera")) {
                                            posEscudo = i;
                                            break;
                                        }
                                    }
                                    if (posEscudo != -1) {
                                        monedas -= 45;
                                        defensaHeroe += 2;
                                        inventario[posEscudo] = "Escudo reforzado";
                                        System.out.println("¡Compraste el Escudo reforzado! Tu defensa aumenta permanentemente.");
                                    } else {
                                        System.out.println("No tienes el Escudo de madera en el inventario para realizar el reemplazo.");
                                    }
                                } else {
                                    System.out.println("No tienes suficientes monedas.");
                                }
                                break;
                            case 4:
                                enTienda = false;
                                break;
                            default:
                                System.out.println("Opcion no valida.");
                                break;
                        }
                    }
                    break;
                case 5:
                    // --- SALIR ---
                    System.out.println("Gracias por jugar, " + nombreHeroe + ". Tu aventura termina aqui por hoy.");
                    jugando = false;
                    break;
                default:
                    System.out.println("Opcion no valida. Elige una opcion entre 1 y 5.");
                    break;
            }
        }
        scanner.close();
    }

    // Método auxiliar para proteger saltos de línea y bucles numéricos
    public static int leerEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println("Por favor, introduce un numero valido.");
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }
}