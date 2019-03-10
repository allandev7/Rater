package model;
public abstract class Usuarios {
	//VARIÁVEIS
	protected static String email;
	protected static String nome;
	protected  String senha;
	protected static String foto;
	
	//MÉTODOS
	public abstract int login (String emailTxt, String senhaTxt);
	
	public abstract void alterarInfo(String email,String nome, String identificacao );
	
	
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
	public static String getFoto() {
		return foto;
	}
	public void setFoto(String foto) {
		Usuarios.foto = foto;
	}
}
	
	
