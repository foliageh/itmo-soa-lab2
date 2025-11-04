package com.foliageh.itmosoalab2.bean;

import com.foliageh.itmosoalab2.remote.AsyncTaskServiceRemote;
import com.foliageh.itmosoalab2.repository.FlatRepository;
import jakarta.ejb.EJB;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotAllowedException;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Stateless
public class AsyncTaskServiceBean implements AsyncTaskServiceRemote, Serializable {
    
    private CompletableFuture<List<Double>> currentTask = null;
    private final ExecutorService executorService = Executors.newCachedThreadPool();
    
    @Inject
    private FlatRepository flatRepository;

    @Override
    public boolean launchUniqueLivingSpacesJob() {
        if (currentTask != null && !currentTask.isDone())
            return false;
        
        currentTask = CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(5000);
                return flatRepository.findUniqueLivingSpaces();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Задача была прервана", e);
            }
        }, executorService);
        
        return true;
    }

    @Override
    public boolean cancelUniqueLivingSpacesJob() {
        if (currentTask == null || currentTask.isDone())
            return false;
        currentTask.cancel(true);
        currentTask = null;
        return true;
    }

    @Override
    public List<Double> getTaskResult() {
        if (currentTask == null)
            return null;
        if (!currentTask.isDone())
            throw new NotAllowedException("Задача ещё выполняется");
        try {
            return currentTask.get();
        } catch (Exception e) {
            throw new RuntimeException("Ошибки при получении результата задачи: " + e.getMessage());
        }
    }

    @Override
    public boolean isTaskInProgress() {
        return currentTask != null && !currentTask.isDone();
    }

    @Override
    public boolean isTaskCompleted() {
        return currentTask != null && currentTask.isDone();
    }
}