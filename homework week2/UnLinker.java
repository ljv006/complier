import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UnLinker
{
	String clean(String text)
	{	
		int counter = 1;
		Pattern p = Pattern.compile("(http://|http://www.|www.){1}[A-Za-z0-9.]{1,}(.(com|org|edu|tv|info)){1}");
		Matcher m = p.matcher(text);
		String s = text;
		while (m.find()) {
			String temp = s.substring(m.start(), m.end());
			text = text.replaceFirst(temp, "OMIT" + counter);
			counter++;
		}
		return text;
	}
	public static void main(String[] args) throws IOException
	{
		Scanner in = new Scanner(new File("UnLinker.in"));
		String text;
		UnLinker ul = new UnLinker();
		while (in.hasNextLine())
		{
			text = in.nextLine();
			System.out.println(ul.clean(text));
		}
		in.close();
	}
}
