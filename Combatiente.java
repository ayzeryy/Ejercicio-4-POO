import java.util.Objects;

public abstract class Combatiente {
    private String nombre;
    private int vidaMaxima;
    private int vidaActual;
    private int ataqueBase;
    private String estado; // simple campo para estados como "aturdido", "defensa", etc.

    public Combatiente(String nombre, int vidaMaxima, int ataqueBase) {
        this.nombre = nombre;
        this.vidaMaxima = Math.max(1, vidaMaxima);
        this.vidaActual = this.vidaMaxima;
        this.ataqueBase = Math.max(0, ataqueBase);
        this.estado = "";
    }

    public String getNombre() {
        return nombre;
    }

    public int getVidaMaxima() {
        return vidaMaxima;
    }

    public int getVidaActual() {
        return vidaActual;
    }

    public void setVidaActual(int vidaActual) {
        this.vidaActual = Math.min(Math.max(0, vidaActual), vidaMaxima);
    }

    public int getAtaqueBase() {
        return ataqueBase;
    }

    public void setAtaqueBase(int ataqueBase) {
        this.ataqueBase = Math.max(0, ataqueBase);
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = Objects.requireNonNullElse(estado, "");
    }

    public boolean vivo() {
        return vidaActual > 0;
    }

    public void atacar(Combatiente objetivo) {
        if (!vivo()) {
            return;
        }
        int dano = ataqueBase;
        objetivo.recibirDaño(dano);
        mostrarMensaje(nombre + " ataca a " + objetivo.getNombre() + " causando " + dano + " de daño.");
    }

    public void recibirDaño(int dano) {
        int nuevo = getVidaActual() - Math.max(0, dano);
        setVidaActual(nuevo);
        mostrarMensaje(getNombre() + " recibe " + dano + " de daño. HP: " + getVidaActual() + "/" + getVidaMaxima());
    }

    protected void mostrarMensaje(String msg) {
        System.out.println(msg);
    }

    // Template method: estructura general de tomarTurno
    public abstract void tomarTurno(BatallaController controller);

    // Habilidad especial concreta en subclases
    public abstract void usarHabilidadEspecial(BatallaController controller);
}
