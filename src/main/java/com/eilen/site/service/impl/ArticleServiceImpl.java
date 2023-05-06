package com.eilen.site.service.impl;

import com.eilen.site.entity.Article;
import com.eilen.site.mapper.ArticleMapper;
import com.eilen.site.service.IArticleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author eilen
 * @since 2023-05-05 04:47:55
 */
@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements IArticleService {

}
