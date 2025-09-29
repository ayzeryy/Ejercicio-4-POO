public class Dragon extends Enemigo {
    public Dragon(String nombre, boolean esJefe) {
        super(nombre, esJefe ? 200 : 110, esJefe ? 26 : 16, "Dragon", esJefe);
    }

    @Override
    public void usarHabilidadEspecial(BatallaController controller) {
        if (isEsJefe()) {
            // Vuelo: esquiva el próximo ataque (simplemente se vuelve invulnerable un turno)
            setEstado("volando");
            mostrarMensaje(getNombre() + " usa Vuelo y evita el siguiente ataque.");
        } else {
            // Aliento de fuego: daño a todos los jugadores
            for (Jugador j : controller.getJugadoresVivos()) {
                int dano = getAtaqueBase() + 6;
                j.recibirDaño(dano);
            }
            mostrarMensaje(getNombre() + " usa Aliento de fuego sobre todos los jugadores.");
        }
    }

    @Override
    public void recibirDaño(int dano) {
        if ("volando".equals(getEstado())) {
            // esquiva este ataque
            mostrarMensaje(getNombre() + " está volando y evade el ataque.");
            setEstado("");
            return;
        }
        super.recibirDaño(dano);
    }
}
