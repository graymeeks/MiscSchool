import java.util.ArrayList;

public class ASTProgram extends ASTNode 
{
	private ArrayList<ASTStatement> statements;
	
	public ASTProgram(ArrayList<ASTStatement> s)
	{
		statements = s;
	}
	
	public void addStatement(ASTStatement s)
	{
		statements.add(s);
		return;
	}
	
	public void convertToJava()
	{
		for (ASTStatement curr: statements)
		{
			curr.convertToJava();
		}
		return;
	}
	
	public String toString()
	{
		String ans= "";
		for (ASTStatement curr: statements)
		{
			ans += curr.toString();
		}
		return ans;
	}
	
}
