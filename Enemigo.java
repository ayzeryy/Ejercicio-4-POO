import java.util.Random;

public abstract class Enemigo extends Combatiente {
    private String tipoEnemigo;
    private boolean esJefe;
    protected Random rng = new Random();

    public Enemigo(String nombre, int vidaMaxima, int ataqueBase, String tipoEnemigo, boolean esJefe) {
        super(nombre, vidaMaxima, ataqueBase);
        this.tipoEnemigo = tipoEnemigo;
        this.esJefe = esJefe;
    }

    public String getTipoEnemigo() {
        return tipoEnemigo;
    }

    public boolean isEsJefe() {
        return esJefe;
    }

    // Permite a subclases ajustar daño recibido (por ejemplo, escudo jefes)
    public int calcularDañoRecibido(int dano) {
        if (esJefe) {
            // jefes reciben menos daño fijo
            int reducido = Math.max(0, dano - 2);
            return reducido;
        }
        return dano;
    }

    @Override
    public void recibirDaño(int dano) {
        int danoCalculado = calcularDañoRecibido(dano);
        super.recibirDaño(danoCalculado);
    }

    @Override
    public void tomarTurno(BatallaController controller) {
        if (!vivo()) {
            return;
        }
        // Enemigo decide: atacar o usar habilidad especial (probabilístico)
        int decision = rng.nextInt(100);
        if (esJefe) {
            // jefes más propensos a usar habilidades especiales
            if (decision < 50) {
                usarHabilidadEspecial(controller);
            } else {
                Combatiente objetivo = controller.seleccionarJugadorAleatorioVivo();
                if (objetivo != null) {
                    atacar(objetivo);
                }
            }
        } else {
            if (decision < 20) {
                usarHabilidadEspecial(controller);
            } else {
                Combatiente objetivo = controller.seleccionarJugadorAleatorioVivo();
                if (objetivo != null) {
                    atacar(objetivo);
                }
            }
        }
    }

    @Override
    public abstract void usarHabilidadEspecial(BatallaController controller);
}
