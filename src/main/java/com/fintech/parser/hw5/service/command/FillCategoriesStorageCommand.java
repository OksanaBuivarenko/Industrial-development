package com.fintech.parser.hw5.service.command;

import com.fintech.parser.hw5.service.observer.FillStoragesWithObserver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FillCategoriesStorageCommand implements Command{

    private final FillStoragesWithObserver fillStoragesWithObserver;

    @Override
    public void execute() {
        fillStoragesWithObserver.fillCategoryStorage();
    }
}