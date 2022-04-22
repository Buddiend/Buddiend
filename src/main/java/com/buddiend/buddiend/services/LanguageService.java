package com.buddiend.buddiend.services;

import com.buddiend.buddiend.models.Language;

import java.util.*;
import java.util.Optional;

public interface LanguageService {

    List<Language> findAll();
    Optional<Language> findById(Long id);
    Optional<Language> findByName(String name);

}
