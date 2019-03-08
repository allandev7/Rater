package model;
public abstract class Usuarios {
	//VARIÁVEIS
	protected static String email;
	protected static String nome;
	protected  String senha;
	
	//MÉTODOS
	public abstract int login (String emailTxt, String senhaTxt);
	
	public abstract void alterarInfo(String email,String nome, String senha );
	
	
	//GETTERS E SETTERS
	
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public static String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		Usuarios.nome = nome;
	}
	public static String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		Usuarios.email = email;
	}
}
	
	
