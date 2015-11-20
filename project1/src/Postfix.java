import java.io.IOException;

class Parser {
	static int lookahead;
	static int count;
	String[] error = new String[100];
	int cnt_err = 0;
	boolean flag = false;
	boolean flag1 = false;
	public Parser() throws IOException {
		lookahead = System.in.read();
		count = 1;
	}

	void expr() throws IOException {
		term();
		rest();
	}

	void rest() throws IOException {
		while (lookahead != '\r') {
		if (lookahead == '+') {
			//getNext
			match('+');
			//judge the next char is digit or not
			term();
			System.out.write('+');
		} else if (lookahead == '-') {
			//getNext
			match('-');
			//judge the next char is digit or not
			term();
			System.out.write('-');
		} else if (lookahead >= '0' && lookahead <= '9') {
			if (!flag) {
				error[cnt_err] = "(" + cnt_err + ")"  + "Syntax error at " + count + ":lack of operator";
				cnt_err++;
				match(lookahead);
			}
			 else {
				 System.out.write((char)lookahead);
				 match(lookahead);
				 flag = false;
			 } 
			}
			else {
				error[cnt_err] = "(" + cnt_err + ")" + "Lexical error at " + count + ":illegal character";
				cnt_err++;
				match(lookahead);
			}
			
		}
	}	

	void term() throws IOException {
		if (Character.isDigit((char)lookahead)) {
			System.out.write((char)lookahead);
			match(lookahead);
		}  else if (lookahead == '+') {
			error[cnt_err] = "(" + cnt_err + ")" + "Syntax error at " + count + ":lack of operand";
			match('+');
			cnt_err++;
			term();
		} 
		else if (lookahead == '-') {
			error[cnt_err] = "(" + cnt_err + ")"  + "Syntax error at " + count + ":lack of operand";
			match('-');
			cnt_err++;
			term();
		} else {
			error[cnt_err] = "(" + cnt_err + ")"  + "Lexical error at " + count + ":illegal character";
			match(lookahead);
			cnt_err++;
			term();
		}
	}

	void match(int t) throws IOException {
		if (lookahead == t)  {
			lookahead = System.in.read();
			count++;
		}
		else {
		}
	}
}
public class Postfix {
	public static void main(String[] args) throws IOException {
		System.out.println("Input an infix expression and output its postfix notation:");
		Parser p = new Parser();
		p.expr();
		if (p.cnt_err != 0) {
			System.out.println();
			for (int i = 0; i < p.cnt_err; i++) {
				System.out.println(p.error[i]);
			}
		}
		System.out.println("\nEnd of program.");
	}
}
