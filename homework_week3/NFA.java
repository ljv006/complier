/*************************************************************************/
/*     Author:longjiawei                                                 */
/*     This program can distinguish a string using NFA                  */
/*************************************************************************/
import java.util.*;
import java.io.*;

class NFA
{
	//此标记用于处理空串输入的情况
	boolean flag = false;
	void dfs(int pos, int move[][][], int accept_state[], String word, int count) {
		int len = word.length();
		if (count == len && !flag) {
			//判断当前状态是否为接受态
			for (int i = 0; i < accept_state.length; i++) {
				if (pos == accept_state[i])
					flag = true;
			}
			//输入的字符串为空串
			if (count == 0 && !flag) {
				//通过标记为空串的边递归遍历NFA
				for (int i = 0; i < move[pos][0].length; i++) {
					dfs(move[pos][0][i], move, accept_state, word, count);
				}
			}
		} else {
			//处理非空字符
			int[] tmp = move[pos][word.charAt(count) - 'a' + 1];
			for (int i = 0; i < tmp.length; i++)
				dfs(tmp[i], move, accept_state, word, count+1);
			//处理空串
			tmp = move[pos][0];
			for (int i = 0; i < tmp.length; i++)
				dfs(tmp[i], move, accept_state, word, count);	
		}
	}
	boolean recognizeString(int move[][][], int accept_state[], String word)
	{
		flag = false;
		dfs(0, move, accept_state, word, 0);
		return flag;
	}
	public static void main(String args[]) throws IOException
	{
		int n, m;
		BufferedReader in = new BufferedReader(new FileReader("NFA.in"));
		StringTokenizer st = new StringTokenizer(in.readLine());
		n = Integer.parseInt(st.nextToken());
		m = Integer.parseInt(st.nextToken());
		while (n != 0)
		{
			int[][][] move = new int[n][m][];
			for(int i=0; i<n; i++)
			{
				String line = in.readLine();
				int k = 0;
				for (int j=0; j<m; j++)
				{
					while (line.charAt(k) != '{') k++;
					k++;
					String states = "";
					while (line.charAt(k) != '}')
					{
						states = states + line.charAt(k);
						k++;
					}
					states = states.trim();
					if (states.length() > 0)
					{
						String[] stateArray = states.split(",");
						move[i][j] = new int[stateArray.length];
						for (int l=0; l<move[i][j].length; l++) move[i][j][l] = Integer.parseInt(stateArray[l].trim());
					}
					else move[i][j] = new int[0];
				}
			}
			String[] temp = in.readLine().split("\\s");
			int[] accept = new int[temp.length];
			for (int i=0; i<accept.length; i++) accept[i] = Integer.parseInt(temp[i]);
			String word = in.readLine();
			while (word.compareTo("#") != 0)
			{
				NFA nfa = new NFA();
				if (nfa.recognizeString(move, accept, word)) System.out.println("YES"); else System.out.println("NO");
				word = in.readLine();
			}
			st = new StringTokenizer(in.readLine());
			n = Integer.parseInt(st.nextToken());
			m = Integer.parseInt(st.nextToken());
		}
	}
}