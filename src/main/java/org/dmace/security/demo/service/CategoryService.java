package org.dmace.security.demo.service;

import org.dmace.security.demo.model.Category;
import org.dmace.security.demo.repo.CategoryRepository;
import org.dmace.security.demo.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class CategoryService extends BaseService<Category, Long, CategoryRepository> {
}
