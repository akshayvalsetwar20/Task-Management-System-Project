package com.tms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.tms.dto.CategoryDTO;
import com.tms.service.CategoryFrontendService;

@Controller
@RequestMapping("/admin/dashboard/category")
public class CategoryController {

    @Autowired
    CategoryFrontendService categoryService;

    
    @GetMapping("/all")
    public String getAllCategories(Model model) {
        List<CategoryDTO> categories = categoryService.getAllCategories();
        model.addAttribute("category", categories);
        return "category"; 
    }

   
    @GetMapping("/create")
    public String showCreateCategoryPage(Model model) {
        model.addAttribute("category", new CategoryDTO());
        return "category-create"; 
    }

    
    @PostMapping("/save")
    public String createCategory(@ModelAttribute("category") CategoryDTO categoryDTO, Model model) {
        String response = categoryService.createCategory(categoryDTO);
        model.addAttribute("message", response);
        return "redirect:/admin/dashboard/category/all"; 
    }

    
    @GetMapping("/{categoryId}")
    public String getCategory(@PathVariable("categoryId") Integer categoryId, Model model) {
        CategoryDTO category = categoryService.getCategoryById(categoryId);
        model.addAttribute("category", category);
        return "category"; 
    }

    
    @GetMapping("/edit/{categoryId}")
    public String showEditCategoryPage(@PathVariable("categoryId") Integer categoryId, Model model) {
        
        CategoryDTO category = categoryService.getCategoryById(categoryId);

        
        if (category == null) {
           
            model.addAttribute("message", "Invalid Category ID! The category does not exist.");
            return "category-message"; 
        }

       
        model.addAttribute("category", category);
        return "category-form"; 
    }

    
    @PostMapping("/update/{categoryId}")
    public String updateCategory(@PathVariable("categoryId") Integer categoryId, 
                                  @ModelAttribute("category") CategoryDTO categoryDTO, 
                                  Model model) {
        
        String response = categoryService.updateCategory(categoryId, categoryDTO);
        model.addAttribute("message", response); 
        return "redirect:/admin/dashboard/category/all"; 
    }


   
    @GetMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable("categoryId") Integer categoryId, Model model) {
        String response = categoryService.deleteCategory(categoryId);
        model.addAttribute("message", response);
        return "redirect:/admin/dashboard/category/all"; // Redirect to category list
    }

    
    
 
    @GetMapping("/searchById/{categoryId}")
    public String searchCategoryById(@RequestParam("categoryId") Integer categoryId, Model model) {
        
        CategoryDTO category = categoryService.searchCategoryById(categoryId);

        if (category == null) {
            
            model.addAttribute("message", "Invalid Category ID! The category does not exist.");
            return "category"; 
        }

 
        model.addAttribute("category", category);
        return "category";
        
        
    }
    
    
    
}
