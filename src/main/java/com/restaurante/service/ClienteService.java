package com.restaurante.service;

import com.restaurante.helper.DataHelper;
import com.restaurante.helper.FormatadorHelper;
import com.restaurante.helper.UserHelper;
import com.restaurante.model.Cliente;
import com.restaurante.model.dto.ClienteDto;
import com.restaurante.model.type.Status;
import com.restaurante.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteService {

    @Autowired
    private UserHelper userHelper;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private FormatadorHelper formatadorHelper;

    @Autowired
    private DataHelper dataHelper;

    public ResponseEntity<?> adicionar(ClienteDto clienteDTO){

        Cliente objCliente = clienteRepository.findByUsuario(userHelper.getUser().get().getId());

        if (objCliente != null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        Cliente cliente = new Cliente();

        cliente.setNome(clienteDTO.getNome());
        cliente.setDocumento(clienteDTO.getDocumento() != null ? formatadorHelper.formatarDocumentoBanco(clienteDTO.getDocumento()) : null);
        cliente.setTipoDocumento(clienteDTO.getTipoDocumento() != null ? clienteDTO.getTipoDocumento() : null);
        cliente.setCelular(clienteDTO.getCelular() != null ? formatadorHelper.formatarCelularBanco(clienteDTO.getCelular()) : null);
        cliente.setEmail(clienteDTO.getEmail());
        cliente.setTipoPessoa(clienteDTO.getTipoPessoa());
        cliente.setUsuario(userHelper.getUser().get().getId());
        cliente.setDataIni(dataHelper.getDataHora());
        cliente.setStatus(Status.ATIVO);

        Cliente retorno = clienteRepository.save(cliente);

        return new ResponseEntity<>(retorno, HttpStatus.CREATED);
    }

    public ResponseEntity<?> alterar(ClienteDto clienteDTO){

        Cliente cliente = clienteRepository.findById(clienteDTO.getId()).get();

        if (cliente == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (cliente.getId() != clienteDTO.getId()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        cliente.setNome(clienteDTO.getNome() != null ? clienteDTO.getNome() : cliente.getNome());
        cliente.setDocumento(clienteDTO.getDocumento() != null ? formatadorHelper.formatarDocumentoBanco(clienteDTO.getDocumento()) : cliente.getDocumento());
        cliente.setTipoDocumento(clienteDTO.getTipoDocumento() != null ? clienteDTO.getTipoDocumento() : cliente.getTipoDocumento());
        cliente.setCelular(clienteDTO.getCelular() != null ? formatadorHelper.formatarCelularBanco(clienteDTO.getCelular()) : cliente.getCelular());
        cliente.setEmail(clienteDTO.getEmail() != null ? clienteDTO.getEmail() : cliente.getEmail());
        cliente.setTipoPessoa(clienteDTO.getTipoPessoa() != null ? clienteDTO.getTipoPessoa() : cliente.getTipoPessoa());
        cliente.setDataAlt(dataHelper.getDataHora());

        Cliente retorno = clienteRepository.save(cliente);

        return new ResponseEntity<>(retorno, HttpStatus.OK);
    }

    public ResponseEntity<?> excluir(ClienteDto clienteDTO){

        Cliente cliente = clienteRepository.findById(clienteDTO.getId()).get();

        if (cliente == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else if (cliente.getId() != clienteDTO.getId()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        if (cliente.getStatus().equals(Status.ATIVO)){
            cliente.setDataFim(dataHelper.getDataHora());
            cliente.setStatus(Status.INATIVO);

            clienteRepository.save(cliente);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<?> listar(ClienteDto clienteDto){

        if (clienteDto.getPagina() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Integer pageNo   = clienteDto.getPagina() - 1;
        Integer pageSize = 20;
        String sortBy    = "id";
        Pageable paging  = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

        List<Cliente> clientes = clienteRepository.listarClientesFiltro(
                clienteDto.getNome() != null ? clienteDto.getNome() : null,
                clienteDto.getEmail() != null ? clienteDto.getEmail() : null,
                clienteDto.getDocumento() != null ? formatadorHelper.formatarDocumentoBanco(clienteDto.getDocumento()) : null,
                clienteDto.getCelular() != null ? formatadorHelper.formatarCelularBanco(clienteDto.getCelular()) : null,
                pageNo * pageSize,
                pageSize
        );

        Long total = clienteRepository.totalClientesFiltro(
                clienteDto.getNome() != null ? clienteDto.getNome() : null,
                clienteDto.getEmail() != null ? clienteDto.getEmail() : null,
                clienteDto.getDocumento() != null ? formatadorHelper.formatarDocumentoBanco(clienteDto.getDocumento()) : null,
                clienteDto.getCelular() != null ? formatadorHelper.formatarCelularBanco(clienteDto.getCelular()) : null
        );

        Page<Cliente> page = new PageImpl<>(clientes, paging, total);

        return new ResponseEntity<>(page, HttpStatus.OK);
    }
}
