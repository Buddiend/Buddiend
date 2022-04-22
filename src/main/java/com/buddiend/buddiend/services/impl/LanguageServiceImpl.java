package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.Language;
import com.buddiend.buddiend.repositories.LanguageRepository;
import com.buddiend.buddiend.services.LanguageService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {

    private final LanguageRepository languageRepository;

    public LanguageServiceImpl(LanguageRepository languageRepository) {
        this.languageRepository = languageRepository;
    }

    @Override
    public List<Language> findAll() {
        return this.languageRepository.findAll();
    }

    @Override
    public Optional<Language> findById(Long id) {
        return this.languageRepository.findById(id);
    }

    @Override
    public Optional<Language> findByName(String name) {
        return Optional.empty();
    }
}
