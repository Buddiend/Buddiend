package com.buddiend.buddiend.services.impl;

import com.buddiend.buddiend.models.Language;
import com.buddiend.buddiend.services.LanguageService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class LanguageServiceImpl implements LanguageService {

    @Override
    public Optional<Language> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<Language> findByName(String name) {
        return Optional.empty();
    }
}
