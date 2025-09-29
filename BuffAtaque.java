public class BuffAtaque extends Item {
    private int incremento;
    private int duracion; // en turnos

    public BuffAtaque(String nombre, String descripcion, int cantidad, int incremento, int duracion) {
        super(nombre, descripcion, cantidad);
        this.incremento = incremento;
        this.duracion = duracion;
    }

    @Override
    public void usar(Jugador jugador, BatallaController controller) {
        if (getCantidad() <= 0) {
            jugador.mostrarMensaje("No quedan " + getNombre() + ".");
            return;
        }
        int nuevoAtaque = jugador.getAtaqueBase() + incremento;
        jugador.setAtaqueBase(nuevoAtaque);
        disminuirCantidad(1);
        jugador.mostrarMensaje(jugador.getNombre() + " usa " + getNombre() + " y aumenta su ataque en " + incremento + " por " + duracion + " turnos.");
        controller.registrarBuffTemporal(jugador, incremento, duracion);
    }
}
