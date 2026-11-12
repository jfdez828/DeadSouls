import java.util.Random;
import java.util.Scanner;

public class Deadsouls {

    // --- CONSTANTES DE COLORES ANSI ---
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_PURPLE = "\u001B[35m";

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

        System.out.println(ANSI_CYAN + "========================================");
        System.out.println("     BIENVENIDO A DEAD SOULS            ");
        System.out.println("========================================" + ANSI_RESET);
        System.out.print("Introduce el nombre de tu heroe: ");
        nombreHeroe = scanner.nextLine().trim();

        // Validación del nombre del personaje
        if (nombreHeroe.isEmpty()) {
            nombreHeroe = "Aventurero";
            System.out.println(ANSI_YELLOW + "No has introducido ningun nombre. Se usara el predeterminado: " + nombreHeroe + ANSI_RESET);
        }

        System.out.println("\nTienes " + ANSI_YELLOW + puntosDisponibles + ANSI_RESET + " puntos para repartir en tus estadisticas.");
        System.out.println("Estadisticas base -> Vida: " + vidaMaxima + " | Ataque: " + ataqueHeroe + " | Defensa: " + defensaHeroe);

        // Bucle de reparto de puntos de estadísticas
        while (puntosDisponibles > 0) {
            System.out.println("\nTe quedan [" + ANSI_YELLOW + puntosDisponibles + ANSI_RESET + "] puntos disponibles.");
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
                            System.out.println(ANSI_GREEN + "Vida Maxima aumentada a " + vidaMaxima + "." + ANSI_RESET);
                            break;
                        case 2:
                            ataqueHeroe += (puntosAAsignar * 2);
                            System.out.println(ANSI_GREEN + "Ataque aumentado a " + ataqueHeroe + "." + ANSI_RESET);
                            break;
                        case 3:
                            defensaHeroe += puntosAAsignar;
                            System.out.println(ANSI_GREEN + "Defensa aumentada a " + defensaHeroe + "." + ANSI_RESET);
                            break;
                    }
                    puntosDisponibles -= puntosAAsignar;
                } else {
                    System.out.println(ANSI_RED + "Cantidad de puntos no valida. Debes elegir entre 1 y " + puntosDisponibles + "." + ANSI_RESET);
                }
            } else {
                System.out.println(ANSI_RED + "Opcion no valida. Selecciona 1, 2 o 3." + ANSI_RESET);
            }
        }

        // Estadísticas secundarias, Vidas e Inventario inicial tras el reparto
        int vidaActual = vidaMaxima;
        int nivel = 1;
        int experiencia = 0;
        int expSiguienteNivel = 100;
        int monedas = 50;
        int vidasHeroe = 3; 
        String[] inventario = { "Pocion de vida", "Espada oxidada", "Escudo de madera", "Vacio", "Vacio" };

        // Base de datos de Enemigos (Arrays Paralelos)
        String[] enemigosNombres = { "Duende", "Lobo Salvaje", "Esqueleto", "Orco" };
        int[] enemigosVidaMax = { 30, 45, 60, 80 };
        int[] enemigosAtaque = { 8, 12, 15, 20 };

        // Variables de progresión del Boss Final
        int enemigosDerrotados = 0;
        final int ENEMIGOS_PARA_BOSS = 10;

        System.out.println("\nPersonaje creado con exito.");
        System.out.println("Estadisticas finales -> Vidas: " + vidasHeroe + " | Vida: " + vidaMaxima + " | Ataque: " + ataqueHeroe + " | Defensa: " + defensaHeroe);
        System.out.println(ANSI_CYAN + "=========================================================================================" + ANSI_RESET);

        boolean jugando = true;

        // Bucle principal del juego
        while (jugando) {
            System.out.println(ANSI_CYAN + "\n--- MENU PRINCIPAL ---" + ANSI_RESET);
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
                        System.out.println(ANSI_RED + "No puedes explorar sin vida. Debes descansar o curarte." + ANSI_RESET);
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
                        System.out.println(ANSI_RED + "\n¡¡ EL CIELO SE VUELVE NEGRO !!" + ANSI_RESET);
                        System.out.println("Has completado tu camino. Te enfrentas al enemigo definitivo: " + ANSI_RED + enemigo + ANSI_RESET);
                        mostrarAsciiEnemigo(enemigo);
                    } else {
                        // Seleccionar enemigo común aleatorio
                        int indiceEnemigo = random.nextInt(enemigosNombres.length);
                        enemigo = enemigosNombres[indiceEnemigo];
                        enemigoVida = enemigosVidaMax[indiceEnemigo];
                        enemigoAtaque = enemigosAtaque[indiceEnemigo];

                        System.out.println(ANSI_YELLOW + "\nExplorando el reino..." + ANSI_RESET);
                        System.out.println("Te has encontrado con un " + ANSI_RED + enemigo + ANSI_RESET + " (Vida: " + enemigoVida + ", Ataque: " + enemigoAtaque + ").");
                        mostrarAsciiEnemigo(enemigo);
                    }

                    boolean enCombate = true;

                    // Bucle de combate por turnos
                    while (enCombate) {
                        System.out.println("\n----------------------------------------");
                        System.out.println("Vidas: " + ANSI_PURPLE + vidasHeroe + ANSI_RESET + " | Tu Vida: " + ANSI_GREEN + vidaActual + "/" + vidaMaxima + ANSI_RESET + " | Vida del " + enemigo + ": " + ANSI_RED + enemigoVida + ANSI_RESET);
                        System.out.println("1. Atacar");
                        System.out.println("2. Defenderse");
                        System.out.println("3. Usar Objeto (Inventario en Combate)");
                        System.out.print("Que deseas hacer?: ");

                        int accion = leerEntero(scanner);
                        boolean seDefendio = false;
                        boolean turnoValido = true;

                        if (accion == 1) {
                            int dañoAlEnemigo = Math.max(1, ataqueHeroe + random.nextInt(5) - 2);
                            enemigoVida -= dañoAlEnemigo;
                            System.out.println("Atacas al " + enemigo + " y le infringes " + ANSI_GREEN + dañoAlEnemigo + ANSI_RESET + " de daño.");
                        } else if (accion == 2) {
                            seDefendio = true;
                            System.out.println(ANSI_CYAN + "Te pones en guardia. Tu defensa aumentara este turno." + ANSI_RESET);
                        } else if (accion == 3) {
                            System.out.println(ANSI_CYAN + "\n --- INVENTARIO EN COMBATE ---" + ANSI_RESET);
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
                                        System.out.println(ANSI_YELLOW + "Tu vida ya esta al maximo. No gastas el objeto." + ANSI_RESET);
                                        turnoValido = false;
                                    } else {
                                        vidaActual = Math.min(vidaMaxima, vidaActual + 40);
                                        inventario[slot - 1] = "Vacio";
                                        System.out.println(ANSI_GREEN + "Te has tomado la Pocion de vida en mitad del combate. Recuperas hasta 40 PV. Vida actual: " + vidaActual + ANSI_RESET);
                                    }
                                } else if (objetoSeleccionado.equals("Vacio")) {
                                    System.out.println(ANSI_RED + "No hay ningun objeto en ese hueco." + ANSI_RESET);
                                    turnoValido = false;
                                } else {
                                    System.out.println(ANSI_YELLOW + "Ese objeto es pasivo, no se puede activar de forma manual. Elige otra opcion." + ANSI_RESET);
                                    turnoValido = false;
                                }
                            } else if (slot == opcionCancelar) {
                                System.out.println("Cancelando uso de objeto. Volviendo a las opciones de combate...");
                                turnoValido = false;
                            } else {
                                System.out.println(ANSI_RED + "Opcion invalida. Introduce un numero del 1 al " + opcionCancelar + "." + ANSI_RESET);
                                turnoValido = false;
                            }
                        } else {
                            System.out.println(ANSI_RED + "Opcion invalida. Pierdes el turno por dudar." + ANSI_RESET);
                        }

                        if (!turnoValido) {
                            continue;
                        }

                        // Comprobar si el enemigo murió
                        if (enemigoVida <= 0) {
                            System.out.println(ANSI_GREEN + "\nHas derrotado al " + enemigo + "." + ANSI_RESET);
                            if (esBoss) {
                                System.out.println(ANSI_GREEN + "\n========================================================");
                                System.out.println(" ¡¡ ENHORABUENA, " + nombreHeroe.toUpperCase() + " !!");
                                System.out.println(" Has derrotado al Señor de las Sombras y salvado el reino.");
                                System.out.println(" ¡HAS GANADO EL JUEGO!");
                                System.out.println("========================================================" + ANSI_RESET);
                                jugando = false;
                                enCombate = false;
                                break;
                            }
                            enemigosDerrotados++;
                            int monedasGanadas = random.nextInt(15) + 10;
                            int expGanada = random.nextInt(20) + 20;
                            monedas += monedasGanadas;
                            experiencia += expGanada;
                            System.out.println(ANSI_YELLOW + "Encontraste " + monedasGanadas + " monedas." + ANSI_RESET);
                            System.out.println(ANSI_CYAN + "Ganaste " + expGanada + " puntos de experiencia." + ANSI_RESET);
                            
                            // SUBIDA DE NIVEL EQUILIBRADA
                            if (experiencia >= expSiguienteNivel) {
                                nivel++;
                                experiencia -= expSiguienteNivel;
                                expSiguienteNivel += 50;
                                vidaMaxima += 20;
                                vidaActual = vidaMaxima;
                                ataqueHeroe += 2; 
                                defensaHeroe += 1; 
                                System.out.println(ANSI_GREEN + "\n ¡SUBISTE DE NIVEL! Ahora eres Nivel " + nivel);
                                System.out.println("Estadisticas aumentadas de forma permanente." + ANSI_RESET);
                            }
                            enCombate = false;
                            break;
                        }

                        // Turno del Enemigo
                        System.out.println(ANSI_RED + "\nEl " + enemigo + " se prepara para atacar..." + ANSI_RESET);
                        int defensaTotal = seDefendio ? (defensaHeroe * 2) : defensaHeroe;
                        int dañoAlHeroe = Math.max(1, enemigoAtaque - defensaTotal + random.nextInt(3) - 1);
                        vidaActual -= dañoAlHeroe;
                        System.out.println("El " + enemigo + " te inflige " + ANSI_RED + dañoAlHeroe + " de daño." + ANSI_RESET);

                        // Comprobar si el jugador murió
                        if (vidaActual <= 0) {
                            vidasHeroe--; 
                            System.out.println(ANSI_RED + "\nHas sido derrotado por el " + enemigo + "..." + ANSI_RESET);
                            System.out.println("¡Has perdido una vida! Vidas restantes: " + ANSI_PURPLE + vidasHeroe + "/3" + ANSI_RESET);

                            if (vidasHeroe <= 0) {
                                System.out.println(ANSI_RED + "\n========================================================");
                                System.out.println("                     GAME OVER                          ");
                                System.out.println("        Te has quedado sin vidas. Has muerto.           ");
                                System.out.println("========================================================" + ANSI_RESET);
                                jugando = false;
                                enCombate = false;
                                break;
                            }

                            if (esBoss) {
                                System.out.println(ANSI_YELLOW + "El Boss te ha destrozado. El contador de enemigos derrotados se resetea a 5." + ANSI_RESET);
                                enemigosDerrotados = 5;
                            }
                            System.out.println(ANSI_YELLOW + "Unos aldeanos te rescatan, pierdes la mitad de tus monedas y recuperas algo de salud." + ANSI_RESET);
                            monedas /= 2;
                            vidaActual = vidaMaxima / 2;
                            enCombate = false;
                        }
                    }
                    break;
                case 2:
                    // --- CONSULTAR ESTADÍSTICAS ---
                    System.out.println(ANSI_CYAN + "╔════════════════════════════════════════╗");
                    System.out.printf("║ ESTADISTICAS: %-24s ║\n", nombreHeroe.toUpperCase());
                    System.out.println("╠════════════════════════════════════════╣");
                    System.out.printf("║ Vidas:       %-25s ║\n", vidasHeroe + " / 3");
                    System.out.printf("║ Nivel:       %-25d ║\n", nivel);
                    System.out.printf("║ Experiencia: %-25s ║\n", experiencia + "/" + expSiguienteNivel);
                    System.out.printf("║ Vida Actual: %-25s ║\n", vidaActual + "/" + vidaMaxima);
                    System.out.printf("║ Ataque:      %-25d ║\n", ataqueHeroe);
                    System.out.printf("║ Defensa:     %-25d ║\n", defensaHeroe);
                    System.out.printf("║ Monedas:     %-25d ║\n", monedas);
                    System.out.println("╚════════════════════════════════════════╝" + ANSI_RESET);
                    break;
                case 3:
                    // --- VER E INTERACTUAR CON EL INVENTARIO ---
                    System.out.println(ANSI_CYAN + "\n --- INVENTARIO ---" + ANSI_RESET);
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
                                System.out.println(ANSI_YELLOW + "Tu vida ya esta al maximo." + ANSI_RESET);
                            } else {
                                vidaActual = Math.min(vidaMaxima, vidaActual + 40);
                                inventario[slot - 1] = "Vacio";
                                System.out.println(ANSI_GREEN + "Te has tomado la Pocion de vida. Recuperas hasta 40 PV. Vida actual: " + vidaActual + ANSI_RESET);
                            }
                        } else if (objetoSeleccionado.equals("Vacio")) {
                            System.out.println(ANSI_RED + "No hay ningun objeto en ese hueco." + ANSI_RESET);
                        } else {
                            System.out.println("El objeto '" + objetoSeleccionado + "' se equipa automaticamente (efecto pasivo ya aplicado).");
                        }
                    }
                    break;
                case 4:
                    // --- SISTEMA DE TIENDA ---
                    boolean enTienda = true;
                    while (enTienda) {
                        System.out.println(ANSI_CYAN + "\n --- TIENDA DEL REINO ---" + ANSI_RESET);
                        System.out.println("Tus Monedas: " + ANSI_YELLOW + monedas + ANSI_RESET);
                        System.out.println("1. Pocion de vida (20 monedas) [+40 PV]");
                        System.out.println("2. Dagas afiladas (40 monedas) [+2 ATQ] (Reemplaza arma)"); 
                        System.out.println("3. Escudo reforzado (45 monedas) [+2 DEF] (Reemplaza escudo)");
                        System.out.println("4. Salir de la tienda");
                        System.out.print("Que deseas comprar?: ");
                        int opcionTienda = leerEntero(scanner);

                        switch (opcionTienda) {
                            case 1:
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
                                        System.out.println(ANSI_GREEN + "¡Compraste una Pocion de vida!" + ANSI_RESET);
                                    } else {
                                        System.out.println(ANSI_RED + "No tienes espacio en tu inventario." + ANSI_RESET);
                                    }
                                } else {
                                    System.out.println(ANSI_RED + "No tienes suficientes monedas." + ANSI_RESET);
                                }
                                break;
                            case 2:
                                boolean tieneDagas = false;
                                for (int i = 0; i < inventario.length; i++) {
                                    if (inventario[i].equals("Dagas afiladas")) {
                                        tieneDagas = true;
                                        break;
                                    }
                                }

                                if (tieneDagas) {
                                    System.out.println(ANSI_YELLOW + "Ya posees las Dagas afiladas. No puedes comprarlas de nuevo." + ANSI_RESET);
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
                                        System.out.println(ANSI_GREEN + "¡Compraste las Dagas afiladas! Tu ataque aumenta de forma equilibrada." + ANSI_RESET);
                                    } else {
                                        System.out.println(ANSI_RED + "No tienes la Espada oxidada en el inventario para realizar el reemplazo." + ANSI_RESET);
                                    }
                                } else {
                                    System.out.println(ANSI_RED + "No tienes suficientes monedas." + ANSI_RESET);
                                }
                                break;
                            case 3:
                                boolean tieneEscudoReforzado = false;
                                for (int i = 0; i < inventario.length; i++) {
                                    if (inventario[i].equals("Escudo reforzado")) {
                                        tieneEscudoReforzado = true;
                                        break;
                                    }
                                }

                                if (tieneEscudoReforzado) {
                                    System.out.println(ANSI_YELLOW + "Ya posees el Escudo reforzado. No puedes comprarlo de nuevo." + ANSI_RESET);
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
                                        System.out.println(ANSI_GREEN + "¡Compraste el Escudo reforzado! Tu defensa aumenta permanentemente." + ANSI_RESET);
                                    } else {
                                        System.out.println(ANSI_RED + "No tienes el Escudo de madera en el inventario para realizar el reemplazo." + ANSI_RESET);
                                    }
                                } else {
                                    System.out.println(ANSI_RED + "No tienes suficientes monedas." + ANSI_RESET);
                                }
                                break;
                            case 4:
                                enTienda = false;
                                break;
                            default:
                                System.out.println(ANSI_RED + "Opcion no valida." + ANSI_RESET);
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
                    System.out.println(ANSI_RED + "Opcion no valida. Elige una opcion entre 1 y 5." + ANSI_RESET);
                    break;
            }
        }
        scanner.close();
    }

    // --- MÉTODO PARA MOSTRAR DIBUJOS ASCII SEGÚN EL ENEMIGO ---
    public static void mostrarAsciiEnemigo(String enemigo) {
        switch (enemigo) {
            case "Duende":
                System.out.println(ANSI_GREEN + " ,---." + ANSI_RESET);
                System.out.println(ANSI_GREEN + "/,---.\\" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "| \\_/ |" + ANSI_RESET);
                System.out.println(ANSI_GREEN + "\\_____/ " + ANSI_RESET);
                break;
            case "Lobo Salvaje":
                System.out.println(ANSI_YELLOW + "  /\\_/\\  " + ANSI_RESET);
                System.out.println(ANSI_YELLOW + " ( o.o ) " + ANSI_RESET);
                System.out.println(ANSI_YELLOW + "  > ^ <  " + ANSI_RESET);
                break;
            case "Esqueleto":
                System.out.println(ANSI_CYAN + "   [oo]  " + ANSI_RESET);
                System.out.println(ANSI_CYAN + "  / || \\ " + ANSI_RESET);
                System.out.println(ANSI_CYAN + "   /  \\  " + ANSI_RESET);
                break;
            case "Orco":
                System.out.println(ANSI_PURPLE + "  .---.  " + ANSI_RESET);
                System.out.println(ANSI_PURPLE + " / > < \\ " + ANSI_RESET);
                System.out.println(ANSI_PURPLE + " \\ vv  / " + ANSI_RESET);
                break;
            case "EL SEÑOR DE LAS SOMBRAS (BOSS FINAL)":
               System.out.println(ANSI_RED + "         ,     .");
                System.out.println("        /(     )\\               A");
                System.out.println("   .--.( `.___.' ).--.         /_\\\\");
                System.out.println("   `._ `%_&%#%$_ ' _.'     /| <___> |\\");
                System.out.println("     `|(@\\*%%/@)|'         / (  |L|  ) \\");
                System.out.println("      |  |%%#|   |         J d8bo|=|od8b L");
                System.out.println("       \\ \\$#%/ /         | 8888|=|8888 |");
                System.out.println("        |\\|%%#|/|         J Y8P\"|=\\" + "\"Y8P F");
                System.out.println("        | (. .)%|          \\ (  |L|  ) /");
                System.out.println("   ___.'  `-'  `.___       \\|  |L|  |/");
                System.out.println(" .'#*#`-        -'$#*`.        / )|");
                System.out.println("/#%^#%*_ *%^%_  #   %$%\\     .J (__)");
                System.out.println("#&  . %%%#% ###%*.   *%\\\\.-'&# (__)");
                System.out.println("%*  J %.%#_|_#$.\\J* \\ %'#%*^  (__)" + ANSI_RESET);
                break;
        }
    }

    // Método auxiliar para proteger saltos de línea y bucles numéricos
    public static int leerEntero(Scanner scanner) {
        while (!scanner.hasNextInt()) {
            System.out.println(ANSI_RED + "Por favor, introduce un numero valido." + ANSI_RESET);
            scanner.next();
        }
        int numero = scanner.nextInt();
        scanner.nextLine();
        return numero;
    }
}