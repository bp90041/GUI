import java.util.ArrayList;


public class Question {

	private String question;
	private ArrayList<?> answers;
	private Type type;

	public Question(String question, ArrayList<?> answers, Type type){
		this.question = question;
		this.answers = answers;
		this.type = type;
	}
	public Question(String question, ArrayList<?> answers, String goo){
		this.question = question;
		this.answers = answers;
		
		if(goo.equals("SLIDER")){
			this.type = Type.SLIDER;	
		}
		else if(goo.equals("CHECK")){
			this.type = Type.CHECK;	
		}
		else if(goo.equals("BUTTON")){
			this.type = Type.BUTTON;	
		}
		else if(goo.equals("TEXT")){
			this.type = Type.TEXT;
		}
		else if(goo.equals("RADIO")){
			this.type = Type.RADIO;	
		}
		else{
			this.type = Type.BUTTON;
		}
		}
	
	public ArrayList<?> getAnswers(){
		if (true){
			return new ArrayList<Integer>();
		}
		else{
			return new ArrayList<String>();
		}
	}
}
