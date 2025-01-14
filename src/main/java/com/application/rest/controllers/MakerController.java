package com.application.rest.controllers;

import com.application.rest.controllers.dto.MakerDTO;
import com.application.rest.entities.Maker;
import com.application.rest.service.IMakerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api/maker") //establecemos la ruta de los creadores
public class MakerController {

    @Autowired
    private IMakerService makerService;

    @RequestMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable Long id) {
        Optional<Maker> makerOptional = makerService.findById(id);

        if(makerOptional.isPresent()){
            Maker maker = makerOptional.get(); //Obtenemos la entidad entity

            //Construimos nuestro DTO
            MakerDTO makerDTO = MakerDTO.builder()
                    .id(maker.getId())
                    .name(maker.getName())
                    .productList(maker.getProductList())
                    .build();
            return ResponseEntity.ok(makerDTO);
        }

        return ResponseEntity.notFound().build();
    }

    @GetMapping("/findAll")
    public ResponseEntity<?> findAll(){
        List<MakerDTO> makerList = makerService.findAll()
                .stream()
                .map(maker -> MakerDTO.builder()
                        .id(maker.getId())
                        .name(maker.getName())
                        .productList(maker.getProductList())
                        .build())
                .toList();

        return ResponseEntity.ok(makerList);
    }

    @PostMapping("/save")
    public ResponseEntity<?> save(@RequestBody MakerDTO makerDTO) throws URISyntaxException {

        //Validamos que el name no sea nulo ya que es un parametro obligatorio
        if(makerDTO.getName().isBlank()){
            return ResponseEntity.badRequest().build();
        }

        //Convertimos el DTO en Maker ya que makerService no puede recibir un DTO
        makerService.save(Maker.builder()
                .name(makerDTO.getName())
                .build());

        return ResponseEntity.created(new URI("/api/maker/save")).build();
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateMaker(@PathVariable Long id, @RequestBody MakerDTO makerDTO){

        Optional<Maker> makerOptional = makerService.findById(id);

        //validamos si el maker esta presente
        if(makerOptional.isPresent()){

            //Recuperamos el objeto
            Maker maker = makerOptional.get();
            //Cambiamos el nombre por el que pasamos por parametros
            maker.setName(makerDTO.getName());
            //Guardamos los cambios
            makerService.save(maker);

            return ResponseEntity.ok("Registro Actualizado");
        }

        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public  ResponseEntity<?> deleteById(@PathVariable Long id){

        if(id != null){
            makerService.deleteById(id);
            return ResponseEntity.ok("Registro eliminado");
        }

        return ResponseEntity.badRequest().build();
    }
}
