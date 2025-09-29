public class Explorador extends Jugador {
    public Explorador(String nombre) {
        super(nombre, 90, 14, "Explorador");
    }

    @Override
    public void usarHabilidadEspecial(BatallaController controller) {
        // Ataque rápido: dos ataques más débiles al mismo objetivo
        Combatiente objetivo = controller.seleccionarEnemigoAleatorioVivo();
        if (objetivo == null) {
            mostrarMensaje("No hay objetivos válidos.");
            return;
        }
        int dano1 = getAtaqueBase();
        int dano2 = Math.max(1, getAtaqueBase() - 4);
        objetivo.recibirDaño(dano1);
        objetivo.recibirDaño(dano2);
        mostrarMensaje(getNombre() + " usa Ataque rápido sobre " + objetivo.getNombre() + " causando " + (dano1 + dano2) + " de daño en total.");
    }
}
