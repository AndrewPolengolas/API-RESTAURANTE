package com.restaurante.repository;

import com.restaurante.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {

    Cliente findByUsuario(Long usuario);

    @Query(value = "SELECT FIRST :total SKIP :inicio * " +
            " FROM CLIENTE " +
            " WHERE STATUS = 'ATIVO'  " +
            "   AND (NOME LIKE :nome OR :nome IS NULL) " +
            "   AND (EMAIL LIKE :email OR :email IS NULL) " +
            "   AND (DOCUMENTO LIKE :documento OR :documento IS NULL) " +
            "   AND (CELULAR LIKE :celular OR :celular IS NULL) " +
            " ORDER BY DATA_INI DESC", nativeQuery = true)
    List<Cliente> listarClientesFiltro(@Param("nome") String nome,
                                       @Param("email") String email,
                                       @Param("documento") String documento,
                                       @Param("celular") String celular,
                                       @Param("inicio") Integer inicio,
                                       @Param("total") Integer total);

    @Query(value = "SELECT COUNT(*) FROM CLIENTE  " +
            " WHERE STATUS = 'ATIVO' " +
            "   AND (NOME LIKE :nome OR :nome IS NULL) " +
            "   AND (EMAIL LIKE :email OR :email IS NULL) " +
            "   AND (DOCUMENTO LIKE :documento OR :documento IS NULL) " +
            "   AND (CELULAR LIKE :celular OR :celular IS NULL) ", nativeQuery = true)
    Long totalClientesFiltro(@Param("nome") String nome,
                             @Param("email") String email,
                             @Param("documento") String documento,
                             @Param("celular") String celular);

}
