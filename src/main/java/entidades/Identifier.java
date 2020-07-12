package entidades;

import java.util.ArrayList;

public class Identifier {
	
	public String nombre,tipo,valor,alcance;
	public int posicion,faux;
	public ArrayList<Token> exp;
	public Identifier(String nombre, String tipo,String valor,int f,int fa){
		this.nombre = nombre; this.tipo = tipo; this.valor = valor; this.posicion = posicion; this.faux = fa;
	}
	public Identifier(String nombre, String tipo,String valor,int posicion){
		this.nombre = nombre; this.tipo = tipo; this.valor = valor; this.posicion = posicion;
	}
	public Identifier(String nombre, String tipo,String valor,int posicion,String alcance){
		this.nombre = nombre; this.tipo = tipo; this.valor = valor; this.posicion = posicion; this.alcance = alcance;
	}
	public String getName() {
		return nombre;
	}
	public void setName(String nombre) {
		this.nombre = nombre;
	}
	public String getType() {
		return tipo;
	}
	public void setType(String tipo) {
		this.tipo = tipo;
	}
	public String getValue() {
		return valor;
	}
	public void setValue(String valor) {
		this.valor = valor;
	}
	public int getLine() {
		return posicion;
	}
	public void setLine(int posicion) {
		this.posicion = posicion;
	}
	public int getFaux() {
		return faux;
	}
	public void setFaux(int faux) {
		this.faux = faux;
	}
	public ArrayList<Token> getExp(){
		return exp;
	}
	public void setExp(ArrayList<Token> e){
		exp = e;
	}
	public String getScope() {
		return alcance;
	}
	public void setScope(String alcance) {
		this.alcance = alcance;
	}
	public String retExpression(){
		String res = "";
		if( exp != null && exp.size() != 0){
			res = nombre+" =";
			for(Token token : exp) 
				res += " "+token.getToken();
		}
		return res;
	}
	
}
