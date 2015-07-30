import java.util.ArrayList;


public class Entry {

	private String question;
	private ArrayList<?> answers;
	private Type type;

	public Entry(String question, ArrayList<?> answers, Type type){
		this.question = question;
		this.answers = answers;
		this.type = type;
	}
	public Entry(String question, ArrayList<?> answers, String style){
		this.question = question;
		this.answers = answers;

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
	
	public ArrayList<?> getAnswers(){
		if (this.type.equals(Type.SLIDER)){
			return answers;
			//TODO Can't remember how java returns ArrayList<>. May need to create a copy and
			// return the copy.
			// answers = newArrayList<Integer>();
			//return new ArrayList<Integer>();
		}
		else{
			return answers;
			//return new ArrayList<String>();
		}
	}
	
	public void setAnswers(ArrayList<?> answers){
		this.answers = answers;
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

	public String toString(){
		String name = "";
		name += "Question: " + this.question.toString() + " ";
		name += "Type: " + this.type.toString() + " ";
		name += "Answers: " + this.answers.toString() + " ";
		return name;
	}



}
