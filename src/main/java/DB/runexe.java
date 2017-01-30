package DB;

class runexe
{
public static void main(String[] args)
{
Runtime r =Runtime.getRuntime();
Process p =null;
String cmd[]={"notepad","f:/mytext.txt"};
try{
p=r.exec(cmd);
}
catch(java.lang.Exception e)
{} 
}
}