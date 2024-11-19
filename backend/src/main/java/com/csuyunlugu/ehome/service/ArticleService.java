package com.csuyunlugu.ehome.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.csuyunlugu.ehome.dao.ArticleMapper;
import com.csuyunlugu.ehome.dto.ArticleDTO;
import com.csuyunlugu.ehome.entity.Admin;
import com.csuyunlugu.ehome.entity.Article;
import com.csuyunlugu.ehome.util.FileUploadUtil;
import jakarta.validation.constraints.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @ClassName ArticleService
 * @Description TODO
 * @Author lybugproducer
 * @Date 2024/11/8 15:39
 * @Version 1.0
 */
@Service
public class ArticleService {
    private final ArticleMapper articleMapper;

    public ArticleService(ArticleMapper articleMapper) {
        this.articleMapper = articleMapper;
    }

    public List<ArticleDTO> list(Integer count) {
        return articleMapper.selectList(new QueryWrapper<Article>()
                        .orderByDesc("id")
                        .last("limit " + count))
                .stream()
                .map(article -> {
                    article.setContent(null);
                    return ArticleDTO.convertToArticleDTO(article);
                })
                .toList();
    }

    public ArticleDTO read(Integer id) {
        return ArticleDTO.convertToArticleDTO(articleMapper.selectById(id));
    }

    public void uploadArticle(Admin admin, @NotNull String title, @NotNull String content, @NotNull MultipartFile file) {
        String pictureUrl = FileUploadUtil.storeFile(file, "article");
        Article article = new Article();
        article.setAuthor(admin.getUsername());
        article.setTitle(title);
        article.setContent(content);
        article.setPictureUrl(pictureUrl);
        articleMapper.insert(article);
    }

    public void deleteArticle(@NotNull Integer id) {
        articleMapper.deleteById(id);
    }

    public List<ArticleDTO> findArticles() {
        return articleMapper.selectList(new QueryWrapper<Article>()
                        .orderByDesc("id"))
                .stream()
                .map(article -> {
                     article.setContent(null);
                     return ArticleDTO.convertToArticleDTO(article);
                 })
                .toList();
    }

    public void updateArticle(@NotNull Integer id, @NotNull String title, @NotNull String content, @NotNull MultipartFile file) {
        String url = FileUploadUtil.storeFile(file, "article");
        Article article = new Article();
        article.setId(id);
        article.setTime(Timestamp.valueOf(LocalDateTime.now()));
        article.setTitle(title);
        article.setContent(content);
        article.setPictureUrl(url);
        articleMapper.updateById(article);
    }
}
