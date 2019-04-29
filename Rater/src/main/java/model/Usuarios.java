package model;
public abstract class Usuarios {
	//VARIÃ�VEIS
	protected static String nomeUsuario;
	protected static String email;
	protected static String nome;
	protected  String senha;
	protected static String foto;
	
	//MÃ‰TODOS
	public abstract int login (String emailTxt, String senhaTxt);
	
	public abstract void alterarInfo(String email,String nome, String identificacao);
	
	
	//GETTERS E SETTERS
	
	
	public static String getNomeUsuario() {
		return nomeUsuario;
	}
	public static void setNomeUsuario(String nomeUsuario) {
		Usuarios.nomeUsuario = nomeUsuario;
	}
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
	
	
