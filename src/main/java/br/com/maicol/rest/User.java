package br.com.maicol.rest;

public class User {

	private String name;
	private Integer idade;
	private Double salario;
	private Long id;

	public User(String name, Integer idade) {
		this.name = name;
		this.idade = idade;
	}

	public User(Long id) {
		super();
		this.id = id;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return name;
	}

	public void setNome(String name) {
		this.name = name;
	}

	public Integer getIdade() {
		return idade;
	}

	public void setIdade(Integer idade) {
		this.idade = idade;
	}

	public Double getSalario() {
		return salario;
	}

	public void setSalario(Double salario) {
		this.salario = salario;
	}

	@Override
	public String toString() {
		return "User [name=" + name + ", idade=" + idade + ", salario=" + salario + ", id=" + id + "]";
	}
	

}
