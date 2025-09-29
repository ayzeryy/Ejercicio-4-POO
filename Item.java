public abstract class Item {
    private String nombre;
    private String descripcion;
    private int cantidad;

    public Item(String nombre, String descripcion, int cantidad) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cantidad = Math.max(0, cantidad);
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void disminuirCantidad(int n) {
        cantidad = Math.max(0, cantidad - n);
    }

    public abstract void usar(Jugador jugador, BatallaController controller);
}
