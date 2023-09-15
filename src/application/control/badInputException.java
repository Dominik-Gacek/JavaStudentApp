package application.control;

public class badInputException extends Exception{
	
	private static final long serialVersionUID = 6279757893602072490L;
	private String outputMessage;

		public badInputException() {
			super();
			this.outputMessage = "";
		}
		public badInputException(String s) {
			super();
			this.outputMessage = s;
		}
		
		public String getMessage() {
			return this.outputMessage;
		}
		
}