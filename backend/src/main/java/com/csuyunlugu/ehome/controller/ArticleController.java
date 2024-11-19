package com.csuyunlugu.ehome.controller;

import com.csuyunlugu.ehome.dto.ArticleDTO;
import com.csuyunlugu.ehome.service.ArticleService;
import com.csuyunlugu.ehome.dto.ApiResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @ClassName ArticleController
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/8 15:34
 * @Version 1.0
 */
@RestController
@RequestMapping("/api/article")
public class ArticleController {
    private final ArticleService articleService;

    public ArticleController(ArticleService articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/list")
    public ApiResponse<List<ArticleDTO>> list(@NotNull Integer count) {
        List<ArticleDTO> list = articleService.list(count);
        return ApiResponse.success(list);
    }

    @GetMapping("/read")
    public ApiResponse<ArticleDTO> read(@NotNull Integer id) {
        ArticleDTO articleDTO = articleService.read(id);
        return ApiResponse.success(articleDTO);
    }
}
