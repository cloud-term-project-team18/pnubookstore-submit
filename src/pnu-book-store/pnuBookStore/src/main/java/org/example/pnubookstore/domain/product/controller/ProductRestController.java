package org.example.pnubookstore.domain.product.controller;

import lombok.RequiredArgsConstructor;
import org.example.pnubookstore.domain.product.dto.FindProductsDto;
import org.example.pnubookstore.domain.product.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductRestController {

    private final ProductService productService;

    @GetMapping(value = "/api/products")
    public Page<FindProductsDto> products(@RequestParam(value="page", defaultValue="0") int page,
                                          @RequestParam(value = "college", required = false) String college,
                                          @RequestParam(value = "department", required = false) String department,
                                          @RequestParam(value = "professor", required = false) String professor,
                                          @RequestParam(value = "course", required = false) String subjectName){
        return productService.findProductList(page, college, department, professor, subjectName);
    }
}
