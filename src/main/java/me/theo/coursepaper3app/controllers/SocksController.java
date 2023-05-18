package me.theo.coursepaper3app.controllers;

import io.swagger.v3.oas.annotations.Operation;
import me.theo.coursepaper3app.exception.InSufficientSockQuantityException;
import me.theo.coursepaper3app.models.Color;
import me.theo.coursepaper3app.models.Size;
import me.theo.coursepaper3app.models.Sock;
import me.theo.coursepaper3app.models.SockWQ;
import me.theo.coursepaper3app.services.SockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/socks")
public class SocksController {
    private  final SockService sockService;

    public SocksController(SockService sockService) {
        this.sockService = sockService;
    }

    @ExceptionHandler(InSufficientSockQuantityException.class)
    public ResponseEntity<String> handleInvalidException(InSufficientSockQuantityException invalidSockRequestException) {
        return ResponseEntity.badRequest().body(invalidSockRequestException.getMessage());
    }

    @PostMapping
    @Operation(summary = "добавление носков",description = "добавление носков через тело запроса в json")
    public ResponseEntity<Void> addSockToStock(@RequestBody Sock sock) {
       if (sockService.addSock(sock)){
           return ResponseEntity.ok().build();
       } else if (!sockService.addSock(sock)) {
           return ResponseEntity.badRequest().build();
       } else {
           return ResponseEntity.internalServerError().build();
       }
    }

    @PutMapping
    @Operation(summary = "выдача носков",description = "выдача носков через тело запроса в json")
    public ResponseEntity<Void> issueSocks(@RequestBody Sock sock) {
        if (sockService.issueFromStock(sock)) {
            return ResponseEntity.ok().build();
        } else if (!sockService.issueFromStock(sock)) {
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping
    @Operation(summary = "получение количества носков", description = "получение носков через тело запроса в json")
    public int getSocks(    @RequestParam(required = false, name = "color") Color color,
                            @RequestParam(required = false, name = "size") Size size,
                            @RequestParam(required = false, name = "cottonMin")Integer cottonMin,
                            @RequestParam(required = false, name = "cottonMax")Integer cottonMax) {
      return sockService.getSockQuantity(color, size, cottonMin, cottonMax);
    }

    @DeleteMapping
    @Operation(summary = "списание носков", description = "списание носков")
    public ResponseEntity<Void> removeDefectiveSocks(@RequestBody Sock sock) {
        if (sockService.decreaseSockQuantity(sock)) {
            return ResponseEntity.ok().build();
        } else if (!sockService.decreaseSockQuantity(sock)) {
            return ResponseEntity.badRequest().build();
        } else {
            return ResponseEntity.internalServerError().build();
        }
    }
}
