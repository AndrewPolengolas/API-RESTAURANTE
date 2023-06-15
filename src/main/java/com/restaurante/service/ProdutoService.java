package com.restaurante.service;

import com.restaurante.helper.CodigoHelper;
import com.restaurante.helper.DataHelper;
import com.restaurante.model.Produto;
import com.restaurante.model.dto.ProdutoDto;
import com.restaurante.model.type.Status;
import com.restaurante.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private CodigoHelper codigoHelper;

    @Autowired
    private DataHelper dataHelper;

    public ResponseEntity<?> listar(ProdutoDto filtro) {

        Integer pageNo = filtro.getPagina() - 1;
        Integer pageSize = 20;
        String sortBy = "id";
        Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        Page<Produto> produtosPaging = null;

        List<Produto> listaProdutos;

        Long total;

        switch (filtro.getTipoPreco()) {
            case MENOR_PRECO:
                listaProdutos = produtoRepository.filtarProdutosPrecoAsc(
                        filtro.getPrecoMin() != null ? filtro.getPrecoMin() : null,
                        filtro.getPrecoMax() != null ? filtro.getPrecoMax() : null,
                        filtro.getNome() != null ? filtro.getNome() : null,
                        pageNo * pageSize,
                        pageSize
                );

                total = produtoRepository.totalFiltarProdutosPreco(
                        filtro.getPrecoMin() != null ? filtro.getPrecoMin() : null,
                        filtro.getPrecoMax() != null ? filtro.getPrecoMax() : null,
                        filtro.getNome() != null ? filtro.getNome() : null
                );

                produtosPaging = new PageImpl<>(listaProdutos, paging, total);
                break;
            case MAIOR_PRECO:
                listaProdutos = produtoRepository.filtarProdutosPrecoDesc(
                        filtro.getPrecoMin() != null ? filtro.getPrecoMin() : null,
                        filtro.getPrecoMax() != null ? filtro.getPrecoMax() : null,
                        filtro.getNome() != null ? filtro.getNome() : null,
                        pageNo * pageSize,
                        pageSize
                );

                total = produtoRepository.totalFiltarProdutosPreco(
                        filtro.getPrecoMin() != null ? filtro.getPrecoMin() : null,
                        filtro.getPrecoMax() != null ? filtro.getPrecoMax() : null,
                        filtro.getNome() != null ? filtro.getNome() : null
                );

                produtosPaging = new PageImpl<>(listaProdutos, paging, total);
                break;
        }

        return new ResponseEntity<>(produtosPaging, HttpStatus.OK);
    }

    public ResponseEntity<?> adicionar(ProdutoDto produtoDto) {

        Produto produto = new Produto();

        produto.setNome(produtoDto.getNome());
        produto.setPreco(produtoDto.getPreco());
        produto.setDescricao(produtoDto.getDescricao());

        produto.setDataIni(dataHelper.getDataHora());
        produto.setCodigo(codigoHelper.unique());
        produto.setStatus(Status.ATIVO);

        Produto retorno = produtoRepository.save(produto);

        return new ResponseEntity<>(retorno, HttpStatus.CREATED);
    }

    public ResponseEntity<?> editar(ProdutoDto produtoDto) {

        Produto objProduto = produtoRepository.findByStatusAndCodigo(
                Status.ATIVO,
                produtoDto.getCodigo()
        );

        if (objProduto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        objProduto.setNome(produtoDto.getNome());
        objProduto.setPreco(produtoDto.getPreco());
        objProduto.setDescricao(produtoDto.getDescricao());

        objProduto.setDataAlt(dataHelper.getDataHora());

        Produto retorno = produtoRepository.save(objProduto);

        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }

    public ResponseEntity<?> excluir(ProdutoDto produtoDto) {

        Produto objProduto = produtoRepository.findByStatusAndCodigo(
                Status.ATIVO,
                produtoDto.getCodigo()
        );

        if (objProduto == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        objProduto.setDataFim(dataHelper.getDataHora());
        objProduto.setStatus(Status.INATIVO);

        produtoRepository.save(objProduto);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
