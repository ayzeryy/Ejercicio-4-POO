public class Guerrero extends Jugador {
    public Guerrero(String nombre) {
        super(nombre, 120, 18, "Guerrero");
    }

    @Override
    public void usarHabilidadEspecial(BatallaController controller) {
        // Golpe fuerte: daño extra a un enemigo
        Combatiente objetivo = controller.seleccionarEnemigoAleatorioVivo();
        if (objetivo == null) {
            mostrarMensaje("No hay objetivos válidos.");
            return;
        }
        int dano = getAtaqueBase() + 12;
        objetivo.recibirDaño(dano);
        mostrarMensaje(getNombre() + " usa Golpe poderoso sobre " + objetivo.getNombre() + " causando " + dano + " de daño.");
    }
}
