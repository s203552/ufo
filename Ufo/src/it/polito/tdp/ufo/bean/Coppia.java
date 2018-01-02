package it.polito.tdp.ufo.bean;
public class Coppia {
	Sighting s1;
	Sighting s2;
	double peso;
	public Coppia(Sighting s1, Sighting s2, double peso) {
		super();
		this.s1 = s1;
		this.s2 = s2;
		this.peso = peso;
	}
	public Sighting getS1() {
		return s1;
	}
	public void setS1(Sighting s1) {
		this.s1 = s1;
	}
	public Sighting getS2() {
		return s2;
	}
	public void setS2(Sighting s2) {
		this.s2 = s2;
	}
	public double getPeso() {
		return peso;
	}
	public void setPeso(double peso) {
		this.peso = peso;
	}
	@Override
	public String toString() {
		return "["+ s1 +" , "+ s2 + ", peso=" + peso+"]" ;
	}
	

}
