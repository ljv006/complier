import java.io.IOException;
/**
 * Parser��ʵ�ֶ����ַ����ж��ַ��Ƿ�Ϸ���ͬʱ�ṩ����ָ����ܡ�
 * @author ����ΰ
 */
class Parser {
	/** lookahead���ڴ��Ԥ���������ַ� */
	static int lookahead;
	/** count���ڼ�¼��ǰ�����ַ����ַ����е�λ�ã������ṩ����λ����Ϣ*/
	static int count;
	/** error�û���Ŵ�����Ϣ�����ڳ������ǰ���*/
	String[] error = new String[100];
	/** cnt_err��¼���������*/
	int cnt_err = 0;
	/** flag��������Ƿ��Ѿ������˵�һ�������ַ�*/
	boolean flag = false;
	/**
	 * Parser�Ĺ��캯����ϵͳ�����������һ���ַ���ʼ��lookahead������������ǰ����λ�ó�ʼ��Ϊ1��
	 * @throws IOException
	 */
	public Parser() throws IOException {
		lookahead = System.in.read();
		count = 1;
	}
	/**
	 * expr()��������term()��ʶ���һ���ַ���������rest()�Դ���ʣ����ַ�����
	 * @throws IOException
	 */
	void expr() throws IOException {
		term();
		rest();
	}
	/**
	 * rest()��������+���͡�-����������Լ�⵽�Ĵ�����м�¼�ͻָ���
	 * @throws IOException
	 */
	void rest() throws IOException {
		//�ж��ַ����Ƿ����
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
			//��������ַ�Ϊ������֮ǰ���ַ�Ҳ������
			if (!flag) {
				error[cnt_err] = "(" + cnt_err + ")"  + "Syntax error at " + count + ":lack of operator";
				cnt_err++;
				match(lookahead);
			}
			//�����һ�������ַ�
			 else {
				 System.out.write((char)lookahead);
				 match(lookahead);
				 flag = false;
			 } 
			}
			//�Ƿ������ַ�
			else {
				error[cnt_err] = "(" + cnt_err + ")" + "Lexical error at " + count + ":illegal character";
				cnt_err++;
				match(lookahead);
			}
			
		}
	}	
	/**
	 * �����ֽ���������������Լ�⵽�Ĵ�����м�¼�ͻָ�.<br>
	 * ʹ���˵ݹ飬�ڴ���ָ�ʱ�����ַ���ֱ������Ϸ�Ϊֹ.
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
	 * ��t��lookaheadƥ��ʱ����ȡ��һλ�ַ�
	 * @param t ������lookahead�ַ��ȶ�
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
 * Postfix�๹��һ��Parser�������������ַ�����
 * @author ����ΰ
 */
public class Postfix {
	/**
	 * main����������Parser��������ַ��������ռ����Ĵ�����Ϣ���������
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
