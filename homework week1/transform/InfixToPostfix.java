/* author:longjiawei                                                  */ 
/* this program can transform infix expression into suffix expression */                                        

import java.util.Stack;
import java.util.Scanner;
public class InfixToPostfix  
{ 
	private String convertExpr(String expression)
	{
		Stack<Character> s = new Stack<Character>();
		String str = "";
		for (int i = 0; i < expression.length(); i++) {
			char c = expression.charAt(i);
				//left bracket
				if (c == '(') {
					s.push(c);
				}
				else if (c == '+' || c == '-') {
					// * and / have higher priority than + and -
					//so they should be output earlier than + and -
					str += ' ';
					if (!s.empty() && (s.peek() == '*' || s.peek() == '/')) {
						while (!s.empty()) {
							str += s.peek();
							str += ' ';
							s.pop();
						}
						s.push(c);
					} 
					else {
						s.push(c);
					}
				}
				else if (c == '*' || c == '/') {
					str += ' ';
					s.push(c);
				}
				//right bracket
				else if (c == ')') {
					while (s.peek() != '(') {
						str += ' ';
						str += s.peek();
						s.pop();
					}
					s.pop();
				}
				else {
					str += c;
				}
		}
		//to output all of the operators in the stack
		while (!s.empty()) {
			str += ' ';
			str += s.peek();
			s.pop();
		}
		return str;
	}  
	public static void main(String[] args)
	{
		InfixToPostfix trans = new InfixToPostfix();
		Scanner s = new Scanner(System.in);
		String temp = s.nextLine();
		String res = trans.convertExpr(temp);
		System.out.println(res);
	} 
} 