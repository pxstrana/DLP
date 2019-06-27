package symboltable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ast.definitions.Definition;

public class SymbolTable {
	
	
	private int scope=0;
	private List<Map<String,Definition>> table;
	public SymbolTable()  {
		table=new ArrayList<>();
		table.add(new HashMap<>());
	}	 

	
	public void set() {
		this.scope++;
		this.table.add(new HashMap<>());
	}
	
	public void reset() {
		this.table.remove(scope);
		this.scope--;
	}
	
	public boolean insert(Definition definition) {
	
		Map<String, Definition> map = table.get(scope);
		if (findInCurrentScope(definition.getName()) != null) {
			return false;
		} else {
			map.put(definition.getName(), definition);
			definition.setScope(scope);
			return true;
		}
	}
	
	public Definition find(String id) {
		for (int i = scope; i >= 0; i--) {
			if (table.get(i).containsKey(id)) {
				return table.get(i).get(id);
			}
		}
		return null;
	}

	public Definition findInCurrentScope(String id) {
		return table.get(scope).get(id);
	}
}
