public class Orco extends Enemigo {
    public Orco(String nombre, boolean esJefe) {
        super(nombre, esJefe ? 150 : 80, esJefe ? 22 : 12, "Orco", esJefe);
    }

    @Override
    public void usarHabilidadEspecial(BatallaController controller) {
        if (isEsJefe()) {
            // Rugido: reduce ataque de todos los jugadores temporalmente
            for (Jugador j : controller.getJugadoresVivos()) {
                int nuevoAtaque = Math.max(0, j.getAtaqueBase() - 4);
                j.setAtaqueBase(nuevoAtaque);
            }
            mostrarMensaje(getNombre() + " usa Rugido intimidante. Ataque de los jugadores reducido temporalmente.");
        } else {
            // Golpe fuerte a un jugador
            Combatiente objetivo = controller.seleccionarJugadorAleatorioVivo();
            if (objetivo != null) {
                int dano = getAtaqueBase() + 6;
                objetivo.recibirDaño(dano);
                mostrarMensaje(getNombre() + " usa Golpe del orco sobre " + objetivo.getNombre() + ".");
            }
        }
    }
}
