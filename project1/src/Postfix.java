import java.io.IOException;
/**
 * Parser类实现读入字符并判断字符是否合法，同时提供错误恢复功能。
 * @author 龙嘉伟
 */
class Parser {
	/** lookahead用于存放预读进来的字符 */
	static int lookahead;
	/** count用于记录当前读入字符在字符串中的位置，用于提供错误定位的信息*/
	static int count;
	/** error用户存放错误信息，并在程序结束前输出*/
	String[] error = new String[100];
	/** cnt_err记录错误的数量*/
	int cnt_err = 0;
	/** flag用来标记是否已经读入了第一个数字字符*/
	boolean flag = false;
	/**
	 * Parser的构造函数用系统输入流读入的一个字符初始化lookahead变量，并将当前读入位置初始化为1。
	 * @throws IOException
	 */
	public Parser() throws IOException {
		lookahead = System.in.read();
		count = 1;
	}
	/**
	 * expr()函数调用term()来识别第一个字符，并调用rest()以处理剩余的字符串。
	 * @throws IOException
	 */
	void expr() throws IOException {
		term();
		rest();
	}
	/**
	 * rest()函数处理‘+’和‘-’的情况并对检测到的错误进行记录和恢复。
	 * @throws IOException
	 */
	void rest() throws IOException {
		//判断字符串是否结束
		while (lookahead != '\r') {
		if (lookahead == '+') {
			match('+');
			term();
			System.out.write('+');
		} else if (lookahead == '-') {
			match('-');
			term();
			System.out.write('-');
		} else if (lookahead >= '0' && lookahead <= '9') {
			//当读入的字符为数字且之前的字符也是数字
			if (!flag) {
				error[cnt_err] = "(" + cnt_err + ")"  + "Syntax error at " + count + ":lack of operator";
				cnt_err++;
				match(lookahead);
			}
			//读入第一个数字字符
			 else {
				 System.out.write((char)lookahead);
				 match(lookahead);
				 flag = false;
			 } 
			}
			//非法输入字符
			else {
				error[cnt_err] = "(" + cnt_err + ")" + "Lexical error at " + count + ":illegal character";
				cnt_err++;
				match(lookahead);
			}
			
		}
	}	
	/**
	 * 对数字进行输出操作，并对检测到的错误进行记录和恢复.<br>
	 * 使用了递归，在错误恢复时丢弃字符。直到输入合法为止.
	 * @throws IOException
	 */
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
	/**
	 * 当t与lookahead匹配时，读取下一位字符
	 * @param t 用于与lookahead字符比对
	 * @throws IOException
	 */
	void match(int t) throws IOException {
		if (lookahead == t)  {
			lookahead = System.in.read();
			count++;
		}
		else {
		}
	}
}
/**
 * Postfix类构造一个Parser类对象处理输入的字符串。
 * @author 龙嘉伟
 */
public class Postfix {
	/**
	 * main函数，构造Parser类对象处理字符串并将收集到的错误信息进行输出。
	 * @param args
	 * @throws IOException
	 */
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
