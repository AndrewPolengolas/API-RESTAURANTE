package com.restaurante.service;

import com.restaurante.helper.CodigoHelper;
import com.restaurante.helper.DataHelper;
import com.restaurante.helper.UserHelper;
import com.restaurante.model.*;
import com.restaurante.model.dto.EntregaDto;
import com.restaurante.model.type.Status;
import com.restaurante.model.type.StatusEntrega;
import com.restaurante.repository.ClienteRepository;
import com.restaurante.repository.EnderecoClienteRepository;
import com.restaurante.repository.EntregaRepository;
import com.restaurante.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EntregaService {

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
    private EnderecoClienteRepository enderecoClienteRepository;

    @Autowired
    private EntregaRepository entregaRepository;

    public ResponseEntity<?> adicionar(EntregaDto entregaDto) {

        Entrega entrega = new Entrega();

        Usuario usuario = userHelper.getUser().get();

        Cliente cliente = clienteRepository.findByUsuario(usuario.getId());

        Pedido pedido = pedidoRepository.findByCodigo(entregaDto.getPedido());

        if (pedido == null || cliente == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        if (cliente.getEnderecos().isEmpty()){
            return new ResponseEntity<>("Favor cadastrar um endereço para entrega", HttpStatus.NOT_ACCEPTABLE);
        }

        EnderecoCliente enderecoCliente = enderecoClienteRepository.findById(entregaDto.getEndereco()).get();

        entrega.setDataIni(dataHelper.getDataHora());
        entrega.setStatus(Status.ATIVO);
        entrega.setStatusEntrega(StatusEntrega.EM_ANDAMENTO);
        entrega.setCodigo(codigoHelper.unique());
        entrega.setEndereco(enderecoCliente);
        entrega.setPedido(pedido);
        entrega.setCliente(cliente);

        Entrega retorno = entregaRepository.save(entrega);

        return new ResponseEntity<>(retorno, HttpStatus.CREATED);
    }

    public ResponseEntity<?> editar(EntregaDto entregaDto){

        Entrega entrega = entregaRepository.findByCodigo(entregaDto.getCodigo());

        entrega.setDataAlt(dataHelper.getDataHora());
        entrega.setStatusEntrega(entregaDto.getStatusEntrega());

        Entrega retorno = entregaRepository.save(entrega);

        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }

    public ResponseEntity<?> excluir(EntregaDto entregaDto){

        Entrega entrega = entregaRepository.findByCodigo(entregaDto.getCodigo());

        entrega.setDataAlt(dataHelper.getDataHora());
        entrega.setStatus(Status.INATIVO);

        entregaRepository.save(entrega);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> listar(EntregaDto entregaDto){

        Usuario usuario = userHelper.getUser().get();

        Cliente cliente = clienteRepository.findByUsuario(usuario.getId());

        if (entregaDto.getCodigo() != null){

            Entrega entrega = entregaRepository.findByCodigo(entregaDto.getCodigo());

            return new ResponseEntity<>(entrega, HttpStatus.OK);
        }else {

            List<Entrega> entregas = entregaRepository.findByCliente(cliente);

            return new ResponseEntity<>(entregas, HttpStatus.OK);
        }
    }
}
