package com.restaurante.repository;

import com.restaurante.model.Produto;
import com.restaurante.model.type.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

    Produto findByStatusAndCodigo(Status status, String codigo);

    @Query(value = "SELECT FIRST :total SKIP :inicio * " +
            " FROM produto p " +
            " WHERE p.status = 'ATIVO' " +
            "   AND (p.preco BETWEEN :precoMin AND :precoMax OR (:precoMin IS NULL AND :precoMax IS NULL)) " +
            "   AND (p.nome LIKE '%' || :nome || '%' OR :nome IS NULL) " +
            " ORDER BY p.preco DESC ", nativeQuery = true)
    List<Produto> filtarProdutosPrecoDesc(@Param("precoMin") Double precoMin,
                                          @Param("precoMax") Double precoMax,
                                          @Param("nome") String nome,
                                          @Param("inicio") Integer inicio,
                                          @Param("total") Integer total);

    @Query(value = "SELECT FIRST :total SKIP :inicio * " +
            " FROM produto p " +
            " WHERE p.status = 'ATIVO' " +
            "   AND (p.preco BETWEEN :precoMin AND :precoMax OR (:precoMin IS NULL AND :precoMax IS NULL)) " +
            "   AND (p.nome LIKE '%' || :nome || '%' OR :nome IS NULL) " +
            " ORDER BY p.preco ASC ", nativeQuery = true)
    List<Produto> filtarProdutosPrecoAsc(@Param("precoMin") Double precoMin,
                                         @Param("precoMax") Double precoMax,
                                         @Param("nome") String nome,
                                         @Param("inicio") Integer inicio,
                                         @Param("total") Integer total);

    @Query(value = "SELECT COUNT(*) FROM produto p  " +
            " WHERE p.status = 'ATIVO' " +
            "   AND (p.preco BETWEEN :precoMin AND :precoMax OR (:precoMin IS NULL AND :precoMax IS NULL)) " +
            "   AND (p.nome LIKE '%' || :nome || '%' OR :nome IS NULL) ", nativeQuery = true)
    Long totalFiltarProdutosPreco(@Param("precoMin") Double precoMin,
                                  @Param("precoMax") Double precoMax,
                                  @Param("nome") String nome);
}
