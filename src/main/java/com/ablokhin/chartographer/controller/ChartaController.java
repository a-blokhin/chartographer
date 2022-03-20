package com.ablokhin.chartographer.controller;

import com.ablokhin.chartographer.exception.FragmentNotFoundException;
import com.ablokhin.chartographer.exception.IntersectionException;
import com.ablokhin.chartographer.service.ChartaServiceImpl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Slf4j
@RestController
@RequestMapping("/chartas/")
public class ChartaController {

    @Autowired
    private ChartaServiceImpl chartaServiceImpl;

    @PostMapping
    public ResponseEntity createCharta(@RequestParam Integer width, @RequestParam Integer height) {
        try {
            String id = chartaServiceImpl.createCharta(width, height);
            return ResponseEntity.status(HttpStatus.CREATED).body(id);
        } catch (IntersectionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FragmentNotFoundException | IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("{id}/")
    public ResponseEntity addFragment(@PathVariable String id, @RequestBody byte[] postedFragment,
                                      @RequestParam int x, @RequestParam int y,
                                      @RequestParam int width, @RequestParam int height) {
        try {
            chartaServiceImpl.addFragment(id, postedFragment, x, y, width, height);
            return ResponseEntity.ok("");
        } catch (IOException | IntersectionException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (FragmentNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping(value = "{id}/", produces = "image/bmp")
    public ResponseEntity<byte[]> getFragment(@PathVariable String id,
                                              @RequestParam int x, @RequestParam int y,
                                              @RequestParam int width, @RequestParam int height) {
        try {
            return ResponseEntity.ok().body(chartaServiceImpl.getFragment(id, x, y, width, height));
        } catch (IntersectionException e) {
            return ResponseEntity.badRequest().build();
        } catch (FragmentNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IOException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping(value = "{id}/")
    public ResponseEntity deleteCharta(@PathVariable String id) {
        try {
            chartaServiceImpl.deleteCharta(id);
            return ResponseEntity.ok().body("");
        } catch (FragmentNotFoundException | IOException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
