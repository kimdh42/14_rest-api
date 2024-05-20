package com.ohgiraffers.springdatajpa.product.controller;


import com.ohgiraffers.springdatajpa.common.Pagenation;
import com.ohgiraffers.springdatajpa.common.PagingButton;
import com.ohgiraffers.springdatajpa.product.dto.ProductDTO;
import com.ohgiraffers.springdatajpa.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    /* 특정 코드로 제품 조회 */
    @GetMapping("/{productCode}")
    public String findProductByCode(@PathVariable int productCode, Model model) {

        ProductDTO resultMenu = productService.findProductByProductCode(productCode);

        model.addAttribute("product", resultMenu);

        return "product/detail";
    }

    @GetMapping("/list")
    public String findProductList(Model model, @PageableDefault Pageable pageable) {

        /* 페이징 처리 이전 */
//        List<MenuDTO> menuList = menuService.findMenuList();
//
//        model.addAttribute("menuList", menuList);

        /* 페이징 처리 이후 */
        log.info("pageable: {}", pageable);

        Page<ProductDTO> productList = productService.findProductList(pageable);

        log.info("{}", productList.getContent());
        log.info("{}", productList.getTotalPages());
        log.info("{}", productList.getTotalElements());
        log.info("{}", productList.getSize());
        log.info("{}", productList.getNumberOfElements());
        log.info("{}", productList.isFirst());
        log.info("{}", productList.isLast());
        log.info("{}", productList.getSort());
        log.info("{}", productList.getNumber());

        PagingButton paging = Pagenation.getPagingButtonInfo(productList);

        model.addAttribute("productList", productList);
        model.addAttribute("paging", paging);

        return "product/list";
    }
//
//    @GetMapping("/querymethod")
//    public void querymethodPage() {}
//
//    @GetMapping("/search")
//    public String findByMenuPrice(@RequestParam Integer menuPrice, Model model) {
//
//        List<ProductDTO> menuList = menuService.findByMenuPrice(menuPrice);
//
//        model.addAttribute("menuList", menuList);
//        model.addAttribute("menuPrice", menuPrice);
//
//        return "menu/searchResult";
//    }
//
//    @GetMapping("/regist")
//    public void registPage() {}
//
//    @GetMapping("/category")
//    @ResponseBody
//    public List<CategoryDTO> findCategoryList() {
//
//        return menuService.findAllCategory();
//    }
//
//    @PostMapping("/regist")
//    public String registNewMenu(@ModelAttribute ProductDTO menuDTO) {
//
//        menuService.registMenu(menuDTO);
//
//        return "redirect:/menu/list";
//    }
//
//    @GetMapping("/modify")
//    public void modifyPage() {}
//
//    @PostMapping("/modify")
//    public String modifyMenu(@ModelAttribute ProductDTO menuDTO) {
//
//        menuService.modifyMenu(menuDTO);
//
//        return "redirect:/menu/" + menuDTO.getMenuCode();
//    }
//
//    @GetMapping("/delete")
//    public void deletePage() {}
//
//    @PostMapping("/delete")
//    public String deleteMenu(@RequestParam Integer menuCode) {
//
//        menuService.deleteMenu(menuCode);
//
//        return "redirect:/menu/list";
//    }
}
