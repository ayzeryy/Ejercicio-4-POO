import java.util.List;
import java.util.Scanner;

public class VistaConsola {
    private Scanner sc = new Scanner(System.in);

    public void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    public void mostrarEstado(BatallaController controller) {
        System.out.println("----- ESTADO DE BATALLA -----");
        System.out.println("Jugadores:");
        List<Jugador> jugadores = controller.obtenerJugadores();
        for (Jugador j : jugadores) {
            System.out.println("- " + j.getNombre() + " HP: " + j.getVidaActual() + "/" + j.getVidaMaxima() + " Ataque: " + j.getAtaqueBase());
        }
        System.out.println("Enemigos:");
        List<Enemigo> enemigos = controller.obtenerEnemigos();
        for (Enemigo e : enemigos) {
            System.out.println("- " + e.getNombre() + " HP: " + e.getVidaActual() + "/" + e.getVidaMaxima());
        }
        System.out.println("----------------------------");
    }

    public int leerOpcionJugador(Jugador jugador) {
        System.out.println("Acciones disponibles para " + jugador.getNombre() + ":");
        System.out.println("1) Atacar (objetivo aleatorio)");
        System.out.println("2) Usar habilidad especial");
        System.out.println("3) Usar item");
        System.out.print("Elige una opción (1-3): ");
        String linea = sc.nextLine();
        try {
            return Integer.parseInt(linea.trim());
        } catch (Exception e) {
            return -1;
        }
    }

    public int leerIndiceItem(Jugador jugador) {
        System.out.println("Inventario de " + jugador.getNombre() + ":");
        List<Item> inv = jugador.getInventario();
        if (inv.isEmpty()) {
            System.out.println("No tienes items.");
            return -1;
        }
        for (int i = 0; i < inv.size(); i++) {
            Item it = inv.get(i);
            System.out.println(i + ") " + it.getNombre() + " x" + it.getCantidad() + " - " + it.getDescripcion());
        }
        System.out.print("Elige índice de item para usar: ");
        String linea = sc.nextLine();
        try {
            return Integer.parseInt(linea.trim());
        } catch (Exception e) {
            return -1;
        }
    }
}
