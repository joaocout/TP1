package interfaces;

import exception.AlugarEx;
import exception.DevolverEx;
import exception.PrecoEx;

public interface Aluguel {
	public abstract void alugar() throws AlugarEx;
	public abstract void devolver() throws DevolverEx;
	public abstract double PrecoFinal() throws PrecoEx;
}