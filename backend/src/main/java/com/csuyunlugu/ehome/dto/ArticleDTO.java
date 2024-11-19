package com.csuyunlugu.ehome.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.csuyunlugu.ehome.entity.Article;
import com.csuyunlugu.ehome.util.TimeUtil;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @ClassName ArticleDTO
 * @Description  TODO
 * @Author lybugproducer
 * @Date 2024/11/8 17:04
 * @Version 1.0
 */
@Data
public class ArticleDTO {
    private Integer id;
    private String time;
    private String title;
    private String content;
    private String author;
    private String pictureUrl;

    public static ArticleDTO convertToArticleDTO(Article article) {
        ArticleDTO articleDTO = new ArticleDTO();
        articleDTO.setId(article.getId());
        articleDTO.setTitle(article.getTitle());
        articleDTO.setContent(article.getContent());
        articleDTO.setAuthor(article.getAuthor());
        articleDTO.setPictureUrl(article.getPictureUrl());
        String time = TimeUtil.formatTime(article.getTime());
        articleDTO.setTime(time);
        return articleDTO;
    }
}
