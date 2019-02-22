
public class ASTNum extends ASTSimple
{
	Token token;
	
	public ASTNum(Token t)
	{
		token = t;
	}
	
	public String toString()
	{
		String ans = "";
		ans += token.integer;
		return ans;
	}
	
	public void convertToJava()
	{
		System.out.print(token.integer);
		return;
	}
}
