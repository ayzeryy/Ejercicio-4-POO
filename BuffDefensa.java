public class BuffDefensa extends Item {
    private int reduccionDano;
    private int duracion;

    public BuffDefensa(String nombre, String descripcion, int cantidad, int reduccionDano, int duracion) {
        super(nombre, descripcion, cantidad);
        this.reduccionDano = reduccionDano;
        this.duracion = duracion;
    }

    @Override
    public void usar(Jugador jugador, BatallaController controller) {
        if (getCantidad() <= 0) {
            jugador.mostrarMensaje("No quedan " + getNombre() + ".");
            return;
        }
        disminuirCantidad(1);
        jugador.mostrarMensaje(jugador.getNombre() + " usa " + getNombre() + " y reduce daño entrante por " + duracion + " turnos.");
        controller.registrarReducirDanoTemporal(jugador, reduccionDano, duracion);
    }
}
