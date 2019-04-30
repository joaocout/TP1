package locadora.interfaces;

import locadora.exception.*;

public interface Aluguel {
	public abstract void alugar() throws AlugarEx;
	public abstract void devolver() throws DevolverEx;
	public abstract double PrecoFinal() throws PrecoEx;
}
/*ola*/