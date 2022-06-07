package com.tcc.repository;


import com.tcc.model.Leitura;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LeituraRepository extends JpaRepository<Leitura, Long>{
	
	@Query("select l from Leitura l where trim(l.dataLeitura) like %?1%")
	List<Leitura> buscarPorData(String dataBusca);
}
