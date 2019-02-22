
public class ASTIf extends ASTStatement
{
	ASTCondition condition;
	ASTProgram statementList;
	
	public ASTIf(ASTCondition c, ASTProgram s)
	{
		condition = c;
		statementList = s;
	}
	
	public String toString()
	{
		String ans = "IF " + condition.toString() 
		+ " THEN\n" + statementList.toString()
		+ "ENDIF\n";
		return ans;
	}
	
	public void convertToJava()
	{
		printIndent();
		System.out.print("if ");
		condition.convertToJava();
		System.out.print("\n");
		printIndent();
		System.out.print("{\n");
		tabLevel += 4;
		statementList.convertToJava();
		tabLevel -= 4;
		printIndent();
		System.out.print("}\n");
		return;
	}
}
