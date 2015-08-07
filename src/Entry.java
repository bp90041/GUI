import java.util.ArrayList;
import java.util.HashMap;


public class Entry {

	private String question;
	private ArrayList<String> answers;
	private HashMap<String, Boolean> selections;
	private Type type;
	
	public Entry(String question, ArrayList<String> answers, Type type){
		this.question = question;
		this.answers = answers;
		this.selections = new HashMap<String, Boolean>();
		for(String answer : answers){
			this.selections.put(answer,false);
		}
		this.type = type;
	}
	public Entry(String question, ArrayList<String> answers, String style){
		this.question = question;
		this.answers = answers;
		this.selections = new HashMap<String, Boolean>();
		for(String answer: answers){
			this.selections.put(answer, false);
		}
		if(style.equals("SLIDER")){
			this.type = Type.SLIDER;	
		}
		else if(style.equals("CHECK")){
			this.type = Type.CHECK;	
		}
		else if(style.equals("BUTTON")){
			this.type = Type.BUTTON;	
		}
		else if(style.equals("TEXT")){
			this.type = Type.TEXT;
		}
		else if(style.equals("RADIO")){
			this.type = Type.RADIO;	
		}
		else{
			this.type = Type.BUTTON;
		}
	}
	
	public String getQuestion(){
		return this.question; 
	}
	
	public void setQuestion(String question){
		this.question = question;
	}
	
	public Type getType(){
		return this.type;
	}
	
	public void setType(Type type){
		this.type = type;
	}
	
	public ArrayList<String> getAnswers(){
		return this.answers;
	}
	
	public void setAnswers(ArrayList<String> answers){
		this.answers = answers;
		this.selections = new HashMap<String, Boolean>();
		for(String answer: answers){
			this.selections.put(answer, false);
		}
	}

	public ArrayList<String> getSelected(){
		ArrayList<String> selected = new ArrayList<String>();
		for(String answer: this.selections.keySet()){
			if(this.selections.get(answer)){
				selected.add(answer);
			}
		}
		return selected;
	}
	
	public void select(String answer){
		if(this.selections.containsKey(answer)){
			if(this.type != Type.CHECK){
				for (String selection : this.selections.keySet()){
					this.selections.put(selection, false);
				}
			}
			this.selections.put(answer,true);
		}
	}
	
	public void select(Integer answer){
		String a = this.answers.get(answer);
		if(this.selections.containsKey(a)){
			if(this.type != Type.CHECK){
				for (String selection : this.selections.keySet()){
					this.selections.put(selection, false);
				}
			}
			this.selections.put(a,true);
		}
	}
	
	public void deselect(String answer){
		if(this.selections.containsKey(answer)){
			this.selections.put(answer, false);
		}
	}
	
	public void deselect(Integer answer){
		String a = this.answers.get(answer);
		if(this.selections.containsKey(a)){
			this.selections.put(a,false);
		}
	}
	
	public Boolean isSelected(String answer){
		return this.selections.get(answer);
	}
	
	public Boolean isSelected(Integer answer){
		return this.selections.get(this.answers.get(answer));
	}
	
	public void addAnswer(String answer){
		if(!this.answers.contains(answer)){
			this.answers.add(answer);
		}
		this.selections.put(answer, false);
	}
	
	public void removeAnswer(String answer){
		if(!this.answers.contains(answer)){
			this.answers.remove(answer);
			this.selections.remove(answer);
		}
	}
	
	public String toString(){
		String name = "";
		name += "Question: " + this.question.toString() + " ";
		name += "Type: " + this.type.toString() + " ";
		name += "Answers: " + this.selections.toString() + " ";
		return name;
	}



}
