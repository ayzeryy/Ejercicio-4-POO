public class Pocion extends Item {
    private int curacion;

    public Pocion(String nombre, String descripcion, int cantidad, int curacion) {
        super(nombre, descripcion, cantidad);
        this.curacion = curacion;
    }

    @Override
    public void usar(Jugador jugador, BatallaController controller) {
        if (getCantidad() <= 0) {
            jugador.mostrarMensaje("No quedan " + getNombre() + ".");
            return;
        }
        int nuevaVida = Math.min(jugador.getVidaMaxima(), jugador.getVidaActual() + curacion);
        jugador.setVidaActual(nuevaVida);
        disminuirCantidad(1);
        jugador.mostrarMensaje(jugador.getNombre() + " usa " + getNombre() + " y recupera " + curacion + " HP.");
    }
}
