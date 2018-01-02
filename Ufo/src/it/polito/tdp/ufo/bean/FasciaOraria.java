package it.polito.tdp.ufo.bean;

public class FasciaOraria {

	private int OraP;
	private int OraA;
	public FasciaOraria(int oraP, int oraA) {
		super();
		OraP = oraP;
		OraA = oraA;
	}
	public int getOraP() {
		return OraP;
	}
	public void setOraP(int oraP) {
		OraP = oraP;
	}
	public int getOraA() {
		return OraA;
	}
	public void setOraA(int oraA) {
		OraA = oraA;
	}
	@Override
	public String toString() {
		return "FasciaOraria [OraP=" + OraP + ", OraA=" + OraA + "]";
	}
	
}
