package com.ablokhin.chartographer.controller;

import com.ablokhin.chartographer.exception.ChartaNotFoundException;
import com.ablokhin.chartographer.exception.SizePositionException;
import com.ablokhin.chartographer.service.ChartaServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/chartas/")
public class ChartaController {

    @Autowired
    private ChartaServiceImpl chartaServiceImpl;

    @PostMapping
    public ResponseEntity createCharta(@RequestParam Integer width,@RequestParam Integer height) {
        try {
            Long id = chartaServiceImpl.createCharta(width, height);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (SizePositionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ChartaNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e){
            return ResponseEntity.ok().body(e.getStackTrace());
        }
    }

    @PostMapping("{id}/")
    public ResponseEntity addFragment(@PathVariable Long id, @RequestBody byte[] postedFragment,
                                      @RequestParam Integer x, @RequestParam Integer y,
                                      @RequestParam Integer width, @RequestParam Integer height) {
        try {
            chartaServiceImpl.createFragment(id, postedFragment, x, y, width, height);
            return ResponseEntity.ok("");
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        } catch (SizePositionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (ChartaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "{id}/", produces = "image/bmp")
    public ResponseEntity<byte[]> getFragment(@PathVariable Long id,
                                         @RequestParam Integer x, @RequestParam Integer y,
                                         @RequestParam Integer width, @RequestParam Integer height) {
        try {
            return ResponseEntity.ok().body(chartaServiceImpl.getFragment(id, x, y, width, height));
        } catch (SizePositionException e) {
            return ResponseEntity.badRequest().build();
        } catch (ChartaNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "{id}/")
    public ResponseEntity deleteCharta(@PathVariable Long id) {
        try {
            chartaServiceImpl.deleteCharta(id);
            return ResponseEntity.ok().body("");
        } catch (ChartaNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
