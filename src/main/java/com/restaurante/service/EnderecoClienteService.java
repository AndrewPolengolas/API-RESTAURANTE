package com.restaurante.service;

import com.restaurante.helper.DataHelper;
import com.restaurante.helper.UserHelper;
import com.restaurante.model.Cliente;
import com.restaurante.model.EnderecoCliente;
import com.restaurante.model.Usuario;
import com.restaurante.model.dto.EnderecoDto;
import com.restaurante.model.type.Status;
import com.restaurante.repository.ClienteRepository;
import com.restaurante.repository.EnderecoClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnderecoClienteService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private DataHelper dataHelper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private EnderecoClienteRepository enderecoClienteRepository;


    public ResponseEntity<?> adicionar(EnderecoDto enderecoDto){

        Usuario usuario = userHelper.getUser().get();

        Cliente cliente = clienteRepository.findByUsuario(usuario.getId());

        if (cliente == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        EnderecoCliente endereco = new EnderecoCliente();

        endereco.setDataIni(dataHelper.getDataHora());
        endereco.setStatus(Status.ATIVO);
        endereco.setCep(enderecoDto.getCep());
        endereco.setCidade(enderecoDto.getCidade());
        endereco.setComplemento(enderecoDto.getComplemento());
        endereco.setEstado(enderecoDto.getEstado());
        endereco.setPais(enderecoDto.getPais());
        endereco.setNumero(enderecoDto.getNumero());
        endereco.setLogradouro(enderecoDto.getLogradouro());
        endereco.setTipoEndereco(enderecoDto.getTipoEndereco());
        endereco.setCliente(cliente);

        EnderecoCliente retorno = enderecoClienteRepository.save(endereco);

        cliente.addEndereco(retorno);

        clienteRepository.save(cliente);

        return new ResponseEntity<>(retorno, HttpStatus.CREATED);
    }

    public ResponseEntity<?> editar(EnderecoDto enderecoDto){

        EnderecoCliente endereco = enderecoClienteRepository.findById(enderecoDto.getId()).get();

        if (endereco == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = userHelper.getUser().get();

        Cliente cliente = clienteRepository.findByUsuario(usuario.getId());

        if (cliente.getId() != endereco.getCliente().getId()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        endereco.setDataAlt(dataHelper.getDataHora());
        endereco.setCep(enderecoDto.getCep() != null ? enderecoDto.getCep() : endereco.getCep());
        endereco.setCidade(enderecoDto.getCidade() != null ? enderecoDto.getCidade() : endereco.getCidade());
        endereco.setComplemento(enderecoDto.getComplemento() != null ? enderecoDto.getComplemento() : endereco.getComplemento());
        endereco.setEstado(enderecoDto.getEstado() != null ? enderecoDto.getEstado() : endereco.getEstado());
        endereco.setPais(enderecoDto.getPais() != null ? enderecoDto.getPais() : endereco.getPais());
        endereco.setNumero(enderecoDto.getNumero() != null ? endereco.getNumero() : endereco.getNumero());
        endereco.setLogradouro(enderecoDto.getLogradouro() != null ? enderecoDto.getLogradouro() : endereco.getLogradouro());
        endereco.setTipoEndereco(enderecoDto.getTipoEndereco() != null ? enderecoDto.getTipoEndereco() : endereco.getTipoEndereco());

        EnderecoCliente retorno = enderecoClienteRepository.save(endereco);

        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }

    public ResponseEntity<?> excluir(EnderecoDto enderecoDto){

        EnderecoCliente endereco = enderecoClienteRepository.findById(enderecoDto.getId()).get();

        if (endereco == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Usuario usuario = userHelper.getUser().get();

        Cliente cliente = clienteRepository.findByUsuario(usuario.getId());

        if (cliente.getId() != endereco.getCliente().getId()){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        endereco.setDataFim(dataHelper.getDataHora());
        endereco.setStatus(Status.INATIVO);

        enderecoClienteRepository.save(endereco);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> listar(){
        Usuario usuario = userHelper.getUser().get();

        Cliente cliente = clienteRepository.findByUsuario(usuario.getId());

        if (cliente == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<EnderecoCliente> enderecoCliente = enderecoClienteRepository.findByCliente(cliente);

        return new ResponseEntity<>(enderecoCliente, HttpStatus.OK);
    }
}
