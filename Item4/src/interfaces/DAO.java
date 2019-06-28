package interfaces;

import java.util.ArrayList;

public interface DAO<T,PK> {
	
	public boolean add(T t);
	public boolean remove(T t);
	public T get(PK pk);
	public ArrayList<T> getAll();
	public boolean update(T t);
}
