import java.util.ArrayList;

public class EntrySet {
	private ArrayList<Entry> entries = new ArrayList<Entry>();
	
	public void EntrySet(){	}
	
	public void EntrySet(ArrayList<Entry> entries){
		this.entries = entries;
	}
	
	public ArrayList<Entry> getEntries(){
		return this.entries;
	}
	
	public void setEntries(ArrayList<Entry> entries){
		this.entries = entries;
	}
	
	public void add(Entry entry){
		this.entries.add(entry);
	}
	
	public void add(String question, ArrayList<String> answers, Type type){
		this.entries.add(new Entry(question, answers, type));
	}
	
	public void remove(Entry entry){
		this.entries.remove(entry);
	}
	
	public void remove(String question){
		for(Entry entry : this.entries){
			if (entry.getQuestion().equals(question)){
				this.entries.remove(entry);
			}
		}
	}
	
	/*
	public void remove(String question, ArrayList<String> answers, Type type){
		
	}
	*/
	
	public Boolean contains(Entry entry){
		return this.entries.contains(entry);
	}
	
	public Boolean contains(String question){
		for(Entry entry : this.entries){
			if (entry.getQuestion().equals(question)){
				return true;
			}
		}
		return false;
	}
	
	public Entry get(Entry entry){
		for(Entry e : this.entries){
			if (e.equals(entry)){
				return e;
			}
		}
		return null;
	}
	
	public Entry get(String question){
		for(Entry entry : this.entries){
			if (entry.getQuestion().equals(question)){
				return entry;
			}
		}
		return null;
	}
}
