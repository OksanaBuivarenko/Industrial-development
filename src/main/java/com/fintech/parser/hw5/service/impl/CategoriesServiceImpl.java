package com.fintech.parser.hw5.service.impl;

import com.fintech.parser.hw5.dto.request.CategoriesRq;
import com.fintech.parser.hw5.dto.responce.CategoriesRs;
import com.fintech.parser.hw5.dto.responce.DeleteRs;
import com.fintech.parser.hw5.exseption.ObjectAlreadyExistsException;
import com.fintech.parser.hw5.exseption.ObjectNotFoundException;
import com.fintech.parser.hw5.mapper.CategoriesMapper;
import com.fintech.parser.hw5.model.Categories;
import com.fintech.parser.hw5.repository.CategoriesRepository;
import com.fintech.parser.hw5.service.CategoriesService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CategoriesServiceImpl implements CategoriesService {

    private final CategoriesRepository categoriesRepository;

    @Autowired
    @Lazy
    public CategoriesServiceImpl(CategoriesRepository categoriesRepository) {
        this.categoriesRepository = categoriesRepository;
    }

    @Override
    public List<Categories> getCategories() {
        return categoriesRepository.findAll();
    }

    @Override
    public List<CategoriesRs> getCategoriesRs() {
        return categoriesRepository.findAll().stream()
                .map(CategoriesMapper.INSTANCE::toDto).collect(Collectors.toList());
    }

    @Override
    public Categories getCategoryById(Long id) {
        return categoriesRepository.findById(id).orElseThrow(()
                -> new ObjectNotFoundException("categories", id.toString()));
    }

    @Override
    public CategoriesRs getCategoryRsById(Long id) {
        return CategoriesMapper.INSTANCE.toDto(getCategoryById(id));
    }

    @Override
    public CategoriesRs createCategory(CategoriesRq categoriesRq) {
        for (Categories el: getCategories()) {
            if(el.getSlug().equals(categoriesRq.getSlug())) {
                throw new ObjectAlreadyExistsException("Categories with slug " + el.getSlug() + " already exists");
            }
            if(el.getName().equals(categoriesRq.getName())) {
                throw new ObjectAlreadyExistsException("Categories with name " + el.getName() + " already exists");
            }
        }
        categoriesRepository.incrementMaxId();
        Categories categories = CategoriesMapper.INSTANCE.toEntity(categoriesRq, categoriesRepository.getMaxId());
        categoriesRepository.save(categories.getId(), categories);
        log.info("Categories with id " + categories.getId() + " create successfully");
        return CategoriesMapper.INSTANCE.toDto(categories);

    }

    @Override
    public CategoriesRs updateCategory(Long id, CategoriesRq categoriesRq) {
        Categories categories = getCategoryById(id);
        if (!categoriesRq.getName().isEmpty() && !categoriesRq.getName().equals(categories.getName())) {
            categories.setName(categoriesRq.getName());
            log.debug("Categories with id " + id + " update name");
        }
        if (!categoriesRq.getSlug().isEmpty()&& !categoriesRq.getSlug().equals(categories.getSlug())) {
            categories.setSlug(categoriesRq.getSlug());
            log.debug("Categories with id " + id + " update slug");
        }
        return CategoriesMapper.INSTANCE.toDto(categoriesRepository.update(categories.getId(), categories));
    }

    @Override
    public DeleteRs deleteCategory(Long id) {
        if (categoriesRepository.containsId(id)) {
            categoriesRepository.delete(id);
            log.info("Categories with id " + id + " delete successfully");
            return DeleteRs.builder().message("Categories with id " + id + " delete successfully").build();

        } else {
            log.warn("Unable to delete categories. Storage don't contains categories with id " + id);
            return DeleteRs.builder().message("Storage don't contains categories with id " + id).build();
        }
    }
}