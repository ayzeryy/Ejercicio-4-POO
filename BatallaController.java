import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class BatallaController {
    private List<Jugador> jugadores;
    private List<Enemigo> enemigos;
    private VistaConsola vista;
    private Random rng = new Random();

    // Estructuras para manejar buffs temporales simples
    private List<BuffTemporal> buffsTemporales = new ArrayList<>();
    private List<ReducirDanoTemporal> reduccionesTemporales = new ArrayList<>();

    public BatallaController(VistaConsola vista) {
        this.vista = vista;
        this.jugadores = new ArrayList<>();
        this.enemigos = new ArrayList<>();
    }

    public void agregarJugador(Jugador j) {
        jugadores.add(j);
    }

    public void agregarEnemigo(Enemigo e) {
        enemigos.add(e);
    }

    public List<Jugador> getJugadoresVivos() {
        List<Jugador> vivos = new ArrayList<>();
        for (Jugador j : jugadores) {
            if (j.vivo()) {
                vivos.add(j);
            }
        }
        return vivos;
    }

    public List<Enemigo> getEnemigosVivos() {
        List<Enemigo> vivos = new ArrayList<>();
        for (Enemigo e : enemigos) {
            if (e.vivo()) {
                vivos.add(e);
            }
        }
        return vivos;
    }

    public Combatiente seleccionarEnemigoAleatorioVivo() {
        List<Enemigo> vivos = getEnemigosVivos();
        if (vivos.isEmpty()) {
            return null;
        }
        return vivos.get(rng.nextInt(vivos.size()));
    }

    public Combatiente seleccionarJugadorAleatorioVivo() {
        List<Jugador> vivos = getJugadoresVivos();
        if (vivos.isEmpty()) {
            return null;
        }
        return vivos.get(rng.nextInt(vivos.size()));
    }

    public void iniciarBatalla() {
        vista.mostrarMensaje("Iniciando batalla...");
        // Loop de batalla principal
        while (!verificarFinBatalla()) {
            procesarTurnos();
            procesarEfectosTemporales();
            vista.mostrarEstado(this);
        }
        if (getEnemigosVivos().isEmpty()) {
            vista.mostrarMensaje("¡Victoria! Todos los enemigos han sido derrotados.");
        } else {
            vista.mostrarMensaje("Derrota. Tus jugadores han caído.");
        }
    }

    private void procesarTurnos() {
        // Construir orden de turno: mezcla jugadores y enemigos vivos
        List<Combatiente> orden = new LinkedList<>();
        orden.addAll(getJugadoresVivos());
        orden.addAll(getEnemigosVivos());
        Collections.shuffle(orden, rng);

        for (Combatiente c : orden) {
            if (!c.vivo()) {
                continue;
            }
            c.tomarTurno(this);
            if (verificarFinBatalla()) {
                return;
            }
        }
    }

    public boolean verificarFinBatalla() {
        boolean jugadoresVivos = !getJugadoresVivos().isEmpty();
        boolean enemigosVivos = !getEnemigosVivos().isEmpty();
        return !(jugadoresVivos && enemigosVivos);
    }

    // Este método es llamado por Jugador.tomarTurno cuando es su turno
    public void handleJugadorTurn(Jugador jugador) {
        vista.mostrarMensaje("Turno de " + jugador.getNombre());
        vista.mostrarEstado(this);
        int opcion = vista.leerOpcionJugador(jugador);

        switch (opcion) {
            case 1: // atacar
                Combatiente objetivo = seleccionarEnemigoAleatorioVivo();
                if (objetivo != null) {
                    jugador.atacar(objetivo);
                } else {
                    vista.mostrarMensaje("No hay enemigos vivos.");
                }
                break;
            case 2: // usar habilidad
                jugador.usarHabilidadEspecial(this);
                break;
            case 3: // usar item
                int indice = vista.leerIndiceItem(jugador);
                jugador.usarItem(indice, this);
                break;
            default:
                vista.mostrarMensaje("Opción inválida. Se pasa el turno.");
        }
    }

    // Registro de buffs/reducciones temporales
    public void registrarBuffTemporal(Jugador jugador, int incremento, int duracion) {
        buffsTemporales.add(new BuffTemporal(jugador, incremento, duracion));
    }

    public void registrarReducirDanoTemporal(Jugador jugador, int reduccion, int duracion) {
        reduccionesTemporales.add(new ReducirDanoTemporal(jugador, reduccion, duracion));
    }

    private void procesarEfectosTemporales() {
        // Reducir duración y aplicar restauraciones cuando toque
        Iterator<BuffTemporal> it = buffsTemporales.iterator();
        while (it.hasNext()) {
            BuffTemporal b = it.next();
            b.duracion--;
            if (b.duracion <= 0) {
                // revertir buff
                b.jugador.setAtaqueBase(Math.max(0, b.jugador.getAtaqueBase() - b.incremento));
                b.jugador.mostrarMensaje(b.jugador.getNombre() + " pierde el efecto de buff de ataque.");
                it.remove();
            }
        }

        Iterator<ReducirDanoTemporal> it2 = reduccionesTemporales.iterator();
        while (it2.hasNext()) {
            ReduccionDanoTemporal r = it2.next();
            r.duracion--;
            if (r.duracion <= 0) {
                r.jugador.mostrarMensaje(r.jugador.getNombre() + " ya no tiene la reducción de daño.");
                it2.remove();
            }
        }
    }

    // Helpers para vista
    public List<Jugador> obtenerJugadores() {
        return new ArrayList<>(jugadores);
    }

    public List<Enemigo> obtenerEnemigos() {
        return new ArrayList<>(enemigos);
    }

    // Clases internas para manejo temporal simple
    private static class BuffTemporal {
        Jugador jugador;
        int incremento;
        int duracion;

        BuffTemporal(Jugador j, int incremento, int duracion) {
            this.jugador = j;
            this.incremento = incremento;
            this.duracion = duracion;
        }
    }

    private static class ReduccionDanoTemporal {
        Jugador jugador;
        int reduccion;
        int duracion;

        ReduccionDanoTemporal(Jugador j, int reduccion, int duracion) {
            this.jugador = j;
            this.reduccion = reduccion;
            this.duracion = duracion;
        }
    }
}
