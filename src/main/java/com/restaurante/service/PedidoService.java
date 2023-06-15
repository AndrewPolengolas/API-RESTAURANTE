package com.restaurante.service;

import com.restaurante.helper.CodigoHelper;
import com.restaurante.helper.DataHelper;
import com.restaurante.helper.UserHelper;
import com.restaurante.model.Cliente;
import com.restaurante.model.Pedido;
import com.restaurante.model.Produto;
import com.restaurante.model.Usuario;
import com.restaurante.model.dto.PedidoDto;
import com.restaurante.model.type.Status;
import com.restaurante.model.type.StatusPedido;
import com.restaurante.repository.ClienteRepository;
import com.restaurante.repository.PedidoRepository;
import com.restaurante.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private CodigoHelper codigoHelper;

    @Autowired
    private DataHelper dataHelper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    public ResponseEntity<?> adicionar(PedidoDto pedidoDto) {
        Pedido pedido = new Pedido();

        pedido.setDataIni(dataHelper.getDataHora());
        pedido.setCodigo(codigoHelper.unique());
        pedido.setStatus(Status.ATIVO);

        Double total = 0.0;

        List<Produto> produtos = new ArrayList<>();

        for (String codigo : pedidoDto.getProdutos()){
            Produto produto = produtoRepository.findByStatusAndCodigo(
                    Status.ATIVO,
                    codigo
            );

            produtos.add(produto);
        }

        for (Produto produto : produtos) {
            total += produto.getPreco();
        }

        Cliente cliente = clienteRepository.findByUsuario(userHelper.getUser().get().getId());

        pedido.setProdutoList(produtos);
        pedido.setStatusPedido(StatusPedido.EM_ANDAMENTO);
        pedido.setValorTotal(total);

        Pedido retornoPedido = pedidoRepository.save(pedido);

        cliente.addPedido(retornoPedido);

        clienteRepository.save(cliente);

        return new ResponseEntity<>(retornoPedido, HttpStatus.CREATED);
    }

    public ResponseEntity<?> editar(PedidoDto pedidoDto){

        Pedido pedido = pedidoRepository.findByCodigo(pedidoDto.getCodigo());

        pedido.setDataAlt(dataHelper.getDataHora());
        pedido.setStatusPedido(pedidoDto.getStatusPedido());

        Pedido retornoPedido = pedidoRepository.save(pedido);

        return new ResponseEntity<>(retornoPedido, HttpStatus.OK);
    }

    public ResponseEntity<?> excluir(PedidoDto pedidoDto){

        Pedido pedido = pedidoRepository.findByCodigo(pedidoDto.getCodigo());

        pedido.setDataFim(dataHelper.getDataHora());
        pedido.setStatus(Status.INATIVO);
        pedido.setStatusPedido(StatusPedido.CANCELADO);

        pedidoRepository.save(pedido);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> listar(PedidoDto pedidoDto){

        Usuario usuario = userHelper.getUser().get();

        Cliente cliente = clienteRepository.findByUsuario(usuario.getId());

        if (pedidoDto.getCodigo() != null){
            Pedido pedido = pedidoRepository.findByCodigo(pedidoDto.getCodigo());

            return new ResponseEntity<>(pedido, HttpStatus.OK);
        }else {
            List<Pedido> pedidos = pedidoRepository.findByCliente(cliente);

            return new ResponseEntity<>(pedidos, HttpStatus.OK);
        }
    }

}
