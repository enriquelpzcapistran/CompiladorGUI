package abs;


public class SymbolTable
{
    private String nombre, valor, tipo, posicion, alcance;

    public SymbolTable(String nombre, String valor, String tipo, String posicion, String alcance)
    {
        this.nombre = nombre;
        this.valor = valor;
        this.tipo = tipo;
        this.posicion = posicion;
        this.alcance = alcance;
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getValor()
    {
        return valor;
    }

    public String getTipo()
    {
        return tipo;
    }

    public String getPosicion()
    {
        return posicion;
    }
    
    public String getAlcance()
    {
        return alcance;
    }
}

	