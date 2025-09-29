import java.util.ArrayList;
import java.util.List;

public class Jugador extends Combatiente {
    private List<Item> inventario;
    private String rol;

    public Jugador(String nombre, int vidaMaxima, int ataqueBase, String rol) {
        super(nombre, vidaMaxima, ataqueBase);
        this.rol = rol;
        this.inventario = new ArrayList<>();
    }

    public List<Item> getInventario() {
        return inventario;
    }

    public String getRol() {
        return rol;
    }

    public void agregarItem(Item item) {
        if (item != null) {
            inventario.add(item);
        }
    }

    public void usarItem(int indice, BatallaController controller) {
        if (indice < 0 || indice >= inventario.size()) {
            mostrarMensaje("Índice de item inválido.");
            return;
        }
        Item item = inventario.get(indice);
        item.usar(this, controller);
        if (item.getCantidad() <= 0) {
            inventario.remove(indice);
        }
    }

    @Override
    public void tomarTurno(BatallaController controller) {
        // Delegamos la interacción del turno del jugador al controller (vista)
        controller.handleJugadorTurn(this);
    }

    @Override
    public void usarHabilidadEspecial(BatallaController controller) {
        // Se delega a subclases de rol (Guerrero, Explorador) si es necesario
        mostrarMensaje(getNombre() + " intenta usar una habilidad especial base (no definida).");
    }
}
