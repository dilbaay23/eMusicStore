package com.emusicstore.controller;


import com.emusicstore.dao.ProductDao;
import com.emusicstore.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * Created by Moon
 * on 12/3/2020.
 */

@Controller
public class HomeController {

    private Path path;

    @Autowired
    private  ProductDao productDao ;

    @Autowired
    public HomeController(ProductDao productDao) {
        this.productDao = productDao;
    }

    @RequestMapping("/")
    public String home() {
        return "home";
    }

    @RequestMapping("/productList")
    public String getProduct(Model model){
        List<Product> products = productDao.getALlProducts();

        model.addAttribute("products",products);

        return "productList";
    }

    @RequestMapping("/productList/viewProduct/{productId}")
    public String viewProduct(@PathVariable String productId, Model model) throws IOException {
        Product product = productDao.getProductById(productId);

        model.addAttribute("product",product);


        return "viewProduct";
    }

    @RequestMapping("/admin")
    public String adminPage(Model model){


        return "admin";
    }
    @RequestMapping("/admin/productInventory")
    public String productInventory( Model model)  {

        List<Product> products = productDao.getALlProducts();

        model.addAttribute("products",products);

        return "productInventory";
    }

    @RequestMapping("/admin/productInventory/addProduct")
    public String addProduct(Model model)  {

       Product product=new Product();
       product.setProductCategory("Instrument");
       product.setProductCondition("New");
       product.setProductStatus("Active");

        model.addAttribute("product",product);

        return "addProduct";
    }

    @RequestMapping(value = "/admin/productInventory/addProduct", method = RequestMethod.POST)
    public String addProductPost(@ModelAttribute ("product") Product product, HttpServletRequest request)  {

        productDao.addProduct(product);

        MultipartFile productImage= product.getProductImage();
        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        path= Paths.get(rootDirectory+ "\\WEB-INF\\resources\\images\\" + product.getProductId()+ ".png");
        if (productImage != null && !productImage.isEmpty()){
            try {
                productImage.transferTo(new File(path.toString()));
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Product image saving failed", e);
            }
        }
        return "redirect:/admin/productInventory";
    }

    @RequestMapping("/admin/productInventory/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable String productId, Model model,HttpServletRequest request)  {

        String rootDirectory = request.getSession().getServletContext().getRealPath("/");
        path= Paths.get(rootDirectory+ "\\WEB-INF\\resources\\images\\" + productId + ".png");

        if (Files.exists(path)){
            try {
                Files.delete(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        productDao.deleteProduct(productId);


        return "redirect:/admin/productInventory";
    }


}
