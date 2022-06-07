package com.tcc.model;



import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "leituraArduino")
public class Leitura {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long idLeitura;

	@Column(nullable = false, length = 3)
	private int tempLeitura;

	@Column(nullable = false)
	private String gasLeitura;

	@Column(nullable = false)
	private String dataLeitura;
	
	//Construtores
	public Leitura() {	
	}
	
	public Leitura(int tempLeitura, String gasLeitura) {
		this.tempLeitura = tempLeitura;
		this.gasLeitura = gasLeitura;
	}

	//Getters e Setters
	public Long getIdLeitura() {
		return idLeitura;
	}
	public void setIdLeitura(Long idLeitura) {
		this.idLeitura = idLeitura;
	}
	public int getTempLeitura() {
		return tempLeitura;
	}
	public void setTempLeitura(int tempLeitura) {
		this.tempLeitura = tempLeitura;
	}
	
	public String getGasLeitura() {
		return gasLeitura;
	}

	public void setGasLeitura(String gasLeitura) {
		this.gasLeitura = gasLeitura;
	}

	public String getDataLeitura() {
		return dataLeitura;
	}

	public void setDataLeitura(String dataLeitura) {
		this.dataLeitura = dataLeitura;
	}
			
}
